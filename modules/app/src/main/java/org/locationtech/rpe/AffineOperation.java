package org.locationtech.rpe;

import java.awt.image.RenderedImage;

import it.geosolutions.jaiext.affine.AffineDescriptor;

// I need core parameter blocks
public class AffineOperation extends Operation {
	public AffineOperation() {
		super(Affine.NAME);
	}

	@Override
	public RenderedImage create(OperationBuilder builder) {
		AffineDescriptor descriptor = new AffineDescriptor();
        descriptor.isRenderableSupported();
        
		return null;
	}

}