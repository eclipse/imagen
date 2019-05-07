# ImageN Demo
 
JAIDemo is a simple GUI front-end for experimenting with ImageN
operators.  In this release, the JAIDemo has panels for the
Scale, Rotate, Convolve, Pattern, AddConst, Transpose, Gradient and
Blur/Sharpen operators, as well as a set of Dyadic (two source)
operators.

 dditionally, the demo has a panel for the "sample" operator.  The
"sample" operator is not a standard Java Advanced Imaging API
operator, but rather an extension imaging operator which is defined in
the SampleDescriptor.java file found in this directory.  This file is
meant to be used as a quick-start template for users who want to
extend JAI with their own user-defined imaging operators.  Currently,
SampleDescriptor.java defines a thresholding operator which has two
parameters that can be controlled from the JAIDemo application.

Refer to comments in the file SampleDescriptor.java and the Java
Advanced Imaging website at:

     http://java.sun.com/products/java-media/jai

for further details on writing user-defined extensions to JAI.

In the 1.1.1 release, the AddConst, Convolve, Scale, Rotate, Transpose, 
Gradient and Blur/Sharpen operators of the demo are natively accelerated 
on Solaris and Win32 platforms.

JAIDemo may be run using the runjai.sh script on Solaris, or 
runjai.bat on Windows 95/98/NT.


II.   JAIExampleApp/SampleDescriptor

  JAIExampleApp is a simple java application that loads a source
image, performs the operation described in SampleDescriptor.java and
displays the resulting image.  It's meant to be a simple "Hello World"
application that uses Java Advanced Imaging and isn't complicated by
other API calls.

  JAIExampleApp may be run using the runjai2.sh script on Solaris, or
runjai2.bat on Windows 95/98/NT.


III.  JAIFileBrowser

  JAIFileBrowser reads a variety of common image formats and displays
any properties found in readable files in a table of key-value pairs.
Currently, JAI supports the reading of BMP, GIF, JPEG, PNG, PNM and
some FlashPix and TIFF images.  The JAIFileBrowser is a simple
application that uses JAI to turn image files into images in a
convenient generic way.

  JAIFileBrowser may be run using the runjai3.sh script on Solaris, or
runjai3.bat on Windows 95/98/NT.


IV.   JAIWarpDemo

  JAIWarpDemo allows users to define warps based on an array of input
and output points.  Users select the degree of the warp and place the
appropriate number of points throughout the image.  When the minimum
number of points have been placed, JAIWarpDemo calculates the warp of
the given degree using the array of point pairs.  This warp is then
applied to the source image and the results are displayed.  Note that
as more points are added, the warp becomes over-specified.  The
JAIWarpDemo calculates the best fit warp under these circumstances.

  Click the left mouse button on an spot with no previous point to add
a point.  Click on an existing point and drag to move that point to a
new location.  The position of a point in the source image is shown as
a cyan square; its position in the destination is shown as a yellow
square.  Click the right mouse button on a point to remove it.

  The "Delete all points" button removes all of the points that have
been added so far.  The "Show src positions" check-box toggles the
display of the cyan points; "Show dst positions" similarly toggles the
display of the yellow points.

  The "Show warped image" check-box determines whether to compute and
display the warped output image.  By toggling it, it is possible to
see how points in the image move between the specified source and
destination positions.

  The "Magnify displacements" check-box, when set, causes the yellow
points signifying destination positions to be drawn at an exaggerated
distance from their corresponding cyan source point.  This may be used
to obtain finer control over the destination positions.

  The "Degree" list box may be used to determine the degree of the
polynomial warp.  Degree 1, or Affine warps map any 3 source points to
any 3 destination points.  Degree 2 warps require 6 point
correspondences.  The number of points required for each degree is as
follows:

        Degree          Points
        ------          ------
          1                3
          2                6
          3               10
          4               15
          5               21
          6               28
          7               36

  JAIWarpDemo may be run using the runjai4.sh script on Solaris, or
runjai4.bat on Windows 95/98/NT.

V. JAIImageReader

  The JAIImageReader.java file is used by the demonstration
applications described above.  It consists of a static method
"PlanarImage readImage(String filename)" that shows the proper
procedure for reading an image from a file.  But the "JAI.create()"
and ImageDecoder.decodeAsRenderedImage()" interfaces are demonstrated.
By default, JAI.create is used; to use the codec interface directly,
add the flag "-DJAI_IMAGE_READER_USE_CODECS" to the java command line
when invoking a program that makes use of JAIImageReader.

  JAIImageReader does not contain a "main" method and is not intended
to be run as a stand-alone application.
