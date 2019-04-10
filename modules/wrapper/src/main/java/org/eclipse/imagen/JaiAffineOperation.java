package org.eclipse.imagen;

import javax.media.jai.InterpolationBilinear;
import javax.media.jai.InterpolationNearest;
import javax.media.jai.ParameterBlockJAI;
import javax.media.jai.registry.RenderedRegistryMode;
import java.awt.geom.AffineTransform;
import java.awt.image.RenderedImage;
import java.util.Map;

public class JaiAffineOperation extends Operation {

    public JaiAffineOperation() {
        super("affine");
    }

    public RenderedImage create(OperationBuilder builder) {
        AffineTransform transform = new AffineTransform();

        Map<String, Object> params = builder.getParameters();
        transform.setToScale(10, 10);
        if (params.containsKey("sx") && params.containsKey("sy")) {
            transform.setToScale((Double) params.get("sx"), (Double) params.get("sy"));
        }

        javax.media.jai.Interpolation interpolation;
        if (params.get("interpolation") == Interpolation.BILINEAR)
            interpolation = new InterpolationBilinear();
        else
            interpolation = new InterpolationNearest();

        return it.geosolutions.jaiext.affine.AffineDescriptor.create(
                (RenderedImage) builder.getSource(0),
                transform,
                interpolation,
                new double[]{},
                null,
                false,
                false,
                null,
                builder.hints
        );
    }
}
