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

package org.eclipse.imagen.media.codecimpl;
import java.awt.image.RenderedImage;
import java.io.InputStream;
import java.io.IOException;
import org.eclipse.imagen.media.codec.FPXDecodeParam;
import org.eclipse.imagen.media.codec.ImageDecoder;
import org.eclipse.imagen.media.codec.ImageDecodeParam;
import org.eclipse.imagen.media.codec.ImageDecoderImpl;
import org.eclipse.imagen.media.codec.SeekableStream;
import org.eclipse.imagen.media.codecimpl.fpx.FPXImage;

/**
 * @since EA3
 */
public class FPXImageDecoder extends ImageDecoderImpl {

    public FPXImageDecoder(SeekableStream input,
                           ImageDecodeParam param) {
        super(input, param);
    }

    public RenderedImage decodeAsRenderedImage(int page) throws IOException {
        if (page != 0) {
            throw new IOException(JaiI18N.getString("FPXImageDecoder0"));
        }
        try {
            return new FPXImage(input, (FPXDecodeParam)param);
        } catch(Exception e) {
            throw CodecUtils.toIOException(e);
        }
    }
}
