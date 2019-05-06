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
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.util.Map;
import org.eclipse.imagen.ImageLayout;
import org.eclipse.imagen.OpImage;
import org.eclipse.imagen.PlanarImage;
import org.eclipse.imagen.PointOpImage;
import org.eclipse.imagen.RasterFactory;
import org.eclipse.imagen.media.util.ImageUtil;
import org.eclipse.imagen.media.util.JDKWorkarounds;
import com.sun.medialib.mlib.*;

/**
 * An OpImage class that extracts (a) selected band(s) from an image.
 *
 */
final class MlibBandSelectOpImage extends PointOpImage {
    /* Bitmask for the bands to be extracted. */
    private int cmask = 0x00000000;

    /**
     * Constructs an MlibBandSelectOpImage. The image dimensions are copied
     * from the source image.  The tile grid layout, SampleModel, and
     * ColorModel may optionally be specified by an ImageLayout object.
     *
     * @param source    a RenderedImage.
     * @param layout    an ImageLayout optionally containing the tile
     *                  grid layout, SampleModel, and ColorModel, or null.
     */
    public MlibBandSelectOpImage(RenderedImage source,
                                 Map config,
                                 ImageLayout layout,
                                 int[] bandIndices) {
        super(source, layout, config, true);

        int numBands = bandIndices.length;
        if (getSampleModel().getNumBands() != numBands) {
            // Create a new SampleModel and ColorModel.
            sampleModel = RasterFactory.createComponentSampleModel(sampleModel,
                                  sampleModel.getDataType(),
                                  tileWidth, tileHeight, numBands);

            if(colorModel != null &&
               !JDKWorkarounds.areCompatibleDataModels(sampleModel,
                                                       colorModel)) {
                colorModel = ImageUtil.getCompatibleColorModel(sampleModel,
                                                               config);
            }
        }

        // Initialize the band selection bitmask.
        int maxShift = source.getSampleModel().getNumBands() - 1;
        for(int i = 0; i < bandIndices.length; i++) {
            cmask |= 0x00000001 << (maxShift - bandIndices[i]);
        }
    }

    /**
     * Extract the selected bands.
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
        Raster source = sources[0];
        Rectangle srcRect = mapDestRect(destRect, 0);

        int formatTag = MediaLibAccessor.findCompatibleTag(sources,dest);

        MediaLibAccessor srcAccessor = 
            new MediaLibAccessor(source,srcRect,formatTag);
        MediaLibAccessor dstAccessor = 
            new MediaLibAccessor(dest,destRect,formatTag);

        mediaLibImage[] srcML = srcAccessor.getMediaLibImages();
        mediaLibImage[] dstML = dstAccessor.getMediaLibImages();

        for (int i = 0; i < dstML.length; i++) {
            Image.ChannelExtract(dstML[i], srcML[i], cmask);
        }

        if (dstAccessor.isDataCopy()) {
            dstAccessor.clampDataArrays();
            dstAccessor.copyDataToRaster();
        }
    }
}
