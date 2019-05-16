---
layout: default
title: API Summary
parent: Programming Guide
nav_order: 18
---

  -------------------------------------------
    A P P E N D I X ![](shared/sm-space.gif)B
  -------------------------------------------

[]{#72446}

+----------------------------------------------------------------------:+
| -------------------------------------------------------------------   |
|                                                                       |
| Java Advanced Imaging API Summary                                     |
+-----------------------------------------------------------------------+

 \
 \
 \

 **T**HIS appendix summarizes the imaging interfaces and classes for
 Java AWT, Java 2D, and Java Advanced Imaging API.

 []{#75376}

 B.1 Java AWT Imaging
 -----------------------------------------

 [Table B-1](API-summary.doc.html#75399) lists and describes the
 `java.awt` imaging classes.

   ----------------------------------------------------------------------------------------------
   [Class]{#75403}    [Description]{#75405}
   ------------------ ---------------------------------------------------------------------------
   [Image]{#75407}\   [Extends: Object]{#75409}\
                      [The superclass of all classes that represent graphical images.]{#75413}\

   ----------------------------------------------------------------------------------------------

   :  **[Table B-1 **java.awt** Imaging Classes]{#75399}**

 []{#73746}

 B.2 Java 2D Imaging
 ----------------------------------------

 The Java 2D API is a set of classes for advanced 2D graphics and
 imaging. It encompasses line art, text, and images in a single
 comprehensive model. The API provides extensive support for image
 compositing and alpha channel images, a set of classes to provide
 accurate color space definition and conversion, and a rich set of
 display-oriented imaging operators.

 The Java 2D classes are provided as additions to the `java.awt` and
 `java.awt.image` packages (rather than as a separate package).

 []{#73747}

 ### B.2.1 Java 2D Imaging Interfaces

 [Table B-2](API-summary.doc.html#73751) lists and briefly describes
 the imaging interfaces defined in the `java.awt.image `(Java 2D) API.

   ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   [Interface]{#73755}                 [Description]{#73757}
   ----------------------------------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   [BufferedImageOp]{#73759}\          [Describes single-input/single-output operations performed on BufferedImage objects. This is implemented by such classes as AffineTransformOp, ConvolveOp, BandCombineOp,]{#75427}\
                                       [and LookupOp.]{#73761}\

   [ImageConsumer]{#73763}\            [Used for objects expressing interest in image data through the ImageProducer interfaces.]{#73793}\

   [ImageObserver]{#73851}\            [Receives notifications about Image information as the Image is constructed.]{#75436}\

   [ImageProducer]{#73847}\            [Used for objects that can produce the image data for Images. Each image contains an ImageProducer that is used to reconstruct the image whenever it is needed, for example, when a new size of the Image is scaled, or when the width or height of the Image is being requested.]{#73888}\

   [ImagingLib]{#73843}\               [Provides a hook to access platform-specific imaging code.]{#73920}\

   [RasterImageConsumer]{#73839}\      [Extends: ImageConsumer]{#73954}\
                                       [The interface for objects expressing interest in image data through the ImageProducer interfaces. When a consumer is added to an image producer, the producer delivers all of the data about the image using the method calls defined in this interface.]{#75463}\

   [RasterOp]{#73835}\                 [Describes single-input/single-output operations performed on Raster objects. This is implemented by such classes as AffineTransformOp, ConvolveOp, and LookupOp.]{#73998}\

   [RenderedImage]{#73831}\            [A common interface for objects that contain or can produce image data in the form of Rasters.]{#74043}\

   [TileChangeListener]{#73827}\       [An interface for objects that wish to be informed when tiles of a WritableRenderedImage become modifiable by some writer via a call to getWritableTile, and when they become unmodifiable via the last call to releaseWritableTile.]{#74081}\

   [WriteableRenderedImage]{#73823}\   [Extends: RenderedImage]{#74163}\
                                       [A common interface for objects that contain or can produce image data that can be modified and/or written over.]{#75484}\
   ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

   :  **[Table B-2 **java.awt.image** Interfaces]{#73751}**

 []{#74224}

 ### B.2.2 Java 2D Imaging Classes

 [Table B-3](API-summary.doc.html#74246) lists and briefly describes
 the imaging classes defined in the `java.awt.image `(Java 2D) API.

   ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   [Class]{#74414}                                [Description]{#74416}
   ---------------------------------------------- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   [AffineTransformOp]{#74254}\                   [Extends: Object]{#74696}\
                                                  [Implements: BufferedImageOp, RasterOp]{#74703}\
                                                  [An abstract class that uses an affine transform to perform a linear mapping from 2D coordinates in the source image or Raster to 2D coordinates in the destination image or Raster.]{#74420}\

   [AreaAveragingScaleFilter]{#74258}\            [Extends: ReplicateScaleFilter]{#74705}\
                                                  [An ImageFilter class for scaling images using a simple area averaging algorithm that produces smoother results than the nearest-neighbor algorithm.]{#74547}\

   [BandCombineOp]{#74262}\                       [Extends: Object]{#74715}\
                                                  [Implements: RasterOp]{#74722}\
                                                  [Performs an arbitrary linear combination of bands in a Raster, using a specified matrix.]{#74639}\

   [BandedSampleModel]{#74266}\                   [Extends: SampleModel]{#74268}\
                                                  [Provides more efficent implementations for accessing]{#74729}\
                                                  [image data than are provided in SampleModel. Used when working with images that store sample data for each band in a different bank of the DataBuffer.]{#74738}\

   [BilinearAffineTransformOp]{#74270}\           [Extends: AffineTransformOp]{#74272}\
                                                  [Uses an affine transformation with bilinear interpolation to transform an image or Raster.]{#74757}\

   [BufferedImage]{#74274}\                       [Extends: Image]{#74276}\
                                                  [Implements: WritableRenderedImage]{#74766}\
                                                  [Describes an Image with an accessible buffer of image data.]{#74767}\

   [BufferedImageFilter]{#74278}\                 [Extends: ImageFilter]{#74280}\
                                                  [Implements: RasterImageConsumer, Cloneable]{#74779}\
                                                  [Provides a simple means of using a single-source/single-destination image operator (BufferedImageOp) to filter a BufferedImage or Raster in the Image Producer/Consumer/Observer paradigm.]{#74782}\

   [ByteLookupTable]{#74282}\                     [Extends: LookupTable]{#74284}\
                                                  [Defines a lookup table object. The lookup table contains byte data for one or more tile channels or image components (for example, separate arrays for R, G, and B), and it contains an offset that will be subtracted from the input value before indexing the array.]{#74805}\

   [ColorConvertOp]{#74286}\                      [Extends: Object]{#74288}\
                                                  [Implements: BufferedImageOp, RasterOp]{#74823}\
                                                  [Performs a pixel-by-pixel color conversion of the data in the source image. The resulting color values are scaled to the precision of the destination image data type.]{#74826}\

   [ColorModel]{#74290}\                          [Extends: Object]{#74292}\
                                                  [Implements: Transparency]{#74835}\
                                                  [An abstract class that encapsulates the methods for translating from pixel values to color components (e.g., red, green, blue) for an image.]{#74838}\

   [ComponentColorModel]{#74294}\                 [Extends: ColorModel]{#74296}\
                                                  [A ColorModel class that can handle an arbitrary ColorSpace and an array of color components to match the ColorSpace.]{#74856}\

   [ComponentSampleModel]{#74298}\                [Extends: SampleModel]{#74867}\
                                                  [Stores the N samples that make up a pixel in N separate data array elements all of which are in the same bank of a dataBuffer.]{#74875}\

   [ConvolveOp]{#74302}\                          [Extends: Object]{#74884}\
                                                  [Implements: BufferedImageOp, RasterOp]{#74885}\
                                                  [Implements a convolution from the source to the destination. Convolution using a convolution kernel is a spatial operation that computes the output pixel from an input pixel by multiplying the kernel with the surround of the input pixel.]{#74895}\

   [CropImageFilter]{#74306}\                     [Extends: ImageFilter]{#74905}\
                                                  [An ImageFilter class for cropping images.]{#74308}\

   [DataBuffer]{#74310}\                          [Extends: Object]{#74921}\
                                                  [Wraps one or more data arrays. Each data array in the DataBuffer is referred to as a bank. Accessor methods for getting and setting elements of the DataBuffer\'s banks exist with and without a bank specifier.]{#74929}\

   [DataBufferByte]{#74314}\                      [Extends: DataBuffer]{#74316}\
                                                  [Stores data internally as bytes.]{#74941}\

   [DataBufferInt]{#74318}\                       [Extends: DataBuffer]{#74951}\
                                                  [Stores data internally as ints.]{#74952}\

   [DataBufferShort]{#74322}\                     [Extends: DataBuffer]{#74964}\
                                                  [Stores data internally as shorts.]{#74965}\

   [DirectColorModel]{#74326}\                    [Extends: PackedColorModel]{#74328}\
                                                  [Represents pixel values that have RGB color components embedded directly in the bits of the pixel itself.]{#74977}\

   [FilteredImageSource]{#74330}\                 [Extends: Object]{#74984}\
                                                  [Implements: ImageProducer]{#74332}\
                                                  [An implementation of the ImageProducer interface which takes an existing image and a filter object and uses them to produce image data for a new filtered version of the original image.]{#74993}\

   [ImageFilter]{#74334}\                         [Extends: Object]{#75005}\
                                                  [Implements: ImageConsumer, Cloneable]{#75006}\
                                                  [Implements a filter for the set of interface methods that are used to deliver data from an ImageProducer to an ImageConsumer.]{#75016}\

   [IndexColorModel]{#74338}\                     [Extends: ColorModel]{#74340}\
                                                  [Represents pixel values that are indices into a fixed colormap in the ColorModel\'s color space.]{#75027}\

   [Kernel]{#74342}\                              [Extends: Object]{#75040}\
                                                  [Defines a Kernel object - a matrix describing how a given pixel and its surrounding pixels affect the value of the given pixel in a filtering operation.]{#75052}\

   [LookupOp]{#74346}\                            [Extends: Object]{#75067}\
                                                  [Implements: BufferedImageOp, RasterOp]{#74348}\
                                                  [Implements a lookup operation from the source to the destination.]{#75074}\

   [LookupTable]{#74350}\                         [Extends: Object]{#75083}\
                                                  [Defines a lookup table object. The subclasses are ByteLookupTable and ShortLookupTable, which contain byte and short data, respectively.]{#75091}\

   [MemoryImageSource]{#74354}\                   [Extends: Object]{#75098}\
                                                  [Implements: ImageProducer]{#75099}\
                                                  [An implementation of the ImageProducer interface, which uses an array to produce pixel values for an Image.]{#75107}\

   [MultiPixelPackedSampleModel]{#74358}\         [Extends: SampleModel]{#74360}\
                                                  [Stores one-banded images, but can pack multiple one-sample pixels into one data element.]{#75118}\

   [NearestNeighborAffine-TransformOp]{#74362}\   [Extends: AffineTransformOp]{#74364}\
                                                  [Uses an affine transformation with nearest neighbor interpolation to transform an image or Raster.]{#75133}\

   [PackedColorModel]{#74366}\                    [Extends: ColorModel]{#75144}\
                                                  [An abstract ColorModel class that represents pixel values that have the color components embedded directly in the bits of an integer pixel.]{#75154}\

   [PixelGrabber]{#74370}\                        [Extends: Object]{#75161}\
                                                  [Implements: ImageConsumer]{#75162}\
                                                  [Implements an ImageConsumer which can be attached to an Image or ImageProducer object to retrieve a subset of the pixels in that image.]{#75172}\

   [RGBImageFilter]{#74374}\                      [Extends: ImageFilter]{#74376}\
                                                  [Provides an easy way to create an ImageFilter that modifies the pixels of an image in the default RGB ColorModel. It is meant to be used in conjunction with a FilteredImageSource object to produce filtered versions of existing images.]{#75183}\

   [Raster]{#74378}\                              [Extends: Object]{#75199}\
                                                  [Represents a rectanglular array of pixels and provides methods for retrieving image data. It contains a DataBuffer object that holds a buffer of image data in some format, a SampleModel that describes the format is capable of storing and retrieving Samples from the DataBuffer, and a Rect that defines the coordinate space of the raster (upper left corner, width and height).]{#75207}\

   [ReplicateScaleFilter]{#74382}\                [Extends: ImageFilter]{#75233}\
                                                  [Scales images using the simplest algorithm.]{#74384}\

   [RescaleOp]{#74386}\                           [Extends: Object]{#75247}\
                                                  [Implements: BufferedImageOp, RasterOp]{#74388}\
                                                  [Performs a pixel-by-pixel rescaling of the data in the source image by multiplying each pixel value by a scale factor and then adding an offset.]{#75256}\

   [SampleModel]{#74390}\                         [Extends: Object]{#75267}\
                                                  [Defines an interface for extracting samples of an image without knowing how the underlying data is stored in a DataBuffer.]{#75275}\

   [ShortLookupTable]{#74394}\                    [Extends: LookupTable]{#74396}\
                                                  [Defines a lookup table object. The lookup table contains short data for one or more tile channels or image components (for example, separate arrays for R, G, and B), and it contains an offset that will be subtracted from the input value before indexing the array.]{#75286}\

   [SinglePixelPackedSample-Model]{#74398}\       [Extends: SampleModel]{#74400}\
                                                  [Stores (packs) the N samples that make up a single pixel in one data array element. All data array elements reside in the first bank of a DataBuffer.]{#75303}\

   [ThresholdOp]{#74402}\                         [Extends: Object]{#75318}\
                                                  [Implements: BufferedImageOp, RasterOp]{#75319}\
                                                  [Performs thresholding on the source image by mapping the value of each image component (for BufferedImages) or channel element (for Rasters) that falls between a low and a high value, to a constant.]{#75327}\

   [TileChangeMulticaster]{#74406}\               [Extends: Object]{#75337}\
                                                  [A convenience class that takes care of the details of implementing the TileChangeListener interface.]{#75345}\

   [WritableRaster]{#74410}\                      [Extends: Raster]{#74412}\
                                                  [Provides methods for storing image data and inherits methods for retrieving image data from it\'s parent class Raster.]{#75358}\
   ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

   :  **[Table B-3 **java.awt.image** Classes]{#74246}**

 []{#75368}

 B.3 Java Advanced Imaging
 ----------------------------------------------

 The Java Advanced Imaging API consists of the following packages:

 -   `javax.media.jai` - contains the \"core\" JAI interfaces and
     classes

 <!-- -->

 -   `javax.media.jai.iterator` - contains special iterator interfaces
     and classes, which are useful for writing extension operations

 <!-- -->

 -   `javax.media.jai.operator` - contains classes that describe all of
     the image operators

 <!-- -->

 -   `javax.media.jai.widget` - contains interfaces and classes for
     creating simple image canvases and scrolling windows for image
     display

 []{#75504}

 ### B.3.1 JAI Interfaces

 [Table B-4](API-summary.doc.html#72483) lists and briefly describes
 the interfaces defined in the Java Advanced Imaging API
 (`javax.media.jai`).

   -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   [Interface]{#72535}                 [Description]{#72537}
   ----------------------------------- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   [CollectionImageFactory]{#81076}\   [Abbreviated CIF, this interface is intended to be implemented]{#81080}\
                                       [by classes that wish to act as factories to produce different collection image operators.]{#81078}\

   [ImageFunction]{#81085}\            [A common interface for vector-valued functions that are to be evaluated at positions in the X-Y coordinate system.]{#81089}\

   [ImageJAI]{#80586}\                 [The top-level JAI image type, implemented by all JAI image classes.]{#80590}\

   [OperationDescriptor]{#75916}\      [Describes a family of implementations of a high-level operation (RIF) that are to be added to an OperationRegistry.]{#75956}\

   [PropertyGenerator]{#72491}\        [An interface through which properties may be computed dynamically with respect to an environment of pre-existing properties.]{#72542}\

   [PropertySource]{#72495}\           [Encapsulates the set of operations involved in identifying and reading properties.]{#72497}\

   [TileCache]{#80945}\                [Implements a caching mechanism for image tiles. The TileCache is a central place for OpImages to cache tiles they have computed. The tile cache is created with a given capacity, measured in tiles.]{#80947}\

   [TileScheduler]{#80941}\            [Implements a mechanism for scheduling tile calculation.]{#80943}\
   -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

   :  **[Table B-4 **Summary of jai Interfaces**]{#72483}**

 []{#72561}

 ### B.3.2 JAI Classes

 [Table B-5](API-summary.doc.html#72571) lists and briefly describes
 the classes defined in the Java Advanced Imaging API
 (`javax.media.jai`).

   ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   [Class]{#72627}                              [Description]{#72629}
   -------------------------------------------- -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   [AreaOpImage]{#72583}\                       [Extends: OpImage]{#72585}\
                                                [An abstract base class for image operators that require only a fixed rectangular source region around a source pixel in order to compute each each destination pixel.]{#72636}\

   [BorderExtender]{#80622}\                    [An abstract superclass for classes that extend a WritableRaster with additional pixel data taken from a PlanarImage.]{#80624}\

   [BorderExtenderConstant]{#81098}\            [Extends: BorderExtender]{#81102}\
                                                [Implements border extension by filling all pixels outside of the image bounds with constant values.]{#81106}\

   [BorderExtenderCopy]{#80618}\                [Extends: BorderExtender]{#80620}\
                                                [Implements border extension by filling all pixels outside of the image bounds with copies of the edge pixels.]{#80649}\

   [BorderExtenderReflect]{#80614}\             [Extends: BorderExtender]{#80671}\
                                                [Implements border extension by filling all pixels outside of the image bounds with copies of the whole image.]{#80616}\

   [BorderExtenderWrap]{#80610}\                [Extends: BorderExtender]{#80699}\
                                                [Implements border extension by filling all pixels outside of the image bounds with copies of the whole image.]{#80612}\

   [BorderExtenderZero]{#80606}\                [Extends: BorderExtender]{#81120}\
                                                [Implements border extension by filling all pixels outside of the image bounds with zeros.]{#80608}\

   [CanvasJAI]{#81113}\                         [Extends: java.awt.Canvas]{#81115}\
                                                [Automatically returns an instance of GraphicsJAI from its getGraphics method.]{#81130}\

   [CollectionImage]{#80171}\                   [Extends: ImageJAI]{#80173}\
                                                [Implements: java.util.Collection]{#80189}\
                                                [An abstract superclass for classes representing a collection of objects.]{#80176}\

   [CollectionOp]{#80167}\                      [Extends: CollectionImage]{#80169}\
                                                [A node in a rendered imaging chain representing a CollectionImage.]{#80202}\

   [ColorCube]{#76013}\                         [Extends: LookupTableJAI]{#76017}\
                                                [Represents a color cube lookup table that provides a fixed, invertible mapping between tables indices and sample values.]{#76015}\

   [ComponentSampleModelJAI]{#80716}\           [Extends: ComponentSampleModel]{#80718}\
                                                [Represents image data that is stored such that each sample of a pixel occupies one data element of the DataBuffer.]{#80721}\

   [CoordinateImage]{#80216}\                   [Extends: java.lang.Object]{#80218}\
                                                [Represents an image that is associated with a coordinate. This class is used with ImageStack.]{#80221}\

   [DataBufferDouble]{#72615}\                  [Extends: java.awt.image.DataBuffer]{#72617}\
                                                [Stores DataBuffer data internally in double form.]{#72691}\

   [DataBufferFloat]{#72619}\                   [Extends: java.awt.image.DataBuffer]{#72693}\
                                                [Stores DataBuffer data internally in float form.]{#72621}\

   [DisplayOpImage]{#72740}\                    [Extends: OpImage]{#72742}\
                                                [A placeholder for display functionality.]{#72754}\

   [FloatDoubleColorModel]{#80734}\             [Extends: ComponentColorModel]{#80736}\
                                                [A ColorModel class that works with pixel values that represent color and alpha information as separate samples, using float or double elements, and that store each sample in a separate data element.]{#80742}\

   [GraphicsJAI]{#72732}\                       [Extends: java.awt.Graphics2D]{#72734}\
                                                [An extension of java.awt.Graphics and java.awt.Graphics2D that will support new drawing operations.]{#72772}\

   [Histogram]{#75844}\                         [Extends: java.lang.Object]{#75846}\
                                                [Accumulates histogram information on an image. A histogram counts the number of image samples whose values lie within a given range of values, or *bin*.]{#76131}\

   [ImageLayout]{#72720}\                       [Extends: java.lang.Object]{#72722}\
                                                [Implements: java.lang.Clonable]{#72801}\
                                                [Describes the desired layout of an OpImage.]{#76178}\

   [ImageMIPMap]{#76188}\                       [Extends: ImageCollection]{#76190}\
                                                [Represents a stack of images with a fixed operational relationship between adjacent slices.]{#76199}\

   [ImagePyramid]{#72716}\                      [Extends: ImageCollection]{#72718}\
                                                [Represents a stack of images with a fixed operational relationship between adjacent slices.]{#72815}\

   [ImageSequence]{#72712}\                     [Extends: ImageCollection]{#72714}\
                                                [Represents a sequence of images with associated timestamps and camera positions that can be used to represent video or time-lapse photography.]{#72826}\

   [ImageStack]{#72708}\                        [Extends: ImageCollection]{#72710}\
                                                [Represents a group of images, each with a defined spatial orientation in a common coordinate system, such as CT scans or seismic volumes.]{#72834}\

   [IntegerSequence]{#80234}\                   [Extends: java.lang.Object]{#80236}\
                                                [Represents an image that is associated with a coordinate. This class is used with ImageStack.]{#80247}\

   [Interpolation]{#72704}\                     [Extends: java.lang.Object]{#72706}\
                                                [Encapsulates a particualr algorithm for performing sampling on a regular grid of pixels using a local neighborhood. It is intended to be used by operations that resample their sources, including affine mapping and warping.]{#72849}\

   [InterpolationBicubic]{#72700}\              [Extends: InterpolationTable]{#72702}\
                                                [Performs bicubic interpolation.]{#72857}\

   [InterpolationBicubic2]{#72901}\             [Extends: InterpolationTable]{#72903}\
                                                [Performs bicubic interpolation using a different polynomial than InterpolationBicubic.]{#72908}\

   [InterpolationBilinear]{#72897}\             [Extends: Interpolation]{#72917}\
                                                [Represents bilinear interpolation.]{#72899}\

   [InterpolationNearest]{#72893}\              [Extends: Interpolation]{#72924}\
                                                [Represents nearest-neighbor interpolation.]{#72895}\

   [InterpolationTable]{#76249}\                [Extends: Interpolation]{#76253}\
                                                [Represents nearest-neighbor interpolation.]{#76251}\

   [JAI]{#72885}\                               [Extends: java.lang.Object]{#72887}\
                                                [A convenience class for instantiating operations.]{#72940}\

   [KernelJAI]{#72881}\                         [Extends: java.lang.Object]{#72950}\
                                                [A convolution kernel, used by the Convolve operation.]{#72883}\

   [LookupTableJAI]{#72873}\                    [Extends: java.lang.Object]{#72967}\
                                                [A lookup table object for the Lookup operation.]{#72973}\

   [MultiResolutionRenderable-Image]{#76301}\   [Extends: java.lang.Object]{#76305}\
                                                [Implements: java.awt.image.renderable, RenderableImage]{#76303}\
                                                [A RenderableImage that produces renderings based on a set of supplied RenderedImages at various]{#76315}\
                                                [resolution.]{#76313}\

   [NullOpImage]{#73046}\                       [Extends: PointOpImage]{#73048}\
                                                [A trivial OpImage subclass that simply transmits its source unchanged. Potentially useful when an interface requires an OpImage but another sort of RenderedImage (such as a TiledImage) is to be used.]{#73116}\

   [OperationDescriptorImpl]{#76332}\           [Extends: java.lang.Object]{#76336}\
                                                [Implements: OperationDescriptor]{#76334}\
                                                [A concrete implementation of the OperationDescriptor interface, suitable for subclassing.]{#76346}\

   [OperationRegistry]{#73034}\                 [Extends: java.lang.Object]{#73146}\
                                                [Implements: java.io.Externalizable]{#76360}\
                                                [Maps an operation name into a RenderedImageFactory capable of implementing the operation, given a specific set of sources and parameters.]{#73152}\

   [OpImage]{#76352}\                           [Extends: PlanarImage]{#76354}\
                                                [The parent class for all imaging operations. OpImage centralizes a number of common functions, including connecting sources and sinks during construction of OpImage chains, and tile cache management.]{#76370}\

   [ParameterBlockJAI]{#75802}\                 [Extends: java.awt.image.renderable, ParameterBlock]{#76397}\
                                                [A convenience subclass of ParameterBlock that allows the use of default parameter values and getting/setting parameters by name.]{#76399}\

   [PerspectiveTransform]{#73026}\              [Extends: java.lang.Object]{#76414}\
                                                [Implements: java.lang.Cloneable, java.io.Serializable]{#73028}\
                                                [A 2D perspective (or projective) transform, used by various OpImages.]{#73172}\

   [PlanarImage]{#73018}\                       [Extends: java.awt.Image]{#73020}\
                                                [Implements: java.awt.image.RenderedImage]{#73185}\
                                                [A fundamental base class representing two-dimensional images.]{#73190}\

   [PointOpImage]{#73231}\                      [Extends: OpImage]{#73233}\
                                                [An abstract base class for image operators that require only a single source pixel to compute each destination pixel.]{#76472}\

   [PropertyGeneratorImpl]{#76486}\             [Extends: java.lang.Object]{#76490}\
                                                [A utility class to simplify the writing of property generators.]{#76492}\

   [RasterAccessor]{#76504}\                    [Extends: java.lang.Object]{#76508}\
                                                [An adapter class for presenting image data in a ComponentSampleModel format, even if the data is not stored that way.]{#76510}\

   [RasterFactory]{#79989}\                     [A convenience class for the construction of various types of WritableRaster and SampleModel objects.]{#79993}\

   [RasterFormatTag]{#80841}\                   [Encapsulates some of the information needed for RasterAccessor to understand how a Raster is laid out.]{#80845}\

   [RemoteImage]{#79915}\                       [Extends: PlanarImage]{#79917}\
                                                [An implementation of RenderedImage that uses a RMIImage as its source.]{#79918}\

   [RenderableGraphics]{#76555}\                [Extends: Graphics2D]{#76559}\
                                                [Implements: RenderableImage, Serializable]{#80263}\
                                                [An implementation of Graphics2D with RenderableImage semantics.]{#76557}\

   [RenderableImageAdapter]{#76580}\            [Extends: java.lang.Object]{#76584}\
                                                [Implements: java.awt.image.renderable.RenderableImage, PropertySource]{#76585}\
                                                [An adapter class for externally-generated RenderableImages.]{#76582}\

   [RenderableOp]{#76597}\                      [Extends: java.awt.image.renderable.RenderableImageOp]{#76601}\
                                                [Implements: PropertySource]{#76602}\
                                                [A JAI version of RenderableImageOp.]{#76599}\

   [RenderedImageAdapter]{#76614}\              [Extends: PlanarImage]{#76618}\
                                                [A PlanarImage wrapper for a non-writable RenderedImage.]{#76616}\

   [RenderedOp]{#76630}\                        [Extends: PlanarImage]{#76634}\
                                                [A node in a rendered imaging chain.]{#76632}\

   [ROI]{#73227}\                               [Extends: java.lang.Object]{#73271}\
                                                [Represents a region of interest of an image.]{#73273}\

   [ROIShape]{#73223}\                          [Extends: ROI]{#73225}\
                                                [Represents a region of interest within an image as a Shape.]{#73291}\

   [ScaleOpImage]{#75715}\                      [Extends: WarpOpImage]{#76704}\
                                                [Used by further extension classes that perform scale-like operations and thus require rectilinear backwards mapping and padding by the resampling filter dimensions.]{#76706}\

   [SequentialImage]{#80010}\                   [Extends: java.lang.Object]{#80012}\
                                                [Represents an image that is associated with a time stamp and a camera position. Used with ImageSequence.]{#80026}\

   [SnapshotImage]{#73397}\                     [Extends: PlanarImage:]{#73401}\
                                                [Implements: java.awt.image.TileObserver]{#73399}\
                                                [Provides an arbitrary number of synchronous views of a possibly changing WritableRenderedImage.]{#76734}\

   [SourcelessOpImage]{#73393}\                 [Extends: OpImage]{#73395}\
                                                [An abstract base class for image operators that have no image sources.]{#73422}\

   [StatisticsOpImage]{#76751}\                 [Extends: OpImage]{#76755}\
                                                [An abstract base class for image operators that compute statistics on a given region of an image and with a given sampling rate.]{#76757}\

   [TiledImage]{#73373}\                        [Extends: PlanarImage]{#73451}\
                                                [Implements: java.awt.image.WritableRenderedImage]{#73375}\
                                                [A concrete implementation of WritableRenderedImage.]{#73459}\

   [UntiledOpImage]{#80043}\                    [Extends: OpImage]{#80045}\
                                                [A general class for single-source operations in which the values of all pixels in the source image contribute to the value of each pixel in the destination image.]{#80051}\

   [Warp]{#73369}\                              [Extends: java.lang.Object]{#73467}\
                                                [A description of an image warp.]{#73371}\

   [WarpAffine]{#76860}\                        [Extends: WarpPolynomial]{#76864}\
                                                [A description of an Affine warp.]{#76862}\

   [WarpCubic]{#76872}\                         [Extends: WarpPolynomial]{#76876}\
                                                [A cubic-based description of an image warp.]{#76874}\

   [WarpGeneralPolynomial]{#76912}\             [Extends: WarpPolynomial]{#76916}\
                                                [A general polynomial-based description of an image warp.]{#76918}\

   [WarpGrid]{#76908}\                          [Extends: Warp]{#76930}\
                                                [A regular grid-based description of an image warp.]{#76910}\

   [WarpOpImage]{#76904}\                       [Extends: OpImage]{#76942}\
                                                [A general implementation of image warping, and a superclass for other geometric image operations.]{#76944}\

   [WarpPerspective]{#76900}\                   [Extends: Warp]{#76956}\
                                                [A description of a perspective (projective) warp.]{#76902}\

   [WarpPolynomial]{#76896}\                    [Extends: Warp]{#76968}\
                                                [A polynomial-based description of an image warp.]{#76898}\

   [WarpQuadratic]{#76892}\                     [Extends: WarpPolynomial]{#76980}\
                                                [A quadratic-based description of an image warp.]{#76982}\

   [WritableRenderedImage-Adapter]{#76888}\     [Extends: RenderedImageAdapter]{#76994}\
                                                [Implements: java.awt.image.WritableRenderedImage]{#76995}\
                                                [A PlanarImage wrapper for a WritableRenderedImage.]{#76890}\
   ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

   :  **[Table B-5 Summary of jai Classes]{#72571}**

 []{#77302}

 ### B.3.3 JAI Iterator Interfaces

 [Table B-6](API-summary.doc.html#77306) lists the JAI iterator classes
 (`javax.media.jai.iterator`).

   ------------------------------------------------------------------------------------------------------------------------------------------
   [Interface]{#77310}             [Description]{#77312}
   ------------------------------- ----------------------------------------------------------------------------------------------------------
   [RandomIter]{#77314}\           [An iterator that allows random read-only access to any sample within its bounding rectangle.]{#77362}\

   [RectIter]{#77318}\             [An iterator for traversing a read-only image in top-to-bottom, left-to-right order.]{#77320}\

   [RookIter]{#77358}\             [An iterator for traversing a read-only image using arbitrary up-down and left-right moves.]{#77360}\

   [WritableRandomIter]{#77322}\   [Extends: RandomIter]{#77388}\
                                   [An iterator that allows random read/write access to any sample within its bounding rectangle.]{#77324}\

   [WritableRectIter]{#77326}\     [Extends: RectIter]{#77396}\
                                   [An iterator for traversing a read/write image in top-to-bottom, left-to-right order.]{#77328}\

   [WritableRookIter]{#77330}\     [Extends: RookIter, WritableRectIter]{#77408}\
                                   [An iterator for traversing a read/write image using arbitrary up-down and left-right moves.]{#77332}\
   ------------------------------------------------------------------------------------------------------------------------------------------

   :  **[Table B-6 JAI Iterator Interfaces]{#77306}**

 []{#77419}

 ### B.3.4 JAI Iterator Classes

 [Table B-7](API-summary.doc.html#77427) lists the JAI iterator classes
 (`javax.media.jai.iterator`).

   -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   [Class]{#77431}                [Description]{#77433}
   ------------------------------ ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   [RandomIterFactory]{#77435}\   [Extends: java.lang.Object]{#77461}\
                                  [A factory class to instantiate instances of the RandomIter and WritableRandomIter interfaces on sources of type Raster, RenderedImage, and WritableRenderedImage.]{#77463}\

   [RectIterFactory]{#77439}\     [Extends: java.lang.Object]{#77478}\
                                  [A factory class to instantiate instances of the RectIter and WritableRectIter interfaces on sources of type Raster, RenderedImage, and WritableRenderedImage.]{#77480}\

   [RookIterFactory]{#77443}\     [Extends: java.lang.Object]{#77493}\
                                  [A factory class to instantiate instances of the RookIter and WritableRookIter interfaces on sources of type Raster, RenderedImage, and WritableRenderedImage.]{#77495}\
   -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

   :  **[Table B-7 JAI Iterator Classes]{#77427}**

 ``

 []{#73476}

 ### B.3.5 JAI Operator Classes

 [Table B-8](API-summary.doc.html#73482) lists the JAI operator classes
 (`javax.jai.operator`). These classes extend the JAI
 OperationDescriptor class.

   ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   [Class]{#73486}                              [Description]{#73488}
   -------------------------------------------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   [AbsoluteDescriptor]{#77508}\                [Extends: OperationDescriptorImpl]{#77520}\
                                                [An OperationDescriptor for the Absolute operation, which gives the mathematical absolute value of the pixel values of a source image.]{#77510}\

   [AddCollectionDescriptor]{#80096}\           [Extends: OperationDescriptorImpl]{#80100}\
                                                [An OperationDescriptor for the AddCollection operation, which takes a collection of rendered images, and adds every set of pixels, one from each source image of the corresponding position and band.]{#80109}\

   [AddConstDescriptor]{#77512}\                [Extends: OperationDescriptorImpl]{#77534}\
                                                [An OperationDescriptor for the AddConst operation, which adds one of a set of constant values to the pixel values of a source image on a per-band basis.]{#77514}\

   [AddConstToCollection-Descriptor]{#80360}\   [Extends: OperationDescriptorImpl]{#80372}\
                                                [An OperationDescriptor for the AddConstToCollection operation, which adds constants to a collection of rendered images.]{#80409}\

   [AddDescriptor]{#77516}\                     [Extends: OperationDescriptorImpl]{#77552}\
                                                [An OperationDescriptor for the Add operation, which adds the pixel values of two source images on a per-band basis.]{#77553}\

   [AffineDescriptor]{#73490}\                  [Extends: OperationDescriptorImpl]{#73492}\
                                                [An OperationDescriptor for the Affine operation, which performs an affine mapping between a source and a destination image.]{#77567}\

   [AndConstDescriptor]{#77607}\                [Extends: OperationDescriptorImpl]{#77611}\
                                                [An OperationDescriptor for the AndConst operation, which performs a bitwise logical AND between the pixel values of a source image with one of a set of per-band constants.]{#77612}\

   [AndDescriptor]{#77603}\                     [Extends: OperationDescriptorImpl]{#77624}\
                                                [An OperationDescriptor for the And operation, which performs a bitwise logical AND between the pixel values of the two source images on a per-band basis.]{#77802}\

   [AWTImageDescriptor]{#77599}\                [Extends: OperationDescriptorImpl]{#77648}\
                                                [An OperationDescriptor for the AWTImage operation, which imports a standard AWT image into JAI.]{#77649}\

   [BandCombineDescriptor]{#77595}\             [Extends: OperationDescriptorImpl]{#77661}\
                                                [An OperationDescriptor for the BandCombine operation, which computes an arbitrary linear combination of the bands of a source image for each band of a destination image, using a specified matrix.]{#77662}\

   [BandSelectDescriptor]{#77591}\              [Extends: OperationDescriptorImpl]{#77674}\
                                                [An OperationDescriptor for the BandSelect operation, which copies the pixel data from a specified number of bands in a source image to a destination image in a specified order.]{#77675}\

   [BMPDescriptor]{#77587}\                     [Extends: OperationDescriptorImpl]{#77687}\
                                                [An OperationDescriptor for the BMP operation, which reads BMP image data file from an input stream.]{#77688}\

   [BorderDecriptor]{#77583}\                   [Extends: OperationDescriptorImpl]{#77700}\
                                                [An OperationDescriptor for the Border operation, which adds a border around an image.]{#77701}\

   [BoxFilterDescriptor]{#77579}\               [Extends: OperationDescriptorImpl]{#77713}\
                                                [An OperationDescriptor for the BoxFilter operation, which determines the intensity of a pixel in an image by averaging the source pixels within a rectangular area around the pixel.]{#77854}\

   [ClampDescriptor]{#77730}\                   [Extends: OperationDescriptorImpl]{#77734}\
                                                [An OperationDescriptor for the Clamp operation, which sets all the pixel values below a \"low\" value to that low value, and sets all the pixel values above a \"high\" value to that high value.]{#77864}\

   [ColorConvertDescriptor]{#80384}\            [Extends: OperationDescriptorImpl]{#80388}\
                                                [An OperationDescriptor for the ColorConvert operation, which performs a pixel-by-pixel color conversion of the data in a source image.]{#80400}\

   [CompositeDescriptor]{#77726}\               [Extends: OperationDescriptorImpl]{#77747}\
                                                [An OperationDescriptor for the Composite operation, which combines two images based on their alpha values at each pixel.]{#77874}\

   [ConjugateDescriptor]{#80420}\               [Extends: OperationDescriptorImpl]{#80424}\
                                                [An OperationDescriptor for the Conjugate operation, which negates the imaginary components of pixel values of an image containing complex data.]{#80440}\

   [ConstantDescriptor]{#73494}\                [Extends: OperationDescriptorImpl]{#73496}\
                                                [An OperationDescriptor for the Constant operation, which defines a multi-banded, tiled rendered image with constant pixel values.]{#77934}\

   [ConvolveDescriptor]{#77978}\                [Extends: OperationDescriptorImpl]{#78012}\
                                                [An OperationDescriptor for the Convolve operation, which computes each output sample by multiplying elements of a kernel with the samples surrounding a particular source sample.]{#77980}\

   [CropDescriptor]{#80447}\                    [Extends: OperationDescriptorImpl]{#80451}\
                                                [An OperationDescriptor for the Crop operation, which crops a rendered or renderable image to a specified rectangular area.]{#80460}\

   [DCTDescriptor]{#79749}\                     [Extends: OperationDescriptorImpl]{#79751}\
                                                [An operation descriptor for the DCT operation, which computes the even discrete cosine transform of an image.]{#79766}\

   [DFTDescriptor]{#79745}\                     [Extends: OperationDescriptorImpl]{#79773}\
                                                [An OperationDescriptor for the DFT operation, which computes the discrete Fourier transform of an image.]{#79782}\

   [DivideByConstDescriptor]{#77970}\           [Extends: OperationDescriptorImpl]{#78052}\
                                                [An OperationDescriptor for the DivideByConst operation, which divides the pixel values of a source image by a constant.]{#78063}\

   [DivideComplexDescriptor]{#77966}\           [Extends: OperationDescriptorImpl]{#78069}\
                                                [An OperationDescriptor for the DivideComplex operation, which divides two images representing complex data.]{#78080}\

   [DivideDescriptor]{#77962}\                  [Extends: OperationDescriptorImpl]{#78086}\
                                                [An OperationDescriptor for the Divide operation, which divides the pixel values of one first source image by the pixel values of another source image on a per-band basis.]{#78097}\

   [DivideIntoConstDescriptor]{#77958}\         [Extends: OperationDescriptorImpl]{#78110}\
                                                [An OperationDescriptor for the DivideIntoConst operation, which divides a constant by the pixel values of a source image.]{#78119}\

   [EncodeDescriptor]{#80965}\                  [Extends: OperationDescriptorImpl]{#80969}\
                                                [An OperationDescriptor for the Encode operation, which stores an image to an OutputStream.]{#80967}\

   [ErrorDiffusionDescriptor]{#78162}\          [Extends: OperationDescriptorImpl]{#78166}\
                                                [An OperationDescriptor for the ErrorDiffusion operation, which performs color quantization by finding the nearest color to each pixel in a supplied color map.]{#78164}\

   [ExpDescriptor]{#78158}\                     [Extends: OperationDescriptorImpl]{#78179}\
                                                [An OperationDescriptor for the Exp operation, which takes the exponential of the pixel values of an image.]{#78188}\

   [ExtremaDescriptor]{#78154}\                 [Extends: OperationDescriptorImpl]{#78194}\
                                                [An OperationDescriptor for the Extrema operation, which scans an image and finds the image-wise maximum and minimum pixel values for each band.]{#78209}\

   [FileLoadDescriptor]{#80127}\                [Extends: OperationDescriptorImpl]{#80139}\
                                                [An OperationDescriptor for the FileLoad operation, which reads an image from a file.]{#80129}\

   [FileStoreDescriptor]{#80986}\               [Extends: OperationDescriptorImpl]{#80990}\
                                                [An OperationDescriptor for the FileStore operation, which stores an image to a file.]{#80988}\

   [FormatDescriptor]{#79924}\                  [Extends: OperationDescriptorImpl]{#79926}\
                                                [An OperationDescriptor for the Format operation, which reformats an image.]{#79927}\

   [FPXDescriptor]{#81189}\                     [Extends: OperationDescriptorImpl]{#81191}\
                                                [An OperationDescriptor for the FPX operation, which reads FlashPix data from an input stream.]{#81192}\

   [GIFDescriptor]{#78280}\                     [Extends: OperationDescriptorImpl]{#78301}\
                                                [An OperationDescriptor for the GIF operation, which reads GIF data from an input stream.]{#78282}\

   [GradientMagnitudeDescriptor]{#80471}\       [Extends: OperationDescriptorImpl]{#80475}\
                                                [An OperationDescriptor for the Gradient operation, which is an edge detector that computes the magnitude of the image gradient vector in two orthogonal directions.]{#80484}\

   [HistogramDecriptor]{#78272}\                [Extends: OperationDescriptorImpl]{#78331}\
                                                [An OperationDescriptor for the Histogram operation, which scans a specified region of an image and generates a histogram based on the pixel values within that region of the image.]{#78340}\

   [IDCTDescriptor]{#78268}\                    [Extends: OperationDescriptorImpl]{#78348}\
                                                [An OperationDescriptor for the IDCT operation, which computes the inverse discrete cosine transform of an image.]{#78270}\

   [IDFTDescriptor]{#79830}\                    [Extends: OperationDescriptorImpl]{#79834}\
                                                [An OperationDescriptor for the IDFT operation, which computes the inverse discrete Fourier transform of an image.]{#79835}\

   [IIPDescriptor]{#81242}\                     [Extends: OperationDescriptorImpl]{#81254}\
                                                [An OperationDescriptor for the IIP operation, which reads an image from an IIP server and creates a RenderedImage or a RenderableImage based on data from the server.]{#81244}\

   [IIPResolutionDescriptor]{#81238}\           [Extends: OperationDescriptorImpl]{#81263}\
                                                [An OperationDescriptor for the IIPResolution operation, which reads an image at a particular resolution from an IIP server and creates a RenderedImage based on the data from the server.]{#81240}\

   [ImageFunctionDescriptor]{#80493}\           [Extends: OperationDescriptorImpl]{#80497}\
                                                [An OperationDescriptor for the ImageFunction operation, which generates an image on the basis of a functional description provided by an object that is an instance of a class that implements the ImageFunction interface.]{#80506}\

   [InvertDescriptor]{#73498}\                  [Extends: OperationDescriptorImpl]{#73581}\
                                                [An OperationDescriptor for the Invert operation, which inverts the pixel values of an image.]{#73586}\

   [JPEGDescriptor]{#78409}\                    [Extends: OperationDescriptorImpl]{#78413}\
                                                [An OperationDescriptor for the JPEG operation, which reads a standard JPEG (JFIF) file.]{#78411}\

   [LogDescriptor]{#78405}\                     [Extends: OperationDescriptorImpl]{#78424}\
                                                [An OperationDescriptor for the Log operation, which takes the logarithm of the pixel values of an image.]{#78407}\

   [LookupDescriptor]{#78401}\                  [Extends: OperationDescriptorImpl]{#78439}\
                                                [An OperationDescriptor for the Lookup operation, which performs general table lookup on an image.]{#78448}\

   [MagnitudeDescriptor]{#78397}\               [Extends: OperationDescriptorImpl]{#78456}\
                                                [An OperationDescriptor for the Magnitude operation, which computes the magnitude of each pixel of an image.]{#78467}\

   [MagnitudeSquaredDescriptor]{#79851}\        [Extends: OperationDescriptorImpl]{#80917}\
                                                [An OperationDescriptor for the MagnitudeSquared operation, which computes the squared magnitude of each pixel of a complex image.]{#79867}\

   [MatchCDFDescriptor]{#80880}\                [Extends: OperationDescriptorImpl]{#80909}\
                                                [An OperationDescriptor for the MatchCDF operation, which matches pixel values to a supplied cumulative distribution function (CDF).]{#80894}\

   [MaxDescriptor]{#78393}\                     [Extends: OperationDescriptorImpl]{#78473}\
                                                [An OperationDescriptor for the Max operation, which computes the pixelwise maximum value of two images.]{#78395}\

   [MeanDescriptor]{#78504}\                    [Extends: OperationDescriptorImpl]{#78516}\
                                                [An OperationDescriptor for the Mean operation, which scans a specified region of an image and computes the image-wise mean pixel value for each band within the region.]{#78506}\

   [MedianFilterDescriptor]{#78500}\            [Extends: OperationDescriptorImpl]{#78523}\
                                                [An OperationDescriptor for the MedianFilter operation, which is useful for removing isolated lines or pixels while preserving the overall appearance of an image.]{#78502}\

   [MinDescriptor]{#78496}\                     [Extends: OperationDescriptorImpl]{#78548}\
                                                [An OperationDescriptor for the Min operation, which computes the pixelwise minimum value of two images.]{#78498}\

   [MultiplyComplexDescriptor]{#78492}\         [Extends: OperationDescriptorImpl]{#78563}\
                                                [An OperationDescriptor for the MultiplyComplex operation, which multiplies two images representing complex data.]{#78494}\

   [MultiplyConstDescriptor]{#78488}\           [Extends: OperationDescriptorImpl]{#78578}\
                                                [An OperationDescriptor for the MultiplyConst operation, which multiplies the pixel values of a source image with a constant on a per-band basis.]{#78490}\

   [MultiplyDescriptor]{#78611}\                [Extends: OperationDescriptorImpl]{#78615}\
                                                [An OperationDescriptor for the Multiply operation, which multiplies the pixel values of two source images on a per-band basis.]{#78613}\

   [NotDescriptor]{#78607}\                     [Extends: OperationDescriptorImpl]{#78630}\
                                                [An OperationDescriptor for the Multiply operation, which performs a bitwise logical NOT operation on each pixel of a source image on a per-band basis.]{#78609}\

   [OrConstDescriptor]{#78603}\                 [Extends: OperationDescriptorImpl]{#78643}\
                                                [An OperationDescriptor for the OrConst operation, which performs a bitwise logical OR between the pixel values of a source image with a constant on a per-band basis.]{#78605}\

   [OrderedDitherDescriptor]{#79877}\           [Extends: OperationDescriptorImpl]{#79889}\
                                                [An OperationDescriptor for the OrderedDither operation, which performs color quantization by finding the nearest color to each pixel in a supplied color cube and \"shifting\" the resulting index value by a pseudo-random amount determined by the values of a supplied dither mask.]{#79894}\

   [OrDescriptor]{#81194}\                      [Extends: OperationDescriptorImpl]{#81196}\
                                                [An OperationDescriptor for the Or operation, which performs a bitwise logical OR between the pixel values of the two source images on a per-band basis.]{#81197}\

   [OverlayDescriptor]{#78595}\                 [Extends: OperationDescriptorImpl]{#78673}\
                                                [An OperationDescriptor for the Overlay operation, which overlays one image on top of another image.]{#78597}\

   [PatternDescriptor]{#78706}\                 [Extends: OperationDescriptorImpl]{#78710}\
                                                [An OperationDescriptor for the Pattern operation, which defines a tiled image consisting of a repeated pattern.]{#78708}\

   [PeriodicShiftDescriptor]{#80524}\           [Extends: OperationDescriptorImpl]{#80528}\
                                                [An OperationDescriptor for the PeriodicShift operation, which computes the periodic translation of an image.]{#80526}\

   [PhaseDescriptor]{#78702}\                   [Extends: OperationDescriptorImpl]{#78725}\
                                                [An OperationDescriptor for the Phase operation, which computes the phase angle of each pixel of an image.]{#78704}\

   [PiecewiseDescriptor]{#80905}\               [Extends: OperationDescriptorImpl]{#80921}\
                                                [An OperationDescriptor for the Piecewise operation, which applies a piecewise pixel value mapping to an image.]{#80907}\

   [PNGDescriptor]{#78698}\                     [Extends: OperationDescriptorImpl]{#78740}\
                                                [An OperationDescriptor for the PNG operation, which reads a PNG input stream.]{#78700}\

   [PNMDescriptor]{#78694}\                     [Extends: OperationDescriptorImpl]{#78755}\
                                                [An OperationDescriptor for the PNM operation, which reads a standard PNM file, including PBM, PGM, and PPM images of both ASCII and raw formats.]{#78696}\

   [PolarToComplexDescriptor]{#80541}\          [Extends: OperationDescriptorImpl]{#80545}\
                                                [An OperationDescriptor for the PolarToComplex operation, which computes a complex image from a magnitude and a phase image.]{#80554}\

   [RenderableDescriptor]{#81199}\              [Extends: OperationDescriptorImpl]{#81203}\
                                                [An OperationDescriptor for the Renderable operation, which produces a RenderableImage from a RenderedImage.]{#81201}\

   [RescaleDescriptor]{#78686}\                 [Extends: OperationDescriptorImpl]{#78788}\
                                                [An OperationDescriptor for the Rescale operation, which maps the pixel values of an image from one range to another range.]{#78688}\

   [RotateDescriptor]{#73502}\                  [Extends: OperationDescriptorImpl]{#73592}\
                                                [An OperationDescriptor for the Rotate operation, which rotates an image about a given point by a given angle.]{#73601}\

   [ScaleDescriptor]{#73506}\                   [Extends: OperationDescriptorImpl]{#73626}\
                                                [An OperationDescriptor for the Scale operation, which translates and resizes an image.]{#73635}\

   [ShearDescriptor]{#78848}\                   [Extends: OperationDescriptorImpl]{#78852}\
                                                [An OperationDescriptor for the Shear operation, which shears an image horizontally or vertically.]{#78850}\

   [StreamDescriptor]{#78840}\                  [Extends: OperationDescriptorImpl]{#78886}\
                                                [An OperationDescriptor for the Stream operation, which reads java.io.InputStream files.]{#78842}\

   [SubtractConstDescriptor]{#78836}\           [Extends: OperationDescriptorImpl]{#78905}\
                                                [An OperationDescriptor for the SubtractConst operation, which subtracts one of a set of constant values from the pixel values of a source image on a per-band basis.]{#78838}\

   [SubtractDescriptor]{#78832}\                [Extends: OperationDescriptorImpl]{#78920}\
                                                [An OperationDescriptor for the Subtract operation, which subtracts the pixel values of the second source image from the first source image on a per-band basis.]{#78834}\

   [SubtractFromConstDescriptor]{#78951}\       [Extends: OperationDescriptorImpl]{#78955}\
                                                [An OperationDescriptor for the SubtractFromConst operation, which subtracts the pixel values of a source image from one of a set of constant values on a per-band basis.]{#78953}\

   [ThresholdDescriptor]{#78947}\               [Extends: OperationDescriptorImpl]{#78968}\
                                                [An OperationDescriptor for the Threshold operation, which maps all the pixel values of an image that fall within a given range to one of a set of per-band constants.]{#78949}\

   [TIFFDescriptor]{#78943}\                    [Extends: OperationDescriptorImpl]{#78983}\
                                                [An OperationDescriptor for the TIFF operation, which reads TIFF 6.0 data from an input stream.]{#78945}\

   [TranslateDescriptor]{#73510}\               [Extends: OperationDescriptorImpl]{#73654}\
                                                [An OperationDescriptor for the Translate operation, which copies an image to a new location in the plane.]{#73664}\

   [TransposeDescriptor]{#79054}\               [Extends: OperationDescriptorImpl]{#79062}\
                                                [An OperationDescriptor for the Transpose operation, which flips or rotates an image.]{#79056}\

   [URLDescriptor]{#80561}\                     [Extends: OperationDescriptorImpl]{#80565}\
                                                [An OperationDescriptor for the URL operation, which reads an image from a file, via a URL path.]{#80563}\

   [WarpDescriptor]{#79046}\                    [Extends: OperationDescriptorImpl]{#79096}\
                                                [An OperationDescriptor for the Warp operation, which performs general warping on an image.]{#79107}\

   [XorConstDescriptor]{#79042}\                [Extends: OperationDescriptorImpl]{#79115}\
                                                [An OperationDescriptor for the XorConst operation, which performs a bitwise logical XOR between the pixel values of a source image with a constant.]{#79044}\

   [XorDescriptor]{#79038}\                     [Extends: OperationDescriptorImpl]{#79130}\
                                                [An OperationDescriptor for the Xor operation, which performs a bitwise logical XOR between the pixel values of two source images on a per-band basis.]{#79040}\
   ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

   :  **[Table B-8 JAI Operator Classes]{#73482}**

 []{#79150}

 ### B.3.6 JAI Widget Interfaces

 [Table B-9](API-summary.doc.html#79156) lists the JAI widget
 interfaces (`javax.media.jai.widget`).

   -----------------------------------------------------------------------------------------------------------------------------------------------
   [Interface]{#79160}           [Description]{#79162}
   ----------------------------- -----------------------------------------------------------------------------------------------------------------
   [ViewportListener]{#79164}\   [Used by the ScrollingImagePanel class to inform listeners of the current viewable area of the image.]{#79944}\

   -----------------------------------------------------------------------------------------------------------------------------------------------

   :  **[Table B-9 JAI Widget Interfaces]{#79156}**

 ``

 []{#73681}

 ### B.3.7 JAI Widget Classes

 [Table B-10](API-summary.doc.html#73685) lists the JAI widget classes
 (`javax.media.jai.widget`).

   -------------------------------------------------------------------------------------------------------------------------------------------------------------
   [Class]{#73689}                  [Description]{#73691}
   -------------------------------- ----------------------------------------------------------------------------------------------------------------------------
   [ImageCanvas]{#73693}\           [Extends: java.awt.Canvas]{#73695}\
                                    [A simple output widget for a RenderedImage. This class can be used in any context that calls for a Canvas.]{#73712}\

   [ScrollingImagePanel]{#73697}\   [Extends: java.awt.Panel]{#73699}\
                                    [Implements: java.awt.event.AdjustmentListener, java.awt.event.MouseListener, java.awt.event.MouseMotionListener]{#73725}\
                                    [An extension of java.awt.Panel that contains an ImageCanvas and vertical and horizontal scrollbars.]{#73730}\
   -------------------------------------------------------------------------------------------------------------------------------------------------------------

   :  **[Table B-10 JAI Widget Classes]{#73685}**

 ------------------------------------------------------------------------

 \

   
 

 \

 ##### [Copyright](copyright.html) ©1999 , Sun Microsystems, Inc. All rights reserved.
