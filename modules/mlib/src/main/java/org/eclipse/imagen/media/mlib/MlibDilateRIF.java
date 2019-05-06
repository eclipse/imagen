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
import org.eclipse.imagen.BorderExtender;
import org.eclipse.imagen.ImageLayout;
import org.eclipse.imagen.KernelJAI;
import java.util.Map;
import org.eclipse.imagen.media.opimage.RIFUtil;

/**
 * A <code>RIF</code> supporting the "Dilate" operation in the
 * rendered image mode using MediaLib.
 *
 * @see org.eclipse.imagen.operator.DilateDescriptor
 * @see MlibDilateOpImage
 */
public class MlibDilateRIF implements RenderedImageFactory {

    /** Constructor. */
    public MlibDilateRIF() {}

    /**
     * Creates a new instance of <code>MlibDilateOpImage</code> in
     * the rendered image mode.
     *
     * @param args  The source image and dilation kernel.
     * @param hints  May contain rendering hints and destination image layout.
     */
    public RenderedImage create(ParameterBlock args,
                                RenderingHints hints) {
        /* Get ImageLayout and TileCache from RenderingHints. */
        ImageLayout layout = RIFUtil.getImageLayoutHint(hints);
        

        boolean isBinary = false;
        if (!MediaLibAccessor.isMediaLibCompatible(args, layout) ||
            !MediaLibAccessor.hasSameNumBands(args, layout)) {
            if(!MediaLibAccessor.isMediaLibBinaryCompatible(args, layout)) {
                return null;
            }
            isBinary = true;
        }

        /* Get BorderExtender from hints if any. */
        BorderExtender extender = RIFUtil.getBorderExtenderHint(hints);

        RenderedImage source = args.getRenderedSource(0);

        KernelJAI unRotatedKernel = (KernelJAI)args.getObjectParameter(0);
        KernelJAI kJAI = unRotatedKernel.getRotatedKernel();

        int kWidth = kJAI.getWidth();
        int kHeight= kJAI.getHeight();
	int xOri   = kJAI.getXOrigin();
	int yOri   = kJAI.getYOrigin();
	int numB   = source.getSampleModel().getNumBands();

        /* mediaLib does not handle kernels with either dimension < 2. */

        if (xOri != 1 || yOri != 1 || kWidth != 3 || kHeight != 3 || numB != 1) {
	    return null;
	}
	   
	// check for plus and square type of kernel

	float[] kdata = kJAI.getKernelData();

	if (isBinary && isKernel3Square1(kdata) || !isBinary && isKernel3Square0(kdata)){	  

	    return new MlibDilate3SquareOpImage(source, extender, hints, layout);

	}


	if (isBinary && isKernel3Plus1(kdata)){
  	    // plus shape
	  
	    return new MlibDilate3PlusOpImage(source, extender, hints, layout);
	}
	


	return null;

    }

    // check to see if a 3x3 kernel has 1s at the plus positions and 0s elsewhere
    private boolean isKernel3Plus1(float[] kdata){
      
        return (kdata[0] == 0.0F && kdata[1] == 1.0F && kdata[2] == 0.0F &&
		kdata[3] == 1.0F && kdata[4] == 1.0F && kdata[5] == 1.0F &&
		kdata[6] == 0.0F && kdata[7] == 1.0F && kdata[8] == 0.0F);
    }

    // check to see if a 3x3 kernel has 1s at the plus positions
    private boolean isKernel3Square0(float[] kdata){
      
        return (kdata[0] == 0.0F && kdata[1] == 0.0F && kdata[2] == 0.0F &&
		kdata[3] == 0.0F && kdata[4] == 0.0F && kdata[5] == 0.0F &&
		kdata[6] == 0.0F && kdata[7] == 0.0F && kdata[8] == 0.0F);
    }

    // check to see if a 3x3 kernel has 1s at the plus positions
    private boolean isKernel3Square1(float[] kdata){
      
        return (kdata[0] == 1.0F && kdata[1] == 1.0F && kdata[2] == 1.0F &&
		kdata[3] == 1.0F && kdata[4] == 1.0F && kdata[5] == 1.0F &&
		kdata[6] == 1.0F && kdata[7] == 1.0F && kdata[8] == 1.0F);
    }
}
