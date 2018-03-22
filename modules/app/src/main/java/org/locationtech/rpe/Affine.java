package org.locationtech.rpe;

import java.awt.geom.AffineTransform;
import java.awt.image.RenderedImage;

// I need core parameter blocks
public class Affine extends OperationBuilder {
    private AffineTransform transformation = new AffineTransform();

    public Affine() {
        super("Affine");
    }

    Affine scale(double sx, double sy) {
        transformation.scale(sx, sy);
        return this;
    }

    Affine rotate(double vecx, double vecy) {
        transformation.rotate(vecx, vecy);
        return this;
    }

    Affine shear(double shx, double shy) {
        transformation.shear(shx, shy);
        return this;
    }

    Affine translate(double tx, double ty) {
        transformation.translate(tx, ty);
        return this;
    }

    RenderedImage build() {
        return null;
    }
}