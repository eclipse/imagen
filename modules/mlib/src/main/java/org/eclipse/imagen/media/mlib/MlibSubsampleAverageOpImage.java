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
import org.eclipse.imagen.BorderExtender;
import org.eclipse.imagen.GeometricOpImage;
import org.eclipse.imagen.ImageLayout;
import org.eclipse.imagen.Interpolation;
import org.eclipse.imagen.media.opimage.SubsampleAverageOpImage;
import com.sun.medialib.mlib.Image;
import com.sun.medialib.mlib.mediaLibImage;

public class MlibSubsampleAverageOpImage extends SubsampleAverageOpImage {
    /* XXX
    public static void main(String[] args) throws Throwable {
        RenderedImage source =
            org.eclipse.imagen.JAI.create("fileload", args[0]);
        double scaleX = args.length > 1 ?
            Double.valueOf(args[1]).doubleValue() : 0.25;
        double scaleY = args.length > 2 ?
            Double.valueOf(args[2]).doubleValue() : scaleX;

        RenderedImage dest =
            new MlibSubsampleAverageOpImage(source, null, null,
                                            scaleX, scaleY);

        System.out.println(source.getClass().getName()+": "+
                           new ImageLayout(source));
        System.out.println(dest.getClass().getName()+": "+
                           new ImageLayout(dest));

        java.awt.Frame frame = new java.awt.Frame("Mlib Sub-average Test");
        frame.setLayout(new java.awt.GridLayout(1, 2));
        org.eclipse.imagen.widget.ScrollingImagePanel ps =
            new org.eclipse.imagen.widget.ScrollingImagePanel(source,
                                                           512, 512);
        org.eclipse.imagen.widget.ScrollingImagePanel pd =
            new org.eclipse.imagen.widget.ScrollingImagePanel(dest,
                                                           512, 512);
        frame.add(ps);
        frame.add(pd);
        frame.pack();
        frame.show();
    }
    */

    public MlibSubsampleAverageOpImage(RenderedImage source,
                                       ImageLayout layout,
                                       Map config,
                                       double scaleX,
                                       double scaleY) {
        super(source,
              layout,
              config,
              scaleX,
              scaleY);
    }

    protected void computeRect(Raster[] sources,
                               WritableRaster dest,
                               Rectangle destRect) {
        Raster source = sources[0];
        Rectangle srcRect = source.getBounds();

        int formatTag = MediaLibAccessor.findCompatibleTag(sources,dest);

        MediaLibAccessor srcAccessor =
            new MediaLibAccessor(source,srcRect,formatTag);
        MediaLibAccessor dstAccessor =
            new MediaLibAccessor(dest,destRect,formatTag);

	mediaLibImage srcML[], dstML[];

        switch (dstAccessor.getDataType()) {
        case DataBuffer.TYPE_BYTE:
        case DataBuffer.TYPE_USHORT:
        case DataBuffer.TYPE_SHORT:
        case DataBuffer.TYPE_INT:
            srcML = srcAccessor.getMediaLibImages();
            dstML = dstAccessor.getMediaLibImages();

            Image.SubsampleAverage(dstML[0],
                                   srcML[0],
                                   scaleX,
                                   scaleY);
            break;

        case DataBuffer.TYPE_FLOAT:
        case DataBuffer.TYPE_DOUBLE:
	    srcML = srcAccessor.getMediaLibImages();
            dstML = dstAccessor.getMediaLibImages();

            Image.SubsampleAverage_Fp(dstML[0],
                                      srcML[0],
                                      scaleX,
                                      scaleY);
	    break;

        default:
            // XXX?
        }

        if (dstAccessor.isDataCopy()) {
            dstAccessor.copyDataToRaster();
        }
    }
}
