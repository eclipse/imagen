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
import org.eclipse.imagen.Interpolation;
import org.eclipse.imagen.InterpolationNearest;
import java.util.Map;
import org.eclipse.imagen.media.opimage.CopyOpImage;
import org.eclipse.imagen.media.opimage.RIFUtil;
import org.eclipse.imagen.media.util.ImageUtil;

/**
 * A <code>RIF</code> supporting the "SubsampleBinaryToGray" operation in the
 * rendered image mode using MediaLib.
 *
 * @see org.eclipse.imagen.operator.SubsampleBinaryToGrayDescriptor
 */
public class MlibSubsampleBinaryToGrayRIF implements RenderedImageFactory {

    /**
     * The width and height of blocks to be condensed into one gray pixel.
     * They are expected to be computed in the same way as in 
     * import org.eclipse.imagen.media.opimage.SubsampleBinaryToGrayOpImage;
     */
    private int blockX;
    private int blockY;

    /** Constructor. */
    public MlibSubsampleBinaryToGrayRIF() {}

    /**
     * Creates a new instance of <code>MlibSubsampleBinaryToGrayOpImage</code> in
     * the rendered image mode.
     *
     * @param args  The source image, scale factors,
     *              and the <code>Interpolation</code>.
     * @param hints  May contain rendering hints and destination image layout.
     */
    public RenderedImage create(ParameterBlock args,
                                RenderingHints hints) {
	RenderedImage source = args.getRenderedSource(0);

        // Verify that the source is mediaLib-compatible.
        if (!MediaLibAccessor.isMediaLibBinaryCompatible(args, null)) {
            return null;
        }

        // Get ImageLayout from RenderingHints.
        ImageLayout layout = RIFUtil.getImageLayoutHint(hints);

        // Verify that the destination is mediaLib-compatible and has
        // the same number of bands as the source.
        if ((layout != null &&
             layout.isValid(ImageLayout.SAMPLE_MODEL_MASK) &&
             !MediaLibAccessor.isMediaLibCompatible(
                  layout.getSampleModel(null),
                  layout.getColorModel(null))) ||
            !MediaLibAccessor.hasSameNumBands(args, layout)) {
            return null;
        }

        // Get BorderExtender from hints if any.
	// BorderExtender extender = RIFUtil.getBorderExtenderHint(hints);

        float xScale = args.getFloatParameter(0);
        float yScale = args.getFloatParameter(1);
 
	// When scaling by 1.0 in both x and y, a copy is all we need
	if (xScale == 1.0F && yScale == 1.0F){
            // Use CopyOpImage as MlibCopyOpImage doesn't handle
            // binary-to-gray case.
	    return new CopyOpImage(source, hints, layout);
	}

	return new MlibSubsampleBinaryToGrayOpImage(source,
						    layout,
						    hints,
						    xScale,
						    yScale);

    }
}
