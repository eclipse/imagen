/* Copyright (c) 2018 Eugene Cheipesh and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, Version 2.0
 * which accompanies this distribution and is available at
 * http://www.opensource.org/licenses/apache2.0.php.
 */
package org.eclipse.imagen;

import it.geosolutions.jaiext.JAIExt;
import org.junit.Test;
import java.nio.file.Files;

import javax.media.jai.JAI;
import java.awt.image.RenderedImage;
import java.nio.file.Paths;

import static org.junit.Assert.assertNotNull;

public class AffineOperationTest {

    @Test
    public void testAffineOperationDispatch() {
        OperationDispatch dispatch = new OperationDispatch();
        dispatch.register(new JaiAffineOperation());

        String filename = "/Users/eugene/Downloads/occurrence_0E_60N.tif";

        if( Files.notExists( Paths.get(filename))){
            return; // skip test
        }

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
