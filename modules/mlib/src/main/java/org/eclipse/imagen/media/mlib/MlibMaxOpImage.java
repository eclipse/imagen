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
import org.eclipse.imagen.PointOpImage;
import java.util.Map;
import com.sun.medialib.mlib.*;

/**
 * An <code>OpImage</code> implementing the "Max" operation
 * using MediaLib.
 *
 * @see org.eclipse.imagen.operator.MaxDescriptor
 * @see MlibMaxRIF
 *
 * @since 1.0
 *
 */
final class MlibMaxOpImage extends PointOpImage {

    /** Constructor. */
    public MlibMaxOpImage(RenderedImage source1,
                          RenderedImage source2,
                          Map config,
                          ImageLayout layout) {
        super(source1, source2, layout, config, true);
        // Set flag to permit in-place operation.
        permitInPlaceOperation();
    }

    /**
     * Performs the "Max" operation on a rectangular region of
     * the same.
     */
    protected void computeRect(Raster[] sources,
                               WritableRaster dest,
                               Rectangle destRect) {
        int formatTag = MediaLibAccessor.findCompatibleTag(sources, dest);

        MediaLibAccessor srcMA1 =
            new MediaLibAccessor(sources[0], destRect, formatTag);
        MediaLibAccessor srcMA2 =
            new MediaLibAccessor(sources[1], destRect, formatTag);
        MediaLibAccessor dstMA =
            new MediaLibAccessor(dest, destRect, formatTag);

        mediaLibImage[] srcMLI1 = srcMA1.getMediaLibImages();
        mediaLibImage[] srcMLI2 = srcMA2.getMediaLibImages();
        mediaLibImage[] dstMLI = dstMA.getMediaLibImages();

        switch (dstMA.getDataType()) {
        case DataBuffer.TYPE_BYTE:
        case DataBuffer.TYPE_USHORT:
        case DataBuffer.TYPE_SHORT:
        case DataBuffer.TYPE_INT:
            for (int i = 0 ; i < dstMLI.length; i++) {
                Image.Max(dstMLI[i], srcMLI1[i], srcMLI2[i]);
            }
            break;

        case DataBuffer.TYPE_FLOAT:
        case DataBuffer.TYPE_DOUBLE:
            for (int i = 0 ; i < dstMLI.length; i++) {
                Image.Max_Fp(dstMLI[i], srcMLI1[i], srcMLI2[i]);
            }
            break;

        }

        if (dstMA.isDataCopy()) {
            dstMA.clampDataArrays();
            dstMA.copyDataToRaster();
        }
    }
}
