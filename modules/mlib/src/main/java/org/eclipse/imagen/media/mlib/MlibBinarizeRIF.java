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
import java.awt.image.DataBuffer;
import java.awt.image.MultiPixelPackedSampleModel;
import java.awt.image.RenderedImage;
import java.awt.image.SampleModel;
import java.awt.image.renderable.RenderedImageFactory;
import java.awt.image.renderable.ParameterBlock;
import org.eclipse.imagen.BorderExtender;
import org.eclipse.imagen.ImageLayout;
import java.util.Map;
import org.eclipse.imagen.media.opimage.RIFUtil;

/**
 * A <code>RIF</code> supporting the "Binarize" operation in the
 * rendered image mode using MediaLib.
 *
 * @see org.eclipse.imagen.operator.BinarizeDescriptor
 */
public class MlibBinarizeRIF implements RenderedImageFactory {

    /** Constructor. */
    public MlibBinarizeRIF() {}

    /**
     * Creates a new instance of <code>MlibBinarizeOpImage</code> in
     * the rendered image mode.
     *
     * @param args  The source image, thresh value
     * @param hints  May contain rendering hints and destination image layout.
     */
    public RenderedImage create(ParameterBlock args,
                                RenderingHints hints) {
        // Get the source and its SampleModel.
	RenderedImage source = args.getRenderedSource(0);
        SampleModel sm = source.getSampleModel();

        // Check that the source is single-banded and mediaLib compatible.
        // Ignore the layout because if it doesn't specify a bilevel image
        // then MlibBinarizeOpImage will revise it.
        if (!MediaLibAccessor.isMediaLibCompatible(args) ||
            sm.getNumBands() > 1) {
            return null;
        }

        // Get the threshold value.
        double thresh = args.getDoubleParameter(0);

	// java set all 0's or 1's fast
	if ((thresh > 255|| thresh <=0) && sm.getDataType()== DataBuffer.TYPE_BYTE ||
	    (thresh > Short.MAX_VALUE|| thresh <=0) && sm.getDataType()== DataBuffer.TYPE_SHORT||
	    (thresh > Integer.MAX_VALUE|| thresh <=0) && sm.getDataType()== DataBuffer.TYPE_INT)
	        return null;

        // Get ImageLayout from RenderingHints.
        ImageLayout layout = RIFUtil.getImageLayoutHint(hints);
        
	return new MlibBinarizeOpImage(source,
				       layout,
				       hints,
				       thresh);
    }
}
