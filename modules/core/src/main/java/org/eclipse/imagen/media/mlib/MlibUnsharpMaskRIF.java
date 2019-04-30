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

import org.eclipse.imagen.media.opimage.RIFUtil;
import org.eclipse.imagen.media.util.ImageUtil;
import java.awt.RenderingHints;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.awt.image.renderable.RenderedImageFactory;
import org.eclipse.imagen.BorderExtender;
import org.eclipse.imagen.ImageLayout;
import org.eclipse.imagen.KernelJAI;
import java.util.Map;

/**
 * A <code>RIF</code> supporting the "Convolve" operation in the
 * rendered image mode using MediaLib.
 *
 * @see org.eclipse.imagen.operator.ConvolveDescriptor
 * @see MlibConvolveOpImage
 */
public class MlibUnsharpMaskRIF implements RenderedImageFactory {

    /** Constructor. */
    public MlibUnsharpMaskRIF() {}

    /**
     * Creates a new instance of <code>MlibConvolveOpImage</code> in
     * the rendered image mode.
     *
     * @param args  The source image and convolution kernel.
     * @param hints  May contain rendering hints and destination image layout.
     */
    public RenderedImage create(ParameterBlock args,
                                RenderingHints hints) {

        /* Get ImageLayout and TileCache from RenderingHints. */
        ImageLayout layout = RIFUtil.getImageLayoutHint(hints);
        

        if (!MediaLibAccessor.isMediaLibCompatible(args, layout) ||
            !MediaLibAccessor.hasSameNumBands(args, layout)) {
            return null;
        }

        /* Get BorderExtender from hints if any. */
        BorderExtender extender = RIFUtil.getBorderExtenderHint(hints);

        RenderedImage source = args.getRenderedSource(0);

	// map the input kernel + gain factor to an equivalent
	// convolution kernel and then do a normal convolve.
	KernelJAI unRotatedKernel =
		ImageUtil.getUnsharpMaskEquivalentKernel(
			(KernelJAI)args.getObjectParameter(0),
			args.getFloatParameter(1));

        KernelJAI kJAI = unRotatedKernel.getRotatedKernel();

        int kWidth = kJAI.getWidth();
        int kHeight = kJAI.getHeight();

        /* mediaLib does not handle kernels with either dimension < 2. */
        if (kWidth < 2 || kHeight < 2) {
            return null;
        }

        if (kJAI.isSeparable() && kWidth >= 3 && kWidth <= 7 &&  kWidth == kHeight) {
            return new MlibSeparableConvolveOpImage(source,
                                                    extender, hints, layout,
                                                    kJAI);
        } else if ((kWidth == 3 && kHeight == 3) ||
                   (kWidth == 5 && kHeight == 5)) {
            return new MlibConvolveNxNOpImage(source,
                                              extender, hints, layout,
                                              kJAI);
        } else {
            return new MlibConvolveOpImage(source,
                                           extender, hints, layout,
                                           kJAI);
        }
    }
}
