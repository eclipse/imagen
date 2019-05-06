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
import org.eclipse.imagen.ROI;
import org.eclipse.imagen.media.opimage.RIFUtil;

/**
 * A <code>RIF</code> supporting the "Mean" operation in the
 * rendered image mode using MediaLib.
 *
 * @see org.eclipse.imagen.operator.MeanDescriptor
 * @see MlibMeanOpImage
 *
 * @since EA3
 *
 */
public class MlibMeanRIF implements RenderedImageFactory {

    /** Constructor. */
    public MlibMeanRIF() {}

    /**
     * Creates a new instance of <code>MlibMeanOpImage</code> in
     * the rendered image mode.
     *
     * @param args  The source image and the parameters.
     * @param hints  Rendering hints are ignored.
     */
    public RenderedImage create(ParameterBlock args,
                                RenderingHints hints) {
        if (!MediaLibAccessor.isMediaLibCompatible(args)) {
            return null;
        }

	RenderedImage source = args.getRenderedSource(0);
	ROI roi = (ROI)args.getObjectParameter(0);
        int xPeriod = args.getIntParameter(1);
        int yPeriod = args.getIntParameter(2);
	
        int xStart = source.getMinX();	// default values
        int yStart = source.getMinY();

        int maxWidth = source.getWidth();
        int maxHeight = source.getHeight();

        if (roi != null &&
            !roi.contains(xStart, yStart, maxWidth, maxHeight)) {
            return null;
        }

	// mediaLib supports only a sampling period of 1
	if ((xPeriod != 1) || (yPeriod != 1)) {
	    return null;
	}

        return new MlibMeanOpImage(source,
				   roi,
				   xStart, yStart,
				   xPeriod, yPeriod);
    }
}
