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
import java.awt.image.renderable.RenderedImageFactory;
import java.awt.image.renderable.ParameterBlock;
import org.eclipse.imagen.ImageLayout;
import org.eclipse.imagen.media.opimage.RIFUtil;

/**
 * A <code>RIF</code> supporting the "SubsampleAverage" operation in the
 * rendered image mode using MediaLib.
 *
 * @see org.eclipse.imagen.operator.SubsampleaverageDescriptor
 * @see MlibSubsampleAverageOpImage

 */
public class MlibSubsampleAverageRIF implements RenderedImageFactory {

    /** Constructor. */
    public MlibSubsampleAverageRIF() {}

    /**
     * Creates a new instance of <code>MlibSubsampleAverageOpImage</code> in
     * the rendered image mode.
     *
     * @param args  The source image, scale factors,
     *              and the <code>Interpolation</code>.
     * @param hints  May contain rendering hints and destination image layout.
     */
    public RenderedImage create(ParameterBlock args,
                                RenderingHints hints) {
        // Get the scale factors.
        double scaleX = args.getDoubleParameter(0);
        double scaleY = args.getDoubleParameter(1);

        // If unity scaling return the source directly.
        if(scaleX == 1.0 && scaleY == 1.0) {
            return args.getRenderedSource(0);
        }

        // Get the layout.
        ImageLayout layout = RIFUtil.getImageLayoutHint(hints);

        // Check mediaLib compatibility.
        if (!MediaLibAccessor.isMediaLibCompatible(args, layout) ||
            !MediaLibAccessor.hasSameNumBands(args, layout)) {
            // Return null to indicate to fallback to next RIF.
            return null;
        }

        // Create and return the OpImage.
        return new MlibSubsampleAverageOpImage(args.getRenderedSource(0),
                                               layout, hints,
                                               scaleX, scaleY);
    }
}
