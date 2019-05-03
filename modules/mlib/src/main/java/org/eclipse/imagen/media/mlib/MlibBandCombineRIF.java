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
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.awt.image.renderable.RenderedImageFactory;
import org.eclipse.imagen.ImageLayout;
import java.util.Map;
import org.eclipse.imagen.media.opimage.RIFUtil;

/**
 * A <code>RIF</code> supporting the "BandCombine" operation in the
 * rendered image mode using MediaLib.
 *
 * @see org.eclipse.imagen.operator.BandCombineDescriptor
 * @see MlibBandCombineOpImage
 *
 */
public class MlibBandCombineRIF implements RenderedImageFactory {

    /** Constructor. */
    public MlibBandCombineRIF() {}

    /**
     * Creates a new instance of <code>MlibBandCombineOpImage</code> in
     * the rendered image mode.
     *
     * @param args  The source image and the matrix.
     * @param hints  May contain rendering hints and destination image layout.
     */
    public RenderedImage create(ParameterBlock args,
                                RenderingHints hints) {
        // Get ImageLayout and TileCache from RenderingHints.
        ImageLayout layout = RIFUtil.getImageLayoutHint(hints);
        

        if (!MediaLibAccessor.isMediaLibCompatible(args, layout)) {
            return null;
        }

        // Fall back to Java code if the matrix is not 3-by-4.
        double[][] matrix = (double[][])args.getObjectParameter(0);
        if(matrix.length != 3) {
            return null;
        }
        for(int i = 0; i < 3; i++) {
            if(matrix[i].length != 4) {
                return null;
            }
        }

	return new MlibBandCombineOpImage(args.getRenderedSource(0),
                                          hints, layout,
                                          matrix);
    }
}
