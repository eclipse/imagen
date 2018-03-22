package org.locationtech.rpe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.geom.AffineTransform;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;

import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

import org.junit.jupiter.api.Test;

class BuilderTest {
    @Test
    void testJAI() {
        String filename = "/Users/eugene/Downloads/occurrence_0E_60N.tif"; 
        // path and name of the file to be read,that is on an accessible filesystem //";
        RenderedImage image = JAI.create("fileload", filename);
        Raster data = image.getData();
        System.out.println(data.toString());
    }

    @Test
    void testBuilder() {
        new Affine()
            .scale(3,9)
            .rotate(1,2)
            .rotate(2,4)
            .build();
    }

    @Test
    void testOperationBuilder() {
        RenderedImage image1 = null;
        AffineTransform affine = null;

        // trying to dispatch to get Affine from JAIEXT
        // 1. construct a keyed parameter block
        // 2. use SPI to avoid direct dependency on dispatcher
        // 3. implemnt SPI to dispatch to JAI
        // ??? What is the dispatcher ?
        //  - knows which parameters are optional
        
        // actually its the SPI instances that will
        // know which parameters are optional or not to them
        RenderedImage image2 = 
            new OperationBuilder("Affine")
                .source(image1)
                .parameter( "affine", affine )
                .hint(Interpolation.BILINEAR)
                .build();
    }
}
