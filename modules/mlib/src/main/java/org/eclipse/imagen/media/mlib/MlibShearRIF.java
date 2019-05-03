/*
 * Copyright (c) [2019,] 2019, Oracle and/or its affiliates. All rights reserved.
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
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.awt.image.renderable.RenderedImageFactory;
import org.eclipse.imagen.EnumeratedParameter;
import org.eclipse.imagen.BorderExtender;
import org.eclipse.imagen.ImageLayout;
import org.eclipse.imagen.Interpolation;
import org.eclipse.imagen.InterpolationBicubic2;
import org.eclipse.imagen.InterpolationBicubic;
import org.eclipse.imagen.InterpolationBilinear;
import org.eclipse.imagen.InterpolationNearest;
import org.eclipse.imagen.InterpolationTable;
import java.util.Map;
import org.eclipse.imagen.BorderExtender;
import org.eclipse.imagen.operator.ShearDescriptor;
import org.eclipse.imagen.media.opimage.RIFUtil;

/**
 * A <code>RIF</code> supporting the "Shear" operation in the
 * rendered image mode using MediaLib.
 *
 * @see org.eclipse.imagen.operator.ShearDescriptor
 * @see MlibAffineOpImage
 */
public class MlibShearRIF implements RenderedImageFactory {

    /** Constructor. */
    public MlibShearRIF() {}

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

        Interpolation interp = (Interpolation)args.getObjectParameter(4);

        RenderedImage source = args.getRenderedSource(0);

        if (!MediaLibAccessor.isMediaLibCompatible(args, layout) ||
            !MediaLibAccessor.hasSameNumBands(args, layout) ||
	    // Medialib cannot deal with source image having tiles with any
	    // dimension greater than or equal to 32768
	    source.getTileWidth() >= 32768 ||
	    source.getTileHeight() >= 32768) {
            return null;
        }

        /* Get BorderExtender from hints if any. */
        BorderExtender extender = RIFUtil.getBorderExtenderHint(hints);

        float shear_amt = args.getFloatParameter(0);
        EnumeratedParameter shear_dir =
            (EnumeratedParameter)args.getObjectParameter(1);
        float xTrans = args.getFloatParameter(2);
        float yTrans = args.getFloatParameter(3);
        double[] backgroundValues = (double[])args.getObjectParameter(5);

        // Create the affine transform
        AffineTransform tr = new AffineTransform();

        if (shear_dir.equals(ShearDescriptor.SHEAR_HORIZONTAL)) {
            // SHEAR_HORIZONTAL
            tr.setTransform(1.0, 0.0, shear_amt, 1.0, xTrans, 0.0);
        } else {
            // SHEAR_VERTICAL
            tr.setTransform(1.0, shear_amt, 0.0, 1.0, 0.0, yTrans);
        }

        if (interp instanceof InterpolationNearest) {
            return new MlibAffineNearestOpImage(source, extender,
                                                hints, layout,
                                                tr,
                                                interp,
                                                backgroundValues);
        } else if (interp instanceof InterpolationBilinear) {
            return new MlibAffineBilinearOpImage(source,
                                                 extender, hints, layout,
                                                 tr,
                                                 interp,
                                                 backgroundValues);
        } else if (interp instanceof InterpolationBicubic ||
                   interp instanceof InterpolationBicubic2) {
            return new MlibAffineBicubicOpImage(source,
                                                extender, hints, layout,
                                                tr,
                                                interp,
                                                backgroundValues);
        } else if (interp instanceof InterpolationTable) {
            return new MlibAffineTableOpImage(source,
                                              extender, hints, layout,
                                              tr,
                                              interp,
                                              backgroundValues);
        } else {
           return null;
        }
    }
}
