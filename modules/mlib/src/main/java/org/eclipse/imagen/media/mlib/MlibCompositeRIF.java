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
import java.awt.image.ComponentSampleModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.RenderedImage;
import java.awt.image.SampleModel;
import java.awt.image.renderable.ParameterBlock;
import java.awt.image.renderable.RenderedImageFactory;
import org.eclipse.imagen.EnumeratedParameter;
import org.eclipse.imagen.ImageLayout;
import java.util.Map;
import org.eclipse.imagen.operator.CompositeDescriptor;
import org.eclipse.imagen.media.opimage.RIFUtil;

/**
 * A <code>RIF</code> supporting the "Composite" operation in the
 * rendered image mode using MediaLib.
 *
 * @see org.eclipse.imagen.operator.CompositeDescriptor
 * @see MlibCompositeOpImage
 *
 * @since 1.0
 *
 */
public class MlibCompositeRIF implements RenderedImageFactory {

    /** Constructor. */
    public MlibCompositeRIF() {}

    /**
     * Creates a new instance of <code>MlibCompositeOpImage</code> in
     * the rendered image mode.
     *
     * @param args  The source images.
     * @param hints  May contain rendering hints and destination image layout.
     */
    public RenderedImage create(ParameterBlock args,
                                RenderingHints hints) {
        /* Get ImageLayout and TileCache from RenderingHints. */
        ImageLayout layout = RIFUtil.getImageLayoutHint(hints);
        

        if (!MediaLibAccessor.isMediaLibCompatible(args, layout) ||
            !MediaLibAccessor.hasSameNumBands(args, layout)) {
            return null;
        }

        RenderedImage alpha1 = (RenderedImage)args.getObjectParameter(0);
        Object alpha2 = args.getObjectParameter(1);
        boolean premultiplied =
            ((Boolean)args.getObjectParameter(2)).booleanValue();
        EnumeratedParameter destAlpha =
            (EnumeratedParameter)args.getObjectParameter(3);

        SampleModel sm = alpha1.getSampleModel();

        if (!(sm instanceof ComponentSampleModel) ||
            sm.getNumBands() != 1 ||
            !(alpha1.getColorModel() instanceof ComponentColorModel) ||
            alpha2 != null ||
            premultiplied ||
            !(destAlpha.equals(CompositeDescriptor.NO_DESTINATION_ALPHA))) {
            return null;
        }
            

        return new MlibCompositeOpImage(args.getRenderedSource(0),
                                        args.getRenderedSource(1),
                                        hints, layout,
                                        alpha1);
    }
}
