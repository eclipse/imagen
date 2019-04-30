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
import org.eclipse.imagen.OpImage;
import org.eclipse.imagen.PointOpImage;
import java.util.Map;
import com.sun.medialib.mlib.*;
// import org.eclipse.imagen.media.test.OpImageTester;

/**
 * An OpImage class that copies an image from source to dest.
 *
 */
final class MlibCopyOpImage extends PointOpImage {

    /**
     * Constructs an MlibCopyOpImage. The image dimensions are copied
     * from the source image.  The tile grid layout, SampleModel, and
     * ColorModel may optionally be specified by an ImageLayout object.
     *
     * @param source    a RenderedImage.

     *        or null.  If null, a default cache will be used.
     * @param layout    an ImageLayout optionally containing the tile
     *                  grid layout, SampleModel, and ColorModel, or null.
     */
    public MlibCopyOpImage(RenderedImage source,
                           Map config,
                           ImageLayout layout) {
        super(source, layout, config, true);

    }

    /**
     * Copy the pixel values of a rectangle with a given constant.
     * The sources are cobbled.
     *
     * @param sources   an array of sources, guarantee to provide all
     *                  necessary source data for computing the rectangle.
     * @param dest      a tile that contains the rectangle to be computed.
     * @param destRect  the rectangle within this OpImage to be processed.
     */
    protected void computeRect(Raster[] sources,
                               WritableRaster dest,
                               Rectangle destRect) {
        int formatTag = MediaLibAccessor.findCompatibleTag(sources,dest);

        MediaLibAccessor srcMA = 
            new MediaLibAccessor(sources[0], destRect, formatTag);
        MediaLibAccessor dstMA = 
            new MediaLibAccessor(dest, destRect, formatTag);

        mediaLibImage[] srcMLI = srcMA.getMediaLibImages();
        mediaLibImage[] dstMLI = dstMA.getMediaLibImages();

        for (int i = 0 ; i < dstMLI.length; i++) {
            Image.Copy(dstMLI[i], srcMLI[i]);
        }

        if (dstMA.isDataCopy()) {
            dstMA.copyDataToRaster();
        }
    }
}
