package org.locationtech.rpe;

import java.awt.image.RenderedImage;

public abstract class Operation {
	final private String name;

	protected Operation(String name) {
		this.name = name;
	}

	/**
	 * Name of operation implemented by factory.
	 * 
	 * @return name of operation
	 */
	public String getName() {
		return name;
	}

	/**
	 * Create RenderedImage using named operation
	 * 
	 * @param builder
	 *            OperationBuilder, must match factory name
	 * @return RenderedImage
	 */
	public abstract RenderedImage create(OperationBuilder builder);

}
