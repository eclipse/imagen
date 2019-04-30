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
import java.io.OutputStream;
import org.eclipse.imagen.media.codec.ImageCodec;
import org.eclipse.imagen.media.codec.ImageDecoder;
import org.eclipse.imagen.media.codec.ImageDecodeParam;
import org.eclipse.imagen.media.codec.ImageEncoder;
import org.eclipse.imagen.media.codec.ImageEncodeParam;
import org.eclipse.imagen.media.codec.SeekableStream;
import org.eclipse.imagen.media.codec.FPXDecodeParam;
// import org.eclipse.imagen.media.codec.FPXEncodeParam;

/**
 * @since EA3
 */
public final class FPXCodec extends ImageCodec {

    public FPXCodec() {}

    public String getFormatName() {
        return "fpx";
    }

    public Class getEncodeParamClass() {
        return null;
    }

    public Class getDecodeParamClass() {
        return org.eclipse.imagen.media.codec.FPXDecodeParam.class;
    }

    public boolean canEncodeImage(RenderedImage im,
                                  ImageEncodeParam param) {
        return false;
    }

    protected ImageEncoder createImageEncoder(OutputStream dst,
                                              ImageEncodeParam param) {
        throw new RuntimeException(JaiI18N.getString("FPXCodec0"));
    }

    protected ImageDecoder createImageDecoder(SeekableStream src,
                                              ImageDecodeParam param) {
        return new FPXImageDecoder(src, param);
    }

    public int getNumHeaderBytes() {
         return 8;
    }

    public boolean isFormatRecognized(byte[] header) {
        return ((header[0] == (byte)0xd0) &&
                (header[1] == (byte)0xcf) &&
                (header[2] == (byte)0x11) &&
                (header[3] == (byte)0xe0) &&
                (header[4] == (byte)0xa1) &&
                (header[5] == (byte)0xb1) &&
                (header[6] == (byte)0x1a) &&
                (header[7] == (byte)0xe1));
    }

}
