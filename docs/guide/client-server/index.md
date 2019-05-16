---
layout: default
title: Client-Server Imaging
parent: Programming Guide
nav_order: 14
---



  -----------------------------------------
    C H A P T E R![](shared/sm-space.gif)12
  -----------------------------------------


+----------------------------------------------------------------------:+
| -------------------------------------------------------------------   |
|                                                                       |
| Client-Server Imaging                                                 |
+-----------------------------------------------------------------------+

\
\
\

**T**HIS chapter describes JAI\'s client-server imaging system.


12.1 ![](shared/space.gif)Introduction
--------------------------------------

Client-server imaging provides the ability to distribute computation
between a set of processing nodes. For example, it is possible to set
up a large, powerful server that provides image processing services to
several thin clients. With JAI, it is possible for a client to set up
a complex imaging chain on a server, including references to source
images on other network hosts, and to request rendered output from the
server.

JAI uses Java Remote Method Invocation (RMI) to implement
client-server imaging. To communicate using Remote Method Invocation,
both the client and server must be running Java. A *stub* object is
instantiated on the client. The stub object forwards its method calls
to a corresponding server object. Method call arguments and return
values are transmitted between the client and server by means of the
Java Development Environment\'s *serialization* capability.

The hostname and port depend on the local setup. The host must be
running an RMI registry process and have a `RemoteImageServer`
listening at the desired port.

This call will result in the creation of a server-side `RMIImageImpl`
object and a client-side stub object. The client stub serializes its
method arguments and transfers them to the server over a socket; the
server serializes its return values and returns them in the same
manner.


12.2 ![](shared/space.gif)Server Name and Port Number
-----------------------------------------------------

The `RemoteImage` constructor requires a `serverName` parameter that
consists of a host name and port number, in the following format:

         host:port

For example:

         camus.eng.sun.com:1099

