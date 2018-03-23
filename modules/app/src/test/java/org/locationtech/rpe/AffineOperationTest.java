package org.locationtech.rpe;

import org.junit.Test;

import java.awt.image.RenderedImage;

import static org.junit.Assert.assertNotNull;

public class AffineOperationTest {

    @Test
    public void testAffineOperationDispatch() {
        RenderedImage image  = new Affine().scale(10, 10).build();
        assertNotNull(image);
    }
}
