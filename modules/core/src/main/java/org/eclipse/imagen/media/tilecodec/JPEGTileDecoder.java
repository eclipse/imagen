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

package org.eclipse.imagen.media.tilecodec;

import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.awt.image.SampleModel;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.eclipse.imagen.JAI;
import org.eclipse.imagen.ParameterListDescriptor;
import org.eclipse.imagen.RasterFactory;
import org.eclipse.imagen.tilecodec.TileCodecDescriptor;
import org.eclipse.imagen.tilecodec.TileCodecParameterList;
import org.eclipse.imagen.tilecodec.TileDecoderImpl;
import org.eclipse.imagen.util.ImagingListener;
import com.sun.image.codec.jpeg.JPEGDecodeParam;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGQTable;
import sun.awt.image.codec.JPEGParam;
import org.eclipse.imagen.media.util.ImageUtil;
/**
 * A concrete implementation of the <code>TileDecoderImpl</code> class
 * for the jpeg tile codec.
 */
public class JPEGTileDecoder extends TileDecoderImpl {
    /* The associated TileCodecDescriptor */
    private TileCodecDescriptor tcd = null;

    /**
     * Constructs a <code>JPEGTileDecoder</code>.
     * <code>JPEGTileDecoder</code> may throw a
     * <code>IllegalArgumentException</code> if <code>param</code>'s
     * <code>getParameterListDescriptor()</code> method does not return
     * the same descriptor as that from the associated
     * <code>TileCodecDescriptor</code>'s
     * <code>getParameterListDescriptor</code> method for the "tileDecoder"
     * registry mode.
     *
     * <p> If param is null, then the default parameter list for decoding
     * as defined by the associated <code>TileCodecDescriptor</code>'s
     * <code>getDefaultParameters()</code> method will be used for decoding.
     *
     * @param input The <code>InputStream</code> to decode data from.
     * @param param  The object containing the tile decoding parameters.
     * @throws IllegalArgumentException if input is null.
     * @throws IllegalArgumentException if param is not appropriate.
     */
    public JPEGTileDecoder(InputStream input, TileCodecParameterList param) {
	super("jpeg", input, param);
        tcd = TileCodecUtils.getTileCodecDescriptor("tileDecoder", "jpeg");
    }

    /**
     * Returns a <code>Raster</code> that contains the decoded contents
     * of the <code>InputStream</code> associated with this
     * <code>TileDecoder</code>.
     *
     * <p>This method can perform the decoding correctly only when
     * <code>includesLocationInfoInfo()</code> returns true.
     *
     * @throws IOException if an I/O error occurs while reading from the
     * associated InputStream.
     * @throws IllegalArgumentException if the associated
     * TileCodecDescriptor's includesLocationInfoInfo() returns false.
     */
    public Raster decode() throws IOException{
	if (!tcd.includesLocationInfo())
	    throw new IllegalArgumentException(
		JaiI18N.getString("JPEGTileDecoder0") );
	return decode(null);
    }

    public Raster decode(Point location) throws IOException{
	SampleModel sm = null;
	byte[] data = null;

        ObjectInputStream ois = new ObjectInputStream(inputStream);

        try {
	    // read the quality and qualitySet from the stream
	    paramList.setParameter("quality", ois.readFloat());
	    paramList.setParameter("qualitySet", ois.readBoolean());
            sm = TileCodecUtils.deserializeSampleModel(ois.readObject());
	    location = (Point)ois.readObject();
            data = (byte[]) ois.readObject();
        }
        catch (ClassNotFoundException e) {
            ImagingListener listener =
                ImageUtil.getImagingListener((RenderingHints)null);
            listener.errorOccurred(JaiI18N.getString("ClassNotFound"),
                                   e, this, false);
//            e.printStackTrace();
	    return null;
        }
	finally {
            ois.close();
	}

	ByteArrayInputStream bais = new ByteArrayInputStream(data);
        JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(bais);

        Raster ras = decoder.decodeAsRaster()
			.createTranslatedChild(location.x, location.y);
	extractParameters(decoder.getJPEGDecodeParam(),
			  ras.getSampleModel().getNumBands());

	// set the original sample model to the decoded raster
	if (sm != null) {
	    int minX = ras.getMinX();
	    int minY = ras.getMinY();
	    int h = ras.getHeight();
	    int w = ras.getWidth();
	    double[] buf = ras.getPixels(minX, minY, w, h, (double[])null);
	    ras = RasterFactory.createWritableRaster(sm,
						     new Point(minX, minY));
	    ((WritableRaster)ras).setPixels(minX, minY, w, h, buf);
	}
	return ras;
    }

    private void extractParameters(JPEGDecodeParam jdp, int bandNum) {

	// extract the horizontal subsampling rates
	int[] horizontalSubsampling = new int[bandNum];
	for (int i = 0; i < bandNum; i++)
	    horizontalSubsampling[i] = jdp.getHorizontalSubsampling(i);
	paramList.setParameter("horizontalSubsampling", horizontalSubsampling);

	// extract the vertical subsampling rates
	int[] verticalSubsampling = new int[bandNum];
	for (int i = 0; i < bandNum; i++)
	    verticalSubsampling[i] = jdp.getVerticalSubsampling(i);
	paramList.setParameter("verticalSubsampling", verticalSubsampling);

	// if the quality is not set, extract the quantization tables from
	// the stream; otherwise, define them with the default values.
	if (!paramList.getBooleanParameter("qualitySet"))
	    for (int i = 0; i < 4; i++) {
		JPEGQTable table = jdp.getQTable(i);
		paramList.setParameter("quantizationTable"+i,
		    (table == null) ? null : table.getTable());
	    }
	else {
	    ParameterListDescriptor pld
		= paramList.getParameterListDescriptor();
	    for (int i = 0; i < 4; i++) {
		paramList.setParameter("quantizationTable"+i,
		    pld.getParamDefaultValue("quantizationTable"+i));
	    }
	}

	// extract the quantizationTableMapping
	int[] quanTableMapping = new int[bandNum];
	for (int i = 0; i < bandNum; i++)
	    quanTableMapping[i] = jdp.getQTableComponentMapping(i);
	paramList.setParameter("quantizationTableMapping", quanTableMapping);

	// extract the writeTableInfo and writeImageInfo
	paramList.setParameter("writeTableInfo", jdp.isTableInfoValid());
	paramList.setParameter("writeImageInfo", jdp.isImageInfoValid());

	// extract the restart interval
	paramList.setParameter("restartInterval", jdp.getRestartInterval());

	// define writeJFIFHeader by examing the APP0_MARKER is set or not
	paramList.setParameter("writeJFIFHeader",
			       jdp.getMarker(JPEGDecodeParam.APP0_MARKER));
    }
}

