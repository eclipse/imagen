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
import java.awt.image.DataBuffer;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.awt.image.renderable.RenderedImageFactory;
import org.eclipse.imagen.ImageLayout;
import org.eclipse.imagen.LookupTableJAI;
import java.util.Map;
import org.eclipse.imagen.media.opimage.RIFUtil;

/**
 * A <code>RIF</code> supporting the "Lookup" operation in the
 * rendered image mode using MediaLib.
 *
 * @see org.eclipse.imagen.operator.LookupDescriptor
 * @see MlibLookupOpImage
 *
 */
public class MlibLookupRIF implements RenderedImageFactory {

    /** Constructor. */
    public MlibLookupRIF() {}

    /**
     * Creates a new instance of <code>MlibLookupOpImage</code> in
     * the rendered image mode.
     *
     * @param args  The source image and lookup table.
     * @param hints  May contain rendering hints and destination image layout.
     */
    public RenderedImage create(ParameterBlock args,
                                RenderingHints hints) {
        /* Get ImageLayout and TileCache from RenderingHints. */
        ImageLayout layout = RIFUtil.getImageLayoutHint(hints);
        

        if (!MediaLibAccessor.isMediaLibCompatible(args)) {
            return null;
        }

        /* The table should be less than or equal to 4 bands. */
        LookupTableJAI table = (LookupTableJAI)args.getObjectParameter(0);
        if (table.getNumBands() > 4 ||
            table.getDataType() == DataBuffer.TYPE_USHORT) {
            return null;
        }

        return new MlibLookupOpImage(args.getRenderedSource(0),
                                     hints, layout,
                                     table);
    }
}
