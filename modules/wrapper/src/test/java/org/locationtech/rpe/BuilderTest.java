/* Copyright (c) 2018 Eugene Cheipesh and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution and is available at
 * http://www.opensource.org/licenses/apache2.0.php.
 */
package org.locationtech.rpe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.media.jai.*;
import javax.media.jai.Interpolation;
import javax.media.jai.operator.AffineDescriptor;
import javax.media.jai.registry.RenderedRegistryMode;
import javax.media.jai.util.Range;

import it.geosolutions.jaiext.JAIExt;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

class BuilderTest {


    @Test
    void testAffineParameterBlock() {
        // exploring what it takes to make a correct ParameterBlock for JAI

        String filename = "/Users/eugene/Downloads/occurrence_0E_60N.tif";
        if( Files.notExists( Paths.get(filename))){
            return; // skip test
        }

        RenderedImage source0 = JAI.create("fileload", filename);

        AffineTransform transform  = new AffineTransform();        
        javax.media.jai.Interpolation interp = new InterpolationBilinear();
        double[] backgroundValues = null;
        ROI roi = null;
        boolean useROIAccessor = false;
        boolean setDestinationNoData = false;
        Range nodata = null;
        RenderingHints hints = null;

        JAIExt.initJAIEXT();

        ParameterBlockJAI pb = new ParameterBlockJAI(
           "Affindde",
            RenderedRegistryMode.MODE_NAME);
        // Set the source image
        pb.setSource("source0", source0);
        transform.setToScale(10, 10);
        pb.setParameter("transform", transform);
        pb.setParameter("interpolation", interp);
        pb.setParameter("backgroundValues", backgroundValues);
        pb.setParameter("setDestinationNoData", setDestinationNoData);
        pb.setParameter("nodata", nodata);
        if (roi != null) {
            pb.setParameter("roi", roi);
            pb.setParameter("useROIAccessor", useROIAccessor);
        }


        return;
    }

    @Test
    void testJAI() {
        String filename = "/Users/eugene/Downloads/occurrence_0E_60N.tif";
        if( Files.notExists( Paths.get(filename))){
            return; // skip test
        }
        // path and name of the file to be read,that is on an accessible filesystem //";
        RenderedImage image = JAI.create("fileload", filename);
        Raster data = image.getData();
        System.out.println(data.toString());
    }

    @Ignore
    void testBuilder() {
       RenderedImage affine = new Affine()
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
        //RenderedImage image2 = new OperationBuilder("Affine").source(image1).parameter("affine", affine)
        //        .hint(Interpolation.BILINEAR).build();
    }
}
