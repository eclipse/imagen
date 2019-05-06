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
import java.awt.image.ColorModel;
import com.sun.medialib.mlib.Image;
import com.sun.medialib.mlib.mediaLibImage;

final class MlibUtils {
    /**
     * If constants array is less than numBands it is replaced
     * by an array of length numBands filled with constants[0].
     * Otherwise the input array is cloned.
     */
    static final int[] initConstants(int[] constants, int numBands) {
        int[] c = null;
        if (constants.length < numBands) {
            c = new int[numBands];
            for (int i = 0; i < numBands; i++) {
                c[i] = constants[0];
            }
        } else {
            c = (int[])constants.clone();
        }

        return c;
    }

    /**
     * If constants array is less than numBands it is replaced
     * by an array of length numBands filled with constants[0].
     * Otherwise the input array is cloned.
     */
    static final double[] initConstants(double[] constants, int numBands) {
        double[] c = null;
        if (constants.length < numBands) {
            c = new double[numBands];
            for (int i = 0; i < numBands; i++) {
                c[i] = constants[0];
            }
        } else {
            c = (double[])constants.clone();
        }

        return c;
    }

    /**
     * If the color depth in bits of any band of the image does not
     * match the full bit depth as determined from the image type then
     * clamp the image to the unnomarlized range represented by the
     * ColorModel.
     */
    static void clampImage(mediaLibImage image, ColorModel colorModel) {
        if(image == null) {
            throw new IllegalArgumentException("image == null!");
        }

        if(colorModel != null) {
            // Set the full bit depth as a function of image type.
            int fullDepth = 0;
            switch(image.getType()) {
            case mediaLibImage.MLIB_BYTE:
                fullDepth = 8;
                break;
            case mediaLibImage.MLIB_INT:
                fullDepth = 32;
                break;
            default: // USHORT and SHORT
                fullDepth = 16;
            }

            // Set the low and high thresholds and the thresholding flag.
            int[] numBits = colorModel.getComponentSize();
            int[] high = new int[numBits.length];
            int[] low = new int[numBits.length]; // zero by default
            boolean applyThreshold = false;
            for(int j = 0; j < numBits.length; j++) {
                high[j] = (1 << numBits[j]) - 1;
                if(numBits[j] != fullDepth) {
                    applyThreshold = true;
                }
            }

            // Apply threshold if color depth of any band is not full depth.
            if(applyThreshold) {
                Image.Thresh4(image, high, low, high, low);
            }
        }
    }
}
