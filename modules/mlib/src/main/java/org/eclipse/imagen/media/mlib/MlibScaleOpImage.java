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
import java.awt.image.RenderedImage;
import org.eclipse.imagen.BorderExtender;
import org.eclipse.imagen.ImageLayout;
import org.eclipse.imagen.Interpolation;
import org.eclipse.imagen.ScaleOpImage;
import java.util.Map;

/**
 * MlibScaleOpImage extends ScaleOpImage for use by further extension
 * classes.
 *
 * @see ScaleOpImage
 *
 */
abstract class MlibScaleOpImage extends ScaleOpImage {

    /**
     * Constructs a MlibScaleOpImage from a RenderedImage source,
     * Interpolation object, x and y scale values.  The image
     * dimensions are determined by forward-mapping the source bounds.
     * The tile grid layout, SampleModel, and ColorModel are specified
     * by the image source, possibly overridden by values from the
     * ImageLayout parameter.
     * 
     * @param source a RenderedImage.
     * @param extender a BorderExtender, or null.

     *        or null.  If null, a default cache will be used.
     * @param layout an ImageLayout optionally containing the tile grid layout,
     *        SampleModel, and ColorModel, or null.
     * @param scaleX scale factor along x axis.
     * @param scaleY scale factor along y axis.
     * @param transX translation factor along x axis.
     * @param transY translation factor along y axis.
     * @param interp an Interpolation object to use for resampling.
     * @param cobbleSources a boolean indicating whether computeRect()
     *        expects contiguous sources.
     */
    public MlibScaleOpImage(RenderedImage source,
                            BorderExtender extender,
                            Map config,
			    ImageLayout layout,
			    float scaleX,
			    float scaleY,
			    float transX,
			    float transY,
			    Interpolation interp,
			    boolean cobbleSources) {
        super(source,
              layout,
              config,
              cobbleSources,
              extender,
              interp,
              scaleX,
              scaleY,
              transX,
              transY);

	// If the user did not provide a BorderExtender, attach a
	// BorderExtenderCopy to Medialib such that when Medialib
	// ask for additional source which may lie outside the 
	// bounds, it always gets it.
	this.extender = (extender == null) ? 
	    BorderExtender.createInstance(BorderExtender.BORDER_COPY)
	    : extender;
    }

    // Override backwardMapRect to pad the source by one extra pixel
    // in all directions for non Nearest Neighbor interpolations, so 
    // that precision issues don't cause Medialib to not write areas
    // in the destination rectangle.

    /**
     * Returns the minimum bounding box of the region of the specified
     * source to which a particular <code>Rectangle</code> of the
     * destination will be mapped.
     *
     * @param destRect the <code>Rectangle</code> in destination coordinates.
     * @param sourceIndex the index of the source image.
     *
     * @return a <code>Rectangle</code> indicating the source bounding box,
     *         or <code>null</code> if the bounding box is unknown.
     *
     * @throws IllegalArgumentException if <code>sourceIndex</code> is
     *         negative or greater than the index of the last source.
     * @throws IllegalArgumentException if <code>destRect</code> is
     *         <code>null</code>.
     */
    protected Rectangle backwardMapRect(Rectangle destRect,
                                        int sourceIndex) {

	Rectangle srcRect = super.backwardMapRect(destRect, sourceIndex);
	Rectangle paddedSrcRect = new Rectangle(srcRect.x - 1, 
						srcRect.y - 1,
						srcRect.width + 2, 
						srcRect.height + 2);

	return paddedSrcRect;
    }
}
