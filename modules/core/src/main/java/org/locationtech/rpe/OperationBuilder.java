package org.locationtech.rpe;

import java.awt.image.RenderedImage;


public class OperationBuilder {
    String name;
  
    public OperationBuilder(String name) {
  		this.name = name;
    }

    OperationBuilder source(RenderedImage... source) {
      //TODO: Support named sources
      return this;
    }

    OperationBuilder parameter(String name, Object value) {
      return this;
    }

    OperationBuilder hint(String key, Object value) {
      return this;
    }

    OperationBuilder hint(KeyedHint hint) {
      return this;
    }

    RenderedImage build() {
      // Call to RPE dispatcher
      return null;
    }
}