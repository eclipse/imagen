package org.locationtech.rpe;

import javafx.util.Pair;

import javax.media.jai.JAI;
import javax.media.jai.ParameterBlockJAI;
import javax.media.jai.registry.RenderedRegistryMode;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.util.Map;

public class JAIOperationDispatch {
    // TODO: This should extent OperationDispatch when they have similar shapes

    public ParameterBlock getParameterBlock(OperationBuilder builder) {
        ParameterBlockJAI pb = new ParameterBlockJAI(builder.name, RenderedRegistryMode.MODE_NAME);
        Map<String, Object> params = builder.getParameters();

        for (String name: params.keySet()) {
            pb.setParameter(name, params.get(name));
        }
        return pb;
    }

    public RenderingHints getRenderingHints(OperationBuilder builder){
        return builder.hints;
    }

    public RenderedImage create(OperationBuilder builder) {
        return JAI.create(builder.name, getParameterBlock(builder), builder.hints);
    }
}
