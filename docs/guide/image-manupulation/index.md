---
layout: default
title: Image Manipulation
parent: Programming Guide
nav_order: 7
---



  ----------------------------------------
    C H A P T E R![](shared/sm-space.gif)6
  ----------------------------------------


+----------------------------------------------------------------------:+
| -------------------------------------------------------------------   |
|                                                                       |
| Image Manipulation                                                    |
+-----------------------------------------------------------------------+

\
\
\

**T**HIS chapter describes the basics of manipulating images to
prepare them for further processing.


6.1 ![](shared/space.gif)Introduction
-------------------------------------

The JAI image manipulation objects and methods are used to enhance and
geometrically modify images and to extract information from images.
Image manipulation includes:

-   Region of interest (ROI) control


-   Relational operators


-   Logical operators


-   Arithmetic operators


-   Dithering


-   Clamping pixel values


-   Band copy


6.2 ![](shared/space.gif)Region of Interest Control
---------------------------------------------------

Typically, any image enhancement operation takes place over the entire
image. While the image enhancement operation may improve portions of
an image, other portions of the image may lose detail. You usually
want some way of limiting the enhancement operation to specific
regions of the image.

To restrict the image enhancement operations to specific regions of an
image, a region-of-interest mask is created. A region of interest
(ROI) is conceptually a mask of true or false values. The ROI mask
controls which source image pixels are to be processed and which
destination pixels are to be recorded.

JAI supports two different types of ROI mask: a Boolean mask and a
threshold value. The `ROIShape` class uses a Boolean mask, which
allows operations to be performed quickly and with compact storage.
The `ROI` class allows the specification of a threshold value; pixel
values greater than or equal to the threshold value are included in
the ROI. Pixel values less than the threshold are excluded.

The region of interest is usually defined using a `ROIShape`, which
stores its area using the `java.awt.Shape` classes. These classes
define an area as a geometrical description of its outline. The `ROI`
class stores an area as a single-banded image.

