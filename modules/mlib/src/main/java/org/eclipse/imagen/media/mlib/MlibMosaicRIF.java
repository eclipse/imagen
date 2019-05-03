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
import java.awt.image.DataBuffer;
import java.awt.image.RenderedImage;
import java.awt.image.SampleModel;
import java.awt.image.renderable.ParameterBlock;
import java.awt.image.renderable.RenderedImageFactory;
import java.util.Vector;
import org.eclipse.imagen.ImageLayout;
import org.eclipse.imagen.PlanarImage;
import org.eclipse.imagen.ROI;
import org.eclipse.imagen.operator.MosaicType;
import org.eclipse.imagen.media.opimage.RIFUtil;

/**
 * A <code>RIF</code> supporting the "Mosaic" operation in the rendered
 * image layer.
 *
 * @since JAI 1.1.2
 * @see org.eclipse.imagen.operator.MosaicDescriptor
 */
public class MlibMosaicRIF implements RenderedImageFactory {

    /** Constructor. */
    public MlibMosaicRIF() {}

    /**
     * Renders a "Mosaic" operation node.
     */
    public RenderedImage create(ParameterBlock paramBlock,
                                RenderingHints renderHints) {
        // Get ImageLayout from renderHints if any.
        ImageLayout layout = RIFUtil.getImageLayoutHint(renderHints);

        // Return if not mediaLib-compatible.
        if(!MediaLibAccessor.isMediaLibCompatible(paramBlock, layout) ||
           !MediaLibAccessor.hasSameNumBands(paramBlock, layout)) {
            return null;
        }

        // Get sources.
        Vector sources = paramBlock.getSources();

        // Get target SampleModel.
        SampleModel targetSM = null;
        if(sources.size() > 0) {
            targetSM = ((RenderedImage)sources.get(0)).getSampleModel();
        } else if(layout != null &&
                  layout.isValid(ImageLayout.SAMPLE_MODEL_MASK)) {
            targetSM = layout.getSampleModel(null);
        }

        if(targetSM != null) {
            // Return if target data type is floating point. Other more
            // extensive type checking is done in MosaicOpImage constructor.
            int dataType = targetSM.getDataType();
            if(dataType == DataBuffer.TYPE_FLOAT ||
               dataType == DataBuffer.TYPE_DOUBLE) {
                return null;
            }
        }

        return
            new MlibMosaicOpImage(sources,
                                  layout,
                                  renderHints,
                                  (MosaicType)paramBlock.getObjectParameter(0),
                                  (PlanarImage[])paramBlock.getObjectParameter(1),
                                  (ROI[])paramBlock.getObjectParameter(2),
                                  (double[][])paramBlock.getObjectParameter(3),
                                  (double[])paramBlock.getObjectParameter(4));
    }
}
