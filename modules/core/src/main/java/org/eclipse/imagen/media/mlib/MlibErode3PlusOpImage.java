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
import java.awt.Rectangle;
import java.awt.RenderingHints;
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
import org.eclipse.imagen.JAI;
import org.eclipse.imagen.KernelJAI;
import org.eclipse.imagen.OpImage;
import java.util.Map;
import com.sun.medialib.mlib.*;
// import org.eclipse.imagen.media.test.OpImageTester;

/**
 * An OpImage class to perform erosion on a source image
 * for the specific case when the kernel is a 3x3 plus shape
 * with the key position in the middle,
 * using mediaLib, of course :-).
 *
 * @see org.eclipse.imagen.operator.ErodeDescriptor
 * @see KernelJAI
 */
final class MlibErode3PlusOpImage extends AreaOpImage {

    // Since medialib expects single banded data with IndexColorModel, we
    // should not expand the indexed data
    private static Map configHelper(Map configuration) {

        Map config;

        if (configuration == null) {

            config = new RenderingHints(JAI.KEY_REPLACE_INDEX_COLOR_MODEL,
                                        Boolean.FALSE);

        } else {

            config = configuration;

	    // If the user has specified a hint for this, then we don't
	    // want to change it, so change only if this hint is not 
	    // already specified
            if (!(config.containsKey(JAI.KEY_REPLACE_INDEX_COLOR_MODEL))) {
                RenderingHints hints = (RenderingHints)configuration;
                config = (RenderingHints)hints.clone();
                config.put(JAI.KEY_REPLACE_INDEX_COLOR_MODEL, Boolean.FALSE);
            }
        }

        return config;
    }

    /**
     * Creates a MlibErode3PlusOpImage given the image source
     * The image dimensions are
     * derived from the source image.  The tile grid layout,
     * SampleModel, and ColorModel may optionally be specified by an
     * ImageLayout object.
     *
     * @param source a RenderedImage.
     * @param extender a BorderExtender, or null.

     *        or null.  If null, a default cache will be used.
     * @param layout an ImageLayout optionally containing the tile grid layout,
     *        SampleModel, and ColorModel, or null.
     */
    public MlibErode3PlusOpImage(RenderedImage source,
                                  BorderExtender extender,
                                  Map config,
                                  ImageLayout layout
                                  ) {
	super(source,
              layout,
              configHelper(config),
              true,
              extender,
              1, //kernel.getLeftPadding(),
              1, //kernel.getRightPadding(),
              1, //kernel.getTopPadding(),
              1  //kernel.getBottomPadding()
	      );	
    }


    /**
     * Performs erosion on a specified rectangle. The sources are
     * cobbled.
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

        int formatTag = MediaLibAccessor.findCompatibleTag(sources,dest);

        MediaLibAccessor srcAccessor =
            new MediaLibAccessor(source, srcRect, formatTag, true);
        MediaLibAccessor dstAccessor =
            new MediaLibAccessor(dest, destRect, formatTag, true);
        int numBands = getSampleModel().getNumBands();


        mediaLibImage[] srcML = srcAccessor.getMediaLibImages();
        mediaLibImage[] dstML = dstAccessor.getMediaLibImages();
        for (int i = 0; i < dstML.length; i++) {
            switch (dstAccessor.getDataType()) {
            case DataBuffer.TYPE_BYTE:
            case DataBuffer.TYPE_USHORT:
            case DataBuffer.TYPE_SHORT:
            case DataBuffer.TYPE_INT:
                Image.Erode4(dstML[i], srcML[i]);
                break;
            case DataBuffer.TYPE_FLOAT:
            case DataBuffer.TYPE_DOUBLE:
                Image.Erode4_Fp(dstML[i], srcML[i]);
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
//         float data[] = {0.05f,0.10f,0.05f,
//                         0.10f,0.40f,0.10f,
//                         0.05f,0.10f,0.05f};
//         KernelJAI kJAI = new KernelJAI(3,3,1,1,data);
//         return new MlibErode4OpImage(oit.getSource(), null, null,
//                                           new ImageLayout(oit.getSource()),
//                                           kJAI);
//     }
 
//     public static void main (String args[]) {
//         String classname = "org.eclipse.imagen.media.mlib.MlibErode4OpImage";
//         OpImageTester.performDiagnostics(classname,args);
//     }
}
