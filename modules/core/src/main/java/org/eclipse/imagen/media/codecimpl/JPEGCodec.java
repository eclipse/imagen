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
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.eclipse.imagen.media.codec.ImageCodec;
import org.eclipse.imagen.media.codec.ImageDecoder;
import org.eclipse.imagen.media.codec.ImageDecodeParam;
import org.eclipse.imagen.media.codec.ImageEncoder;
import org.eclipse.imagen.media.codec.ImageEncodeParam;
import org.eclipse.imagen.media.codec.JPEGDecodeParam;
import org.eclipse.imagen.media.codec.JPEGEncodeParam;
import org.eclipse.imagen.media.codec.SeekableStream;

/**
 * @since EA2
 */
public final class JPEGCodec extends ImageCodec {

    public JPEGCodec() {}

    public String getFormatName() {
        return "jpeg";
    }

    public Class getEncodeParamClass() {
        return org.eclipse.imagen.media.codec.JPEGEncodeParam.class;
    }

    public Class getDecodeParamClass() {
        return org.eclipse.imagen.media.codec.JPEGDecodeParam.class;
    }

    public boolean canEncodeImage(RenderedImage im,
                                  ImageEncodeParam param) {
        return true;
    }

    protected ImageEncoder createImageEncoder(OutputStream dst,
                                              ImageEncodeParam param) {
        JPEGEncodeParam p = null;
        if (param != null) {
            p = (JPEGEncodeParam)param;
        }

        return new JPEGImageEncoder(dst, p);
    }

    protected ImageDecoder createImageDecoder(InputStream src,
                                              ImageDecodeParam param) {
        return new JPEGImageDecoder(src, param);
    }

    protected ImageDecoder createImageDecoder(File src,
                                              ImageDecodeParam param) 
        throws IOException {
        return new JPEGImageDecoder(new FileInputStream(src), param);
    }

    protected ImageDecoder createImageDecoder(SeekableStream src,
                                              ImageDecodeParam param) {
        return new JPEGImageDecoder(src, param);
    }

    public int getNumHeaderBytes() {
        return 3;
    }

    public boolean isFormatRecognized(byte[] header) {
        return ((header[0] == (byte)0xff) &&
                (header[1] == (byte)0xd8) &&
                (header[2] == (byte)0xff));
    }
}
