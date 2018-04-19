package org.locationtech.rpe;

import it.geosolutions.jaiext.JAIExt;
import org.junit.Test;

import javax.media.jai.JAI;
import java.awt.image.RenderedImage;

import static org.junit.Assert.assertNotNull;

public class AffineOperationTest {

    @Test
    public void testAffineOperationDispatch() {
        OperationDispatch dispatch = new OperationDispatch();
        dispatch.register(new JaiAffineOperation());

        String filename = "/Users/eugene/Downloads/occurrence_0E_60N.tif";
        RenderedImage source0 = JAI.create("fileload", filename);

        // 1. the parameters in RPE Operation and JAI operation may not match
        // - this could be fixed in core.Affine
        // - this could be fixed with some translator of core.Affine => (ParameterBlock, RenderingHints)
        //  - such translator would have to be tied to the factory
        //  - Yup, its name is JAIAffineOperation extends Operation

        OperationBuilder builder = new Affine(source0)
                .scale(10, 10);

        // Because JAIAffineOperation delegates to JAI dispatcher this will fail if JAIExt without init
        // - Probably the correct action is to make RPE dispatcher that composes over and drives
        //   multiple jai.OperationDispatcher. @see JAIOperationDispatcher.
        JAIExt.registerAllOperations(true);
        RenderedImage image  = dispatch.create(builder);

        assertNotNull(image);
    }
}
