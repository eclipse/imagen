/*
 * Copyright (c) 2019, Oracle and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.eclipse.imagen.media.mlib;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.DataBuffer;
import java.awt.image.MultiPixelPackedSampleModel;
import java.awt.image.RenderedImage;
import java.awt.image.SampleModel;
import java.awt.image.renderable.ParameterBlock;
import java.awt.image.renderable.RenderedImageFactory;
import org.eclipse.imagen.BorderExtender;
import org.eclipse.imagen.ImageLayout;
import org.eclipse.imagen.Interpolation;
import org.eclipse.imagen.InterpolationBicubic2;
import org.eclipse.imagen.InterpolationBicubic;
import org.eclipse.imagen.InterpolationBilinear;
import org.eclipse.imagen.InterpolationNearest;
import org.eclipse.imagen.InterpolationTable;
import java.util.Map;
import org.eclipse.imagen.media.opimage.RIFUtil;
import org.eclipse.imagen.media.opimage.TranslateIntOpImage;

/**
 * A <code>RIF</code> supporting the "Affine" operation in the
 * rendered image mode using MediaLib.
 *
 * @see org.eclipse.imagen.operator.AffineDescriptor
 * @see MlibAffineOpImage
 * @see MlibScaleOpImage
 *
 * @since EA4
 */
public class MlibAffineRIF implements RenderedImageFactory {

    private static final float TOLERANCE = 0.01F;

    /** Constructor. */
    public MlibAffineRIF() {}

    /**
     * Creates a new instance of <code>MlibAffineOpImage</code> in
     * the rendered image mode.
     *
     * @param args  The source image, the <code>AffineTransform</code>,
     *              and the <code>Interpolation</code>.
     * @param hints  May contain rendering hints and destination image layout.
     */
    public RenderedImage create(ParameterBlock args,
                                RenderingHints hints) {
        /* Get ImageLayout and TileCache from RenderingHints. */
        ImageLayout layout = RIFUtil.getImageLayoutHint(hints);

        // Get operation parameters.
        AffineTransform transform =
            (AffineTransform)args.getObjectParameter(0);
        Interpolation interp = (Interpolation)args.getObjectParameter(1);
        double[] backgroundValues = (double[])args.getObjectParameter(2);

        RenderedImage source = args.getRenderedSource(0);
	
        if (!MediaLibAccessor.isMediaLibCompatible(args, layout) ||
            !MediaLibAccessor.hasSameNumBands(args, layout) ||
	    // Medialib cannot deal with source image having tiles with any
	    // dimension greater than or equal to 32768
	    source.getTileWidth() >= 32768 || 
	    source.getTileHeight() >= 32768) {
            return null;
        }

        SampleModel sm = source.getSampleModel();
        boolean isBilevel = (sm instanceof MultiPixelPackedSampleModel) &&
            (sm.getSampleSize(0) == 1) &&
            (sm.getDataType() == DataBuffer.TYPE_BYTE ||
             sm.getDataType() == DataBuffer.TYPE_USHORT ||
             sm.getDataType() == DataBuffer.TYPE_INT);
        if (isBilevel) {
            // Let Java code handle it, reformatting is slower
            return null;
        }

        // Get BorderExtender from hints if any.
        BorderExtender extender = RIFUtil.getBorderExtenderHint(hints);

        /* Get the affine transform. */
        double[] tr = new double[6];
        transform.getMatrix(tr);

        /*
         * Check and see if the affine transform is doing a copy.
         * If so call the copy operation.
         */
        if ((tr[0] == 1.0) &&
            (tr[3] == 1.0) &&
            (tr[2] == 0.0) &&
            (tr[1] == 0.0) &&
            (tr[4] == 0.0) &&
            (tr[5] == 0.0)) {
            /* It's a copy. */
            return new MlibCopyOpImage(source, hints, layout);
        }

        /*
         * Check and see if the affine transform is in fact doing
         * a Translate operation. That is a scale by 1 and no rotation.
         * In which case call translate. Note that only integer translate
         * is applicable. For non-integer translate we'll have to do the
         * affine.
         */
        if ((tr[0] == 1.0) &&
            (tr[3] == 1.0) &&
            (tr[2] == 0.0) &&
            (tr[1] == 0.0) &&
            (Math.abs(tr[4] - (int) tr[4]) < TOLERANCE) &&
            (Math.abs(tr[5] - (int) tr[5]) < TOLERANCE) &&
	    layout == null) { // TranslateIntOpImage can't deal with ImageLayout hint
            /* It's a integer translate. */
            return new TranslateIntOpImage(source,
					   hints,
                                           (int)tr[4],
                                           (int)tr[5]);
        }

        /*
         * Check and see if the affine transform is in fact doing
         * a Scale operation. In which case call Scale which is more
         * optimized than Affine.
         */
        if ((tr[0] > 0.0) &&
            (tr[2] == 0.0) &&
            (tr[1] == 0.0) &&
            (tr[3] > 0.0)) {
            /* It's a scale. */
            if (interp instanceof InterpolationNearest) {
                return new MlibScaleNearestOpImage(source,
						   extender,
                                                   hints,
                                                   layout,
                                                   (float)tr[0], // xScale
                                                   (float)tr[3], // yScale
                                                   (float)tr[4], // xTrans
                                                   (float)tr[5], // yTrans
                                                   interp);
            } else if (interp instanceof InterpolationBilinear) {
                return new MlibScaleBilinearOpImage(source,
                                                    extender,
                                                    hints,
                                                    layout,
                                                    (float)tr[0], // xScale
                                                    (float)tr[3], // yScale
                                                    (float)tr[4], // xTrans
                                                    (float)tr[5], // yTrans
                                                    interp);
            } else if (interp instanceof InterpolationBicubic ||
                       interp instanceof InterpolationBicubic2) {
                return new MlibScaleBicubicOpImage(source,
                                                   extender,
                                                   hints,
                                                   layout,
                                                   (float)tr[0], // xScale
                                                   (float)tr[3], // yScale
                                                   (float)tr[4], // xTrans
                                                   (float)tr[5], // yTrans
                                                   interp);
            } else if (interp instanceof InterpolationTable) {
                return new MlibScaleTableOpImage(source,
                                                 extender,
                                                 hints,
                                                 layout,
                                                 (float)tr[0], // xScale
                                                 (float)tr[3], // yScale
                                                 (float)tr[4], // xTrans
                                                 (float)tr[5], // yTrans
                                                 interp);
            } else {
                return null;
            }
        }

        /* Have to do an Affine. */
        if (interp instanceof InterpolationNearest) {
            return new MlibAffineNearestOpImage(source,
						extender,
                                                hints,
                                                layout,
                                                transform,
                                                interp,
                                                backgroundValues);
        } else if (interp instanceof InterpolationBilinear) {
            return new MlibAffineBilinearOpImage(source,
                                                 extender,
                                                 hints,
                                                 layout,
                                                 transform,
                                                 interp,
                                                 backgroundValues);
        } else if (interp instanceof InterpolationBicubic ||
                   interp instanceof InterpolationBicubic2) {
            return new MlibAffineBicubicOpImage(source,
                                                extender,
                                                hints,
                                                layout,
                                                transform,
                                                interp,
                                                backgroundValues);
        } else if (interp instanceof InterpolationTable) {
            return new MlibAffineTableOpImage(source,
                                              extender,
                                              hints,
                                              layout,
                                              transform,
                                              interp,
                                              backgroundValues);
        } else {
            return null;
        }
    }
}
