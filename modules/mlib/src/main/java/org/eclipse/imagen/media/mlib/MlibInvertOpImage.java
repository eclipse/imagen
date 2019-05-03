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
 * An <code>OpImage</code> implementing the "Invert" operation
 * using MediaLib.
 *
 * @see org.eclipse.imagen.operator.InvertDescriptor
 * @see MlibInvertRIF
 *
 * @since 1.0
 *
 */
final class MlibInvertOpImage extends PointOpImage {

    /** Constructor. */
    public MlibInvertOpImage(RenderedImage source,
                             Map config,
                             ImageLayout layout) {
        super(source, layout, config, true);
        // Set flag to permit in-place operation.
        permitInPlaceOperation();
    }

    /**
     * Performs the "Invert" operation on a rectangular region of
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
                Image.Invert(dstMLI[i], srcMLI[i]);
            }
            break;

        case DataBuffer.TYPE_FLOAT:
        case DataBuffer.TYPE_DOUBLE:
            for (int i = 0 ; i < dstMLI.length; i++) {
                Image.Invert_Fp(dstMLI[i], srcMLI[i]);
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
