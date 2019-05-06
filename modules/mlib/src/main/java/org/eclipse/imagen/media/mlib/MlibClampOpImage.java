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
import org.eclipse.imagen.ImageLayout;
import org.eclipse.imagen.PointOpImage;
import java.util.Map;
import org.eclipse.imagen.media.util.ImageUtil;
import com.sun.medialib.mlib.*;

/**
 * An <code>OpImage</code> implementing the "Clamp" operation
 * using MediaLib.
 *
 * @see org.eclipse.imagen.operator.ClampDescriptor
 * @see MlibClampRIF
 *
 * @since 1.0
 *
 */
final class MlibClampOpImage extends PointOpImage {

    /** The lower bound, one for each band. */
    private double[] low;

    /** The integer version of lower bound. */
    private int[] lowInt;

    /** The upper bound, one for each band. */
    private double[] high;

    /** The integer version of upper bound. */
    private int[] highInt;

    /** Constructor. */
    public MlibClampOpImage(RenderedImage source,
                                Map config,
                                ImageLayout layout,
                                double[] low,
                                double[] high) {
        super(source, layout, config, true);

        int numBands = getSampleModel().getNumBands();
        this.low = new double[numBands];
        this.lowInt = new int[numBands];
        this.high = new double[numBands];
        this.highInt = new int[numBands];

        for (int i = 0; i < numBands; i++) {
            if (low.length < numBands) {
                this.low[i] = low[0];
            } else {
                this.low[i] = low[i];
            }
            this.lowInt[i] = ImageUtil.clampRoundInt(this.low[i]);

            if (high.length < numBands) {
                this.high[i] = high[0];
            } else {
                this.high[i] = high[i];
            }
            this.highInt[i] = ImageUtil.clampRoundInt(this.high[i]);
        }
        // Set flag to permit in-place operation.
        permitInPlaceOperation();
    }

    /**
     * Performs the "Clamp" operation on a rectangular region of
     * the same.
     */
    protected void computeRect(Raster[] sources,
                               WritableRaster dest,
                               Rectangle destRect) {
        int formatTag = MediaLibAccessor.findCompatibleTag(sources, dest);

        MediaLibAccessor srcMA =
            new MediaLibAccessor(sources[0], destRect, formatTag);
        MediaLibAccessor dstMA =
            new MediaLibAccessor(dest, destRect, formatTag);

        mediaLibImage[] srcMLI = srcMA.getMediaLibImages();
        mediaLibImage[] dstMLI = dstMA.getMediaLibImages();

        switch (dstMA.getDataType()) {
        case DataBuffer.TYPE_BYTE:
        case DataBuffer.TYPE_USHORT:
        case DataBuffer.TYPE_SHORT:
        case DataBuffer.TYPE_INT:
            for (int i = 0 ; i < dstMLI.length; i++) {
                int[] mlLow = dstMA.getIntParameters(i, lowInt);
                int[] mlHigh = dstMA.getIntParameters(i, highInt);
                Image.Thresh4(dstMLI[i], srcMLI[i],
                                              mlHigh, mlLow, mlHigh, mlLow);
            }
            break;

        case DataBuffer.TYPE_FLOAT:
        case DataBuffer.TYPE_DOUBLE:
            for (int i = 0 ; i < dstMLI.length; i++) {
                double[] mlLow = dstMA.getDoubleParameters(i, low);
                double[] mlHigh = dstMA.getDoubleParameters(i, high);
                Image.Thresh4_Fp(dstMLI[i], srcMLI[i],
                                                 mlHigh, mlLow, mlHigh, mlLow);
            }
            break;

        default:
            throw new RuntimeException(JaiI18N.getString("Generic2"));
        }

        if (dstMA.isDataCopy()) {
            dstMA.clampDataArrays();
            dstMA.copyDataToRaster();
        }
    }
}
