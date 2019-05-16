
\

[]{#47771}

+----------------------------------------------------------------------:+
| Glossary                                                              |
|                                                                       |
| -------------------------------------------------------------------   |
+-----------------------------------------------------------------------+

> \
> \
> \
>
> **T**HIS glossary contains descriptions of significant terms that
> appear in this book.
>
> **affine transformation** []{#49193} 
> :   Geometric image transformation, such as translation, scaling, or
>     rotation.
>     []{#49188}
>
> **band** []{#49191} 
> :   The set of all samples of one type in an image, such as all red
>     samples or all green samples.
>     []{#49258}
>
> **box filter** []{#49259} 
> :   A low-pass spatial filter composed of uniformly-weighted
>     convolution coefficients.
>     []{#49198}
>
> **bicubic interpolation** []{#49199} 
> :   Two-dimensional cubic interpolation of pixel values based on the
>     16 pixels in a 4 x 4 pixel neighborhood. See also [*bilinear
>     interpolation*](Glossary.doc.html#49200), [*nearest-neighbor
>     interpolation*](Glossary.doc.html#49221).
>     []{#49200}
>
> **bilinear interpolation** []{#49201} 
> :   Two-dimensional linear interpolation of pixel values based on the
>     four pixels in a 2 x 2 pixel neighborhood. See also [*bicubic
>     interpolation*](Glossary.doc.html#49198), [*nearest-neighbor
>     interpolation*](Glossary.doc.html#49221).
>     []{#49302}
>
> **binary image** []{#49303} 
> :   An image that consists of only two brightness levels: black and
>     white.
>     []{#49304}
>
> **chain code** []{#49305} 
> :   A pixel-by-pixel direction code that defines boundaries of objects
>     within an image. The chain code records an object boundary as a
>     series of direction codes.
>     []{#49296}
>
> **cobble** []{#49297} 
> :   To assemble multiple tile regions into a single contiguous region.
>     []{#49260}
>
> **color space conversion** []{#49261} 
> :   The conversion of a color using one space to another color space,
>     such as RGB to CMYK.
>     []{#49190}
>
> **components** []{#49189} 
> :   Values of samples independent of color interpretation.
>     []{#49328}
>
> **compression ratio** []{#49329} 
> :   In image compression, the ratio of an uncompressed image data file
>     size to its compressed counterpart.
>     []{#48221}
>
> **data element** []{#49056} 
> :   Primitive types used as units of storage of image data. Data
>     elements are individual members of a `DataBuffer` array.
>     []{#49265}
>
> **directed graph (digraph)** []{#49282} 
> :   A graph with one-way edges. See also [*directed acyclic graph
>     (DAG)*](Glossary.doc.html#49269).
>     []{#49269}
>
> **directed acyclic graph (DAG)** []{#49287} 
> :   A directed graph containing no cycles. This means that if there is
>     a route from node A to node B then there is no way back.
>     []{#49330}
>
> **first-order interpolation** []{#49335} 
> :   See [*bilinear interpolation*](Glossary.doc.html#49200).
>     []{#49343}
>
> **high-pass filter** []{#49344} 
> :   A spatial filter that accentuates an image\'s high-frequency
>     detail or attenuates the low-frequency detail. Contrast with
>     [*low-pass filter*](Glossary.doc.html#49345), [*median
>     filter*](Glossary.doc.html#49355).
>     []{#49316}
>
> **histogram** []{#49317} 
> :   A measure of the amplitude distribution of pixels within an image.
>     []{#49298}
>
> **Lempel-Ziv-Welch (LZW) compression** []{#49299} 
> :   A lossless image coding method that scans the image for repeating
>     patterns of blocks of pixels and codes the patterns into a code
>     list.
>     []{#49345}
>
> **low-pass filter** []{#49350} 
> :   A spatial filter that attenuates an image\'s high-frequency detail
>     or accentuates the low-frequency detail. Contrast with [*high-pass
>     filter*](Glossary.doc.html#49343), [*median
>     filter*](Glossary.doc.html#49355).
>     []{#49355}
>
> **median filter** []{#49356} 
> :   A non-linear spatial filter used to remove noise spikes from an
>     image.
>     []{#49221}
>
> **nearest-neighbor interpolation** []{#49222} 
> :   Two-dimensional interpolation of pixel values in which the
>     amplitude of the interpolated sample is the amplitude of its
>     nearest neighbor. See also [*bicubic
>     interpolation*](Glossary.doc.html#49198), [*bilinear
>     interpolation*](Glossary.doc.html#49200).
>     []{#49364}
>
> **perspective warp** []{#49365} 
> :   An image distortion in which objects appear trapezoidal due to
>     foreshortening.
>     []{#49366}
>
> **projective warp** []{#49367} 
> :   See [*perspective warp*](Glossary.doc.html#49364).
>     []{#49374}
>
> **quantization** []{#49375} 
> :   The conversion of discrete image samples to digital quantities.
>     []{#49431}
>
> **ROI** []{#49432} 
> :   Abbreviation for *region of interest*. An area or pixel group
>     within an image that has been selected for processing.
>     []{#49376}
>
> **run-length coding** []{#49377} 
> :   A type of lossless image data compression that scans for sequences
>     of pixels with the same brightness level and codes them into a
>     reduced description.
>     []{#49378}
>
> **Sobel edge detection** []{#49379} 
> :   A spatial edge detection filter that detects edges by finding the
>     gradient of an image.
>     []{#49390}
>
> **square pixel** []{#49391} 
> :   A pixel with equal height and width.
>     []{#49394}
>
> **thresholding** []{#49395} 
> :   A point operation that maps all the pixel values of an image that
>     fall within a given range to one of a set of per-band constants.
>     []{#49404}
>
> **transform coding** []{#49405} 
> :   A form of lossy block coding that transforms blocks of an image
>     from the spatial domain to the frequency domain.
>     []{#49418}
>
> **trapping** []{#49419} 
> :   An image manipulation technique used in printing that uses
>     dilation and erosion to compensation for misregistration of
>     colors.
>     []{#49244}
>
> **unsharp masking** []{#49245} 
> :   An image enhancement technique using a high-frequency accentuating
>     filter.
>     []{#49420}
>
> **zero-order interpolation** []{#49421} 
> :   See [*nearest-neighbor interpolation*](Glossary.doc.html#49221).
>
> ------------------------------------------------------------------------
>
> \
>
> > >
> \
>
> ##### [Copyright](copyright.html) © 1999, Sun Microsystems, Inc. All rights reserved.
