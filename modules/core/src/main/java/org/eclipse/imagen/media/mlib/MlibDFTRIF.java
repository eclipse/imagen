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
import org.eclipse.imagen.EnumeratedParameter;
import org.eclipse.imagen.ImageLayout;
import org.eclipse.imagen.JAI;
import java.util.Map;
import org.eclipse.imagen.operator.DFTDescriptor;
import org.eclipse.imagen.media.opimage.RIFUtil;
import org.eclipse.imagen.media.opimage.DFTOpImage;
import org.eclipse.imagen.media.opimage.FFT;
import org.eclipse.imagen.media.util.MathJAI;

/**
 * A <code>RIF</code> supporting the "DFT" operation in the
 * rendered image mode using MediaLib.
 *
 * @since EA4
 *
 * @see org.eclipse.imagen.operator.DFTDescriptor
 * @see org.eclipse.imagen.media.opimage.DFTOpImage
 *
 */
public class MlibDFTRIF implements RenderedImageFactory {

    /** Constructor. */
    public MlibDFTRIF() {}

    /**
     * Creates a new instance of <code>DFTOpImage</code> in
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

        RenderedImage source = args.getRenderedSource(0);
        EnumeratedParameter scalingType =
            (EnumeratedParameter)args.getObjectParameter(0);
        EnumeratedParameter dataNature =
            (EnumeratedParameter)args.getObjectParameter(1);

        boolean isComplexSource =
            !dataNature.equals(DFTDescriptor.REAL_TO_COMPLEX);
        int numSourceBands = source.getSampleModel().getNumBands();

        // Use the two-dimensional mediaLib DFT if possible: it supports
        // only data which have a single component (real or complex)
        // per pixel and which have dimensions which are equal to a positive
        // power of 2.
        if(((isComplexSource && numSourceBands == 2) ||
            (!isComplexSource && numSourceBands == 1)) &&
           MlibDFTOpImage.isAcceptableSampleModel(source.getSampleModel())) {
            // If necessary, pad the source to ensure that
            // both dimensions are positive powers of 2.
            int sourceWidth = source.getWidth();
            int sourceHeight = source.getHeight();
            if(!MathJAI.isPositivePowerOf2(sourceWidth) ||
               !MathJAI.isPositivePowerOf2(sourceHeight)) {
                ParameterBlock pb = new ParameterBlock();
                pb.addSource(source);
                pb.add(0);
                pb.add(MathJAI.nextPositivePowerOf2(sourceWidth) -
                       sourceWidth);
                pb.add(0);
                pb.add(MathJAI.nextPositivePowerOf2(sourceHeight) -
                       sourceHeight);
                pb.add(BorderExtender.createInstance(BorderExtender.BORDER_ZERO));
                source = JAI.create("border", pb, null);
            }

            return new MlibDFTOpImage(source, hints, layout, dataNature,
                                      true, scalingType);
        } else { // General case
            FFT fft = new FFTmediaLib(true,
                                      new Integer(scalingType.getValue()), 2);

            return new DFTOpImage(source, hints, layout, dataNature, fft);
        }
    }
}
