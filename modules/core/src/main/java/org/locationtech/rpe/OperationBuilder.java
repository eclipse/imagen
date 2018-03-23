/* Copyright (c) 2018 Jody Garnett and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/edl-v10.html
 */
package org.locationtech.rpe;

import java.awt.RenderingHints;
/**
 * 
 */
import java.awt.image.RenderedImage;
import java.util.Collections;
import java.util.Map;

public class OperationBuilder {
	/**
	 * Operation name.
	 */
	final String name;

	int sourceCount = 0;

	/** Parameter map */
	Map<String, Object> parameters;

	/** Optional rendering hints */
	RenderingHints hints;

	public OperationBuilder(String name) {
		if( name == null ) {
			throw new NullPointerException("OperationBuilder name is required");
		}
		this.name = name;
	}

	/**
	 * Add sources (will be named "source1","source2", ...)
	 * 
	 * @param renderedImage
	 * @return this builder is returned for chaining
	 */
	OperationBuilder source(RenderedImage... sources) {
		if (sources == null) {
			throw new NullPointerException("source is required");
		}
		for (RenderedImage source : sources) {
			source(source);
		}
		return this;
	}

	/**
	 * Add source (will be named "source1","source2", ...)
	 * 
	 * @param renderedImage
	 * @return this builder is returned for chaining
	 */
	OperationBuilder source(RenderedImage source) {
		if (source == null) {
			throw new NullPointerException("source is required");
		}
		String key = "source" + (sourceCount++);
		parameters.put(key, source);
		return this;
	}

	/**
	 * Add named parameter with the provided value. 
	 * 
	 * @param name parameter name
	 * @param value parameter value
	 * @return this builder is returned for chaining
	 */
	OperationBuilder parameter(String name, Object value) {
		parameter(name, value);
		return this;
	}
	/**
	 * Add named hint with the provided value. 
	 * 
	 * @param hint Rendering hint
	 * @param value hint value
	 * @return this builder is returned for chaining
	 */
	OperationBuilder hint(RenderingHints key, Object value) {
		hints.put(key, value);
		return this;
	}

	OperationBuilder hint(KeyedHint hint) {
		hints.put( hint.key(),  hint.value() );
		return this;
	}

	RenderedImage build() {
		// Call to RPE dispatcher
		return null;
	}

	public String getName() {
		return name;
	}
	
	public Map<String, Object> getParameters() {
		return Collections.unmodifiableMap(parameters);
	}
}