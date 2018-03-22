package org.locationtech.rpe;

import java.awt.image.RenderedImage;

public abstract class Operation {
	/**
	 * Name of operation implemented by factory.
	 * 
	 * @return name of operation
	 */
	abstract public String getName();

	/**
	 * Create RenderedImage using named operation
	 * 
	 * @param builder
	 *            OperationBuilder, must match factory name
	 * @return RenderedImage
	 */
	abstract RenderedImage create(OperationBuilder builder);

}
