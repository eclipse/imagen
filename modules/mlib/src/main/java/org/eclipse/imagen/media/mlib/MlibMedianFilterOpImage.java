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
import org.eclipse.imagen.OpImage;
import org.eclipse.imagen.RasterAccessor;
import java.util.Map;
import org.eclipse.imagen.operator.MedianFilterDescriptor;
import org.eclipse.imagen.operator.MedianFilterShape;
import com.sun.medialib.mlib.*;

/**
 * An OpImage class that subclasses will use to perform
 * MedianFiltering with specific masks.
 *
 *
 */
final class MlibMedianFilterOpImage extends AreaOpImage {

    protected int maskType;
    protected int maskSize;

    /**
     * Creates a MlibMedianFilterOpImage given an image source, an
     * optional BorderExtender, a maskType and maskSize.  The image
     * dimensions are derived the source image.  The tile grid layout,
     * SampleModel, and ColorModel may optionally be specified by an
     * ImageLayout object.
     *
     * @param source a RenderedImage.
     * @param extender a BorderExtender, or null.
     * @param layout an ImageLayout optionally containing the tile grid layout,
     *        SampleModel, and ColorModel, or null.
     * @param maskType the filter mask type.
     * @param maskSize the filter mask size.
     */
    public MlibMedianFilterOpImage(RenderedImage source,
                                   BorderExtender extender,
                                   Map config,
                                   ImageLayout layout,
                                   MedianFilterShape maskType,
                                   int maskSize) {
	super(source,
              layout,
              config,
              true,
              extender,
              (maskSize-1)/2,
              (maskSize-1)/2,
              (maskSize/2),
              (maskSize/2));
        this.maskType = mapToMlibMaskType(maskType);
        this.maskSize = maskSize;
    }

    private static int mapToMlibMaskType(MedianFilterShape maskType) {
        if(maskType.equals(MedianFilterDescriptor.MEDIAN_MASK_SQUARE)) {
            return Constants.MLIB_MEDIAN_MASK_RECT;
        } else if(maskType.equals(MedianFilterDescriptor.MEDIAN_MASK_PLUS)) {
            return Constants.MLIB_MEDIAN_MASK_PLUS;
        } else if(maskType.equals(MedianFilterDescriptor.MEDIAN_MASK_X)) {
            return Constants.MLIB_MEDIAN_MASK_X;
        } else if(maskType.equals(MedianFilterDescriptor.MEDIAN_MASK_SQUARE_SEPARABLE)) {
            return Constants.MLIB_MEDIAN_MASK_RECT_SEPARABLE;
        }
        throw new RuntimeException(JaiI18N.getString("MedianFilterOpImage0")); 
    }

    /**
     * Performs median filtering on a specified rectangle. The sources are
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
            new MediaLibAccessor(source,srcRect,formatTag);
        MediaLibAccessor dstAccessor =
            new MediaLibAccessor(dest,destRect,formatTag);
        int numBands = getSampleModel().getNumBands();
 

        int cmask = (1 << numBands) -1; 
        mediaLibImage[] srcML = srcAccessor.getMediaLibImages();
        mediaLibImage[] dstML = dstAccessor.getMediaLibImages();
        for (int i = 0; i < dstML.length; i++) {
            switch (dstAccessor.getDataType()) {
            case DataBuffer.TYPE_BYTE:
            case DataBuffer.TYPE_USHORT:
            case DataBuffer.TYPE_SHORT:
            case DataBuffer.TYPE_INT:
                if (maskSize == 3) {
                    // Call appropriate Medialib accelerated function
                    Image.MedianFilter3x3(dstML[i],
                                                          srcML[i],
                                                          maskType,
                                                          cmask,
                                                          Constants.MLIB_EDGE_DST_NO_WRITE);
                } else if (maskSize == 5) {
                    // Call appropriate Medialib accelerated function
                    Image.MedianFilter5x5(dstML[i],
                                                          srcML[i],
                                                          maskType,
                                                          cmask,
                                                          Constants.MLIB_EDGE_DST_NO_WRITE);
                } else if (maskSize == 7) {
                    // Call appropriate Medialib accelerated function
                    Image.MedianFilter7x7(dstML[i],
                                                          srcML[i],
                                                          maskType,
                                                          cmask,
                                                          Constants.MLIB_EDGE_DST_NO_WRITE);
                } else {
                    // Call the generic version
                    Image.MedianFilterMxN(dstML[i],
                                                          srcML[i],
                                                          maskSize,
                                                          maskSize,
                                                          maskType,
                                                          cmask,
                                                          Constants.MLIB_EDGE_DST_NO_WRITE);
                }
                break;
            case DataBuffer.TYPE_FLOAT:
            case DataBuffer.TYPE_DOUBLE:
                if (maskSize == 3) {
                    // Call appropriate Medialib accelerated function
                    Image.MedianFilter3x3_Fp(dstML[i],
                                                             srcML[i],
                                                             maskType,
                                                             cmask,
                                                             Constants.MLIB_EDGE_DST_NO_WRITE);
                } else if (maskSize == 5) {
                    // Call appropriate Medialib accelerated function
                    Image.MedianFilter5x5_Fp(dstML[i],
                                                             srcML[i],
                                                             maskType,
                                                             cmask,
                                                             Constants.MLIB_EDGE_DST_NO_WRITE);
                }  else if (maskSize == 7) {
                    // Call appropriate Medialib accelerated function
                    Image.MedianFilter7x7_Fp(dstML[i],
                                                             srcML[i],
                                                             maskType,
                                                             cmask,
                                                             Constants.MLIB_EDGE_DST_NO_WRITE);
                } else {
                    // Call the generic version
                    Image.MedianFilterMxN_Fp(dstML[i],
                                                             srcML[i],
                                                             maskSize,
                                                             maskSize, 
                                                             maskType,
                                                             cmask,
                                                             Constants.MLIB_EDGE_DST_NO_WRITE);
                }
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
}
