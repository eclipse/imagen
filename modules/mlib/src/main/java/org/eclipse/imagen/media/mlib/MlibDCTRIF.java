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
import org.eclipse.imagen.media.opimage.DCTOpImage;
import org.eclipse.imagen.media.opimage.FCT;
import org.eclipse.imagen.media.opimage.RIFUtil;

/**
 * A <code>RIF</code> supporting the "DCT" operation in the
 * rendered image mode using MediaLib.
 *
 * @see org.eclipse.imagen.operator.DCTDescriptor
 * @see org.eclipse.imagen.media.opimage.DCTOpImage
 *
 * @since EA4
 *
 */
public class MlibDCTRIF implements RenderedImageFactory {

    /** Constructor. */
    public MlibDCTRIF() {}

    /**
     * Creates a new instance of <code>DCTOpImage</code> in
     * the rendered image mode.
     *
     * @param args  The source image.
     * @param hints  May contain rendering hints and destination image layout.
     */
    public RenderedImage create(ParameterBlock args,
                                RenderingHints hints) {
        /* Get ImageLayout and TileCache from RenderingHints. */
        ImageLayout layout = RIFUtil.getImageLayoutHint(hints);
        

        if (!MediaLibAccessor.isMediaLibCompatible(new ParameterBlock())) {
            return null;
        }

        return new DCTOpImage(args.getRenderedSource(0),
                              hints, layout,
                              new FCTmediaLib(true, 2));
    }
}