The port number is optional and need be supplied only if the host name
is supplied. If the `serverName` parameter is null, the default is to
search for the RMIImage service on the local host at the default
*rmiregistry* port (1099.

**API:** 
|                                   | `javax.media.jai.RemoteImage`     |

    RemoteImage(String serverName, RenderedImage source)

:   constructs a `RemoteImage` from a `RenderedImage`.
    *Parameters*:
    `serverName`
    The name of the server in the appropriate format.
    `source`
    A `RenderedImage` source.


    RemoteImage(String serverName, RenderedOp source)

:   constructs a `RemoteImage` from a `RenderedOp`, i.e., an imaging
    DAG (directed acyclic graph). Note that the properties of the
    `RemoteImage` will be those of the `RenderedOp` node and not of
    its rendering.


    RemoteImage(String serverName, RenderableOp source, 
    RenderContext renderContext)

:   constructs a `RemoteImage` from a `RenderableOp` and
    `RenderContext`. The entire `RenderableOp` DAG will be copied over
    to the server. Note that the properties of the `RemoteImage` will
    be those of the `RenderableOp` node and not of its rendering.


12.3 ![](shared/space.gif)Setting the Timeout Period and Number of Retries
--------------------------------------------------------------------------

A network error or a delay caused by the server failing to respond to
the request for an image is dealt with through retries. If, on the
first attempt, the server fails to respond, the program will wait a
specified amount of time and then make another request for the image.
When the limit of retries is exceeded, a null Raster may be returned.

The amount of time to wait between retries defaults to 1 second (1000
milliseconds). The `getTimeout` method is used to get the amount of
time between retries, in milliseconds. The `setTimeout` method is used
to set the amount of time between retries.

The number of times the program will attempt to read the remote image
may be read with the `getNumRetries` method. The `setNumRetries`
method is used to set the maximum number of retries.

**API:** 
|                                   | `javax.media.jai.RemoteImage`     |

    void setTimeout(int timeout)

:   sets the amount of time between retries.
      -------------- ----------- ----------------------------------------------------
      *Parameter*:   `timeout`   The time interval between retries in milliseconds.
      -------------- ----------- ----------------------------------------------------

      : 


    int getTimeout()

:   returns the amount of time between retries.


    void setNumRetries(int numRetries)

:   sets the number of retries.
      -------------- -------------- -------------------------------------------------------------------------------------------------
      *Parameter*:   `numRetries`   The maximum number of retries. If this is a negative value, the number of retries is unchanged.
      -------------- -------------- -------------------------------------------------------------------------------------------------

      : 


12.4 ![](shared/space.gif)Remote Imaging Test Example
-----------------------------------------------------

This section contains two examples of remote imaging programs.


### 12.4.1 ![](shared/space.gif)Simple Remote Imaging Example

[Listing 12-1](Client-server.doc.html#54012) shows a complete code
example of a `RemoteImaging` test. This example displays a 2 x 2 grid
of `ScrollingImagePanel`s, with each window displaying the sum of two
byte images that were rescaled to the range \[0,127\] prior to
addition. The panels display the following specific results:

-   upper left: local rendering


-   upper right: result of remote rendering of a RenderedOp graph


-   lower left: result of remote loading of a RenderedImage


-   lower right: result of remote rendering of a RenderableOp graph

The lower right image is a dithered version of the sum image passed
through a color cube lookup table and may appear slightly different
from the other three images, which should be identical.

**[]{#54012}**

***Listing 12-1* ![](shared/sm-blank.gif) Remote Imaging Example
Program (Sheet 1 of 4)**

------------------------------------------------------------------------

         import java.awt.*;
         import java.awt.event.WindowEvent;
         import java.awt.geom.*;
         import java.awt.image.*;
         import java.awt.image.renderable.*;
         import java.util.*;
         import javax.media.jai.*;
         import javax.media.jai.operator.*;
         import javax.media.jai.widget.*;
         public class RemoteImagingTest extends WindowContainer {

         /** Default remote server. */
         private static final String DEFAULT_SERVER =
                                  "camus.eng.sun.com:1099";

         /** Tile dimensions. */
         private static final int TILE_WIDTH = 256;
         private static final int TILE_HEIGHT = 256;

         public static void main(String args[]) {
         String fileName1 = null;
         String fileName2 = null;

         // Check args.
             if(!(args.length >= 0 && args.length <= 3)) {
                System.out.println("\nUsage: java RemoteImagingTest "+
                               "[[[serverName] | [fileName1 fileName2]] | "+
                               "[serverName fileName1 fileName2]]"+"\n");
                System.exit(1);
             }

         // Set the server name.
         String serverName = null;
            if(args.length == 0 || args.length == 2) {
                serverName = DEFAULT_SERVER;
                System.out.println("\nUsing default server '"+
                                   DEFAULT_SERVER+"'\n");
            } else {
                serverName = args[0];
            }

         // Set the file names.
            if(args.length == 2) {
                fileName1 = args[0];
                fileName2 = args[1];
            } else if(args.length == 3) {
                fileName1 = args[1];
                fileName2 = args[2];
            } else {
          fileName1 = "/import/jai/JAI_RP/test/images/Boat_At_Dock.tif";
          fileName2 = "/import/jai/JAI_RP/test/images/FarmHouse.tif";
                System.out.println("\nUsing default images '"+
                                   fileName1 + "' and '" + fileName2 + "'\n");
              }

         RemoteImagingTest riTest =
                new RemoteImagingTest(serverName, fileName1, fileName2);
             }

         /**
         * Run a remote imaging test.
         *
         * @param serverName The name of the remote server to use.
         * @param fileName1 The first addend image file to use.
         * @param fileName2 The second addend image file to use.
         */
         RemoteImagingTest(String serverName, String fileName1, String
                           fileName2) {
         // Create the operations to load the images from files.
         RenderedOp src1 = JAI.create("fileload", fileName1);
         RenderedOp src2 = JAI.create("fileload", fileName2);

         // Render the sources without freezing the nodes.
         PlanarImage ren1 = src1.createInstance();
         PlanarImage ren2 = src2.createInstance();

         // Create TiledImages with the file images as their sources
         // thereby ensuring that the serialized images are truly tiled.
            SampleModel sampleModel1 =
         ren1.getSampleModel().createCompatibleSampleModel(TILE_WIDTH,
                                                           TILE_HEIGHT);
         TiledImage ti1 = new TiledImage(ren1.getMinX(), ren1.getMinY(),
                                         ren1.getWidth(), ren1.getHeight(),
                                         ren1.getTileGridXOffset(),
                                         ren1.getTileGridYOffset(),
                                         sampleModel1, ren1.getColorModel());
         ti1.set(src1);
         SampleModel sampleModel2 =
           ren2.getSampleModel().createCompatibleSampleModel(TILE_WIDTH,
                                                             TILE_HEIGHT);
          TiledImage ti2 = new TiledImage(ren2.getMinX(), ren2.getMinY(),
                                          ren2.getWidth(), ren2.getHeight(),
                                          ren2.getTileGridXOffset(),
                                          ren2.getTileGridYOffset(),
                                        sampleModel2, ren2.getColorModel());
           ti2.set(src2);

         // Create a hint to specify the tile dimensions.
         ImageLayout layout = new ImageLayout();
         layout.setTileWidth(TILE_WIDTH).setTileHeight(TILE_HEIGHT);
              RenderingHints rh = new
                  RenderingHints(JAI.KEY_IMAGE_LAYOUT, layout);

         // Rescale the images to the range [0, 127].
         ParameterBlock pb = (new ParameterBlock());
         pb.addSource(ti1);
         pb.add(new double[] {0.5}).add(new double[] {0.0});
         RenderedOp addend1 = JAI.create("rescale", pb, rh);
         pb = (new ParameterBlock());
         pb.addSource(ti2);
         pb.add(new double[] {0.5}).add(new double[] {0.0});
         RenderedOp addend2 = JAI.create("rescale", pb, rh);

         // Add the rescaled images.
         pb = (new
            ParameterBlock()).addSource(addend1).addSource(addend2);
                 RenderedOp sum = JAI.create("add", pb, rh);

                 // Dither the sum of the rescaled images.
                 pb = (new ParameterBlock()).addSource(sum);
                 
    pb.add(ColorCube.BYTE_496).add(KernelJAI.DITHER_MASK_443);
                 RenderedOp dithered = JAI.create("ordereddither", pb, rh);

         // Construct a RemoteImage from the RenderedOp chain.
         RemoteImage remoteImage = new RemoteImage(serverName, sum);

         // Set the display title and window layout.
         setTitle(getClass().getName());
         setLayout(new GridLayout(2, 2));

         // Local rendering.
         add(new ScrollingImagePanel(sum,
                                     sum.getWidth(),
                                     sum.getHeight()));

         // RenderedOp remote rendering.
         add(new ScrollingImagePanel(remoteImage,
                                     remoteImage.getWidth(),
                                     remoteImage.getHeight()));

         // RenderedImage remote rendering
         PlanarImage sumImage = sum.getRendering();
         remoteImage = new RemoteImage(serverName, sumImage);
         add(new ScrollingImagePanel(remoteImage,
                                     remoteImage.getWidth(),
                                     remoteImage.getHeight()));

         // RenderableOp remote rendering.
         pb = new ParameterBlock();
         pb.addSource(dithered);
         RenderableOp absImage = JAI.createRenderable("absolute", pb);
         pb = new ParameterBlock();
         pb.addSource(absImage).add(ColorCube.BYTE_496);
         RenderableOp lutImage = JAI.createRenderable("lookup", pb);
         AffineTransform tf =
                AffineTransform.getScaleInstance(384/dithered.getWidth(),
                                                 256/dithered.getHeight());
         Rectangle aoi = new Rectangle(128, 128, 384, 256);
         RenderContext rc = new RenderContext(tf, aoi, rh);
         remoteImage = new RemoteImage(serverName, lutImage, rc);
         add(new ScrollingImagePanel(remoteImage,
                                     remoteImage.getWidth(),
                                     remoteImage.getHeight()));

         // Finally display everything
                 pack();
                 show();
             }
         }

------------------------------------------------------------------------


### 12.4.2 ![](shared/space.gif)RemoteImaging Example Across Two Nodes

[Listing 12-2](Client-server.doc.html#52766) shows an example of a
RemoteImaging chain spread across two remote nodes, and displays the
results locally.

**[]{#52766}**

***Listing 12-2* ![](shared/sm-blank.gif) RemoteImaging Example
Program Using Two Nodes (Sheet 1 of 2)**

------------------------------------------------------------------------

         import java.awt.image.*;
         import java.awt.image.renderable.ParameterBlock;
         import javax.media.jai.*;
         import javax.media.jai.widget.*;

         /**
          * This test creates an imaging chain spread across two remote 
          * nodes and displays the result locally.
          */

         public class MultiNodeTest extends WindowContainer {
             public static void main(String[] args) {
                 if(args.length != 3) {
                   throw new RuntimeException("Usage: java MultiNodeTest "+
                                                "file node1 node2");
                 }

                 new MultiNodeTest(args[0], args[1], args[2]);
             }
         public MultiNodeTest(String fileName, String node1, String
                              node2) {

         // Create a chain on node 1.
         System.out.println("Creating dst1 = log(invert(fileload("+
                                    fileName+"))) on "+node1);
                 RenderedOp src = JAI.create("fileload", fileName);
                 RenderedOp op1 = JAI.create("invert", src);
                 RenderedOp op2 = JAI.create("log", op1);
                 RemoteImage rmt1 = new RemoteImage(node1, op2);

         // Create a chain on node 2.
         System.out.println("Creating dst2 = not(exp(dst1)) on "+node2);
                 RenderedOp op3 = JAI.create("exp", rmt1);
                 RenderedOp op4 = JAI.create("not", op3);
                 RemoteImage rmt2 = new RemoteImage(node2, op4);

         // Display the result of node 2.
         System.out.println("Displaying results");
         setTitle(getClass().getName()+" "+fileName);
         add(new ScrollingImagePanel(rmt2, rmt2.getWidth(),
                                     rmt2.getHeight()));
                 pack();
                 show();
             }
         }

------------------------------------------------------------------------

**API:** 
|                                   | `javax.media.jai.RemoteImage`     |

    int getWidth()

:   returns the width of the `RemoteImage`.


    int getHeight()

:   returns the height of the `RemoteImage`.


    Raster getData()

:   returns the entire image as one large tile.


    Raster getData(Rectangle rect)

:   returns an arbitrary rectangular region of the `RemoteImage`.
      ------------- -------- -------------------------------------------------
      Parameters:   `rect`   The region of the `RemoteImage` to be returned.
      ------------- -------- -------------------------------------------------

      : 


    WritableRaster copyData(WritableRaster raster)

:   returns an arbitrary rectangular region of the `RemoteImage` in a
    user-supplied `WritableRaster`. The rectangular region is the
    entire image if the argument is null or the intersection of the
    argument bounds with the image bounds if the region is non-null.
    If the argument is non-null but has bounds that have an empty
    intersection with the image bounds, the return value will be null.
    The return value may also be null if the argument is non-null but
    is incompatible with the `Raster` returned from the remote image.
      --------------- ---------- ---------------------------------------------------------------
      *Parameters*:   `raster`   A `WritableRaster` to hold the returned portion of the image.
      --------------- ---------- ---------------------------------------------------------------

      : 

    
:   If the `raster` argument is null, the entire image will be copied
    into a newly-created WritableRaster with a SampleModel that is
    compatible with that of the image.


    Raster getTile(int x, int y)

:   returns a tile (*x*, *y*). Note that *x* and *y* are indices into
    the tile array, not pixel locations. Unlike in the true
    `RenderedImage` interface, the `Raster` that is returned should be
    considered a copy.
    *Parameters*:
    `x`
    The *x* index of the requested tile in the tile array
    y
    The *y* index of the requested tile in the tile array


12.5 ![](shared/space.gif)Running Remote Imaging
------------------------------------------------

To run remote imaging in JAI, you have to do the following:

These four steps are explained in more detail in the following
sections.


### 12.5.1 ![](shared/space.gif)Step 1: Create a Security Policy File

The default RMI security policy implementation is specified within one
or more policy configuration files. These configuration files specify
what permissions are allowed for code from various sources. There is a
default system-wide policy file and a single user policy file. For
more information on policy files and permissions, see:

         http://java.sun.com/products/jdk/1.2/docs/guide/security/
    PolicyFiles.html
         http://java.sun.com/products/jdk/1.2/docs/guide/security/
    permissions.html

The policy file is located in the base directory where Java Advanced
Imaging is installed. If `$JAI` is the base directory where Java
Advanced Imaging is installed, use any simple text editor to create a
text file named `$JAI/policy` containing the following:

         grant {
         // Allow everything for now
            permission java.security.AllPermission;
         };

Note that this policy file is for testing purposes only.


### 12.5.2 ![](shared/space.gif)Step 2: Start the RMI Registry

The RMI registry is a simple server-side name server that allows
remote clients to get a reference to a remote object. Typically, the
registry is used only to locate the first remote object an application
needs to talk to. Then that object in turn provides
application-specific support for finding other objects.

------------------------------------------------------------------------

**Note:** Before starting the rmiregistry, make sure that the shell or
window in which you will run the registry either has no `CLASSPATH`
set or has a `CLASSPATH` that does not include the path to any classes
you want downloaded to your client, including the stubs for your
remote object implementation classes.

------------------------------------------------------------------------

To start the registry on the server, log in to the remote system where
the image server will be running and execute the `rmiregistry`
command.

For example, in the **Solaris** operating environment using a
Bourne-compatible shell (e.g., /bin/sh):

         $ unset CLASSPATH
         $ rmiregistry &

Note that the `CLASSPATH` environment variable is deliberately not
set.

For example, on **Windows 95** or **Windows NT**:

         start rmiregistry

If the `start` command is not available, use `javaw`.


### 12.5.3 ![](shared/space.gif)Step 3: Start the Remote Image Server

While still logged in to the remote server system, set the `CLASSPATH`
and `LD_LIBRARY_PATH` environment variables as required for JAI (see
the `INSTALL` file) and start the remote imaging server. For example:

         $ CLASSPATH=$JAI/lib/jai.jar:\
                     $JAI/lib/mlibwrapper_jai.jar
         $ export CLASSPATH
         $ LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$JAI/lib
         $ export LD_LIBRARY_PATH
         $ java \
         -Djava.rmi.server.codebase=\
              file:$JAI/lib/jai.jar \
         -Djava.rmi.server.useCodebaseOnly=false \
         -Djava.security.policy=file:$JAI/policy \
               com.sun.media.jai.rmi.RMIImageImpl

For example, when the above steps are executed on a machine with IP
address 123.456.78.90 the following is printed:

         Server: using host 123.456.78.90 port 1099
         Registering image server as
           "rmi://123.456.78.90:1099/RemoteImageServer".
         Server: Bound RemoteImageServer into
           the registry.


### 12.5.4 ![](shared/space.gif)Step 4: Run the Local Application

After completing steps 1 through 3, you are ready to run the local
application. When running the local application, make sure that the
`serverName` parameter of any RemoteImage constructors corresponds to
the machine on which the remote image server is running. For example,
if the machine with IP address 123.456.78.90 above is named
`myserver`, the `serverName` parameter of any `RemoteImage()`
constructors should be `"myserver"`.


12.6 ![](shared/space.gif)Internet Imaging Protocol (IIP)
---------------------------------------------------------

There are two JAI operations that support Internet Imaging Protocol
(IIP) operations. Two separate operations provide client-side support
of the Internet Imaging Protocol. These operations, `IIP` and
`IIPResolution`, request an image from an IIP server then create
either a RenderedImage or a RenderableImage.


### 12.6.1 ![](shared/space.gif)IIP Operation

The `IIP` operation provides client-side support of the Internet
Imaging Protocol (IIP) in both the rendered and renderable modes. It
creates a `RenderedImage` or a `RenderableImage` based on the data
received from the IIP server, and optionally applies a sequence of
operations to the created image.

The operations that may be applied and the order in which they are
applied are defined in section 2.2.1.1 of the *Internet Imaging
Protocol Specification* version 1.0.5. Some or all of the requested
operations may be executed on the IIP server if it is determined that
the server supports such operations. Any of the requested operations
not supported by the server will be executed on the host on which the
operation chain is rendered.

The processing sequence for the supplied operations is as follows:

-   Filtering (blur or sharpen)


-   Tone and color correction (\"color twist\")


-   Contrast adjustment


-   Selection of source rectangle of interest


-   Spatial orientation (rendering-independent affine transformation)


-   Selection of destination rectangle of interest


-   Rendering transformation (renderable mode only)


-   Transposition (rotation and/or mirroring).

As indicated, the rendering transformation is performed only in
renderable mode processing. This transformation is derived from the
AffineTransform supplied in the RenderContext when rendering actually
occurs. Rendered mode processing creates a RenderedImage which is the
default rendering of the RenderableImage created in renderable mode
processing.

The `IIP` operation takes 14 parameters.

  -------------------------------------------------------------------------------------------------------------------------------------------------
  [Parameter]{#55324}      [Type]{#55326}                  [Description]{#55328}
  ------------------------ ------------------------------- ----------------------------------------------------------------------------------------
  [URL]{#55330}\           [String]{#55332}\               [The URL of the IIP image]{#55334}\

  [subImages]{#55336}\     [int\[\]]{#55338}\              [The sub-images to be used by the server for images at each resolution level]{#55340}\

  [filter]{#55342}\        [Float]{#55344}\                [The filtering value]{#55346}\

  [colorTwist]{#55424}\    [float\[\]]{#55426}\            [The color twist matrix]{#55428}\

  [contrast]{#55418}\      [Float]{#55420}\                [The contrast value]{#55422}\

  [sourceROI]{#55412}\     [Rectangle2D.Float]{#55414}\    [The source rectangle of interest in rendering-independent coordinates]{#55416}\

  [transform]{#55406}\     [AffineTransform]{#55408}\      [The rendering-independent spatial orientation transform]{#55410}\

  [aspectRatio]{#55400}\   [Float]{#55402}\                [The aspect ratio of the destination image]{#55404}\

  [destROI]{#55394}\       [Rectangle2D.Float]{#55396}\    [The destination rectangle of interest in rendering-independent coordinates]{#55398}\

  [rotation]{#55388}\      [Integer]{#55390}\              [The counterclockwise rotation angle to be applied to the destination]{#55392}\

  [mirrorAxis]{#55382}\    [String]{#55384}\               [The mirror axis]{#55386}\

  [ICCProfile]{#55376}\    [color.ICC\_Profile]{#55378}\   [The ICC profile used to represent the color space of the source image]{#55380}\

  [JPEGQuality]{#55370}\   [Integer]{#55372}\              [The JPEG quality factor]{#55374}\

  [JPEGTable]{#55364}\     [Integer]{#55366}\              [The JPEG compression group index number]{#55368}\
  -------------------------------------------------------------------------------------------------------------------------------------------------

  : 

The `URL` parameter specifies the URL of the IIP image as a
`java.lang.String`. It must represent a valid URL and include any
required FIF or SDS commands. It cannot be null.

The `subImages` parameter optionally indicates the sub-images to be
used by the server to get the images at each resolution level. The
values in this `int` array cannot be negative. If this parameter is
not specified, or if the array is too short (length is 0), or if a
negative value is specified, this operation will use the zeroth
sub-image of the resolution level actually processed.

The `filter` parameter specifies a blur or sharpen operation; a
positive value indicates sharpen and a negative value blur. A unit
step should produce a perceptible change in the image. The default
value is 0 which signifies that no filtering will occur.

The `colorTwist` parameter represents a 4 x 4 matrix stored in
row-major order and should have an array length of at least 16. If an
array of length greater than 16 is specified, all elements from index
16 and beyond are ignored. Elements 12, 13, and 14 must be 0. This
matrix will be applied to the (possibly padded) data in an
intermediate normalized PhotoYCC color space with a premultiplied
alpha channel. This operation will force an alpha channel to be added
to the image if the last column of the last row of the color twist
matrix is not 1.0F. Also, if the image originally has a grayscale
color space it will be cast up to RGB if casting the data back to
grayscale after applying the color twist matrix would result in any
loss of data. The default value is null.

The `contrast` parameter specifies a contrast enhancement operation
with increasing contrast for larger value. It must be greater than or
equal to 1.0F. A value of 1.0F indicates no contrast adjustment. The
default value is 1.0F.

The `sourceROI` parameter specifies the rectangle of interest in the
source image in rendering-independent coordinates. The intersection of
this rectangle with the rendering-independent bounds of the source
image must equal itself. The rendering-independent bounds of the
source image are defined to be (0.0F, 0.0F, r, 1.0F) where r is the
aspect ratio (width/height) of the source image. Note that the source
image will not in fact be cropped to these limits but values outside
of this rectangle will be suppressed.

The `transform` parameter represents an affine backward mapping to be
applied in rendering-independent coordinates. Note that the direction
of transformation is opposite to that of the AffineTransform supplied
in the RenderContext which is a forward mapping. The default value of
this transform is the identity mapping. The supplied AffineTransform
must be invertible.

The `aspectRatio` parameter specifies the rendering-independent width
of the destination image and must be positive. The
rendering-independent bounds of the destination image are (0.0F, 0.0F,
aspectRatio, 1.0F). If this parameter is not provided, the destination
aspect ratio defaults to that of the source.

The `destROI` parameter specifies the rectangle of interest in the
destination image in rendering-independent coordinates. This rectangle
must have a non-empty intersection with the rendering-independent
bounds of the destination image but is not constrained to the
destination image bounds.

The `rotation` parameter specifies a counter-clockwise rotation angle
of the destination image. The rotation angle is limited to 0, 90, 180,
or 270 degrees. By default, the destination image is not rotated.

The `mirrorAxis` parameter may be null, in which case no flipping is
applied, or a String of `x`, `X`, `y`, or `Y`.

The `ICCProfile` parameter may only be used with client-side
processing or with server-side processing if the connection protocol
supports the ability to transfer a profile.

The `JPEGQuality` and `JPEGTable` parameters are only used with
server-side processing. If provided, `JPEGQuality` must be in the
range \[0,100\] and `JPEGTable` in \[1,255\].

There is no source image associated with this operation.

[Listing 12-3](Client-server.doc.html#56227) shows a code sample for
an `IIP` operation.

**[]{#56227}**

***Listing 12-3* ![](shared/sm-blank.gif) IIP Operation Example**

------------------------------------------------------------------------

         public static final String SERVER = "http://istserver:8087/";
         public static final String DEFAULT_IMAGE = "cat.fpx";
         public static final int DEFAULT_HEIGHT = 512;

         public static void main(String[] args) {
             String imagePath = DEFAULT_IMAGE;

            for(int i = 0; i < args.length; i++) {
                if(args[i].equalsIgnoreCase("-image")) {
                    imagePath = args[++i];
                    if(!(imagePath.toLowerCase().endsWith(".fpx"))) {
                         imagePath += ".fpx";
                    }
                }
            }

            String url = SERVER + "FIF=" + imagePath;

            new IIPTest(url);
         }

         // Define the parameter block.
         ParameterBlock pb = (new ParameterBlock()).add(url);

         // Default sub-image array
         pb.set(-10.0F, 2); // filter
         float[] colorTwist = new float[]
             {1.0F, 0.0F, 0.0F, 0.0F,
              0.0F, 0.0F, 1.0F, 0.0F,
              0.0F, 1.0F, 0.0F, 0.0F,
              0.0F, 0.0F, 0.0F, 1.0F};
         pb.set(colorTwist, 3); //color-twist
         pb.set(2.0F, 4); // contrast
         pb.set(new Rectangle2D.Float(0.10F, 0.10F,
                                      0.80F*aspectRatioSource, 0.80F),
                5); // srcROI

         AffineTransform afn = AffineTransform.getShearInstance(0.2,

                            0.1);
         pb.set(afn, 6); // transform
         Rectangle2D destBounds = null;

         try {
              Rectangle2D sourceRect =
                    new Rectangle2D.Float(0.0F, 0.0F, aspectRatioSource,

                                       1.0F);
              Shape shape =
                  afn.createInverse().createTransformedShape(sourceRect);
              destBounds = shape.getBounds2D();
         } catch(Exception e) {
         }

         float aspectRatio = (float)destBounds.getHeight();
         pb.set(aspectRatio, 7); // destination aspect ratio
         pb.set(new Rectangle2D.Float(0.0F, 0.0F,
                                  0.75F*aspectRatio, 0.75F), 8); // dstROI
         pb.set(90, 9); // rotation angle
         pb.set("x", 10); // mirror axis

         // Default ICC profile
         // Default JPEG quality
         // Default JPEG table index

         int height = DEFAULT_HEIGHT;
         AffineTransform at =
             AffineTransform.getScaleInstance(height*aspectRatioSource,

                          height);
         RenderContext rc = new RenderContext(at);

         // Create a RenderableImage.
         RenderableImage renderable = JAI.createRenderable("iip", pb);

------------------------------------------------------------------------


### 12.6.2 ![](shared/space.gif)IIPResolution Operation

The `IIPResolution` operation provides client-side support of the
Internet Imaging Protocol (IIP) in the rendered mode. It is
resolution-specific. It requests from the IIP server an image at a
particular resolution level, and creates a `RenderedImage` based on
the data received from the server. Once the `RenderedImage` is
created, the resolution level cannot be changed.

The layout of the created RenderedImage is set as follows:

-   minX, minY, tileGridXOffset, and tileGridYOffset are set to 0


-   width and height are determined based on the specified resolution
    level


-   tileWidth and tileHeight are set to 64


-   sampleModel is of the type `PixelInterleavedSampleModel` with byte
    data type and the appropriate number of bands


-   colorModel is of the type `java.awt.image.ComponentColorModel`,
    with the ColorSpace set to sRGB, PhotoYCC, or Grayscale, depending
    on the color space of the remote image; if an alpha channel is
    present, it will be premultiplied

The `IIPResolution` operation takes three parameters.

  ------------------------------------------------------------------------------------------------
  [Parameter]{#55886}     [Type]{#55888}       [Description]{#55890}
  ----------------------- -------------------- ---------------------------------------------------
  [URL]{#55892}\          [String]{#55894}\    [The URL of the IIP image]{#55896}\

  [resolution]{#55898}\   [Integer]{#55900}\   [The resolution level to request]{#55902}\

  [subImage]{#55904}\     [Integer]{#55906}\   [The sub-image to be used by the server]{#55908}\
  ------------------------------------------------------------------------------------------------

  : 

The `URL` parameter specifies the URL of the IIP image as a
`java.lang.String`. It must represent a valid URL, and include any
required FIF or SDS commands. It cannot be null.

The `resolution` parameter specifies the resolution level of the
requested IIP image from the server. The lowest resolution level is 0,
with larger integers representing higher resolution levels. If the
requested resolution level does not exist, the nearest resolution
level is used. If this parameter is not specified, it is set to the
default value `IIPResolutionDescriptor.MAX_RESOLUTION`, which
indicates the highest resolution level.

The `subImage` parameter indicates the sub-image to be used by the
server to get the image at the specified resolution level. This
parameter cannot be negative. If this parameter is not specified, it
is set to the default value 0.

There is no source image associated with this operation.

If available from the IIP server certain properties may be set on the
RenderedImage. The names of properties and the class types of their
associated values are listed in the following table. See the IIP
specification for information on each of these properties.

  ---------------------------------------------------------------------------
  [Property]{#56055}             [Type]{#56057}
  ------------------------------ --------------------------------------------
  [affine-transform]{#56151}\    [java.awt.geom.AffineTransform]{#56153}\

  [app-name]{#56059}\            [java.lang.String]{#56061}\

  [aspect-ratio]{#56063}\        [java.lang.Float]{#56065}\

  [author]{#56067}\              [java.lang.String]{#56069}\

  [colorspace]{#56071}\          [int\[\]]{#56073}\

  [color-twist]{#56075}\         [float\[16\]]{#56077}\

  [comment]{#56079}\             [java.lang.String]{#56081}\

  [contrast-adjust]{#56083}\     [java.lang.Float]{#56085}\

  [copyright]{#56087}\           [java.lang.String]{#56089}\

  [create-dtm]{#56091}\          [java.lang.String]{#56093}\

  [edit-time]{#56095}\           [java.lang.String]{#56097}\

  [filtering-value]{#56099}\     [java.lang.Float]{#56101}\

  [iip]{#56103}\                 [java.lang.String]{#56105}\

  [iip-server]{#56107}\          [java.lang.String]{#56109}\

  [keywords]{#56111}\            [java.lang.String]{#56113}\

  [last-author]{#56115}\         [java.lang.String]{#56117}\

  [last-printed]{#56119}\        [java.lang.String]{#56121}\

  [last-save-dtm]{#56123}\       [java.lang.String]{#56125}\

  [max-size]{#56127}\            [int\[2\]]{#56129}\

  [resolution-number]{#56131}\   [java.lang.Integer]{#56133}\

  [rev-number]{#56135}\          [java.lang.String]{#56137}\

  [roi-iip]{#56139}\             [java.awt.geom.Rectangle2D.Float]{#56141}\

  [subject]{#56143}\             [java.lang.String]{#56145}\

  [title]{#56147}\               [java.lang.String]{#56149}\
  ---------------------------------------------------------------------------

  : 

[Listing 12-4](Client-server.doc.html#56497) shows a code sample for
an `IIPResolution` operation.

**[]{#56497}**

***Listing 12-4* ![](shared/sm-blank.gif) IIPResolution Operation
Example**

------------------------------------------------------------------------

         public static final String SERVER = "http://istserver:8087/";
         public static final String DEFAULT_IMAGE = "cat.fpx";
         public static final int DEFAULT_RESOLUTION = 3;

         public static void main(String[] args) {
             String imagePath = DEFAULT_IMAGE;
             int resolution = DEFAULT_RESOLUTION;

             for(int i = 0; i < args.length; i++) {
                 if(args[i].equalsIgnoreCase("-image")) {
                     imagePath = args[++i];
                     if(!(imagePath.toLowerCase().endsWith(".fpx"))) {
                         imagePath += ".fpx";
                     }
                 } else if(args[i].equalsIgnoreCase("-res")) {
                     resolution = Integer.valueOf(args[++i]).intValue();
                 }
             }

             String url = SERVER + "FIF=" + imagePath;

             new IIPResolutionTest(url, resolution);
         }

         ParameterBlock pb = new ParameterBlock();
         pb.add(url).add(resolution);
         PlanarImage pi = JAI.create("iipresolution", pb);

------------------------------------------------------------------------

         

------------------------------------------------------------------------

\




\

##### [Copyright](copyright.html) © 1999, Sun Microsystems, Inc. All rights reserved.
