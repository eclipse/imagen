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
import java.awt.RenderingHints;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.awt.image.renderable.RenderedImageFactory;
import org.eclipse.imagen.BorderExtender;
import org.eclipse.imagen.ImageLayout;
import org.eclipse.imagen.Interpolation;
import org.eclipse.imagen.InterpolationNearest;
import org.eclipse.imagen.InterpolationBilinear;
import org.eclipse.imagen.InterpolationBicubic;
import org.eclipse.imagen.InterpolationBicubic2;
import org.eclipse.imagen.InterpolationTable;
import java.util.Map;
import org.eclipse.imagen.Warp;
import org.eclipse.imagen.WarpGrid;
import org.eclipse.imagen.WarpPolynomial;
import org.eclipse.imagen.media.opimage.RIFUtil;

import com.sun.medialib.mlib.*;

/**
 * A <code>RIF</code> supporting the "Warp" operation in the
 * rendered image mode using MediaLib.
 *
 * @see org.eclipse.imagen.operator.WarpDescriptor
 * @see MlibWarpNearestOpImage
 * @see MlibWarpBilinearOpImage
 * @see MlibWarpBicubicOpImage
 *
 * @since 1.0
 *
 */
public class MlibWarpRIF implements RenderedImageFactory {

    /** Constructor. */
    public MlibWarpRIF() {}

    /**
     * Creates a new instance of <code>MlibWarpOpImage</code> in
     * the rendered image mode.
     *
     * @param args  The source images.
     * @param hints  May contain rendering hints and destination image layout.
     */
    public RenderedImage create(ParameterBlock args,
                                RenderingHints hints) {
        /* Get ImageLayout and TileCache from RenderingHints. */
        ImageLayout layout = RIFUtil.getImageLayoutHint(hints);
        
        RenderedImage source = args.getRenderedSource(0);


        if (!MediaLibAccessor.isMediaLibCompatible(args, layout) ||
            !MediaLibAccessor.hasSameNumBands(args, layout) ||
	    // Medialib cannot deal with source image having tiles with any
	    // dimension greater than or equal to 32768
	    source.getTileWidth() >= 32768 || 
	    source.getTileHeight() >= 32768) {
            return null;
        }

        /* Get BorderExtender from hints if any. */
        BorderExtender extender = RIFUtil.getBorderExtenderHint(hints);

        Warp warp = (Warp)args.getObjectParameter(0);
        Interpolation interp = (Interpolation)args.getObjectParameter(1);
        double[] backgroundValues = (double[])args.getObjectParameter(2);

        int filter = -1;
        if (interp instanceof InterpolationNearest)  {
            filter = Constants.MLIB_NEAREST;
        } else if (interp instanceof InterpolationBilinear) {
            filter = Constants.MLIB_BILINEAR;
        } else if (interp instanceof InterpolationBicubic) {
            filter = Constants.MLIB_BICUBIC;
        } else if (interp instanceof InterpolationBicubic2) {
            filter = Constants.MLIB_BICUBIC2;
        } else if (interp instanceof InterpolationTable) {
	    ;
	    // filter =  Constants.MLIB_TABLE; not defined yet;
        } else {
            /* Other kinds of interpolation cannot be handled via mlib. */
            return null;
        }

        if (warp instanceof WarpGrid) {
	  if (interp instanceof InterpolationTable){
            return new MlibWarpGridTableOpImage(source,
                                           extender, hints, layout,
                                           (WarpGrid)warp,
                                           interp,
                                           backgroundValues);
	  }else{
            return new MlibWarpGridOpImage(source,
                                           extender, hints, layout,
                                           (WarpGrid)warp,
                                           interp, filter,
                                           backgroundValues);
	  }

        } else if (warp instanceof WarpPolynomial) {
	  if (interp instanceof InterpolationTable){
            return new MlibWarpPolynomialTableOpImage(source,
                                                 extender, hints, layout,
                                                 (WarpPolynomial)warp,
                                                 interp,
                                                 backgroundValues);
	  }else{
            return new MlibWarpPolynomialOpImage(source,
                                                 extender, hints, layout,
                                                 (WarpPolynomial)warp,
                                                 interp, filter,
                                                 backgroundValues);
	  }
        } else {
            return null;
        }

    }
}
