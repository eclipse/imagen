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
package org.eclipse.imagen.widgets;

import java.io.*;
import java.awt.image.*;
import java.awt.image.renderable.*;
import org.eclipse.imagen.*;


public class TutorUtils {

    /** produce a 3 band luminance image from a 3 band color image */
    public static PlanarImage convertColorToGray(PlanarImage src, int brightness) {
        PlanarImage dst = null;
        double b = (double) brightness;
        double[][] matrix = {
                                { .114D, 0.587D, 0.299D, b },
                                { .114D, 0.587D, 0.299D, b },
                                { .114D, 0.587D, 0.299D, b }
                            };

        if ( src != null ) {
            ParameterBlock pb = new ParameterBlock();
            pb.addSource(src);
            pb.add(matrix);
            dst = JAI.create("bandcombine", pb, null);
        }

        return dst;
    }

    /** produce a 3 band image from a single band gray scale image */
    public static PlanarImage convertGrayToColor(PlanarImage src, int brightness) {
        PlanarImage dst = null;
        double b = (double) brightness;
        double[][] matrix = {
                                { 1.0D, b },
                                { 1.0D, b },
                                { 1.0D, b }
                            };

        if ( src != null ) {
            int nbands = src.getSampleModel().getNumBands();

// MUST check color model here
            if ( nbands == 1 ) {
                ParameterBlock pb = new ParameterBlock();
                pb.addSource(src);
                pb.add(matrix);
                dst = JAI.create("bandcombine", pb, null);
            } else {
                dst = src;
            }
        }

        return dst;
    }
}