An ROI can be attached to an image as a property. See [Chapter 11,
\"Image Properties](Properties.doc.html#47285).\"


### 6.2.1 ![](shared/space.gif)The ROI Class

The `ROI` class stores an area as a grayscale (single-banded) image.
This class represents region information in image form, and can thus
be used as a fallback where a `Shape` representation is unavailable.
Inclusion and exclusion of pixels is defined by a threshold value.
Source pixel values greater than or equal to the threshold value
indicate inclusion in the ROI and are processed. Pixel values less
than the threshold value are excluded from processing.

Where possible, subclasses such as `ROIShape` are used since they
provide a more compact means of storage for large regions.

The `getAsShape()` method may be called optimistically on any instance
of `ROI`. However, it may return null to indicate that a `Shape`
representation of the `ROI` is not available. In this case,
`getAsImage()` should be called as a fallback.


|                                   | **API:** `javax.media.jai.ROI`    |

    ROI(RenderedImage im)

:   constructs an `ROI` object from a `RenderedImage`. The inclusion
    threshold is taken to be halfway between the minimum and maximum
    sample values specified by the image\'s `SampleModel`.
      --------------- ------ ----------------------------------
      *Parameters*:   `im`   A single-banded `RenderedImage`.
      --------------- ------ ----------------------------------

      : 


    ROI(RenderedImage im, int threshold)

:   constructs an `ROI` object from a `RenderedImage`. The inclusion
    `threshold` is specified explicitly.
    *Parameters*:
    `im`
    A single-banded `RenderedImage`.
    `threshold`
    The inclusion/exclusion threshold of the `ROI.`


    Shape getAsShape()

:   returns a `Shape` representation of the `ROI`, if possible. If
    none is available, null is returned. A proper instance of `ROI`
    (one that is not an instance of any subclass of `ROI`) will always
    return null.


    PlanarImage getAsImage()

:   returns a `PlanarImage` representation of the `ROI`. This method
    will always succeed.


    int getThreshold()

:   returns the inclusion/exclusion threshold value.


    void setThreshold(int threshold)

:   sets the inclusion/exclusion threshold value.


#### 6.2.1.1 ![](shared/space.gif)Determining the ROI Bounds

The `getBounds` methods in the `ROI` class read the bounds of the
`ROI`, as either a `Rectangle` or a `Rectangle2D`.


|                                   | **API:** `javax.media.jai.ROI`    |

    Rectangle getBounds()

:   returns the bounds of the `ROI` as a `Rectangle`.


    Rectangle2D getBounds2D()

:   returns the bounds of the `ROI` as a `Rectangle2D`.


#### 6.2.1.2 ![](shared/space.gif)Determining if an Area Lies Within or Intersects the ROI

The `contains` methods in the `ROI` class test whether a given point
or rectangular region lie within the `ROI`. The `intersects` methods
test whether a given rectangular region intersect with the `ROI`.


|                                   | **API:** `javax.media.jai.ROI`    |

    boolean contains(Point p)

:   returns true if the `Point` lies within the `ROI`.
      --------------- ----- ------------------------------------------------
      *Parameters*:   `p`   A `Point` identifying the pixel to be queried.
      --------------- ----- ------------------------------------------------

      : 


    boolean contains(Point2D p)

:   returns true if the `Point2D` lies within the ROI.
      --------------- ----- --------------------------------------------------
      *Parameters*:   `p`   A `Point2D` identifying the pixel to be queried.
      --------------- ----- --------------------------------------------------

      : 


    boolean contains(int x, int y)

:   returns true if the point lies within the ROI.
    *Parameters*:
    `x`
    An int specifying the *x* coordinate of the pixel to be queried.
    `y`
    An int specifying the *y* coordinate of the pixel to be queried.


    boolean contains(double x, double y)

:   returns true if the point lies within the ROI.
    *Parameters*:
    `x`
    A double specifying the *x* coordinate of the pixel to be queried.
    `y`
    A double specifying the *y* coordinate of the pixel to be queried.


    boolean contains(Rectangle rect)

:   returns true if the `Rectangle` lies within the ROI.
      --------------- -------- -----------------------------------------------------------------
      *Parameters*:   `rect`   A `Rectangle` specifying the region to be tested for inclusion.
      --------------- -------- -----------------------------------------------------------------

      : 


    boolean contains(Rectangle2D r)

:   returns true if the `Rectangle2D` lies within the ROI.
      --------------- ----- -------------------------------------------------------------------
      *Parameters*:   `r`   A `Rectangle2D` specifying the region to be tested for inclusion.
      --------------- ----- -------------------------------------------------------------------

      : 


    boolean contains(int x, int y, int w, int h)

:   returns true if the rectangle lies within the ROI.
    *Parameters*:
    `x`
    The int *x* coordinate of the upper left corner of the region.
    `y`
    The int *y* coordinate of the upper left corner of the region.
    `w`
    The int width of the region.
    `h`
    The int height of the region.


    boolean contains(double x, double y, double w, double h)

:   returns true if the rectangle lies within the ROI.
    *Parameters*:
    `x`
    The double *x* coordinate of the upper left corner of the region.
    `y`
    The double *y* coordinate of the upper left corner of the region.
    `w`
    The double width of the region.
    `h`
    The double height of the region.


    boolean intersects(Rectangle rect)

:   returns true if the `Rectangle` intersects the ROI.
      --------------- -------- -----------------------------------------------------------------
      *Parameters*:   `rect`   A `Rectangle` specifying the region to be tested for inclusion.
      --------------- -------- -----------------------------------------------------------------

      : 


    boolean intersects(Rectangle2D r)

:   returns true if the `Rectangle2D` intersects the ROI.
      --------------- ----- -------------------------------------------------------------------
      *Parameters*:   `r`   A `Rectangle2D` specifying the region to be tested for inclusion.
      --------------- ----- -------------------------------------------------------------------

      : 


    boolean intersects(int x, int y, int w, int h)

:   returns true if the rectangle intersects the ROI.
    *Parameters*:
    `x`
    The int *x* coordinate of the upper left corner of the region.
    `y`
    The int *y* coordinate of the upper left corner of the region.
    `w`
    The int width of the region.
    `h`
    The int height of the region.


    boolean intersects(double x, double y, double w, double h)

:   returns true if the rectangle intersects the `ROI`.
    *Parameters*:
    `x`
    The double *x* coordinate of the upper left corner of the region.
    `y`
    The double *y* coordinate of the upper left corner of the region.
    `w`
    The double width of the region.
    `h`
    The double height of the region.


#### 6.2.1.3 ![](shared/space.gif)Creating a New ROI from an Existing ROI

Several methods allow the creation of a new `ROI` from an existing
`ROI`. The `add` method adds another ROI to an existing one, creating
a new ROI.


|                                   | **API:** `javax.media.jai.ROI`    |

    ROI add(ROI im)

:   adds another `ROI` to this one and returns the result as a new
    `ROI`. The addition is performed by an \"AddROIs\" RIF to be
    specified. The supplied `ROI` will be converted to a rendered form
    if necessary.
      --------------- ------ -----------
      *Parameters*:   `im`   An `ROI`.
      --------------- ------ -----------

      : 


    ROI subtract(ROI im)

:   subtracts another `ROI` to this one and returns the result as a
    new `ROI`. The subtraction is performed by a \"SubtractROIs\" RIF
    to be specified. The supplied `ROI` will be converted to a
    rendered form if necessary.
      --------------- ------ -----------
      *Parameters*:   `im`   An `ROI`.
      --------------- ------ -----------

      : 


    ROI intersect(ROI im)

:   intersects the `ROI` with another `ROI` and returns the result as
    a new `ROI`. The intersection is performed by a \"IntersectROIs\"
    RIF to be specified. The supplied `ROI` will be converted to a
    rendered form if necessary.
      --------------- ------ -----------
      *Parameters*:   `im`   An `ROI`.
      --------------- ------ -----------

      : 


    ROI exclusiveOr(ROI im)

:   exclusive-ORs the `ROI` with another `ROI` and returns the result
    as a new `ROI`. The intersection is performed by an \"XorROIs\"
    RIF to be specified. The supplied `ROI` will be converted to a
    rendered form if necessary.
      --------------- ------ -----------
      *Parameters*:   `im`   An `ROI`.
      --------------- ------ -----------

      : 


    ROI transform(AffineTransform at)

:   performs an affine transformation and returns the result as a new
    `ROI`. The transformation is performed by an \"Affine\" RIF.
      --------------- ------ -----------------------------------------------------
      *Parameters*:   `at`   An `AffineTransform` specifying the transformation.
      --------------- ------ -----------------------------------------------------

      : 


    ROI performImageOp(RenderedImageFactory RIF, ParameterBlock 
           paramBlock, int sourceIndex, Hashtable renderHints, 
           Hashtable renderHintsObserved)

:   transforms an ROI using an imaging operation. The operation is
    specified by a `RenderedImageFactory`. The operation\'s
    `ParameterBlock`, minus the image source itself is supplied, along
    with an index indicating where to insert the `ROI` image. The
    usual `renderHints` and `renderHintsObserved` arguments allow
    rendering hints to be passed in and information on which hints
    were followed to be passed out.
    *Parameters*:
    `RIF`
    A `RenderedImageFactory` that will be used to create the op.
    `paramBlock`
    A `ParameterBlock` containing all sources and parameters for the
    operation except for the `ROI` itself.
    `sourceIndex`
    The index of the `ParameterBlock`\'s sources where the `ROI` is to
    be inserted.
    `renderHints`
    A Hashtable of rendering hints.
    `renderHints-Observed`
    A Hashtable of observed rendering hints.


    ROI performImageOp(RenderedImageFactory RIF, ParameterBlock 
           paramBlock, int sourceIndex)

:   transforms an `ROI` using an imaging operation. The operation is
    specified by a `RenderedImageFactory`. The operation\'s
    `ParameterBlock`, minus the image source itself is supplied, along
    with an index indicating where to insert the `ROI` image.
    Rendering hints are taken to be null.
    *Parameters*:
    `RIF`
    A `RenderedImageFactory` that will be used to create the op.
    `paramBlock`
    A `ParameterBlock` containing all sources and parameters for the
    operation except for the `ROI` itself.
    `sourceIndex`
    The index of the `ParameterBlock`\'s sources where the `ROI` is to
    be inserted.


    ROI performImageOp(String name, ParameterBlock paramBlock, 
           int  sourceIndex, Hashtable renderHints, 
           Hashtable  renderHintsObserved)

:   transforms an `ROI` using an imaging operation. The operation is
    specified by name; the default JAI registry is used to resolve
    this into a RIF. The operation\'s `ParameterBlock`, minus the
    image source itself is supplied, along with an index indicating
    where to insert the `ROI` image. The usual `renderHints` and
    `renderHintsObserved` arguments allow rendering hints to be passed
    in and information on which hints were followed to be passed out.
    *Parameters*:
    `name`
    The name of the operation to be performed.
    `paramBlock`
    A `ParameterBlock` containing all sources and parameters for the
    operation except for the `ROI` itself.
    `sourceIndex`
    The index of the `ParameterBlock`\'s sources where the `ROI` is to
    be inserted.
    `renderHints`
    A Hashtable of rendering hints.
    `renderHints-Observed`
    A Hashtable of observed rendering hints.


    ROI performImageOp(String name, ParameterBlock paramBlock, 
           int  sourceIndex)

:   transforms an `ROI` using an imaging operation. The operation is
    specified by name; the default JAI registry is used to resolve
    this into a RIF. The operation\'s `ParameterBlock`, minus the
    image source itself is supplied, along with an index indicating
    where to insert the ROI image. Rendering hints are taken to be
    null.
    *Parameters*:
    `name`
    The name of the operation to be performed.
    `paramBlock`
    A `ParameterBlock` containing all sources and parameters for the
    operation except for the `ROI` itself.
    `sourceIndex`
    The index of the `ParameterBlock`\'s sources where the `ROI` is to
    be inserted.


    Shape getAsShape()

:   returns a Shape representation of the `ROI`, if possible. If none
    is available, null is returned. A proper instance of `ROI` (one
    that is not an instance of any subclass of `ROI`) will always
    return null.


    PlanarImage getAsImage()

:   returns a `PlanarImage` representation of the `ROI`. This method
    will always succeed.


### 6.2.2 ![](shared/space.gif)The ROIShape Class

The `ROIShape` class is used to store a region of interest within an
image as an instance of a `java.awt.Shape`. Such regions are binary by
definition. Using a `Shape` representation allows Boolean operations
to be performed quickly and with compact storage. If a
`PropertyGenerator` responsible for generating the `ROI` property of a
particular `OperationDescriptor` (such as a `warp`) cannot reasonably
produce an `ROIShape` representing the region, it should call the
`getAsImage()` method on its sources and produce its output `ROI` in
image form.

**API:** `org.eclipse.imagen.jai.ROIShape`

    ROIShape(Shape s)

:   constructs an `ROIShape` from a `Shape`.
      --------------- ----- ------------
      *Parameters*:   `s`   A `Shape`.
      --------------- ----- ------------

      : 


    ROIShape(Area a)

:   constructs an `ROIShape` from an `Area`.
      --------------- ----- ------------
      *Parameters*:   `a`   An `Area`.
      --------------- ----- ------------

      : 


#### 6.2.2.1 ![](shared/space.gif)Determining the ROI Bounds

The following methods in the `ROIShape` class read the bounds of the
`ROI`.

**API:** `org.eclipse.imagen.jai.ROIShape`

    Rectangle getBounds()

:   returns the bounds of the ROI as a `Rectangle`.


    Rectangle2D getBounds2D()

:   returns the bounds of the ROI as a `Rectangle2D`.


#### 6.2.2.2 ![](shared/space.gif)Determining if an Area Lies Within or Intersects the ROIShape

The `ROIShape.contains` method is used to determine if a given pixel
lies within the region of interest. The `ROIShape.intersects` method
is used to determine if a rectangular region of the image intersects
the ROI.``

**API:** `org.eclipse.imagen.jai.ROIShape`

    boolean contains(Point p)

:   returns true if the pixel lies within the `ROI`.
      --------------- ----- ---------------------------------------------
      *Parameters*:   `p`   The coordinates of the pixel to be queried.
      --------------- ----- ---------------------------------------------

      : 


    boolean contains(Point2D p)

:   returns true if the pixel lies within the `ROI`.
      --------------- ----- ---------------------------------------------
      *Parameters*:   `p`   The coordinates of the pixel to be queried.
      --------------- ----- ---------------------------------------------

      : 


    boolean contains(int x, int y)

:   returns true if the pixel lies within the `ROI`.
    *Parameters*:
    `x`
    The *x* coordinate of the pixel to be queried.
    `y`
    The *y* coordinate of the pixel to be queried.


    boolean contains(double x, double y)

:   returns true if the pixel lies within the `ROI`.
    *Parameters*:
    `x`
    The *x* coordinate of the pixel to be queried.
    `y`
    The *y* coordinate of the pixel to be queried.


    boolean contains(Rectangle rect)

:   returns true if the rectangular region is entirely contained
    within the `ROI`.
      --------------- -------- ----------------------------------------
      *Parameters*:   `rect`   The region to be tested for inclusion.
      --------------- -------- ----------------------------------------

      : 


    boolean contains(Rectangle2D r)

:   returns true if the rectangular region is entirely contained
    within the `ROI`.
      --------------- ----- ----------------------------------------
      *Parameters*:   `r`   The region to be tested for inclusion.
      --------------- ----- ----------------------------------------

      : 


    boolean contains(int x, int y, int w, int h)

:   returns true if the rectangular region is entirely contained
    within the `ROI`.
    *Parameters*:
    `x`
    The *x* coordinate of the pixel to be queried.
    `y`
    The *y* coordinate of the pixel to be queried.
    `w`
    The width of the region.
    `h`
    The height of the region.


    boolean contains(double x, double y, double w, double h)

:   returns true if the rectangular region is entirely contained
    within the `ROI`.
    *Parameters*:
    `x`
    The *x* coordinate of the pixel to be queried.
    `y`
    The *y* coordinate of the pixel to be queried.
    `w`
    The width of the region.
    `h`
    The height of the region.


    boolean intersects(Rectangle rect)

:   returns true if the rectangular region intersects the `ROI`.
      --------------- -------- ----------------------------------------
      *Parameters*:   `rect`   The region to be tested for inclusion.
      --------------- -------- ----------------------------------------

      : 


    boolean intersects(Rectangle2D r)

:   returns true if the rectangular region intersects the `ROI`.
      --------------- -------- ----------------------------------------
      *Parameters*:   `rect`   The region to be tested for inclusion.
      --------------- -------- ----------------------------------------

      : 


    boolean intersects(int x, int y, int w, int h)

:   returns true if the rectangular region intersects the `ROI`.
    *Parameters*:
    `x`
    The *x* coordinate of the upper left corner of the region.
    `y`
    The *y* coordinate of the upper left corner of the region.
    `w`
    The width of the region.
    `h`
    The height of the region.


    boolean intersects(double x, double y, double w, double h)

:   returns true if the rectangular region intersects the `ROI`.
    *Parameters*:
    `x`
    The *x* coordinate of the upper left corner of the region.
    `y`
    The *y* coordinate of the upper left corner of the region.
    `w`
    The width of the region.
    `h`
    The height of the region.


#### 6.2.2.3 ![](shared/space.gif)Creating a New ROIShape from an Existing ROIShape

Several methods allow the creation of a new `ROIShape` from the old
`ROIShape`.

**API:** `org.eclipse.imagen.jai.ROIShape`

    ROI add(ROI im)

:   adds another mask to this one. This operation may force this mask
    to be rendered.
      --------------- ------ -----------
      *Parameters*:   `im`   An `ROI`.
      --------------- ------ -----------

      : 


    ROI subtract(ROI im)

:   subtracts another mask from this one. This operation may force
    this mask to be rendered.
      --------------- ------ -----------
      *Parameters*:   `im`   An `ROI`.
      --------------- ------ -----------

      : 


    ROI intersect(ROI im)

:   sets the mask to its intersection with another mask. This
    operation may force this mask to be rendered.
      --------------- ------ -----------
      *Parameters*:   `im`   An `ROI`.
      --------------- ------ -----------

      : 


    ROI exclusiveOr(ROI im)

:   sets the mask to its exclusive-OR with another mask. This
    operation may force this mask to be rendered.
      --------------- ------ -----------
      *Parameters*:   `im`   An `ROI`.
      --------------- ------ -----------

      : 


    ROI transform(AffineTransform at)

:   performs an affine transformation and returns the result as a new
    ROI. The transformation is performed by an \"Affine\" RIF.
      --------------- ------ -----------------------
      *Parameters*:   `at`   The affine transform.
      --------------- ------ -----------------------

      : 


    Shape getAsShape()

:   returns the internal `Shape` representation or null if not
    possible. Since we have a shape available, we simply return it.


    PlanarImage getAsImage()

:   returns the shape as a `PlanarImage`. This requires performing an
    antialiased rendering of the internal `Shape`. We use an
    eight-bit, single channel image with a `ComponentColorModel` and a
    `ColorSpace.TYPE_GRAY` color space.


6.3 ![](shared/space.gif)Relational Operators
---------------------------------------------

Given two source images and a destination image, the JAI relational
operators allow you to:

-   Find the larger of the pixels in the two source images and store
    the results in the destination (`Max`).


-   Find the smaller of the pixels in the two source images and store
    the results in the destination (`Min`).

The relational operators require that both source images and the
destination image have the same data type and number of bands. The
sizes of the two images (height and width), however, need not be the
same.

When determining the maximum and minimum pixels in the two images, JAI
performs a band-by-band comparison.

------------------------------------------------------------------------

**Note:** Don\'t confuse the relational Min and Max operators with the
Extrema operation (see [Section 9.3, \"Finding the Extrema of an
Image](Analysis.doc.html#54907)\"), which finds the image-wise minimum
and maximum pixel values for each band of an image.

------------------------------------------------------------------------


### 6.3.1 ![](shared/space.gif)Finding the Maximum Values of Two Images

The `max` operation takes two rendered images, and for every pair of
pixels, one from each source image of the corresponding position and
band, finds the maximum pixel value.

The two source images may have different numbers of bands and data
types. By default, the destination image bound is the intersection of
the two source image bounds. If the two source images don\'t
intersect, the destination will have a width and a height of 0. The
number of bands of the destination image is the same as the least
number of bands of the source images, and the data type is the biggest
data type of the source images.

The pixel values of the destination image are defined by the following
pseudocode:

         if (srcs[0][x][y][b] srcs[1][x][y][b]) {
             dst[x][y][b] = srcs[0][x][y][b];
         } else {
             dst[x][y][b] = srcs[1][x][y][b];
         }

The `max` operation takes two source images and no parameters.
[Listing 6-1](Image-manipulation.doc.html#70421) shows a partial code
sample of computing the pixelwise maximum value of two images in the
rendered mode.

**[]{#70421}**

***Listing 6-1* ![](shared/sm-blank.gif) Finding the Maximum Value of
Two Images**

------------------------------------------------------------------------

         // Create two constant images
         RenderedOp im0 = JAI.create("constant", param1);
         RenderedOp im1 = JAI.create("constant", param2);

         // Find the maximum value of the two images
         RenderedOp im2 = JAI.create("max", im0, im1);

------------------------------------------------------------------------


### 6.3.2 ![](shared/space.gif)Finding the Minimum Values of Two Images

The `min` operation takes two rendered images, and for every pair of
pixels, one from each source image of the corresponding position and
band, finds the minimum pixel value.

The two source images may have different numbers of bands and data
types. By default, the destination image bound is the intersection of
the two source image bounds. If the two source images don\'t
intersect, the destination will have a width and a height of 0. The
number of bands of the destination image is the same as the least
number of bands of the source images, and the data type is the biggest
data type of the source images.

The pixel values of the destination image are defined by the following
pseudocode:

         if (srcs[0][x][y][b] < srcs[1][x][y][b]) {
             dst[x][y][b] = srcs[0][x][y][b];
         } else {
             dst[x][y][b] = srcs[1][x][y][b];
         }

The `min` operation takes two rendered source images and no
parameters. [Listing 6-2](Image-manipulation.doc.html#70445) shows a
partial code sample of computing the pixelwise minimum value of two
images in the renderable mode.

**[]{#70445}**

***Listing 6-2* ![](shared/sm-blank.gif) Finding the Minimum Value of
Two Images**

------------------------------------------------------------------------

         // Set up the parameter block and add the two source images to it
         ParameterBlock pb = new ParameterBlock();
         pb.add(im0);
         pb.add(im1);

         // Find the maximum value of the two images
         RenderableOp im2 = JAI.createRenderable("min", pb, hints);

------------------------------------------------------------------------


6.4 ![](shared/space.gif)Logical Operators
------------------------------------------

JAI supports *monadic*, *dyadic*, and *unary* logical operators. The
monadic logical operations include pixel-by-pixel AND, OR, and XOR
operations between a source image and a constant to produce a
destination image. The dyadic logical operations include
pixel-by-pixel AND, OR, and XOR operations between two source images
to produce a destination image. The unary logical operation is a NOT
operation (complement image) on each pixel of a source image on a
per-band basis.

JAI supports the following logical operations:

-   Take the bitwise AND of the two source images and store the
    results in the destination (`And`)


-   Take the bitwise AND of a source image and one of a set of
    per-band constants (`AndConst`)


-   Take the bitwise OR of the two source images and store the results
    in the destination (`Or`)


-   Take the bitwise OR of a source image and one of a set of per-band
    constants (`OrConst`)


-   Take the bitwise XOR (exclusiveOR) of the two source images and
    store the results in the destination (`Xor`)


-   Take the bitwise XOR of a source image and one of a set of
    per-band constants (`XorConst`)


-   Take the bitwise NOT of a source image on each pixel on a per-band
    basis (`Not`)

As with the relational operators, the logical operations require that
both source images and the destination image have the same data type
and number of bands. The sizes of the two images (height and width),
however, need not be the same.


### 6.4.1 ![](shared/space.gif)ANDing Two Images

The `And` operation takes two rendered or renderable source images,
and performs a bit-wise logical AND on every pair of pixels, one from
each source image, of the corresponding position and band.

Both source images must have integral data types. The two data types
may be different.

Unless altered by an `ImageLayout` hint, the destination image bound
is the intersection of the two source image bounds. If the two sources
don\'t intersect, the destination will have a width and height of 0.
The number of bands of the destination image is equal to the lesser
number of bands of the source images, and the data type is the
smallest data type with sufficient range to cover the range of both
source data types.

The following matrix defines the logical `And` operation.

  ----------------------------------------------------
  [src0]{#56613}   [src1]{#56615}   [Result]{#56617}
  ---------------- ---------------- ------------------
  [0]{#56619}\     [0]{#56621}\     [0]{#56623}\

  [0]{#56625}\     [1]{#56627}\     [0]{#56629}\

  [1]{#56631}\     [0]{#56633}\     [0]{#56635}\

  [1]{#56637}\     [1]{#56639}\     [1]{#56641}\
  ----------------------------------------------------

  : 

The destination pixel values are defined by the following pseudocode:

         dst[x][y][b] = srcs[0][x][y][b] & srcs[1][x][y][b];

The `And` operation takes two rendered or renderable source images and
no parameters.

[Listing 6-3](Image-manipulation.doc.html#70476) shows a partial code
sample of using the `And` operation to AND two images together.

**[]{#70476}**

***Listing 6-3* ![](shared/sm-blank.gif) ANDing Two Images**

------------------------------------------------------------------------

         // Set up the parameter block and add the two source images to it.
         ParameterBlock pb = new ParameterBlock();
         pb.addSource(im0);          // The first image
         pb.addSource(im1);          // The second image

         // AND the two images together.
         RenderableOp op = JAI.createRenderable("and", pb, hints);

------------------------------------------------------------------------


### 6.4.2 ![](shared/space.gif)ANDing an Image with a Constant

The `AndConst` operation takes one rendered or renderable image and an
array of integer constants, and performs a bit-wise logical AND
between every pixel in the same band of the source and the constant
from the corresponding array entry. If the number of constants
supplied is less than the number of bands of the destination, then the
constant from entry 0 is applied to all the bands. Otherwise, a
constant from a different entry is applied to each band.

The source image must have an integral data type. By default, the
destination image bound, data type, and number of bands are the same
as the source image.

The following matrix defines the logical `AndConst` operation:

  ----------------------------------------------------
  [src]{#57569}   [const]{#57571}   [Result]{#57573}
  --------------- ----------------- ------------------
  [0]{#57575}\    [0]{#57577}\      [0]{#57579}\

  [0]{#57581}\    [1]{#57583}\      [0]{#57585}\

  [1]{#57587}\    [0]{#57589}\      [0]{#57591}\

  [1]{#57593}\    [1]{#57595}\      [1]{#57597}\
  ----------------------------------------------------

  : 

The destination pixel values are defined by the following pseudocode:

         if (constants.length < dstNumBands) {
             dst[x][y][b] = srcs[x][y][b] & constants[0];
         } else {
             dst[x][y][b] = srcs[x][y][b] & constants[b];
         }

The `AndConst` operation takes one rendered or renderable source image
and one parameter:

  --------------------------------------------------------------------------------------------------
  [Parameter]{#59369}    [Type]{#59371}   [Description]{#59373}
  ---------------------- ---------------- ----------------------------------------------------------
  [constants]{#59375}\   [int]{#59377}\   [The per-band constants to logically AND with.]{#59379}\

  --------------------------------------------------------------------------------------------------

  : 

[Listing 6-4](Image-manipulation.doc.html#70503) shows a partial code
sample of using the `AndConst` operation to AND a source image with a
defined constant of value 1.2.

**[]{#70503}**

***Listing 6-4* ![](shared/sm-blank.gif) ANDing an Image with a
Constant**

------------------------------------------------------------------------

         // Set up the parameter block with the source and a constant
         // value.
         ParameterBlock pb = new ParameterBlock();
         pb.addSource(im);       // im as the source image
         pb.add(1.2f);     // The constant
         
         // AND the image with the constant.
         RenderableOp op = JAI.createRenderable("andconst", pb, hints);

------------------------------------------------------------------------


### 6.4.3 ![](shared/space.gif)ORing Two Images

The `Or` operation takes two rendered or renderable images, and
performs a bit-wise logical OR on every pair of pixels, one from each
source image of the corresponding position and band.

Both source images must have integral data types. The two data types
may be different.

Unless altered by an `ImageLayout` hint, the destination image bound
is the intersection of the two source image bounds. If the two sources
don\'t intersect, the destination will have a width and height of 0.
The number of bands of the destination image is equal to the lesser
number of bands of the source images, and the data type is the
smallest data type with sufficient range to cover the range of both
source data types.

The following matrix defines the logical `OR` operation:

  ----------------------------------------------------
  [src0]{#59692}   [src1]{#59694}   [Result]{#59696}
  ---------------- ---------------- ------------------
  [0]{#59698}\     [0]{#59700}\     [0]{#59702}\

  [0]{#59704}\     [1]{#59706}\     [1]{#59708}\

  [1]{#59710}\     [0]{#59712}\     [1]{#59714}\

  [1]{#59716}\     [1]{#59718}\     [1]{#59720}\
  ----------------------------------------------------

  : 

The destination pixel values are defined by the following pseudocode:

         dst[x][y][b] = srcs[0][x][y][b] | srcs[1][x][y][b];

The `Or` operation takes two rendered or renderable source images and
no parameters.

[Listing 6-5](Image-manipulation.doc.html#70533) shows a partial code
sample of using the `or` operation to OR two images.

**[]{#70533}**

***Listing 6-5* ![](shared/sm-blank.gif) ORing Two Images**

------------------------------------------------------------------------

         // Read the first image.
         pb = new ParameterBlock();
         pb.addSource(file1);
         RenderedOp src1 = JAI.create("stream", pb);

         // Read the second image.
         pb = new ParameterBlock();
         pb.addSource(file2);
         RenderedImage src2 = JAI.create("stream", pb);

         // OR the two images.
         RenderedOp dst = JAI.create("or", src1, src2);

------------------------------------------------------------------------


### 6.4.4 ![](shared/space.gif)ORing an Image with a Constant

The `OrConst` operation takes one rendered or renderable image and an
array of integer constants, and performs a bit-wise logical OR between
every pixel in the same band of the source image and the constant from
the corresponding array entry. If the number of constants supplied is
less than the number of bands of the destination, the constant from
entry 0 is applied to all the bands. Otherwise, a constant from a
different entry is applied to each band.

The source image must have an integral data type. By default, the
destination image bound, data type, and number of bands are the same
as the source image.

The following matrix defines the logical `OrConst` operation:

  ----------------------------------------------------
  [src]{#71957}   [const]{#71959}   [Result]{#71961}
  --------------- ----------------- ------------------
  [0]{#71963}\    [0]{#71965}\      [0]{#71967}\

  [0]{#71969}\    [1]{#71971}\      [1]{#71973}\

  [1]{#71975}\    [0]{#71977}\      [1]{#71979}\

  [1]{#71981}\    [1]{#71983}\      [1]{#71985}\
  ----------------------------------------------------

  : 

The destination pixel values are defined by the following pseudocode:

         if (constants.length < dstNumBands) {
             dst[x][y][b] = src[x][y][b] | constants[0];
         } else {
             dst[x][y][b] = src[x][y][b] | constants[b];
         }

The `OrConst` operation takes one rendered or renderable source image
and one parameter:

  -------------------------------------------------------------------------------------------------
  [Parameter]{#71989}    [Type]{#71991}   [Description]{#71993}
  ---------------------- ---------------- ---------------------------------------------------------
  [constants]{#71995}\   [int]{#71997}\   [The per-band constants to logically OR with.]{#71999}\

  -------------------------------------------------------------------------------------------------

  : 


### 6.4.5 ![](shared/space.gif)XORing Two Images

The `Xor` operation takes two rendered or renderable images, and
performs a bit-wise logical XOR on every pair of pixels, one from each
source image of the corresponding position and band.

Both source images must have integral data types. The two data types
may be different.

Unless altered by an `ImageLayout` hint, the destination image bound
is the intersection of the two source image bounds. If the two source
images don\'t intersect, the destination will have a width and height
of 0. The number of bands of the destination image is equal to the
lesser number of bands of the source images, and the data type is the
smallest data type with sufficient range to cover the range of both
source data types.

The following matrix defines the `Xor` operation:

  ----------------------------------------------------
  [src0]{#59440}   [src1]{#59442}   [Result]{#59444}
  ---------------- ---------------- ------------------
  [0]{#59446}\     [0]{#59448}\     [0]{#59450}\

  [0]{#59452}\     [1]{#59454}\     [1]{#59456}\

  [1]{#59458}\     [0]{#59460}\     [1]{#59462}\

  [1]{#59464}\     [1]{#59466}\     [0]{#59468}\
  ----------------------------------------------------

  : 

The destination pixel values are defined by the following pseudocode:

         dst[x][y][b] = srcs[0][x][y][b] ^ srcs[0][x][y][b];

The `Xor` operation takes one rendered or renderable source image and
no parameters.


### 6.4.6 ![](shared/space.gif)XORing an Image with a Constant

The `XorConst` operation takes one rendered or renderable image and an
array of integer constants, and performs a bit-wise logical OR between
every pixel in the same band of the source and the constant from the
corresponding array entry. If the number of constants supplied is less
than the number of bands of the destination, the constant from entry 0
is applied to all the bands. Otherwise, a constant from a different
entry is applied to each band.

The source image must have an integral data type. By default, the
destination image bound, data type, and number of bands are the same
as the source image.

The following matrix defines the logical `XorConst` operation:

  ----------------------------------------------------
  [src]{#59198}   [const]{#59200}   [Result]{#59202}
  --------------- ----------------- ------------------
  [0]{#59204}\    [0]{#59206}\      [0]{#59208}\

  [0]{#59210}\    [1]{#59212}\      [1]{#59214}\

  [1]{#59216}\    [0]{#59218}\      [1]{#59220}\

  [1]{#59222}\    [1]{#59224}\      [0]{#59226}\
  ----------------------------------------------------

  : 

The destination pixel values are defined by the following pseudocode:

         if (constants.length < dstNumBands) {
             dst[x][y][b] = src[x][y][b] ^ constants[0];
         } else {
             dst[x][y][b] = src[x][y][b] ^ constants[b];
         }

The `XorConst` operation takes one rendered or renderable source image
and one parameter:

  ---------------------------------------------------------------------------------------
  [Parameter]{#59309}   [Type]{#59311}   [Description]{#59313}
  --------------------- ---------------- ------------------------------------------------
  [constant]{#59315}\   [int]{#59317}\   [The constant to logically XOR with.]{#59319}\

  ---------------------------------------------------------------------------------------

  : 


### 6.4.7 ![](shared/space.gif)Taking the Bitwise NOT of an Image

The `Not` operation takes one rendered or renderable image, and
performs a bit-wise logical NOT on every pixel from every band of the
source image. This operation, also known as a *complement* operation,
creates an image that is somewhat like a photographic negative.

The `Not` operation looks at the values in the source image as binary
values and changes all the 1\'s in those values to 0\'s, and all the
0\'s to 1\'s. The operation then writes the one\'s complement version
of the source image to the destination.

The source image must have an integral data type. By default, the
destination image bound, data type, and number of bands are the same
as the source image.

The following matrix defines the logical NOT operation.

  ----------------------------------
  [src]{#72921}   [Result]{#72923}
  --------------- ------------------
  [1]{#72925}\    [0]{#72927}\

  [0]{#72929}\    [1]{#72931}\
  ----------------------------------

  : 

The pixel values of the destination image are defined by the following
pseudocode:

         dst[x][y][b] = ~(src[x][y][b])

The `Not` operation takes one rendered or renderable source image and
no parameters.

[Listing 6-6](Image-manipulation.doc.html#70578) shows a partial code
sample of using the `Not` operation.

**[]{#70578}**

***Listing 6-6* ![](shared/sm-blank.gif) Taking the NOT of an Image**

------------------------------------------------------------------------

         // Read the source image.
         pb = new ParameterBlock();
         pb.addSource(file);
         RenderedOp src = JAI.create("stream", pb);
         
         // Create the Not operation.
         RenderedOp dst = JAI.create("Not", src);

------------------------------------------------------------------------


6.5 ![](shared/space.gif)Arithmetic Operators
---------------------------------------------

JAI supports both *monadic* and *dyadic* arithmetic operators. The
monadic arithmetic operations include per-band addition, subtraction,
division, and multiplication operations between a source image and a
constant to produce a destination image. The dyadic arithmetic
operations include per-band addition, subtraction, division, and
multiplication operations between two source images to produce a
destination image.

The JAI arithmetic operators allow you to:

-   Add two source images and store the results in a destination image
    (`Add`)


-   Add a constant value to the pixels in a source image and store the
    results in a destination image (`AddConst`)


-   Add a collection of images and store the results in a destination
    image (`AddCollection`)


-   Add a an array of double constants to a collection of rendered
    images (`AddConstToCollection`)


-   Subtract one source image from an other and store the results in a
    destination image (`Subtract`)


-   Subtract a constant value from the pixels in a source image and
    store the results in a destination image (`SubtractConst`)


-   Divide one source image into an other and store the results in a
    destination image (`Divide`)


-   Divide two source images of complex data and store the results in
    a destination image (`DivideComplex`)


-   Divide a source image by a constant value (`DivideByConst`)


-   Divide a source image into a constant value (`DivideIntoConst`)


-   Multiply two source images and store the results in a destination
    image (`Multiply`)


-   Multiply a source image by a constant value (`MultiplyConst)`


-   Multiply two images representing complex data (`MultiplyComplex`)


-   Find the absolute value of pixels in a source image and store the
    results in a destination image (`Absolute`)


-   Take the exponent of an image and store the results in a
    destination image (`Exp`)

As with the relational and logical operators, the arithmetic
operations require that both source images and the destination image
have the same data type and number of bands. The sizes of the two
images (height and width), however, need not be the same.

When JAI adds two images, it takes the value at location 0,0 in one
source image, adds it to the value at location 0,0 in the second
source image, and writes the sum at location 0,0 in the destination
image. It then does the same for all other points in the images.
Subtraction, multiplication, and division are handled similarly.

Arithmetic operations on multi-band images are performed on
corresponding bands in the source images. That is, band 0 of the first
image is added to band 0 of the second image, and so on.


### 6.5.1 ![](shared/space.gif)Adding Two Source Images

The `Add` operation takes two rendered or renderable source images,
and adds every pair of pixels, one from each source image of the
corresponding position and band. The two source images may have
different numbers of bands and data types. By default, the destination
image bounds are the intersection of the two source image bounds. If
the sources don\'t intersect, the destination will have a width and
height of 0.

The default number of bands of the destination image is equal to the
smallest number of bands of the sources, and the data type is the
smallest data type with sufficient range to cover the range of both
source data types (not necessarily the range of their sums).

As a special case, if one of the source images has *N* bands (where
*N* is greater than one), the other source has one band, and an
`ImageLayout` hint is provided containing a destination `SampleModel`
with *K* bands (1 \< *K* ![](shared/chars/lt_equal.gif) *N*), then the
single band of the one1-banded source is added to each of the first
*K* bands of the *N*-band source.

The destination pixel values are defined by the following pseudocode:

         dst[x][y][dstBand] = clamp(srcs[0][x][y][src0Band] +
                                    srcs[1][x][y][src1Band]);

If the result of the addition underflows or overflows the minimum or
maximum value supported by the destination image, the value will be
clamped to the minimum or maximum value, respectively.

The `Add` operation two rendered or renderable source images and no
parameters.

[Listing 6-7](Image-manipulation.doc.html#70605) shows a partial code
sample of using the `Add` operation to add two images.

**[]{#70605}**

***Listing 6-7* ![](shared/sm-blank.gif) Adding Two Images**

------------------------------------------------------------------------

         // Read the two images.
         pb = new ParameterBlock();
         pb.addSource(s1);
         RenderedImage src1 = (RenderedImage)JAI.create("stream", pb);

         pb = new ParameterBlock();
         pb.addSource(s2);
         RenderedImage src2 = (RenderedImage)JAI.create("stream", pb);

         // Create the ParameterBlock for the operation
         pb = new ParameterBlock();
         pb.addSource(src1);
         pb.addSource(src2);

         // Create the Add operation.
         RenderedImage dst = (RenderedImage)JAI.create("add", pb);

------------------------------------------------------------------------


### 6.5.2 ![](shared/space.gif)Adding a Constant Value to an Image

The `AddConst` operation adds one of a set of constant values to every
pixel value of a source image on a per-band basis:

         if (constants.length < dstNumBands) {
             dst[x][y][b] = src[x][y][b] + constants[0];
         else {
             dst[x][y][b] = src[x][y][b] + constants[b]

The `AddConst` operation takes one rendered or renderable source image
and one parameter:

  -------------------------------------------------------------------------------------------
  [Parameter]{#57421}    [Type]{#57423}      [Description]{#57425}
  ---------------------- ------------------- ------------------------------------------------
  [constants]{#57427}\   [double]{#57429}\   [The per-band constants to be added.]{#57431}\

  -------------------------------------------------------------------------------------------

  : 

The set of `constants` must contain one entry for each band of the
source image. If the number of constants supplied is less than the
number of bands of the destination image, the constant from entry 0 is
applied to all the bands. Otherwise, a constant from a different entry
is applied to each band.

By default, the destination image bound, data type, and number of
bands are the same as the source image.

If the result of the addition underflows or overflows the minimum or
maximum value supported by the destination image, the value will be
clamped to the minimum or maximum value, respectively.

[Listing 6-8](Image-manipulation.doc.html#70988) shows a partial code
sample of using the `AddConst` operation.

**[]{#70988}**

***Listing 6-8* ![](shared/sm-blank.gif) Adding a Constant to an
Image**

------------------------------------------------------------------------

         // Create the constant values.
         RenderedImage im1, im2;
         ParameterBlock pb;
         double k0, k1, k2;

         pb = new ParameterBlock();
         pb.addSource(im1);
         double[] constants = new double[3]; // or however many bands
                                             // in im1
         constants[0] = k0;
         constants[1] = k1;
         constants[2] = k2;
         pb.add(constants);

         // Construct the AddConst operation.
         RenderedImage addConstImage = JAI.create("addconst", pb, null);

------------------------------------------------------------------------


### 6.5.3 ![](shared/space.gif)Adding a Collection of Images

The `AddCollection` operation takes a collection of rendered images
and adds every set of pixels, one from each source image of the
corresponding position and band.

There\'s no restriction on the actual class type used to represent the
source collection, but each element of the collection must be of the
class `RenderedImages`. The number of images in the collection may
vary from two to *n*, and is only limited by memory size. The source
images may have different number of bands and data types.

By default, the destination image bound is the intersection of all the
source image bounds. If any of the two sources don\'t intersect, the
destination will have a width and a height of 0. The number of bands
of the destination image is the same as the least number of bands of
all the sources, and the data type is the biggest data type of all the
sources.

The destination pixel values are calculated as:

         dst[x][y][b] = 0;
         for (int i = 0; i < numSources; i++) {
             dst[x][y][b] += srcs[i][x][y][b];
         }

If the result of the operation underflows or overflows the minimum or
maximum value supported by the destination data type, the value will
be clamped to the minimum or maximum value, respectively.

The `AddCollection` operation takes a collection of source images and
no parameters.


### 6.5.4 ![](shared/space.gif)Adding Constants to a Collection of Rendered Images

The `AddConstToCollection` operation takes a collection of rendered
images and an array of double constants, and for each rendered image
in the collection adds a constant to every pixel of its corresponding
band.

The operation will attempt to store the result images in the same
collection class as that of the source images. If a new instance of
the source collection class can not be created, the operation will
store the result images in a `java.util.Vector`. The output collection
will contain the same number of images as in the source collection.

The `AddConstToCollection` operation takes a collection of rendered
images and one parameter.

  ----------------------------------------------------------------------------------
  [Parameter]{#69967}    [Type]{#69969}      [Description]{#69975}
  ---------------------- ------------------- ---------------------------------------
  [constants]{#69971}\   [double]{#69973}\   [The constants to be added.]{#69977}\

  ----------------------------------------------------------------------------------

  : 

If the number of constants supplied is less than the number of bands
of the source image, the same constant from entry 0 is applied to all
the bands. Otherwise, a constant from a different entry is applied to
each band.


### 6.5.5 ![](shared/space.gif)Subtracting Two Source Images

The `Subtract` operation takes two rendered or renderable images, and
for every pair of pixels, one from each source image of the
corresponding position and band, subtracts the pixel from the second
source from the pixel from the first source.

The two source images may have different numbers of bands and data
types. By default, the destination image bounds are the intersection
of the two source image bounds. If the sources don\'t intersect, the
destination will have a width and height of 0.

The default number of bands of the destination image is equal to the
smallest number of bands of the source images, and the data type is
the smallest data type with sufficient range to cover the range of
both source data types (not necessarily the range of their sums).

As a special case, if one of the source images has *N* bands (where
*N* is greater than one), the other source has one band, and an
`ImageLayout` hint is provided containing a destination `SampleModel`
with *K* bands (1 \< *K* ![](shared/chars/lt_equal.gif) *N*), then the
single band of the one-banded source is subtracted from or into each
of the first *K* bands of the *N*-band source.

The destination pixel values are defined by the following pseudocode:

         dst[x][y][dstBand] = clamp(srcs[0][x][y][src0Band] -
                                    srcs[1][x][y][src1Band]);

If the result of the subtraction underflows or overflows the minimum
or maximum value supported by the destination image, the value will be
clamped to the minimum or maximum value respectively.

The `Subtract` operation takes two rendered or renderable source
images and no parameters.


### 6.5.6 ![](shared/space.gif)Subtracting a Constant from an Image

The `Subtract`Const operation takes one rendered or renderable image
and an array of double constants, and subtracts every pixel of the
same band of the source from the constant from the corresponding array
entry. If the number of constants supplied is less than the number of
bands of the destination, the constant from entry 0 is applied to all
the bands. Otherwise, a constant from a different entry is applied to
each band.

By default, the destination image bound, data type, and number of
bands are the same as the source image.

The destination pixel values are defined by the following pseudocode:

         if (constants.length < dstNumBands) {
             dst[x][y][b] = constants[0] - src[x][y][b];
         } else {
             dst[x][y][b] = constants[b] - src[x][y][b];
         }

The `Subtract`Const operation takes rendered or renderable source
image and one parameter:

  ------------------------------------------------------------------------------------------------
  [Parameter]{#60837}    [Type]{#60839}      [Description]{#60841}
  ---------------------- ------------------- -----------------------------------------------------
  [constants]{#60843}\   [double]{#60845}\   [The per-band constants to be subtracted.]{#60847}\

  ------------------------------------------------------------------------------------------------

  : 

If the result of the subtraction underflows or overflows the minimum
or maximum value supported by the destination image, the value will be
clamped to the minimum or maximum value respectively.


### 6.5.7 ![](shared/space.gif)Subtracting an Image from a Constant

The `SubtractFromConst` operation takes one rendered or renderable
source image and an array of double constants, and subtracts a
constant from every pixel of its corresponding band of the source
image. If the number of constants supplied is less than the number of
bands of the destination, the constant from entry 0 is applied to all
the bands. Otherwise, a constant from a different entry is applied to
each band. By default, the destination image bounds, data type, and
number of bands are the same as the source image.

The destination pixel values are defined by the following pseudocode:

         if (constants.length < dstNumBands) {
             dst[x][y][b] = src[x][y][b] - constants[0];
         } else {
             dst[x][y][b] = src[x][y][b] - constants[b];
         }

The `SubtractFrom`Const operation takes one rendered or renderable
source image and one parameter:

  ---------------------------------------------------------------------------------------
  [Parameter]{#63744}    [Type]{#63746}      [Description]{#63748}
  ---------------------- ------------------- --------------------------------------------
  [constants]{#63750}\   [double]{#63752}\   [The constants to be subtracted.]{#63754}\

  ---------------------------------------------------------------------------------------

  : 

If the result of the subtraction underflows or overflows the minimum
or maximum value supported by the destination image, the value will be
clamped to the minimum or maximum value respectively.


### 6.5.8 ![](shared/space.gif)Dividing One Image by Another Image

The `Divide` operation takes two rendered or renderable images, and
for every pair of pixels, one from each source image of the
corresponding position and band, divides the pixel from the first
source by the pixel from the second source.

In case of division by 0, if the numerator is 0, the result is set to
0; otherwise, the result is set to the maximum value supported by the
destination data type.

The `Divide` operation does not require any parameters.

The two source images may have different number of bands and data
types. By default, the destination image bound is the intersection of
the two source image bounds. If the two sources don\'t intersect, the
destination will have a width and a height of 0. The default number of
bands of the destination image is the same as the least number of
bands of the source images, and the data type is the biggest data type
of the sources.

As a special case, if one of the source images has *N* bands (where
*N* is greater than one), the other source has one band, and an
`ImageLayout` hint is provided containing a destination `SampleModel`
with *K* bands (1 \< *K* ![](shared/chars/lt_equal.gif) *N*), then the
single band of the one-banded source will be divided by or into to
each of the first *K* bands of the *N*-band source.

If the result of the operation underflows or overflows the minimum or
maximum value supported by the destination data type, it will be
clamped to the minimum or maximum value respectively.

The `Divide` operation takes two rendered or renderable source images
and no parameters.


### 6.5.9 ![](shared/space.gif)Dividing an Image by a Constant

The `DivideByConst` operation takes one rendered or renderable source
image and an array of double constants, and divides every pixel of the
same band of the source by the constant from the corresponding array
entry. If the number of constants supplied is less than the number of
bands of the destination, the constant from entry 0 is applied to all
the bands. Otherwise, a constant from a different entry is applied to
each band.

In case of division by 0, if the numerator is 0, the result is set to
0. Otherwise, the result is set to the maximum value supported by the
destination data type. By default, the destination image bound, data
type, and number of bands are the same as the source image.

The destination pixel values are defined by the following pseudocode:

         if (constants.length < dstNumBands) {
             dst[x][y][b] = srcs[x][y][b]/constants[0];
         } else {
             dst[x][y][b] = srcs[x][y][b]/constants[b];
         }

The `DivideByConst` operation takes one rendered or renderable source
image and one parameter:

  --------------------------------------------------------------------------------------------
  [Parameter]{#60880}    [Type]{#60882}      [Description]{#60884}
  ---------------------- ------------------- -------------------------------------------------
  [constants]{#60886}\   [double]{#60888}\   [The per-band constants to divide by.]{#60890}\

  --------------------------------------------------------------------------------------------

  : 

If the result of the division underflows or overflows the minimum or
maximum value supported by the destination image, the value will be
clamped to the minimum or maximum value, respectively.


### 6.5.10 ![](shared/space.gif)Dividing an Image into a Constant

The `DivideIntoConst` operation takes one rendered or renderable image
and an array of double constants, and divides every pixel of the same
band of the source into the constant from the corresponding array
entry. If the number of constants supplied is less than the number of
bands of the destination, the constant from entry 0 is applied to all
the bands. Otherwise, a constant from a different entry is applied to
each band.

In case of division by 0, if the numerator is 0, the result is set to
0. Otherwise, the result is set to the maximum value supported by the
destination data type.

By default, the destination image bound, data type, and number of
bands are the same as the source image.

The destination pixel values are defined by the following pseudocode:

         if (constants.length < dstNumBands) {
             dst[x][y][b] = constants[0]/src[x][y][b];
         } else {
             dst[x][y][b] = constants[b]/src[x][y][b];
         }

The `DivideIntoConst` operation takes one rendered or renderable
source image and one parameter:

  --------------------------------------------------------------------------------------------------
  [Parameter]{#60923}    [Type]{#60925}      [Description]{#60927}
  ---------------------- ------------------- -------------------------------------------------------
  [constants]{#60929}\   [double]{#60931}\   [The per-band constants to be divided into.]{#60933}\

  --------------------------------------------------------------------------------------------------

  : 

If the result of the division underflows or overflows the minimum or
maximum value supported by the destination image, the value will be
clamped to the minimum or maximum value, respectively.


### 6.5.11 ![](shared/space.gif)Dividing Complex Images

The `DivideComplex` operation divides two images representing complex
data. The source images must each contain an even number of bands with
the even-indexed bands (0, 2, etc.) representing the real and the
odd-indexed bands (1, 3, etc.) the imaginary parts of each pixel. The
destination image similarly contains an even number of bands with the
same interpretation and with contents defined by:

         a = src0[x][y][2k];
         b = src0[x][y][2k + 1];
         c = src1[x][y][2k];
         d = src1[x][y][2k + 1];
         
         dst[x][y][2k] = (a*c + b*d)/(c2 + d2)
         dst[x][y][2k + 1] = (b*c - a*d)/(c2 + d2)

:   where ![](Image-manipulation.doc.ancA4.gif)

With one exception, the number of bands of the destination image is
the same as the minimum of the number of bands of the two sources, and
the data type is the biggest data type of the sources. The exception
occurs when one of the source images has two bands, the other source
image has *N* = 2*K* bands where *K* is greater than one, and an
`ImageLayout` hint is provided containing a destination `SampleModel`
that specifies *M* = 2*L* bands for the destination image where *L* is
greater than one and L ![](shared/chars/lt_equal.gif) *K*. In this
special case if the first source has two bands, its single complex
component will be divided by each of the first *L* complex components
of the second source. If the second source has two bands, its single
complex component will divide each of the *L* complex components of
the first source.

If the result of the operation underflows or overflows the minimum or
/maximum value supported by the destination data type, it will be
clamped to the minimum or maximum value, respectively.

The `DivideComplex` operation takes two rendered or renderable source
images representing complex data and no parameters.


### 6.5.12 ![](shared/space.gif)Multiplying Two Images

The `Multiply` operation takes two rendered or renderable images, and
multiplies every pair of pixels, one from each source image of the
corresponding position and band.

The two source images may have different number of bands and data
types. By default, the destination image bound is the intersection of
the two source image bounds. If the two source images don\'t
intersect, the destination will have a width and a height of 0.

The default number of bands of the destination image is the same as
the least number of bands of the source images, and the data type is
the biggest data type of the source images. A special case may occur
if one of the source images has *N* bands where *N* is greater than
one, the other source has one band, and an `ImageLayout` hint is
provided containing a destination `SampleModel`. If the `SampleModel`
hint specifies *K* bands for the destination image where *K* is
greater than one and *K* ![](shared/chars/lt_equal.gif) *N*, each of
the first *K* bands of the *N*-band source is multiplied by the single
band of the one-band source.

In the default case the destination pixel values are calculated as:

         for (int h = 0; h < dstHeight; h++) {
              for (int w = 0; w < dstWidth; w++) {
                  for (int b = 0; b < dstNumBands; b++) {
                       dst[h][w][b] = src1[h][w][b] * src2[h][w][b];
                  }
              }
         }

The `Multiply` operation takes two rendered or renderable source
images and no parameters.

If the result of the multiplication underflows or overflows the
minimum or maximum value supported by the destination image, the value
will be clamped to the minimum or maximum value, respectively.


### 6.5.13 ![](shared/space.gif)Multiplying an Image by a Constant

The `MultiplyConst` operation takes one rendered or renderable image
and an array of double constants, and multiplies every pixel of the
same band of the source by the constant from the corresponding array
entry. If the number of constants supplied is less than the number of
bands of the destination, the constant from entry 0 is applied to all
the bands. Otherwise, a constant from a different entry is applied to
each band. By default, the destination image bound, data type, and
number of bands are the same as the source image.

The destination pixel values are calculated as:

         if (constants.length < dstNumBands) {
             dst[x][y][b] = srcs[x][y][b]*constants[0];
         } else {
             dst[x][y][b] = srcs[x][y][b]*constants[b];
         }

The `MultiplyConst` operation takes one rendered or renderable source
image and one parameter:

  ----------------------------------------------------------------------------------------------
  [Parameter]{#60998}    [Type]{#61000}      [Description]{#61002}
  ---------------------- ------------------- ---------------------------------------------------
  [constants]{#61004}\   [double]{#61006}\   [The per-band constants to multiply by.]{#61008}\

  ----------------------------------------------------------------------------------------------

  : 

If the result of the multiplication underflows or overflows the
minimum or maximum value supported by the destination image, the value
will be clamped to the minimum or maximum value respectively.


### 6.5.14 ![](shared/space.gif)Multiplying Two Complex Images

The `MultiplyComplex` operation multiplies two images representing
complex data. The source images must each contain an even number of
bands, with the with the even-indexed bands (0, 2, etc.) representing
the real and the odd-indexed bands (1, 3, etc.) the imaginary parts of
each pixel. The destination image similarly contains an even number of
bands with the same interpretation and with contents defined by:

         a = src0[x][y][2k];
         b = src0[x][y][2k + 1];
         c = src1[x][y][2k];
         d = src1[x][y][2k + 1];
         
         dst[x][y][2k] = a*c - b*d;
         dst[x][y][2k + 1] = a*d + b*c;

:   where ![](Image-manipulation.doc.anc3.gif)

With one exception, the number of bands of the destination image is
the same as the minimum of the number of bands of the two source
images, and the data type is the biggest data type of the sources. The
exception occurs when one of the source images has two bands, the
other source image has *N* = 2*K* bands where *K* is greater than one,
and an `ImageLayout` hint is provided containing a destination
`SampleModel` that specifies *M* = 2*L* bands for the destination
image where *L* is greater than one and *L*
![](shared/chars/lt_equal.gif) *K*. In this special case each of the
first *L* complex components in the *N*-band source will be multiplied
by the single complex component in the one-band source.

If the result of the operation underflows or overflows the minimum or
maximum value supported by the destination data type, it will be
clamped to the minimum or maximum value, respectively.

The `MultiplyComplex` operation takes two rendered source images
representing complex data and no parameters.


### 6.5.15 ![](shared/space.gif)Finding the Absolute Value of Pixels

Images with signed integer pixels have an asymmetrical range of values
from\
-32,768 to 32,767, which is not very useful for many imaging
operations. The `Absolute` operation takes a single rendered or
renderable source image, and computes the mathematical absolute value
of each pixel:

         if (src[x][y][b] < 0) {
              dst[x][y][b] = -src[x][y][b];
          } else {
              dst[x][y][b] = src[x][y][b];
          }

For signed integral data types, the smallest value of the data type
does not have a positive counterpart; such values will be left
unchanged. This behavior parallels that of the Java unary minus
operator.

The `Absolute` operation takes one rendered or renderable source image
and no parameters


### 6.5.16 ![](shared/space.gif)Taking the Exponent of an Image

The `Exp` operation takes the exponential of the pixel values of an
image. The pixel values of the destination image are defined by the
following pseudocode:

         dst[x][y][b] = java.lang.Math.exp(src[x][y][b])

For integral image datatypes, the result will be rounded and clamped
as needed.

The `Exp` operation takes one rendered or renderable source image and
no parameters.

[Listing 6-9](Image-manipulation.doc.html#70720) shows a partial code
sample of using the `Exp` operation to take the exponent of an image.

**[]{#70720}**

***Listing 6-9* ![](shared/sm-blank.gif) Taking the Exponent of an
Image**

------------------------------------------------------------------------

         // Create a ParameterBlock with the source image.
         pb = new ParameterBlock();
         pb.addSource(src);

         // Perform the Exp operation
         RenderedImage dst = JAI.create("exp", pb);

------------------------------------------------------------------------


6.6 ![](shared/space.gif)Dithering an Image
-------------------------------------------

The display of a 24-bit color image on an 8-bit frame buffer requires
an operation known as *dithering*. The dithering operation compresses
the three bands of an RGB image to a single-banded byte image.

The dithering operation uses a lookup table through which the source
image is passed to produce the destination image. The most-common use
for the dithering operation is to convert true-color (three-band byte)
images to pseudo-color (single-band byte) images.

JAI offers two operations for dithering an image: ordered dither and
error-diffusion dither. The choice of dithering operation depends on
desired speed and image quality, as shown in [Table
6-1](Image-manipulation.doc.html#65311).

  ------------------------------------------------------------------------------------
  [Dither Type]{#65317}        [Relative Speed]{#65319}   [Relative Quality]{#65321}
  ---------------------------- -------------------------- ----------------------------
  [Ordered]{#65323}\           [Medium]{#65325}\          [Medium]{#65327}\

  [Error diffusion]{#65329}\   [Slowest]{#65331}\         [Best]{#65333}\
  ------------------------------------------------------------------------------------

  :  **[*Table 6-1* ![](shared/sm-blank.gif) Dithering
  Choices]{#65311}**


### 6.6.1 ![](shared/space.gif)Ordered Dither

``The ordered dithering operation is somewhat faster than the
error-diffusion dither and produces a somewhat better destination
image quality than the error-diffusion dither. The `OrderedDither`
operation also differs from error-diffusion dither in that it
(`OrderedDither`) uses a color cube rather than a general lookup
table.

The `OrderedDither` operation performs color quantization by finding
the nearest color to each pixel in a supplied color cube lookup table
and \"shifting\" the resulting index value by a pseudo-random amount
determined by the values of a supplied *dither mask*.

The `OrderedDither` operation takes one rendered source image and two
parameters:

  ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
  [Parameter]{#63972}     [Type]{#63974}             [Description]{#63976}
  ----------------------- -------------------------- ------------------------------------------------------------------------------------------------------------------
  [colorMap]{#63978}\     [ColorCube]{#63980}\       [The color cube. See]{#63982} [Section 6.6.1.1, \"Color Map Parameter](Image-manipulation.doc.html#65379).\"\

  [ditherMask]{#63984}\   [KernelJAI\[\]]{#63986}\   [The dither mask. See]{#63988} [Section 6.6.1.2, \"Dither Mask Parameter](Image-manipulation.doc.html#65380).\"\
  ---------------------------------------------------------------------------------------------------------------------------------------------------------------------

  : 


#### 6.6.1.1 ![](shared/space.gif)Color Map Parameter

The `colorMap` parameter can be either one of the predefined
`ColorCubes`, or a custom color map can be created as a `ColorCube`
object. To create a custom color map, see [Section 7.6.1.3, \"Creating
a Color-cube Lookup Table](Image-enhance.doc.html#62701).\"

The predefined color maps are:

  ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  [colorMap]{#66064}     [Description]{#66066}
  ---------------------- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  [BYTE\_496]{#66068}\   [A ColorCube with dimensions 4:9:6, useful for dithering RGB images into 216 colors. The offset of this ColorCube is 38. This color cube dithers blue values in the source image to one of four blue levels, green values to one of nine green levels, and red values to one of six red levels. This is the default color cube for the ordered dither operation.]{#66070}\

  [BYTE\_855]{#66072}\   [A ColorCube with dimensions 8:5:5, useful for dithering YCbCr images into 200 colors. The offset of this ColorCube is 54. This color cube dithers blue values in the source image to one of eight blue levels, green values to one of five green levels, and red values to one of five red levels.]{#66074}\
  ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  : 


#### 6.6.1.2 ![](shared/space.gif)Dither Mask Parameter

The dither mask is a three-dimensional array of floating point values,
the depth of which equals the number of bands in the image. The dither
mask is supplied as an array of `KernelJAI` objects. Each element of
the array is a `KernelJAI` object that represents the dither mask
matrix for the corresponding band. All `KernelJAI` objects in the
array must have the same dimensions and contain floating point values
greater than or equal to 0.0 and less than or equal to 1.0.

The `ditherMask` parameter may either be one of the predefined dither
masks or a custom mask may be created. To create a custom dither mask,
see [Section 6.9, \"Constructing a
Kernel](Image-manipulation.doc.html#70882).\"

The predefined dither masks are (see [Figure
6-1](Image-manipulation.doc.html#64816)):

  -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  [ditherMask]{#66117}           [Description]{#66119}
  ------------------------------ ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  [DITHER\_MASK\_441]{#66121}\   [A 4 x 4 x 1 mask useful for dithering eight-bit grayscale images to one-bit images]{#66123}\

  [DITHER\_MASK\_443]{#66125}\   [A 4 x 4 x 3 mask useful for dithering 24-bit color images to eight-bit pseudocolor images. This is the default dither mask for the OrderedDither operation.]{#66127}\
  -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  : 


------------------------------------------------------------------------

![](Image-manipulation.doc.anc9.gif)

------------------------------------------------------------------------


***Figure 6-1* ![](shared/sm-blank.gif) Ordered Dither Masks**


#### 6.6.1.3 ![](shared/space.gif)OrderedDither Example

[Listing 6-10](Image-manipulation.doc.html#70282) shows a partial code
sample of using the `OrderedDither` operation.

**[]{#70282}**

***Listing 6-10* ![](shared/sm-blank.gif) Ordered Dither Example**

------------------------------------------------------------------------

         // Create the color cube.
         ColorCube colorMap =
             srcRescale.getSampleModel().getTransferType() ==
                        DataBuffer.TYPE_BYTE ?
             ColorCube.BYTE_496 :
             ColorCube.createColorCube(dataType, 38, new int[] {4, 9, 6});

         // Set the dither mask to the pre-defined 4x4x3 mask.
         KernelJAI[] ditherMask = KernelJAI.DITHER_MASK_443;

         // Create a new ParameterBlock.
         ParameterBlock pb = new ParameterBlock();
         pb.addSource(srcRescale).add(colorMap).add(ditherMask);

         // Create a gray scale color model.
         ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
         int bits[] = new int[] {8};
         ColorModel cm = new ComponentColorModel(cs, bits, false, false,
                                                 Transparency.OPAQUE,
                                                 DataBuffer.TYPE_BYTE);

         // Create a tiled layout with the requested ColorModel.
         layout = new ImageLayout();
         layout.setTileWidth(TILE_WIDTH).setTileHeight(TILE_HEIGHT);
         layout.setColorModel(cm);

         // Create RenderingHints for the ImageLayout.
         rh = new RenderingHints(JAI.KEY_IMAGE_LAYOUT, layout);

         // Create the ordered dither OpImage.
         PlanarImage image = (PlanarImage)JAI.create("ordereddither",
                                                     pb, rh);

------------------------------------------------------------------------


### 6.6.2 ![](shared/space.gif)Error-diffusion Dither

The error-diffusion dithering operation produces the most accurate
destination image, but is more complex and thus takes longer than the
ordered dither.

The `ErrorDiffusion` operation performs color quantization by finding
the nearest color to each pixel in a supplied lookup table, called a
color map, and \"diffusing\" the color quantization error below and to
the right of the pixel.

The source image and the color map must have the same data type and
number of bands. Also, the color map must have the same offset in all
bands. The resulting image is single-banded.

The `ErrorDiffusion` operation takes one rendered source image and two
parameters:

  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  [Parameter]{#63569}      [Type]{#63587}              [Description]{#63589}
  ------------------------ --------------------------- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  [colorMap]{#63575}\      [LookupTableJAI]{#63577}\   [The color map. A LookupTableJAI (see]{#63579} [Section 7.6.1, \"Creating the Lookup Table](Image-enhance.doc.html#51408)\") or a ColorCube (see [Section 6.6.1.1, \"Color Map Parameter](Image-manipulation.doc.html#65379)\").\

  [errorKernel]{#63581}\   [KernelJAI]{#63583}\        [The error filter kernel. See]{#63585} [Section 6.6.2.1, \"Error Filter Kernel](Image-manipulation.doc.html#65437).\"\
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  : 


#### 6.6.2.1 ![](shared/space.gif)Error Filter Kernel

The `errorKernel` parameter can be one of three predefined error
filters or you can create your own. To create your own, see [Section
6.9, \"Constructing a Kernel](Image-manipulation.doc.html#70882).\"

The predefined kernels are (see [Figure
6-2](Image-manipulation.doc.html#67565)):

  ---------------------------------------------------------------------------------------------------------------------------------------
  [errorKernel]{#66174}                        [Description]{#66176}
  -------------------------------------------- ------------------------------------------------------------------------------------------
  [ERROR\_FILTER\_FLOYD\_STEINBERG]{#66178}\   [Based on the Floyd-Steinberg filter model (the default if none is specified).]{#66180}\

  [ERROR\_FILTER\_JARVIS]{#66182}\             [Based on the Jarvis-Judice-Ninke filter model.]{#66184}\

  [ERROR\_FILTER\_STUCKI]{#66186}\             [Based on the Stucki filter model]{#66188}\
  ---------------------------------------------------------------------------------------------------------------------------------------

  : 

The error filter kernel, also known as the *error distribution
filter*, diffuses the color quantization error below and to the right
of the pixel. The elements of the error filter kernel that are in the
same row and to the right of the key element or are in a row below
that of the key element must be between 0.0 and 1.0 and must sum to
approximately 1.0. The other elements of the error filter kernel are
ignored.

In operation, the filter is laid on top of the source image so that
its origin aligns with the pixel to be passed through the lookup
table. [Figure 6-3](Image-manipulation.doc.html#65540) shows an
example using the Floyd-Steinberg filter. The diffusion operation
then:

-   Sets the pixel at 0,2 to 214 + (5 x \[7/16\])


-   Sets the pixel at 1,0 to 128 + (5 x \[3/16\])


-   Sets the pixel at 1,1 to 255 + (5 x \[5/16\])


-   Sets the pixel at 1,2 to 104 + (5 x \[1/16\])

The filter is then moved to the next pixel and the process is
repeated. The result of this process is an averaging that produces a
smoother dithered image with little or no contouring.


------------------------------------------------------------------------

![](Image-manipulation.doc.anc1.gif)

------------------------------------------------------------------------


***Figure 6-2* ![](shared/sm-blank.gif) Error Diffusion Dither
Filters**


------------------------------------------------------------------------

![](Image-manipulation.doc.anc10.gif)

------------------------------------------------------------------------


***Figure 6-3* ![](shared/sm-blank.gif) Error Diffusion Operation**


#### 6.6.2.2 ![](shared/space.gif)ErrorDiffusion Example

[Listing 6-11](Image-manipulation.doc.html#70390) shows a partial code
sample of using the `ErrorDiffusion` operation.

**[]{#70390}**

***Listing 6-11* ![](shared/sm-blank.gif) Error Diffusion Example**

------------------------------------------------------------------------

         // Create a color map with the 4-9-6 color cube and the 
         // Floyd-Steinberg error kernel.
         ParameterBlock pb;
         pb.addSource(src);
         pb.add(ColorCube.BYTE_496);
         pb.add(KernelJAI.ERROR_FILTER_FLOYD_STEINBERG);
         
         // Perform the error diffusion operation.
         dst = (PlanarImage)JAI.create("errordiffusion", pb, null);

------------------------------------------------------------------------


6.7 ![](shared/space.gif)Clamping Pixel Values
----------------------------------------------

The `clamp` operation restricts the range of pixel values for a source
image by constraining the range of pixels to defined \"low\" and
\"high\" values. The operation takes one rendered or renderable source
image, and sets all the pixels whose value is below a low value to
that low value and all the pixels whose value is above a high value to
that high value. The pixels whose value is between the low value and
the high value are left unchanged.

A different set of low and high values may be applied to each band of
the source image, or the same set of low and high values may be
applied to all bands of the source. If the number of low and high
values supplied is less than the number of bands of the source, the
values from entry 0 are applied to all the bands. Each low value must
be less than or equal to its corresponding high value.

The pixel values of the destination image are defined by the following
pseudocode:

         lowVal = (low.length < dstNumBands) ?
                  low[0] : low[b];
         highVal = (high.length < dstNumBands) ?
                   high[0] : high[b];
         
         if (src[x][y][b] < lowVal) {
             dst[x][y][b] = lowVal;
         } else if (src[x][y][b] highVal) {
             dst[x][y][b] = highVal;
         } else {
             dst[x][y][b] = src[x][y][b];
         }

The `clamp` operation takes one rendered or renderable source image
and two parameters:

  ----------------------------------------------------------------------------------------
  [Parameter]{#73429}   [Type]{#73431}      [Description]{#73433}
  --------------------- ------------------- ----------------------------------------------
  [low]{#73435}\        [Double]{#73437}\   [The lower boundary for each band.]{#73439}\

  [high]{#73441}\       [Double]{#73443}\   [The upper boundary for each band.]{#73445}\
  ----------------------------------------------------------------------------------------

  : 

[Listing 6-12](Image-manipulation.doc.html#73456) shows a partial code
sample of using the `Clamp` operation to clamp pixels values to
between 5 and 250.

**[]{#73456}**

***Listing 6-12* ![](shared/sm-blank.gif) Clamp Operation**

------------------------------------------------------------------------

         // Get the source image width, height, and SampleModel.
         int w = src.getWidth();
         int h = src.getHeight();
         int b = src.getSampleModel().getNumBands();

         // Set the low and high clamp values.
         double[] low, high;

         low  = new double[b];
         high = new double[b];

         for (int i=0; i<b; i++) {
              low[i]  = 5;               // The low clamp value
              high[i] = 250;             // The high clamp value
         }

         // Create the ParameterBlock with the source and parameters.
         pb = new ParameterBlock();
         pb.addSource(src);
         pb.add(low);
         pb.add(high);

         // Perform the operation.
         RenderedImage dst = JAI.create("clamp", pb);

------------------------------------------------------------------------


6.8 ![](shared/space.gif)Band Copying
-------------------------------------

The `BandSelect` operation chooses *N* bands from a rendered or
renderable source image and copies the pixel data of these bands to
the destination image in the order specified. The `bandIndices`
parameter specifies the source band indices, and its size
(*bandIndices.length*) determines the number of bands of the
destination image. The destination image may have ay number of bands,
and a particular band of the source image may be repeated in the
destination image by specifying it multiple times in the `bandIndices`
parameter.

Each of the `bandIndices` value should be a valid band index number of
the source image. For example, if the source only has two bands, 1 is
a valid band index, but 3 is not. The first band is numbered 0.

The destination pixel values are defined by the following pseudocode:

         dst[x][y][b] = src[x][y][bandIndices[b]];

The `bandselect` operation takes one rendered or renderable source
image and one parameter:

  ----------------------------------------------------------------------------------------------------------
  [Parameter]{#63453}      [Type]{#63455}       [Description]{#63457}
  ------------------------ -------------------- ------------------------------------------------------------
  [bandIndices]{#63417}\   [int\[\]]{#63419}\   [The indices of the selected bands of the image.]{#63421}\

  ----------------------------------------------------------------------------------------------------------

  : 

[Listing 6-13](Image-manipulation.doc.html#70887) shows a partial code
sample of using the `BandSelect` operation.

**[]{#70887}**

***Listing 6-13* ![](shared/sm-blank.gif) BandSelect Operation**

------------------------------------------------------------------------

         // Set the indices of three bands of the image.
         int[] bandIndices;
         bandIndices = new int[3];
         bandIndices[0] = 0;
         bandIndices[1] = 2;
         bandIndices[2] = 2;

         // Construct the ParameterBlock.
         pb = new ParameterBlock();
         pb.addSource(src);
         pb.add(bandIndices);

         // Perform the operation
         RenderedImage dst = (RenderedImage)JAI.create("bandSelect",
                                                       pb);

------------------------------------------------------------------------


6.9 ![](shared/space.gif)Constructing a Kernel
----------------------------------------------

The `KernelJAI` class is an auxiliary class used with the convolve,
ordered dither, error diffusion dither, dilate, and erode operations.
A `KernelJAI` is characterized by its width, height, and key element
(origin) position. The key element is the element that is placed over
the current source pixel to perform convolution or error diffusion.

For the `OrderedDither` operation (see [Section 6.6.1, \"Ordered
Dither](Image-manipulation.doc.html#56241)\"), an array of `KernelJAI`
objects is actually required with there being one `KernelJAI` per band
of the image to be dithered. The location of the key element is in
fact irrelevant to the `OrderedDither` operation.

There are four constructors for creating a `KernelJAI`. The following
constructor constructs a `KernelJAI` object with the given parameters.

         KernelJAI(int width, int height, float[] data)

The `width` and `height` parameters determine the kernel size. The
`data` parameter is a pointer to the floating point values stored in a
data array. The key element is set to

:   ![](Image-manipulation.doc.anc7.gif)

The following constructor constructs a `KernelJAI` object with the
given parameters.

         KernelJAI(int width, int height, int xOrigin, int yOrigin,

               float[] data)

The `xOrigin` and `yOrigin` parameters determine the key element\'s
origin.

The following constructor constructs a separable `KernelJAI` object
from two float arrays.

         KernelJAI(int width, int height, int xOrigin, int yOrigin,

               float[] dataH, float[] dataV)

The `dataH` and `dataV` parameters specify the float data for the
horizontal and vertical directions, respectively.

The following constructor constructs a `KernelJAI` object from a
`java.awt.image.Kernel` object.

         KernelJAI(java.awt.image.Kernel k)

[Listing 6-14](Image-manipulation.doc.html#70938) shows a partial code
sample for creating a simple 3 x 3 kernel with the key element located
at coordinates 1,1, as shown in [Figure
6-4](Image-manipulation.doc.html#67855).

**[]{#70938}**

***Listing 6-14* ![](shared/sm-blank.gif) Constructing a KernelJAI**

------------------------------------------------------------------------

         kernel = new KernelJAI;
         float[] kernelData = {
             0.0F,        1.0F,        0.0F,
             1.0F,        1.0F,        1.0F,
             0.0F,        1.0F,        0.0F
         };
         kernel = new KernelJAI(3, 3, 1, 1, kernelData);

------------------------------------------------------------------------


------------------------------------------------------------------------

![](Image-manipulation.doc.anc8.gif)

------------------------------------------------------------------------


***Figure 6-4* ![](shared/sm-blank.gif) Example Kernel**

The Java Advanced Imaging API provides a shorthand method for creating
several commonly-used kernels, listed in [Table
6-2](Image-manipulation.doc.html#68076), which can simply be called by
name. These kernels and their use are described in more detail in
[Section 6.6.1, \"Ordered
Dither](Image-manipulation.doc.html#56241),\" [Section 6.6.2,
\"Error-diffusion Dither](Image-manipulation.doc.html#56245),\" and
[Section 9.5, \"Edge Detection](Analysis.doc.html#51214).\"

  -----------------------------------------------------------------------------------------------------------------------------------------------------------------------
  [Kernel Name]{#68080}                        [Description and Use]{#68082}
  -------------------------------------------- --------------------------------------------------------------------------------------------------------------------------
  [DITHER\_MASK\_441]{#68103}\                 [Ordered dither filter. A 4 x 4 x 1 mask useful for dithering 8-bit grayscale images to 1-bit images]{#68105}\

  [DITHER\_MASK\_443]{#68108}\                 [Ordered dither filter. A 4 x 4 x 3 mask useful for dithering 24-bit color images to 8-bit pseudocolor images.]{#68110}\

  [ERROR\_FILTER\_FLOYD\_STEINBERG]{#68135}\   [Error diffusion filter, based on the Floyd-Steinberg model.]{#68138}\

  [ERROR\_FILTER\_JARVIS]{#68129}\             [Error diffusion filter, based on the Jarvis-Judice-Ninke model.]{#68132}\

  [ERROR\_FILTER\_STUCKI]{#68123}\             [Error diffusion filter, based on the Stucki model]{#68126}\

  [GRADIENT\_MASK\_SOBEL\_\                    [The horizontal gradient filter mask for the Gradient operation.]{#69607}\
  HORIZONTAL]{#69605}\                         

  [GRADIENT\_MASK\_SOBEL\_\                    [The vertical gradient filter mask for the Gradient operation.]{#69603}\
  VERTICAL]{#69601}\                           
  -----------------------------------------------------------------------------------------------------------------------------------------------------------------------

  :  **[*Table 6-2* ![](shared/sm-blank.gif) Named Kernels]{#68076}**

The following code sample shows the format for creating a named
kernel:

         KernelJAI kernel = KernelJAI.ERROR_FILTER_FLOYD_STEINBERG;

**API:** `org.eclipse.imagen.jai.KernelJAI`

    public KernelJAI(int width, int height, int xOrigin, 
           int  yOrigin, float[] data)

:   constructs a `KernelJAI` with the given parameters. The data array
    is copied.
    *Parameters*:
    `width`
    The width of the kernel.
    `height`
    The height of the kernel
    `xOrigin`
    The *x* coordinate of the key kernel element.
    `yOrigin`
    The *y* coordinate of the key kernel element.
    `data`
    The float data in row-major format.


    public KernelJAI(int width, int height, int xOrigin, 
           int  yOrigin, float[] dataH, float[] dataV)

:   constructs a separable `KernelJAI` from two float arrays. The data
    arrays are copied.
    *Parameters*:
    `dataH`
    The float data for the horizontal direction.
    `dataV`
    The float data for the vertical direction.


    public KernelJAI(int width, int height, float[] data)

:   constructs a `KernelJAI` with the given parameters. The data array
    is copied. The key element is set to (trunc(width/2),
    trunc(height/2)).
      --------------- -------- -------------------------------------
      *Parameters*:   `data`   The float data in row-major format.
      --------------- -------- -------------------------------------

      : 


    public KernelJAI(Kernel k)

:   constructs a `KernelJAI` from a `java.awt.image.Kernel` object.

------------------------------------------------------------------------

\




\

##### [Copyright](copyright.html) © 1999, Sun Microsystems, Inc. All rights reserved.
