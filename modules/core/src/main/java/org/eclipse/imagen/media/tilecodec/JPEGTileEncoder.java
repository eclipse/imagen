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

package org.eclipse.imagen.media.tilecodec ;

import java.awt.Point;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.awt.image.SampleModel;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import org.eclipse.imagen.JAI ;
import org.eclipse.imagen.ParameterListDescriptor ;
import org.eclipse.imagen.RasterFactory ;
import org.eclipse.imagen.tilecodec.TileCodecDescriptor ;
import org.eclipse.imagen.tilecodec.TileCodecParameterList ;
import org.eclipse.imagen.tilecodec.TileEncoderImpl ;
import com.sun.image.codec.jpeg.JPEGEncodeParam ;
import com.sun.image.codec.jpeg.JPEGImageEncoder ;
import com.sun.image.codec.jpeg.JPEGCodec ;
import com.sun.image.codec.jpeg.JPEGQTable ;
import sun.awt.image.codec.JPEGParam ;

/**
 * A concrete implementation of the <code>TileEncoderImpl</code> class
 * for the jpeg tile codec.
 */
public class JPEGTileEncoder extends TileEncoderImpl {
    /* The associated TileCodecDescriptor */
    private TileCodecDescriptor tcd = null ;

    /**
     * Constructs an <code>JPEGTileEncoder</code>. Concrete implementations
     * of <code>TileEncoder</code> may throw an
     * <code>IllegalArgumentException</code> if the
     * <code>param</code>'s <code>getParameterListDescriptor()</code> method
     * does not return the same descriptor as that from the associated
     * <code>TileCodecDescriptor</code>'s 
     * <code>getParameterListDescriptor</code> method for the "tileEncoder" 
     * registry mode. 
     *
     * <p> If param is null, then the default parameter list for encoding
     * as defined by the associated <code>TileCodecDescriptor</code>'s 
     * <code>getDefaultParameters()</code> method will be used for encoding.
     *
     * @param output The <code>OutputStream</code> to write encoded data to.
     * @param param  The object containing the tile encoding parameters.
     * @throws IllegalArgumentException if param is not the appropriate 
     * Class type.
     * @throws IllegalArgumentException is output is null.
     */
    public JPEGTileEncoder(OutputStream output, TileCodecParameterList param) {
        super("jpeg", output, param) ;
        tcd = TileCodecUtils.getTileCodecDescriptor("tileEncoder", "jpeg");
    }

    /**
     * Encodes a <code>Raster</code> and writes the output
     * to the <code>OutputStream</code> associated with this 
     * <code>TileEncoder</code>.
     *
     * @param ras the <code>Raster</code> to encode.
     * @throws IOException if an I/O error occurs while writing to the 
     * OutputStream.
     * @throws IllegalArgumentException if ras is null.
     */
    public void encode(Raster ras) throws IOException {
	if(ras == null)
	    throw new IllegalArgumentException(
		JaiI18N.getString("TileEncoder1")) ;

	ByteArrayOutputStream baos = new ByteArrayOutputStream() ;

	SampleModel sm = ras.getSampleModel() ;

	JPEGEncodeParam j2dEP = convertToJ2DJPEGEncodeParam(paramList, sm) ;
        ((JPEGParam)j2dEP).setWidth(ras.getWidth()) ; 
	((JPEGParam)j2dEP).setHeight(ras.getHeight()) ;

	JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(baos, j2dEP) ;
	encoder.encode(ras) ;

	byte[] data = baos.toByteArray() ;

	ObjectOutputStream oos = new ObjectOutputStream(outputStream) ;
	oos.writeFloat(paramList.getFloatParameter("quality"));
	oos.writeBoolean(paramList.getBooleanParameter("qualitySet"));
	oos.writeObject(TileCodecUtils.serializeSampleModel(sm));

	Point location = new Point( ras.getMinX(), ras.getMinY() ) ;
	oos.writeObject( location ) ;

	oos.writeObject( data ) ;
	oos.close() ;
    }

    private JPEGEncodeParam convertToJ2DJPEGEncodeParam(
	TileCodecParameterList paramList, SampleModel sm) {

        if(sm == null)
            return null ;

        int nbands = sm.getNumBands() ;

        JPEGParam j2dJP = createDefaultJ2DJPEGEncodeParam(nbands) ;

        int[] hSubSamp
            = (int[])paramList.getObjectParameter("horizontalSubsampling") ;
        int[] vSubSamp
            = (int[])paramList.getObjectParameter("verticalSubsampling") ;
        int[] qTabSlot
            = (int[])paramList.getObjectParameter("quantizationTableMapping") ;

        for(int i=0; i<nbands; i++) {
            j2dJP.setHorizontalSubsampling(i, hSubSamp[i]) ;
            j2dJP.setVerticalSubsampling(i, vSubSamp[i]) ;

            int[] qTab
                 = (int[]) paramList.getObjectParameter("quantizationTable"+i) ;
	    if(qTab != null && 
	       qTab.equals(ParameterListDescriptor.NO_PARAMETER_DEFAULT)){ 
		j2dJP.setQTableComponentMapping(i, qTabSlot[i]) ;
		j2dJP.setQTable(qTabSlot[i], new JPEGQTable(qTab)) ;
	    }
        }

        if(paramList.getBooleanParameter("qualitySet")) {
            float quality = paramList.getFloatParameter("quality") ;
            j2dJP.setQuality(quality, true) ;
        }

        int rInt = paramList.getIntParameter("restartInterval") ;
        j2dJP.setRestartInterval(rInt) ;

        j2dJP.setImageInfoValid(paramList.getBooleanParameter("writeImageInfo")) ;
        j2dJP.setTableInfoValid(paramList.getBooleanParameter("writeTableInfo")) ;

        if(paramList.getBooleanParameter("writeJFIFHeader")) {
            j2dJP.setMarkerData(JPEGEncodeParam.APP0_MARKER, null) ;
        }

        return (JPEGEncodeParam)j2dJP ;
    }

    private JPEGParam createDefaultJ2DJPEGEncodeParam(int nbands){
        if(nbands == 1)
            return new JPEGParam(JPEGEncodeParam.COLOR_ID_GRAY, 1) ;
        if(nbands == 3)
            return new JPEGParam(JPEGEncodeParam.COLOR_ID_YCbCr, 3) ;
        if(nbands == 4)
            return new JPEGParam(JPEGEncodeParam.COLOR_ID_CMYK, 4) ;
	return null ;
    }
}
