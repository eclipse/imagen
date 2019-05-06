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
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.awt.image.renderable.RenderedImageFactory;
import org.eclipse.imagen.ImageLayout;
import java.util.Map;
import org.eclipse.imagen.media.opimage.RIFUtil;

/**
 * A <code>RIF</code> supporting the "DivideByConst" operation in the
 * rendered image mode using MediaLib.
 *
 * @see org.eclipse.imagen.operator.DivideByConstDescriptor
 * @see MlibMultiplyConstOpImage
 *
 */
public class MlibDivideByConstRIF implements RenderedImageFactory {

    /** Constructor. */
    public MlibDivideByConstRIF() {}

    /**
     * Creates a new instance of <code>MlibMultiplyConstOpImage</code> in
     * the rendered image mode.  By inverting the constants, the result
     * of "DivideByConst" is obtained.
     *
     * @param args  The source image and the constants.
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

        /* Negate the constants vector. */
        double[] constants = (double[])args.getObjectParameter(0);
        int length = constants.length;

        double[] invConstants = new double[length];

        for (int i = 0; i < length; i++) {
            invConstants[i] = 1.0D / constants[i];
        }

	return new MlibMultiplyConstOpImage(args.getRenderedSource(0),
                                            hints, layout,
                                            invConstants);
    }
}
