# JAI Migration Tutorial

Raster Processing Engine offers a migration path for developers using the Java Advanced Imaging Framework.

## Simple JAI Program

Here is "A Simple JAI Program" from Programming in Java Advanced Imaging (1999 Sun Microsystems, Inc.):

     /*
      * Create an input stream from the specified file name
      * to be used with the file decoding operator.
      */
     FileSeekableStream stream = null;
     stream = new FileSeekableStream(args[0]);
     
     /* Create an operator to decode the image file. */
     RenderedOp image1 = JAI.create(“stream”, stream);
     
     /*
      * Create a standard bilinear interpolation object to be * used with the “scale” operator.
      */
     Interpolation interp = Interpolation.getInstance( Interpolation.INTERP_BILINEAR);

     /**
      * Stores the required input source and parameters in a
      * ParameterBlock to be sent to the operation registry,
      * and eventually to the “scale” operator.
      */
     ParameterBlock params = new ParameterBlock();
     params.addSource(image1);
     params.add(2.0F); // x scale factor
     params.add(2.0F); // y scale factor
     params.add(0.0F); // x translate
     params.add(0.0F); // y translate
     params.add(interp); // interpolation method
     
     /* Create an operator to scale image1. */
     RenderedOp image2 = JAI.create(“scale”, params);
     
     /* Get the width and height of image2. */
     int width = image2.getWidth();
     int height = image2.getHeight();
      
     /* Attach image2 to a scrolling panel to be displayed. */
     ScrollingImagePanel panel = new ScrollingImagePanel(image2, width, height);
                                     
     /* Create a frame to contain the panel. */
     Frame window = new Frame(“JAI Sample Program”);
     window.add(panel);
     window.pack();
     window.show();
     
Raster Processing Engine also offers a facade class providing high level access to library functionality. Use of literate programming reduces the need for untyped API contracts (such as parameter blocks).

Here is the sample approach used with raster processing engine library:

     /*
      * Create an input stream from the specified file name
      * to be used with the file decoding operator.
      */
     FileSeekableStream fileSeekableStream = new FileSeekableStream(args[0]);
     
     /* Create an operator to decode the image file. */
     Operation image1 = ImageRead.stream(fileSeekableStream);

     /*
      * Create a scale, with parameters defined using literate api
      */
     Operation image2 = Affine.source(image1)
                              .scale(2.0F,2.0F)
                              .interpolation(Interpolation.BILINEAR).create();
     
     /*
      * Get the width and height of image2.
      * (properties follow java bean naming conventions).
      */
     int width = image2.getWidth();
     int height = image2.getHeight();
      
     /*
      * Attach image2 to a scrolling panel to be displayed.
      */
     ScrollingImagePanel panel = new ScrollingImagePanel(image2, width, height);
                                     
     /*
      * Create a frame to contain the panel.
      */
     Frame window = new Frame(“RPE Sample Program”);
     window.add(panel);
     window.pack();
     window.show();
     
Notes:

- interpolation is always a hint and never a parameter
- example: scale, warp and a translate.

Options:



     image2 = REP.scale(2.0F,2.0F)..interpolation(Interpolation.BILINEAR);      
     image2 = RPE.scale(2.0F,2.0F,0.0F,0.0F).interpolate(Interpolation.BILINEAR).hint("layout", param );
     image4 = RPE.scale(2.0F,2.0F);
     image2 = RPE.image2.translate(0.0F,0.0F).interpolation(Interpolation.BILINEAR);

     image4 = RPE.scale( Interpolation.BILINEAR).scale(2.0F,2.0F).hint("layout",param);
     image4 = RPE.scale( "interpolate",Interpolation.BILINEAR, "layout",param).scale(2.0F,2.0F);
     // option 1 - param blocks - late binding approach
     // option 2 - descriptor with create method - early blinding approach parameters and then hint at the end. Unable to add extra parameters to them.
     // descriptor like but have building approach for each one, with open ended hint method
     // hint( Enum ) --> hint ("interpolation", Interpolation.BILENEAR);
     
     // option 3 - sld build approach 
     
