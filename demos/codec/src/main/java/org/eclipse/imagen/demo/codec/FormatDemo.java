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
package org.eclipse.imagen.demo.codec;

import org.eclipse.imagen.media.codec.FileSeekableStream;
import org.eclipse.imagen.media.codec.ImageCodec;
import org.eclipse.imagen.media.codec.SeekableStream;

public class FormatDemo {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println(
"Format recognizer demo:\n");
            System.out.println(
"  Given a file, determine which file formats it may be encoded in.");
            System.out.println(
"  In addition to the standard formats, a \"samplepnm\" codec is");
            System.out.println(
"  registered.\n");
            System.out.println("usage: java FormatDemo <filenames>");
            System.exit(0);
        }

        // Register the sample PNM codec
        ImageCodec.registerCodec(new SamplePNMCodec());
        
        try {
            for (int i = 0; i < args.length; i++) {
                SeekableStream stream = new FileSeekableStream(args[i]);
                String[] names = ImageCodec.getDecoderNames(stream);

                System.out.println("File " +
                                   args[i] +
                                   " may be in the following format(s):");
                for (int j = 0; j < names.length; j++) {
                    System.out.println("\t" + names[j]);
                }

                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
