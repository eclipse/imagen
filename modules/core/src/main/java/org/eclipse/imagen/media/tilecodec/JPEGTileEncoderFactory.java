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

package org.eclipse.imagen.media.tilecodec ;

import java.awt.image.SampleModel ;
import java.awt.image.DataBuffer ;
import java.io.OutputStream;
import java.util.Vector;
import org.eclipse.imagen.JAI;
import org.eclipse.imagen.ParameterListDescriptor;
import org.eclipse.imagen.ParameterListDescriptorImpl;
import org.eclipse.imagen.remote.NegotiableCapability;
import org.eclipse.imagen.remote.NegotiableNumericRange;
import org.eclipse.imagen.remote.NegotiableCollection;
import org.eclipse.imagen.tilecodec.TileCodecParameterList ;
import org.eclipse.imagen.tilecodec.TileEncoder ;
import org.eclipse.imagen.tilecodec.TileEncoderFactory ;

/**
 * A factory for creating <code>JPEGTileEncoder</code>s.
 *
 * <p> This class stipulates that the capabilities of the
 * <code>TileEncoder</code> be specified by implementing the
 * <code>getEncodingCapability()</code> method.
 *
 * @see org.eclipse.imagen.remote.NegotiableCapability
 */
public class JPEGTileEncoderFactory implements TileEncoderFactory {

    /**
     * Creates a <code>TileEncoder</code> capable of encoding a
     * <code>Raster</code> with the specified <code>SampleModel</code>
     * using the specified <code>TileCodecParameterList</code>
     * containing the encoding parameters to the given <code>OutputStream</code>.
     *
     * <p> This method can return null if the <code>TileEncoder</code> is not
     * capable of producing output for the given set of parameters.
     * For example, if a <code>TileEncoder</code> is only capable of dealing
     * with a <code>PixelInterleavedSampleModel</code>, and the supplied
     * <code>SampleModel</code> is not an instance of
     * <code>PixelInterleavedSampleModel</code>, null should be
     * returned. The supplied <code>SampleModel</code> should be used to
     * decide whether it can be encoded by this class, and is not needed
     * to actually construct a <code>TileEncoder</code>.
     *
     * <p> If the supplied <code>TileCodecParameterList</code> is null,
     * a default <code>TileCodecParameterList</code> from the
     * <code>TileCodecDescriptor</code> will be used to create the encoder.
     *
     * <p>Exceptions thrown by the <code>TileEncoder</code>
     * will be caught by this method and will not be propagated.
     *
     * @param output      The <code>OutputStream</code> to write the encoded
     *                    data to.
     * @param paramList   The <code>TileCodecParameterList</code> containing
     *                    the encoding parameters.
     * @param sampleModel The <code>SampleModel</code> of the encoded
     *                    <code>Raster</code>s.
     * @throws IllegalArgumentException if output is null.
     */
    public TileEncoder createEncoder(OutputStream output,
				     TileCodecParameterList paramList,
				     SampleModel sampleModel) {
	if(output == null)
	    throw new IllegalArgumentException(JaiI18N.getString("TileEncoder0"));
	int nbands = sampleModel.getNumBands() ;
	if(nbands != 1 && nbands != 3 && nbands != 4)
	    throw new IllegalArgumentException(
		JaiI18N.getString("JPEGTileEncoder0")) ;

	if(sampleModel.getDataType() != DataBuffer.TYPE_BYTE)
	    throw new IllegalArgumentException(
		JaiI18N.getString("JPEGTileEncoder1")) ;

	return new JPEGTileEncoder(output, paramList) ;
    }

    /**
     * Returns the capabilities of this <code>TileEncoder</code> as a
     * <code>NegotiableCapability</code>.
     */
    public NegotiableCapability getEncodeCapability() {

	Vector generators = new Vector();
	generators.add(JPEGTileEncoderFactory.class);

	ParameterListDescriptor jpegPld = 
	    JAI.getDefaultInstance().getOperationRegistry().getDescriptor("tileEncoder", "jpeg").getParameterListDescriptor("tileEncoder");

	Class paramClasses[] = {
	    org.eclipse.imagen.remote.NegotiableNumericRange.class,
	    org.eclipse.imagen.remote.NegotiableCollection.class,
	    // XXX How should a negotiable be created to represent int arrays
	    // integer array,  horizontal subsampling
	    // integer array,  vertical subsampling
	    // integer array,  quantization table mapping
	    // integer array,  quantizationTable0
	    // integer array,  quantizationTable1
	    // integer array,  quantizationTable2
	    // integer array,  quantizationTable3
	    org.eclipse.imagen.remote.NegotiableNumericRange.class,
	    org.eclipse.imagen.remote.NegotiableCollection.class,
	    org.eclipse.imagen.remote.NegotiableCollection.class,
	    org.eclipse.imagen.remote.NegotiableCollection.class
	};

	String paramNames[] = {
	    "quality",
	    "qualitySet",
	    "restartInterval",
	    "writeImageInfo",
	    "writeTableInfo",
	    "writeJFIFHeader"
	};

	// A collection containing the valid values for a boolean valued
	// parameters
	Vector v = new Vector();
	v.add(new Boolean(true));
	v.add(new Boolean(false));
	NegotiableCollection negCollection = new NegotiableCollection(v);

	NegotiableNumericRange nnr1 = 
	    new NegotiableNumericRange(
				  jpegPld.getParamValueRange(paramNames[0]));

	NegotiableNumericRange nnr2 = 
	    new NegotiableNumericRange(
				  jpegPld.getParamValueRange(paramNames[2]));

	// The default values
	Object defaults[] = {
	    nnr1, 
	    negCollection, 
	    nnr2,
	    negCollection,
	    negCollection,
	    negCollection
	};

	NegotiableCapability encodeCap =
	    new NegotiableCapability("tileCodec",
				     "jpeg",
				     generators,
				     new ParameterListDescriptorImpl(
							  null, // descriptor
							  paramNames,
							  paramClasses,
							  defaults,
							  null), // validValues
				     false); // a non-preference

	// Set the Negotiables representing the valid values on the capability
	encodeCap.setParameter(paramNames[0], nnr1);
	encodeCap.setParameter(paramNames[1], negCollection);
	encodeCap.setParameter(paramNames[2], nnr2);
	encodeCap.setParameter(paramNames[3], negCollection);
	encodeCap.setParameter(paramNames[4], negCollection);
	encodeCap.setParameter(paramNames[5], negCollection);

	return encodeCap;
    }
}
