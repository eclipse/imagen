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
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import org.eclipse.imagen.ImageLayout;
import org.eclipse.imagen.Interpolation;
import org.eclipse.imagen.InterpolationNearest;
import org.eclipse.imagen.OpImage;
import java.util.Map;
import org.eclipse.imagen.BorderExtender;
import com.sun.medialib.mlib.*;

// import org.eclipse.imagen.media.test.OpImageTester;

/**
 * An OpImage class that scales an image using nearest-neighbor
 * interpolation.
 *
 */
final class MlibScaleNearestOpImage extends MlibScaleOpImage {

    /**
     * Constructs an MlibScaleNearestOpImage. The image dimensions are copied
     * from the source image.  The tile grid layout, SampleModel, and
     * ColorModel may optionally be specified by an ImageLayout object.
     *
     * @param source    a RenderedImage.
     * @param layout    an ImageLayout optionally containing the tile
     *                  grid layout, SampleModel, and ColorModel, or null.
     * @param xScale    the x scaling factor.
     * @param yScale    the y scaling factor.
     * @param xTrans    the x translation factor.
     * @param yTrans    the y translation factor.
     * @param interp    the Nearest interpolation object.
     */
    public MlibScaleNearestOpImage(RenderedImage source,
				   BorderExtender extender,
                                   Map config,
				   ImageLayout layout,
				   float xScale, float yScale,
				   float xTrans, float yTrans,
				   Interpolation interp) {
        super(source, extender, config,
              layout, xScale, yScale, xTrans, yTrans, interp, true);
    }

    /**
     * Scale the given rectangle by the specified scale and translation 
     * factors. The sources are cobbled.
     *
     * @param sources   an array of sources, guarantee to provide all
     *                  necessary source data for computing the rectangle.
     * @param dest      a tile that contains the rectangle to be computed.
     * @param destRect  the rectangle within this OpImage to be processed.
     */
    protected void computeRect(Raster[] sources,
                               WritableRaster dest,
                               Rectangle destRect) {

	Raster source = sources[0];
	Rectangle srcRect = source.getBounds();

        int formatTag = MediaLibAccessor.findCompatibleTag(sources, dest);

        MediaLibAccessor srcAccessor = new MediaLibAccessor(source, srcRect,
							    formatTag);
        MediaLibAccessor dstAccessor = new MediaLibAccessor(dest, destRect,
							    formatTag);

	// Get the floating point scale factors
	float mlibScaleX = scaleX;
	float mlibScaleY = scaleY;

        // Translation to be specified to Medialib. Note that we have to 
	// specify an additional translation since all images are 0 based
	// in Medialib.  Note that scale and translation scalars have
	// rational representations.
	    
	// Calculate intermediate values using rational arithmetic.
	long tempDenomX = scaleXRationalDenom * transXRationalDenom;
        long tempDenomY = scaleYRationalDenom * transYRationalDenom;
	long tempNumerX = (srcRect.x * scaleXRationalNum * transXRationalDenom) + 
	                  (transXRationalNum * scaleXRationalDenom) - 
			  (destRect.x * tempDenomX);
	long tempNumerY = (srcRect.y * scaleYRationalNum * transYRationalDenom) +
	                  (transYRationalNum * scaleYRationalDenom) -
			  (destRect.y * tempDenomY);
        float tx = (float)tempNumerX/(float)tempDenomX;
	float ty = (float)tempNumerY/(float)tempDenomY;

	mediaLibImage srcML[], dstML[];

        switch (dstAccessor.getDataType()) {
        case DataBuffer.TYPE_BYTE:
        case DataBuffer.TYPE_USHORT:
        case DataBuffer.TYPE_SHORT:
        case DataBuffer.TYPE_INT:
            srcML = srcAccessor.getMediaLibImages();
            dstML = dstAccessor.getMediaLibImages();
            for (int i = 0 ; i < dstML.length; i++) {
                Image.ZoomTranslate(dstML[i], srcML[i],
						    (double)mlibScaleX,
						    (double)mlibScaleY,
						    (double)tx, (double)ty, 
						    Constants.MLIB_NEAREST,
                                                    Constants.MLIB_EDGE_DST_NO_WRITE);
            }
            break;

	case DataBuffer.TYPE_FLOAT:
	case DataBuffer.TYPE_DOUBLE:
            srcML = srcAccessor.getMediaLibImages();
            dstML = dstAccessor.getMediaLibImages();
            for (int i = 0 ; i < dstML.length; i++) {
                Image.ZoomTranslate_Fp(dstML[i], srcML[i],
						       (double)mlibScaleX,
						       (double)mlibScaleY,
						       (double)tx, (double)ty, 
						       Constants.MLIB_NEAREST,
						       Constants.MLIB_EDGE_DST_NO_WRITE);
            }
            break;
	    
        default:
            String className = this.getClass().getName();
            throw new RuntimeException(JaiI18N.getString("Generic2"));
        }

        if (dstAccessor.isDataCopy()) {
            dstAccessor.clampDataArrays();
            dstAccessor.copyDataToRaster();
        }
    }

//     public static OpImage createTestImage(OpImageTester oit) {
// 	Interpolation interp = new InterpolationNearest();
//         return new MlibScaleNearestOpImage(oit.getSource(), null,
// 					   new ImageLayout(oit.getSource()),
// 					   2, 2, 0, 0, interp); 
//     }

//     // Calls a method on OpImage that uses introspection, to make this
//     // class, discover it's createTestImage() call, call it and then
//     // benchmark the performance of the created OpImage chain.
//     public static void main (String args[]) {
//         String classname = "org.eclipse.imagen.media.mlib.MlibScaleNearestOpImage";
//         OpImageTester.performDiagnostics(classname, args);
//     }
}
