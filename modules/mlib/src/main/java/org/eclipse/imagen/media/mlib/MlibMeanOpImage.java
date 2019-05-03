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
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.awt.image.RenderedImage;
import java.awt.image.SampleModel;
import java.awt.image.DataBuffer;
import java.awt.image.PixelInterleavedSampleModel;
import org.eclipse.imagen.ImageLayout;
import org.eclipse.imagen.OpImage;
import org.eclipse.imagen.PlanarImage;
import org.eclipse.imagen.ROI;
import org.eclipse.imagen.StatisticsOpImage;
import org.eclipse.imagen.media.opimage.MeanOpImage;
import com.sun.medialib.mlib.*;
// import org.eclipse.imagen.media.test.OpImageTester;

/**
 * An OpImage that performs the Mean operation on an image through mediaLib.
 *
 */
final class MlibMeanOpImage extends MeanOpImage {

    /**
     * Constructs an MlibMeanOpImage. The image dimensions are copied
     * from the source image.  The tile grid layout, SampleModel, and
     * ColorModel may optionally be specified by an ImageLayout object.
     *
     * @param source    The source image.
     */
    public MlibMeanOpImage(RenderedImage source,
                           ROI roi,
                           int xStart,
                           int yStart,
                           int xPeriod,
                           int yPeriod) {

	super(source, roi, xStart, yStart, xPeriod, yPeriod);
    }

    protected void accumulateStatistics(String name,
                                        Raster source,
                                        Object stats) {
        // Get image and band count.
        PlanarImage sourceImage = getSourceImage(0);
        int numBands = sourceImage.getSampleModel().getNumBands();

        // Determine the format tag and create an accessor.
        int formatTag = MediaLibAccessor.findCompatibleTag(null, source);
        MediaLibAccessor srcAccessor = new MediaLibAccessor(source,
                                                            source.getBounds(),
                                                            formatTag);

        // Get the mediaLib image.
        mediaLibImage[] srcML = srcAccessor.getMediaLibImages();

        // NOTE:  currently srcML.length always equals 1

        double[] dmean = new double[numBands];

        switch (srcAccessor.getDataType()) {
        case DataBuffer.TYPE_BYTE:
        case DataBuffer.TYPE_USHORT:
        case DataBuffer.TYPE_SHORT:
        case DataBuffer.TYPE_INT:
            for (int i = 0 ; i < srcML.length; i++) {
                Image.Mean(dmean, srcML[i]);
            }
            break;

        case DataBuffer.TYPE_FLOAT:
        case DataBuffer.TYPE_DOUBLE:
            for (int i = 0 ; i < srcML.length; i++) {
                Image.Mean_Fp(dmean, srcML[i]);
            }
            break;

        default:
            throw new RuntimeException(JaiI18N.getString("Generic2"));
        }

        dmean = srcAccessor.getDoubleParameters(0, dmean);

        // Update the mean.
        double[] mean = (double[])stats;
        double weight =
            (double)(source.getWidth()*source.getHeight())/
            (double)(width*height);
        for ( int i = 0; i < numBands; i++ ) {
            mean[i] += dmean[i]*weight;
        }
    }
}
