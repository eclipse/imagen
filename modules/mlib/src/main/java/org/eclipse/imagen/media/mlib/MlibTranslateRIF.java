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
import org.eclipse.imagen.BorderExtender;
import org.eclipse.imagen.ImageLayout;
import org.eclipse.imagen.Interpolation;
import org.eclipse.imagen.InterpolationNearest;
import org.eclipse.imagen.InterpolationBilinear;
import org.eclipse.imagen.InterpolationBicubic;
import org.eclipse.imagen.InterpolationBicubic2;
import org.eclipse.imagen.InterpolationTable;
import java.util.Map;
import org.eclipse.imagen.BorderExtender;
import org.eclipse.imagen.media.opimage.RIFUtil;
import org.eclipse.imagen.media.opimage.TranslateIntOpImage;

/**
 * A <code>RIF</code> supporting the "Translate" operation in the
 * rendered image mode using MediaLib.
 *
 * @see org.eclipse.imagen.operator.TranslateDescriptor
 * @see MlibScaleOpImage
 */
public class MlibTranslateRIF implements RenderedImageFactory {

    private static final float TOLERANCE = 0.01F;

    /** Constructor. */
    public MlibTranslateRIF() {}

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
        RenderedImage source = args.getRenderedSource(0);
        float xTrans = args.getFloatParameter(0);
        float yTrans = args.getFloatParameter(1);
        Interpolation interp = (Interpolation) args.getObjectParameter(2);

	/* Get ImageLayout and TileCache from RenderingHints. */
	ImageLayout layout = RIFUtil.getImageLayoutHint(hints);

        if ((Math.abs(xTrans - (int)xTrans) < TOLERANCE) &&
            (Math.abs(yTrans - (int)yTrans) < TOLERANCE) &&
	    layout == null) { // TranslateIntOpImage can't deal with ImageLayout hint
            return new TranslateIntOpImage(source,
					   hints,
                                           (int)xTrans,
                                           (int)yTrans);
        } else {
            
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

            /*
             * Call the Scale operation, since it encapsulates Translate
             * and is better optimized than Affine.
             */
            float xScale = 1.0F;
            float yScale = 1.0F;
            if (interp instanceof InterpolationNearest) {
                return new MlibScaleNearestOpImage(source, extender,
                                                   hints, layout,
                                                   xScale, yScale,
                                                   xTrans, yTrans,
                                                   interp);
            } else if (interp instanceof InterpolationBilinear) {
                return new MlibScaleBilinearOpImage(source,
                                                    extender, hints, layout,
                                                    xScale, yScale,
                                                    xTrans, yTrans,
                                                    interp);
            } else if (interp instanceof InterpolationBicubic ||
                       interp instanceof InterpolationBicubic2) {
                return new MlibScaleBicubicOpImage(source,
                                                   extender, hints, layout,
                                                   xScale, yScale,
                                                   xTrans, yTrans,
                                                   interp);
            } else if (interp instanceof InterpolationTable) {
                return new MlibScaleTableOpImage(source,
                                                 extender, hints, layout,
                                                 xScale, yScale,
                                                 xTrans, yTrans,
                                                 interp);
            } else {
                return null;
            }
        }
    }
}
