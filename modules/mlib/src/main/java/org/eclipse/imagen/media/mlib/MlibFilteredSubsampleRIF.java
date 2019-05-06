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
import java.awt.image.DataBuffer;
import java.awt.image.SampleModel;
import java.awt.image.MultiPixelPackedSampleModel;
import java.awt.image.renderable.RenderedImageFactory;
import java.awt.image.renderable.ParameterBlock;
import java.util.Map;
import org.eclipse.imagen.BorderExtender;
import org.eclipse.imagen.ImageLayout;
import org.eclipse.imagen.Interpolation;
import org.eclipse.imagen.JAI;

/**
 * <p> Class implementing the RIF interface for the MlibFilteredSubsample
 * operator.  An instance of this class should be registered with the
 * OperationRegistry with operation name "FilteredSubsample."
 *
 * <p> This code is very similar to FilteredSubsampleRIF.
 */
public class MlibFilteredSubsampleRIF implements RenderedImageFactory {

    /** Default constructor (there is no input). */
    public MlibFilteredSubsampleRIF() {}

    /**
     * Creates a new instance of SubsampleOpImage in the rendered layer.
     * This method satisfies the implementation of RIF.
     *
     * @param paramBlock  The source image, the X and Y scale factors.
     * @param renderHints RenderingHints.
     */
    public RenderedImage create(ParameterBlock paramBlock,
                                RenderingHints renderHints) {

        BorderExtender extender = renderHints == null ? null :
            (BorderExtender)renderHints.get(JAI.KEY_BORDER_EXTENDER);
        ImageLayout layout = renderHints == null ? null :
            (ImageLayout)renderHints.get(JAI.KEY_IMAGE_LAYOUT);

        // Test for media lib compatibility
        if (!MediaLibAccessor.isMediaLibCompatible(paramBlock, layout) ||
            !MediaLibAccessor.hasSameNumBands(paramBlock, layout)) {
            return null;
        }

        RenderedImage source = paramBlock.getRenderedSource(0);
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

        int scaleX = paramBlock.getIntParameter(0);
        int scaleY = paramBlock.getIntParameter(1);
        float [] qsFilter = (float [])paramBlock.getObjectParameter(2);
        Interpolation interp = (Interpolation)paramBlock.getObjectParameter(3);

        return new MlibFilteredSubsampleOpImage(source, extender, (Map)renderHints, layout,
                                    scaleX, scaleY, qsFilter, interp);
    } // create

} // MlibFilteredSubsampleRIF
