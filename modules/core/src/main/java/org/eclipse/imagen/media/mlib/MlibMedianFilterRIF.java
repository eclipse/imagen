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
import org.eclipse.imagen.BorderExtender;
import org.eclipse.imagen.ImageLayout;
import org.eclipse.imagen.KernelJAI;
import java.util.Map;
import org.eclipse.imagen.operator.MedianFilterDescriptor;
import org.eclipse.imagen.operator.MedianFilterShape;
import org.eclipse.imagen.media.opimage.RIFUtil;

/**
 *  Creates a MlibMedianFilterOpImage subclass for the given input
 *  mask type
 *  @see MlibMedianFilterOpImage
 */
public class MlibMedianFilterRIF implements RenderedImageFactory {

    /** Constructor. */
    public MlibMedianFilterRIF() {}

    /**
     * Create a new instance of MlibMedianFilterOpImage in the rendered layer.
     * This method satisfies the implementation of RIF.
     *
     * @param paramBlock  The source image and the convolution kernel.
     */
    public RenderedImage create(ParameterBlock paramBlock,
                                RenderingHints renderHints) {
        // Get ImageLayout from renderHints if any.
        ImageLayout layout = RIFUtil.getImageLayoutHint(renderHints);
        

        if (!MediaLibAccessor.isMediaLibCompatible(paramBlock, layout) ||
            !MediaLibAccessor.hasSameNumBands(paramBlock, layout)) {
            return null;
        }

        // Get BorderExtender from renderHints if any.
        BorderExtender extender = RIFUtil.getBorderExtenderHint(renderHints);

        MedianFilterShape maskType =
            (MedianFilterShape)paramBlock.getObjectParameter(0);
        int maskSize = paramBlock.getIntParameter(1);
        RenderedImage ri = paramBlock.getRenderedSource(0);
        
        return new MlibMedianFilterOpImage(ri,
                                           extender,
                                           renderHints,
                                           layout,
                                           maskType,
                                           maskSize);
    }
}
