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

package org.eclipse.imagen.media.codecimpl;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.eclipse.imagen.media.codec.ImageCodec;
import org.eclipse.imagen.media.codec.ImageDecoder;
import org.eclipse.imagen.media.codec.ImageDecodeParam;
import org.eclipse.imagen.media.codec.ImageEncoder;
import org.eclipse.imagen.media.codec.ImageEncodeParam;
import org.eclipse.imagen.media.codec.FileSeekableStream;
import org.eclipse.imagen.media.codec.SeekableStream;
import org.eclipse.imagen.media.codec.TIFFEncodeParam;

/**
 * @since EA3
 */
public final class TIFFCodec extends ImageCodec {

    public TIFFCodec() {}

    public String getFormatName() {
        return "tiff";
    }

    public Class getEncodeParamClass() {
        return org.eclipse.imagen.media.codec.TIFFEncodeParam.class;
    }

    public Class getDecodeParamClass() {
        return org.eclipse.imagen.media.codec.TIFFDecodeParam.class;
    }

    public boolean canEncodeImage(RenderedImage im,
                                  ImageEncodeParam param) {
        return true;
    }

    protected ImageEncoder createImageEncoder(OutputStream dst,
                                              ImageEncodeParam param) {
        return new TIFFImageEncoder(dst, param);
    }

    protected ImageDecoder createImageDecoder(SeekableStream src,
                                              ImageDecodeParam param) {
        return new TIFFImageDecoder(src, param);
    }

    public int getNumHeaderBytes() {
        return 4;
    }

    public boolean isFormatRecognized(byte[] header) {
        if ((header[0] == 0x49) &&
            (header[1] == 0x49) &&
            (header[2] == 0x2a) &&
            (header[3] == 0x00)) {
            return true;
        }

        if ((header[0] == 0x4d) &&
            (header[1] == 0x4d) &&
            (header[2] == 0x00) &&
            (header[3] == 0x2a)) {
            return true;
        }

        return false;
    }
}
