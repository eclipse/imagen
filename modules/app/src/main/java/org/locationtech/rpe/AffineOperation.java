package org.locationtech.rpe;

import java.awt.image.RenderedImage;

import it.geosolutions.jaiext.affine.AffineDescriptor;

/**
 * TODO Refactor this away ?
 *
 * This may not be needed, the dispatch so far is happening in core
 */
public class AffineOperation extends Operation {
	public AffineOperation() {
		super(Affine.NAME);
	}

	@Override
	public RenderedImage create(OperationBuilder builder) {
		return OperationDispatch.getDefaultInstance().create(builder);
	}
}