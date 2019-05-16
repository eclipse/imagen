![](shared/SunLogo.gif)



Release 1.0.1, November 1999

\

[]{#69260}

+----------------------------------------------------------------------:+
| Contents                                                              |
|                                                                       |
| -------------------------------------------------------------------   |
+-----------------------------------------------------------------------+

 \
 \
 \

 [](Preface.doc.html#48128)

 ### Preface

 :   Disclaimer [](Preface.doc.html#48300)

 :   About This Book [](Preface.doc.html#47778)

 :   Related Documentation [](Preface.doc.html#48699)

 :   Additional Information [](Preface.doc.html#47925)

 :   Style Conventions

 [](Introduction.doc.html#47285)

 ### 1 Introduction to Java Advanced Imaging

 :   1.1 The Evolution of Imaging in Java
     [](Introduction.doc.html#52465)

 :   1.2 Why Another Imaging API? [](Introduction.doc.html#52433)

 :   1.3 JAI Features

     :   1.3.1 Cross-platform Imaging [](Introduction.doc.html#54528)

     :   1.3.2 Distributed Imaging [](Introduction.doc.html#52437)

     :   1.3.3 Object-oriented API [](Introduction.doc.html#54286)

     :   1.3.4 Flexible and Extensible [](Introduction.doc.html#54289)

     :   1.3.5 Device Independent [](Introduction.doc.html#52444)

     :   1.3.6 Powerful [](Introduction.doc.html#52446)

     :   1.3.7 High Performance [](Introduction.doc.html#52448)

     :   1.3.8 Interoperable

     [](Introduction.doc.html#53715)

 :   1.4 A Simple JAI Program

 [](J2D-concepts.doc.html#47285)

 ### 2 Java AWT Imaging

 :   2.1 Introduction

     :   2.1.1 The AWT Push Model [](J2D-concepts.doc.html#52551)

     :   2.1.2 AWT Push Model Interfaces and Classes

     [](J2D-concepts.doc.html#50792)

 :   2.2 The Immediate Mode Model

     :   2.2.1 Rendering Independence [](J2D-concepts.doc.html#51292)

     :   2.2.2 Rendering-independent Imaging in Java AWT
         [](J2D-concepts.doc.html#51333)

     :   2.2.3 The Renderable Layer vs. the Rendered Layer
         [](J2D-concepts.doc.html#51446)

     :   2.2.4 The Render Context

     [](J2D-concepts.doc.html#51549)

 :   2.3 Renderable and Rendered Classes

     :   2.3.1 The Renderable Layer [](J2D-concepts.doc.html#51895)

     :   2.3.2 The Rendered Layer

     [](J2D-concepts.doc.html#52031)

 :   2.4 Java Image Data Representation [](J2D-concepts.doc.html#52335)

 :   2.5 Introducing the Java Advanced Imaging API

     :   2.5.1 Similarities with the Java 2D API
         [](J2D-concepts.doc.html#52719)

     :   2.5.2 JAI Data Classes

 [](Programming-environ.doc.html#47285)

 ### 3 Programming in Java Advanced Imaging

 :   3.1 Introduction [](Programming-environ.doc.html#53105)

 :   3.2 An Overview of Graphs [](Programming-environ.doc.html#55910)

 :   3.3 Processing Graphs

     :   3.3.1 Rendered Graphs [](Programming-environ.doc.html#55932)

     :   3.3.2 Renderable Graphs [](Programming-environ.doc.html#57712)

     :   3.3.3 Reusing Graphs

     [](Programming-environ.doc.html#56364)

 :   3.4 Remote Execution [](Programming-environ.doc.html#52603)

 :   3.5 Basic JAI API Classes

     :   3.5.1 The JAI Class [](Programming-environ.doc.html#52611)

     :   3.5.2 The PlanarImage Class
         [](Programming-environ.doc.html#52625)

     :   3.5.3 The CollectionImage Class
         [](Programming-environ.doc.html#52640)

     :   3.5.4 The TiledImage Class
         [](Programming-environ.doc.html#52693)

     :   3.5.5 The OpImage Class [](Programming-environ.doc.html#52811)

     :   3.5.6 The RenderableOp Class
         [](Programming-environ.doc.html#52834)

     :   3.5.7 The RenderedOp Class

     [](Programming-environ.doc.html#55112)

 :   3.6 JAI API Operators

     :   3.6.1 Point Operators [](Programming-environ.doc.html#55460)

     :   3.6.2 Area Operators [](Programming-environ.doc.html#60616)

     :   3.6.3 Geometric Operators
         [](Programming-environ.doc.html#60618)

     :   3.6.4 Color Quantization Operators
         [](Programming-environ.doc.html#55610)

     :   3.6.5 File Operators [](Programming-environ.doc.html#55692)

     :   3.6.6 Frequency Operators
         [](Programming-environ.doc.html#55775)

     :   3.6.7 Statistical Operators
         [](Programming-environ.doc.html#55826)

     :   3.6.8 Edge Extraction Operators
         [](Programming-environ.doc.html#63124)

     :   3.6.9 Miscellaneous Operators

     [](Programming-environ.doc.html#58389)

 :   3.7 Creating Operations

     :   3.7.1 Operation Name [](Programming-environ.doc.html#58271)

     :   3.7.2 Parameter Blocks [](Programming-environ.doc.html#55991)

     :   3.7.3 Rendering Hints

 [](Acquisition.doc.html#81550)

 ### 4 Image Acquisition and Display

 :   4.1 Introduction

     :   4.1.1 Image Data [](Acquisition.doc.html#52258)

     :   4.1.2 Basic Storage Types

     [](Acquisition.doc.html#52336)

 :   4.2 JAI Image Types

     :   4.2.1 Planar Image [](Acquisition.doc.html#52363)

     :   4.2.2 Tiled Image [](Acquisition.doc.html#53341)

     :   4.2.3 Snapshot Image [](Acquisition.doc.html#77770)

     :   4.2.4 Remote Image [](Acquisition.doc.html#53305)

     :   4.2.5 Collection Image [](Acquisition.doc.html#77784)

     :   4.2.6 Image Sequence [](Acquisition.doc.html#77800)

     :   4.2.7 Image Stack [](Acquisition.doc.html#55720)

     :   4.2.8 Image MIP Map [](Acquisition.doc.html#54266)

     :   4.2.9 Image Pyramid [](Acquisition.doc.html#80612)

     :   4.2.10 Multi-resolution Renderable Images

     [](Acquisition.doc.html#80641)

 :   4.3 Streams [](Acquisition.doc.html#74172)

 :   4.4 Reading Image Files

     :   4.4.1 Standard File Readers for Most Data Types
         [](Acquisition.doc.html#61287)

     :   4.4.2 Reading TIFF Images [](Acquisition.doc.html#75837)

     :   4.4.3 Reading FlashPix Images [](Acquisition.doc.html#61965)

     :   4.4.4 Reading JPEG Images [](Acquisition.doc.html#61988)

     :   4.4.5 Reading GIF Images [](Acquisition.doc.html#62011)

     :   4.4.6 Reading BMP Images [](Acquisition.doc.html#62013)

     :   4.4.7 Reading PNG Images [](Acquisition.doc.html#64990)

     :   4.4.8 Reading PNM Images [](Acquisition.doc.html#62069)

     :   4.4.9 Reading Standard AWT Images
         [](Acquisition.doc.html#71531)

     :   4.4.10 Reading URL Images

     [](Acquisition.doc.html#79558)

 :   4.5 Reformatting an Image [](Acquisition.doc.html#81553)

 :   4.6 Converting a Rendered Image to Renderable
     [](Acquisition.doc.html#72170)

 :   4.7 Creating a Constant Image [](Acquisition.doc.html#73265)

 :   4.8 Image Display

     :   4.8.1 Positioning the Image in the Panel
         [](Acquisition.doc.html#55352)

     :   4.8.2 The ImageCanvas Class [](Acquisition.doc.html#80045)

     :   4.8.3 Image Origin

 [](Color.doc.html#47285)

 ### 5 Color Space

 :   5.1 Introduction [](Color.doc.html#51219)

 :   5.2 Color Management

     :   5.2.1 Color Models [](Color.doc.html#51305)

     :   5.2.2 Color Space [](Color.doc.html#51500)

     :   5.2.3 ICC Profile and ICC Color Space

     [](Color.doc.html#51516)

 :   5.3 Transparency [](Color.doc.html#51551)

 :   5.4 Color Conversion [](Color.doc.html#51591)

 :   5.5 Non-standard Linear Color Conversion (BandCombine)

 [](Image-manipulation.doc.html#47285)

 ### 6 Image Manipulation

 :   6.1 Introduction [](Image-manipulation.doc.html#51458)

 :   6.2 Region of Interest Control

     :   6.2.1 The ROI Class [](Image-manipulation.doc.html#53075)

     :   6.2.2 The ROIShape Class

     [](Image-manipulation.doc.html#51159)

 :   6.3 Relational Operators

     :   6.3.1 Finding the Maximum Values of Two Images
         [](Image-manipulation.doc.html#58446)

     :   6.3.2 Finding the Minimum Values of Two Images

     [](Image-manipulation.doc.html#58465)

 :   6.4 Logical Operators

     :   6.4.1 ANDing Two Images [](Image-manipulation.doc.html#56421)

     :   6.4.2 ANDing an Image with a Constant
         [](Image-manipulation.doc.html#61460)

     :   6.4.3 ORing Two Images [](Image-manipulation.doc.html#58466)

     :   6.4.4 ORing an Image with a Constant
         [](Image-manipulation.doc.html#59163)

     :   6.4.5 XORing Two Images [](Image-manipulation.doc.html#59165)

     :   6.4.6 XORing an Image with a Constant
         [](Image-manipulation.doc.html#62697)

     :   6.4.7 Taking the Bitwise NOT of an Image

     [](Image-manipulation.doc.html#57560)

 :   6.5 Arithmetic Operators

     :   6.5.1 Adding Two Source Images
         [](Image-manipulation.doc.html#56269)

     :   6.5.2 Adding a Constant Value to an Image
         [](Image-manipulation.doc.html#68198)

     :   6.5.3 Adding a Collection of Images
         [](Image-manipulation.doc.html#69890)

     :   6.5.4 Adding Constants to a Collection of Rendered Images
         [](Image-manipulation.doc.html#59012)

     :   6.5.5 Subtracting Two Source Images
         [](Image-manipulation.doc.html#59014)

     :   6.5.6 Subtracting a Constant from an Image
         [](Image-manipulation.doc.html#63705)

     :   6.5.7 Subtracting an Image from a Constant
         [](Image-manipulation.doc.html#63828)

     :   6.5.8 Dividing One Image by Another Image
         [](Image-manipulation.doc.html#59876)

     :   6.5.9 Dividing an Image by a Constant
         [](Image-manipulation.doc.html#59931)

     :   6.5.10 Dividing an Image into a Constant
         [](Image-manipulation.doc.html#59909)

     :   6.5.11 Dividing Complex Images
         [](Image-manipulation.doc.html#58296)

     :   6.5.12 Multiplying Two Images
         [](Image-manipulation.doc.html#58212)

     :   6.5.13 Multiplying an Image by a Constant
         [](Image-manipulation.doc.html#59962)

     :   6.5.14 Multiplying Two Complex Images
         [](Image-manipulation.doc.html#56159)

     :   6.5.15 Finding the Absolute Value of Pixels
         [](Image-manipulation.doc.html#58051)

     :   6.5.16 Taking the Exponent of an Image

     [](Image-manipulation.doc.html#62253)

 :   6.6 Dithering an Image

     :   6.6.1 Ordered Dither [](Image-manipulation.doc.html#56245)

     :   6.6.2 Error-diffusion Dither

     [](Image-manipulation.doc.html#56747)

 :   6.7 Clamping Pixel Values [](Image-manipulation.doc.html#63344)

 :   6.8 Band Copying [](Image-manipulation.doc.html#70882)

 :   6.9 Constructing a Kernel

 [](Image-enhance.doc.html#47285)

 ### 7 Image Enhancement

 :   7.1 Introduction [](Image-enhance.doc.html#68364)

 :   7.2 Adding Borders to Images

     :   7.2.1 The Border Operation [](Image-enhance.doc.html#68606)

     :   7.2.2 Extending the Edge of an Image

     [](Image-enhance.doc.html#73080)

 :   7.3 Cropping an Image [](Image-enhance.doc.html#76502)

 :   7.4 Amplitude Rescaling [](Image-enhance.doc.html#70862)

 :   7.5 Histogram Equalization

     :   7.5.1 Piecewise Linear Mapping
         [](Image-enhance.doc.html#71727)

     :   7.5.2 Histogram Matching

     [](Image-enhance.doc.html#71424)

 :   7.6 Lookup Table Modification

     :   7.6.1 Creating the Lookup Table
         [](Image-enhance.doc.html#56300)

     :   7.6.2 Performing the Lookup [](Image-enhance.doc.html#64998)

     :   7.6.3 Other Lookup Table Operations

     [](Image-enhance.doc.html#51172)

 :   7.7 Convolution Filtering

     :   7.7.1 Performing the Convolve Operation
         [](Image-enhance.doc.html#59705)

     :   7.7.2 Box Filter

     [](Image-enhance.doc.html#66803)

 :   7.8 Median Filtering [](Image-enhance.doc.html#59829)

 :   7.9 Frequency Domain Processing

     :   7.9.1 Fourier Transform [](Image-enhance.doc.html#65675)

     :   7.9.2 Cosine Transform [](Image-enhance.doc.html#66700)

     :   7.9.3 Magnitude Enhancement [](Image-enhance.doc.html#66728)

     :   7.9.4 Magnitude-squared Enhancement
         [](Image-enhance.doc.html#66739)

     :   7.9.5 Phase Enhancement [](Image-enhance.doc.html#66360)

     :   7.9.6 Complex Conjugate [](Image-enhance.doc.html#66438)

     :   7.9.7 Periodic Shift [](Image-enhance.doc.html#67381)

     :   7.9.8 Polar to Complex [](Image-enhance.doc.html#67438)

     :   7.9.9 Images Based on a Functional Description

     [](Image-enhance.doc.html#60152)

 :   7.10 Single-image Pixel Point Processing

     :   7.10.1 Pixel Inverting [](Image-enhance.doc.html#60051)

     :   7.10.2 Logarithmic Enhancement

     [](Image-enhance.doc.html#60966)

 :   7.11 Dual Image Pixel Point Processing

     :   7.11.1 Overlay Images [](Image-enhance.doc.html#61005)

     :   7.11.2 Image Compositing

     [](Image-enhance.doc.html#63249)

 :   7.12 Thresholding

 [](Geom-image-manip.doc.html#51140)

 ### 8 Geometric Image Manipulation

 :   8.1 Introduction [](Geom-image-manip.doc.html#51290)

 :   8.2 Interpolation

     :   8.2.1 Nearest-neighbor Interpolation
         [](Geom-image-manip.doc.html#55403)

     :   8.2.2 Bilinear Interpolation
         [](Geom-image-manip.doc.html#55431)

     :   8.2.3 Bicubic Interpolation
         [](Geom-image-manip.doc.html#55447)

     :   8.2.4 Bicubic2 Interpolation
         [](Geom-image-manip.doc.html#55492)

     :   8.2.5 Table Interpolation

     [](Geom-image-manip.doc.html#56707)

 :   8.3 Geometric Transformation

     :   8.3.1 Translation Transformation
         [](Geom-image-manip.doc.html#60938)

     :   8.3.2 Scaling Transformation
         [](Geom-image-manip.doc.html#61009)

     :   8.3.3 Rotation Transformation
         [](Geom-image-manip.doc.html#51275)

     :   8.3.4 Affine Transformation

     [](Geom-image-manip.doc.html#55959)

 :   8.4 Perspective Transformation

     :   8.4.1 Performing the Transform
         [](Geom-image-manip.doc.html#56192)

     :   8.4.2 Mapping a Quadrilateral
         [](Geom-image-manip.doc.html#56537)

     :   8.4.3 Mapping Triangles [](Geom-image-manip.doc.html#66375)

     :   8.4.4 Inverse Perspective Transform
         [](Geom-image-manip.doc.html#73535)

     :   8.4.5 Creating the Adjoint of the Current Transform

     [](Geom-image-manip.doc.html#66415)

 :   8.5 Transposing [](Geom-image-manip.doc.html#57228)

 :   8.6 Shearing [](Geom-image-manip.doc.html#53798)

 :   8.7 Warping

     :   8.7.1 Performing a Warp Operation
         [](Geom-image-manip.doc.html#58097)

     :   8.7.2 Polynomial Warp [](Geom-image-manip.doc.html#62828)

     :   8.7.3 General Polynomial Warp
         [](Geom-image-manip.doc.html#62411)

     :   8.7.4 Grid Warp [](Geom-image-manip.doc.html#58581)

     :   8.7.5 Quadratic Warp [](Geom-image-manip.doc.html#66987)

     :   8.7.6 Cubic Warp [](Geom-image-manip.doc.html#58571)

     :   8.7.7 Perspective Warp [](Geom-image-manip.doc.html#58577)

     :   8.7.8 Affine Warp

 [](Analysis.doc.html#55366)

 ### 9 Image Analysis

 :   9.1 Introduction [](Analysis.doc.html#54841)

 :   9.2 Finding the Mean Value of an Image Region
     [](Analysis.doc.html#54907)

 :   9.3 Finding the Extrema of an Image [](Analysis.doc.html#54836)

 :   9.4 Histogram Generation

     :   9.4.1 Specifying the Histogram [](Analysis.doc.html#52616)

     :   9.4.2 Performing the Histogram Operation
         [](Analysis.doc.html#51842)

     :   9.4.3 Reading the Histogram Data [](Analysis.doc.html#55476)

     :   9.4.4 Histogram Operation Example

     [](Analysis.doc.html#51214)

 :   9.5 Edge Detection [](Analysis.doc.html#52769)

 :   9.6 Statistical Operations

 [](Graphics.doc.html#51143)

 ### 10 Graphics Rendering

 :   10.1 Introduction

     :   10.1.1 Simple 2D Graphics [](Graphics.doc.html#51362)

     :   10.1.2 Renderable Graphics

     [](Graphics.doc.html#51781)

 :   10.2 A Review of Graphics Rendering

     :   10.2.1 Overview of the Rendering Process
         [](Graphics.doc.html#51812)

     :   10.2.2 Stroke Attributes [](Graphics.doc.html#52230)

     :   10.2.3 Rendering Graphics Primitives

     [](Graphics.doc.html#51398)

 :   10.3 Graphics2D Example [](Graphics.doc.html#51405)

 :   10.4 Adding Graphics and Text to an Image

 [](Properties.doc.html#47285)

 ### 11 Image Properties

 :   11.1 Introduction

     :   11.1.1 The PropertySource Interface
         [](Properties.doc.html#51630)

     :   11.1.2 The PropertyGenerator Interface

     [](Properties.doc.html#52095)

 :   11.2 Synthetic Properties [](Properties.doc.html#51632)

 :   11.3 Regions of Interest [](Properties.doc.html#52134)

 :   11.4 Complex Data

 [](Client-server.doc.html#47285)

 ### 12 Client-Server Imaging

 :   12.1 Introduction [](Client-server.doc.html#51212)

 :   12.2 Server Name and Port Number [](Client-server.doc.html#51218)

 :   12.3 Setting the Timeout Period and Number of Retries
     [](Client-server.doc.html#51951)

 :   12.4 Remote Imaging Test Example

     :   12.4.1 Simple Remote Imaging Example
         [](Client-server.doc.html#52700)

     :   12.4.2 RemoteImaging Example Across Two Nodes

     [](Client-server.doc.html#51162)

 :   12.5 Running Remote Imaging

     :   12.5.1 Step 1: Create a Security Policy File
         [](Client-server.doc.html#55056)

     :   12.5.2 Step 2: Start the RMI Registry
         [](Client-server.doc.html#54962)

     :   12.5.3 Step 3: Start the Remote Image Server
         [](Client-server.doc.html#54969)

     :   12.5.4 Step 4: Run the Local Application

     [](Client-server.doc.html#55174)

 :   12.6 Internet Imaging Protocol (IIP)

     :   12.6.1 IIP Operation [](Client-server.doc.html#55791)

     :   12.6.2 IIPResolution Operation

 [](Encode.doc.html#47285)

 ### 13 Writing Image Files

 :   13.1 Introduction [](Encode.doc.html#56452)

 :   13.2 Writing to a File [](Encode.doc.html#56483)

 :   13.3 Writing to an Output Stream [](Encode.doc.html#51259)

 :   13.4 Writing BMP Image Files

     :   13.4.1 BMP Version [](Encode.doc.html#56260)

     :   13.4.2 BMP Data Layout [](Encode.doc.html#56293)

     :   13.4.3 Example Code

     [](Encode.doc.html#51358)

 :   13.5 Writing JPEG Image Files

     :   13.5.1 JFIF Header [](Encode.doc.html#51386)

     :   13.5.2 JPEG DCT Compression Parameters
         [](Encode.doc.html#51433)

     :   13.5.3 Quantization Table [](Encode.doc.html#51546)

     :   13.5.4 Horizontal and Vertical Subsampling
         [](Encode.doc.html#51700)

     :   13.5.5 Compression Quality [](Encode.doc.html#57621)

     :   13.5.6 Restart Interval [](Encode.doc.html#56121)

     :   13.5.7 Writing an Abbreviated JPEG Stream
         [](Encode.doc.html#54656)

     :   13.5.8 Example Code

     [](Encode.doc.html#51712)

 :   13.6 Writing PNG Image Files

     :   13.6.1 PNG Image Layout [](Encode.doc.html#53157)

     :   13.6.2 PNG Filtering [](Encode.doc.html#53673)

     :   13.6.3 Bit Depth [](Encode.doc.html#53730)

     :   13.6.4 Interlaced Data Order [](Encode.doc.html#53779)

     :   13.6.5 PLTE Chunk for Palette Images [](Encode.doc.html#53197)

     :   13.6.6 Ancillary Chunk Specifications

     [](Encode.doc.html#52304)

 :   13.7 Writing PNM Image Files [](Encode.doc.html#56754)

 :   13.8 Writing TIFF Image Files

     :   13.8.1 TIFF Compression [](Encode.doc.html#56916)

     :   13.8.2 TIFF Tiled Images

 [](Extension.doc.html#47285)

 ### 14 Extending the API

 :   14.1 Introduction [](Extension.doc.html#55405)

 :   14.2 Package Naming Convention [](Extension.doc.html#50856)

 :   14.3 Writing New Operators

     :   14.3.1 Extending the OpImage Class
         [](Extension.doc.html#51155)

     :   14.3.2 Extending the OperationDescriptor Interface

     [](Extension.doc.html#51261)

 :   14.4 Iterators

     :   14.4.1 RectIter [](Extension.doc.html#51452)

     :   14.4.2 RookIter [](Extension.doc.html#53284)

     :   14.4.3 RandomIter [](Extension.doc.html#57770)

     :   14.4.4 Example RectIter

     [](Extension.doc.html#56033)

 :   14.5 Writing New Image Decoders and Encoders

     :   14.5.1 Image Codecs

 [](Examples.doc.html#72446)

 ### A Program Examples

 :   A.1 Lookup Operation Example [](Examples.doc.html#74136)

 :   A.2 Adding an OperationDescriptor Example

 [](API-summary.doc.html#72446)

 ### B Java Advanced Imaging API Summary

 :   B.1 Java AWT Imaging [](API-summary.doc.html#73746)

 :   B.2 Java 2D Imaging

     :   B.2.1 Java 2D Imaging Interfaces
         [](API-summary.doc.html#74224)

     :   B.2.2 Java 2D Imaging Classes

     [](API-summary.doc.html#75368)

 :   B.3 Java Advanced Imaging

     :   B.3.1 JAI Interfaces [](API-summary.doc.html#72561)

     :   B.3.2 JAI Classes [](API-summary.doc.html#77302)

     :   B.3.3 JAI Iterator Interfaces [](API-summary.doc.html#77419)

     :   B.3.4 JAI Iterator Classes [](API-summary.doc.html#73476)

     :   B.3.5 JAI Operator Classes [](API-summary.doc.html#79150)

     :   B.3.6 JAI Widget Interfaces [](API-summary.doc.html#73681)

     :   B.3.7 JAI Widget Classes

 [](Glossary.doc.html#47771)

 ### Glossary

 ------------------------------------------------------------------------

 \

 ##### [Copyright](copyright.html) © 1999, Sun Microsystems, Inc. All rights reserved.
