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
import java.awt.Rectangle;
import java.awt.image.DataBuffer;
import java.awt.image.SampleModel;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.awt.image.renderable.ParameterBlock;
import java.awt.image.renderable.RenderedImageFactory;
import org.eclipse.imagen.AreaOpImage;
import org.eclipse.imagen.BorderExtender;
import org.eclipse.imagen.ImageLayout;
import org.eclipse.imagen.KernelJAI;
import org.eclipse.imagen.OpImage;
import java.util.Map;
import com.sun.medialib.mlib.*;
// import org.eclipse.imagen.media.test.OpImageTester;

/**
 * An OpImage class to perform Sobel (Gradient) on a source image.
 *
 *
 *
 * @see KernelJAI
 */
final class MlibSobelOpImage extends AreaOpImage {
    
    /**
     * Creates a MlibSobelOpImage given the image source.
     * The image dimensions are derived from the source image.
     * The tile grid layout, SampleModel, and ColorModel may
     * optionally be specified by an ImageLayout object.
     *
     * @param source a RenderedImage.
     * @param extender a BorderExtender, or null.

     * @param layout an ImageLayout optionally containing the tile grid layout,
     *        SampleModel, and ColorModel, or null.
     */
    public MlibSobelOpImage(RenderedImage source,
                            BorderExtender extender,
                            Map config,
                            ImageLayout layout,
                            KernelJAI kernel) {
        //
        // Both the orthogonal pair of kernels have the same width & height
        // Hence either of them can be used here
        //
	super(source,
              layout,
              config,
              true,
              extender,
              kernel.getLeftPadding(),
              kernel.getRightPadding(),
              kernel.getTopPadding(),
              kernel.getBottomPadding());
    }

    /**
     * Performs Sobel on a specified rectangle. The sources are cobbled.
     *
     * @param sources an array of source Rasters, guaranteed to provide all
     *                necessary source data for computing the output.
     * @param dest a WritableRaster tile containing the area to be computed.
     * @param destRect the rectangle within dest to be processed.
     */
    protected void computeRect(Raster[] sources,
                               WritableRaster dest,
                               Rectangle destRect) {
        Raster source = sources[0];
        Rectangle srcRect = mapDestRect(destRect, 0);

        int formatTag = MediaLibAccessor.findCompatibleTag(sources, dest);

        MediaLibAccessor srcAccessor =
            new MediaLibAccessor(source, srcRect, formatTag);
        MediaLibAccessor dstAccessor =
            new MediaLibAccessor(dest, destRect, formatTag);
        int numBands = getSampleModel().getNumBands();

        mediaLibImage[] srcML = srcAccessor.getMediaLibImages();
        mediaLibImage[] dstML = dstAccessor.getMediaLibImages();
        for (int i = 0; i < dstML.length; i++) {
            switch (dstAccessor.getDataType()) {
            case DataBuffer.TYPE_BYTE:
            case DataBuffer.TYPE_USHORT:
            case DataBuffer.TYPE_SHORT:
            case DataBuffer.TYPE_INT:
                Image.Sobel(dstML[i],
                                            srcML[i],
                                            ((1 << numBands)-1) ,
                                            Constants.MLIB_EDGE_DST_NO_WRITE);
                break;
                
            case DataBuffer.TYPE_FLOAT:
            case DataBuffer.TYPE_DOUBLE:
                Image.Sobel_Fp(dstML[i],
                                               srcML[i],
                                               ((1 << numBands)-1) ,
                                               Constants.MLIB_EDGE_DST_NO_WRITE);
                break;
                
            default:
                String className = this.getClass().getName();
                throw new RuntimeException(JaiI18N.getString("Generic2"));
            }
        }
 
        if (dstAccessor.isDataCopy()) {
            dstAccessor.copyDataToRaster();
        }
    }

//     public static OpImage createTestImage(OpImageTester oit) {
//         float data_h[] = {-1.0f, -2.0f, -1.0f,
//                            0.0f,  0.0f,  0.0f,
//                            1.0f,  2.0f,  1.0f};
//         float data_v[] = {-1.0f, 0.0f, 1.0f,
//                           -2.0f, 0.0f, 2.0f,
//                           -1.0f, 0.0f, 1.0f};

//         KernelJAI kern_h = new KernelJAI(3,3,data_h);
//         KernelJAI kern_v = new KernelJAI(3,3,data_v);
        
//         return new MlibSobelOpImage(oit.getSource(), null, null,
//                                     new ImageLayout(oit.getSource()),
//                                     kern_h);
//     }
 
//     public static void main (String args[]) {
//         String classname = "org.eclipse.imagen.media.mlib.MlibSobelOpImage";
//         OpImageTester.performDiagnostics(classname,args);
//     }
}
