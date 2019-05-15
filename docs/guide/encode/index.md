---
layout: default
title: Writing Image Files
parent: Programming Guide
nav_order: 15
---

[]{#47227}

  -----------------------------------------
    C H A P T E R![](shared/sm-space.gif)13
  -----------------------------------------

[]{#47285}

+----------------------------------------------------------------------:+
| -------------------------------------------------------------------   |
|                                                                       |
| Writing Image Files                                                   |
+-----------------------------------------------------------------------+

> \
> \
> \
>
> **T**HIS chapter describes JAI\'s codec system for writing image data
> files.
>
> []{#57309}
>
> 13.1 ![](shared/space.gif)Introduction
> --------------------------------------
>
> The JAI codec system supports a variety of image formats for writing
> an image to a file or to an `OutputStream` for further manipulation.
> For writing an image to a file, the `FileStore` operation (see
> [Section 13.2, \"Writing to a File](Encode.doc.html#56452)\") writes
> an image to a specified file in the specified format. For encoding an
> image to an `OutputStream`, the `Encode` operation (see [Section 13.3,
> \"Writing to an Output Stream](Encode.doc.html#56483)\") writes an
> image to a given `OutputStream` in a specified format using the
> encoding parameters supplied via the `ImageEncodeParam` operation
> parameter.
>
> []{#56452}
>
> 13.2 ![](shared/space.gif)Writing to a File
> -------------------------------------------
>
> The `FileStore` operation writes an image to a given file in a
> specified format using the specified encoding parameters. This
> operation is much simpler than the encoders described in the remainder
> of this chapter.
>
> The `FileStore` operation takes one rendered source image and three
> parameters:
>
>   --------------------------------------------------------------------------------------------------
>   [Parameter]{#56518}   [Type]{#56520}                [Description]{#56522}
>   --------------------- ----------------------------- ----------------------------------------------
>   [filename]{#56500}\   [String]{#56502}\             [The path of the file to write to.]{#56504}\
>
>   [format]{#56506}\     [String]{#56508}\             [The format of the file.]{#56510}\
>
>   [param]{#57052}\      [ImageEncodeParam]{#57054}\   [The encoding parameters.]{#57056}\
>   --------------------------------------------------------------------------------------------------
>
>   : 
>
> The `filename` parameter must be supplied or the operation will not be
> performed. Also, the specified file path must be writable.
>
> The `format` parameter defaults to `tiff` if no value is provided.
> [Table 13-1](Encode.doc.html#56610) lists the recognized JAI file
> formats.
>
>   ----------------------------------------------------------------------------------------------------
>   [File Format]{#56614}   [Description]{#56616}
>   ----------------------- ----------------------------------------------------------------------------
>   [BMP]{#56618}\          [Microsoft Windows bitmap image file]{#56620}\
>
>   [JPEG]{#56622}\         [A file format developed by the Joint Photographic Experts Group]{#56648}\
>
>   [PNG]{#56626}\          [Portable Network Graphics]{#56628}\
>
>   [PNM]{#56630}\          [Portable aNy Map file format. Includes PBM, PGM, and PPM]{#56632}\
>
>   [TIFF]{#56634}\         [Tag Image File Format]{#56636}\
>   ----------------------------------------------------------------------------------------------------
>
>   :  **[*Table 13-1* ![](shared/sm-blank.gif) JAI Writable File
>   Formats]{#56610}**
>
> The `param` parameter must either be null or an instance of an
> `ImageEncodeParam` subclass appropriate to the format.
>
> [Listing 13-1](Encode.doc.html#58219) shows a code sample
> demonstrating the use of both the `Encode` and `FileStore` operations.
>
> []{#56483}
>
> 13.3 ![](shared/space.gif)Writing to an Output Stream
> -----------------------------------------------------
>
> The `Encode` operation writes an image to a given `OutputStream` in a
> specified format using the encoding parameters supplied via the
> `ImageEncodeParam` operation parameter.
>
> The `Encode` operation takes one rendered source image and three
> parameters:
>
>   ------------------------------------------------------------------------------------------------
>   [Parameter]{#51210}   [Type]{#51212}                [Description]{#51214}
>   --------------------- ----------------------------- --------------------------------------------
>   [stream]{#51222}\     [OutputStream]{#51224}\       [The OutputStream to write to.]{#51226}\
>
>   [format]{#57025}\     [String]{#57027}\             [The format of the created file.]{#57029}\
>
>   [param]{#51228}\      [ImageEncodeParam]{#51230}\   [The encoding parameters.]{#51232}\
>   ------------------------------------------------------------------------------------------------
>
>   : 
>
> The `param` parameter must either be null or an instance of an
> `ImageEncodeParam` subclass appropriate to the specified image format.
> The image encode parameter depends on the type of image file to be
> encoded. This parameter contains all of the information about the file
> type that the encoder needs to create the file. For example, the BMP
> format requires two parameter values, as described in the
> `BMPEncodeParam` class:
>
> -   Version number - One of three values: `VERSION_2`, `VERSION_3`, or
>     `VERSION_4`.
>
> <!-- -->
>
> -   Data layout - One of two values: `TOP_DOWN` or `BOTTOM_UP`.
>
> These parameters are described in detail in [Section 13.4, \"Writing
> BMP Image Files](Encode.doc.html#51259).\"
>
> [Listing 13-1](Encode.doc.html#58219) shows a code sample
> demonstrating the use of both the `Encode` and `FileStore` operations.
>
> **[]{#58219}**
>
> ***Listing 13-1* ![](shared/sm-blank.gif) Writing an OutputStream and
> a File**
>
> ------------------------------------------------------------------------
>
>          // Define the source and destination file names.
>          String inputFile = /images/FarmHouse.tif
>          String outputFile = /images/FarmHouse.bmp
>
>          // Load the input image.
>          RenderedOp src = JAI.create("fileload", inputFile);
>
>          // Encode the file as a BMP image.
>          FileOutputStream stream =
>              new FileOutputStream(outputFile);
>          JAI.create("encode", src, stream, BMP, null);
>
>          // Store the image in the BMP format.
>          JAI.create("filestore", src, outputFile, BMP, null);
>
> ------------------------------------------------------------------------
>
> []{#51259}
>
> 13.4 ![](shared/space.gif)Writing BMP Image Files
> -------------------------------------------------
>
> As described above, the encoding of BMP images requires the
> specification of two parameters: version and data layout. By default,
> these values are:
>
> -   Version - VERSION\_3
>
> <!-- -->
>
> -   Data layout - pixels are stored in bottom-up order
>
> The JAI BMP encoder does not support compression of BMP image files.
>
> []{#57416}
>
> ### 13.4.1 ![](shared/space.gif)BMP Version
>
> JAI currently reads and writes Version2, Version3, and some of the
> Version 4 images. The BMP version number is read and specified with
> `getVersion` and `setVersion` methods in the `BMPEncodeParam` class.
> The BMP version parameters are as follows:
>
>   ------------------------------------------------------------
>   [Parameter]{#51265}     [Description]{#51267}
>   ----------------------- ------------------------------------
>   [VERSION\_2]{#51269}\   [Specifies BMP Version 2]{#51271}\
>
>   [VERSION\_3]{#51273}\   [Specifies BMP Version 3]{#51275}\
>
>   [VERSION\_4]{#51277}\   [Specifies BMP Version 4]{#51279}\
>   ------------------------------------------------------------
>
>   : 
>
> If not specifically set, `VERSION_3` is the default version.
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.BMPEncod |
> |                                   | eParam`                           |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setVersion(int versionNumber)
>
> :   sets the BMP version to be used.
>
> <!-- -->
>
>     int getVersion()
>
> :   returns the BMP version to be used.
>
> []{#56260}
>
> ### 13.4.2 ![](shared/space.gif)BMP Data Layout
>
> The scan lines in the BMP bitmap are stored from the bottom up. This
> means that the first byte in the array represents the pixels in the
> lower-left corner of the bitmap, and the last byte represents the
> pixels in the upper-right corner.
>
> The in-memory layout of the image data to be encoded is specified with
> `getDataLayout` and `setDataLayout` methods in the `BMPEncodeParam`
> class.
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.BMPEncod |
> |                                   | eParam`                           |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setTopDown(boolean topDown)
>
> :   sets the data layout to be top down.
>
> []{#56293}
>
> ### 13.4.3 ![](shared/space.gif)Example Code
>
> [Listing 13-2](Encode.doc.html#56298) shows a code sample for encoding
> a BMP image.
>
> **[]{#56298}**
>
> ***Listing 13-2* ![](shared/sm-blank.gif) Encoding a BMP Image**
>
> ------------------------------------------------------------------------
>
>          OutputStream os = new FileOutputStream(fileToWriteTo);
>          BMPEncodeParam param = new BMPEncodeParam();
>          ImageEncoder enc = ImageCodec.createImageEncoder("BMP", os,
>                                                           param);
>          enc.encode(op);
>          os.close();
>
> ------------------------------------------------------------------------
>
> []{#51358}
>
> 13.5 ![](shared/space.gif)Writing JPEG Image Files
> --------------------------------------------------
>
> The JPEG standard was developed by a working group, known as the Joint
> Photographic Experts Group (JPEG). The JPEG image data compression
> standard handles grayscale and color images of varying resolution and
> size.
>
> JPEG compression identifies and discards \"extra\" data that is beyond
> what the human eye can see. Since it discards data, the JPEG
> compression algorithm is considered \"lossy.\" This means that once an
> image has been compressed and then decompressed, it will not be
> identical to the original image. In most cases, the difference between
> the original and compressed version of the image is indistinguishable.
>
> An advantage of JPEG compression is the ability to select the quality
> when compressing the image. The lower the quality, the smaller the
> image file size, but the more different it will appear than the
> original.
>
> [Table 13-2](Encode.doc.html#57422) lists the JPEG encode parameters
> that may be set and the default values. The remaining sections
> describe these settings and how to change them.
>
>   ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
>   [Parameter]{#57428}                    [Description]{#57430}                                                                                                                                             [Default Value]{#57432}
>   -------------------------------------- ----------------------------------------------------------------------------------------------------------------------------------------------------------------- ------------------------------------------------------
>   [writeJFIFHeader]{#58085}\             [Controls whether the encoder writes a JFIF header using the APP0 marker. See]{#58087} [Section 13.5.1, \"JFIF Header](Encode.doc.html#57417).\"\                 [True]{#58092}\
>
>   [qTabSlot\[0\],\[1\],\[2\]]{#58098}\   [Quantization tables. See]{#58102} [Section 13.5.3, \"Quantization Table](Encode.doc.html#51433).\"\                                                              [0 for Y channel, 1 for Cb and Cr channels]{#58105}\
>
>   [qTab\[0\],\[1\],\[2\]]{#58107}\       [Quantization table contents. See]{#58111} [Section 13.5.3, \"Quantization Table](Encode.doc.html#51433).\"\                                                      [Null for all three channels]{#58114}\
>
>   [qTabSet\[0\],\[1\],\[2\]]{#58116}\    [Quantization table usage. See]{#58120} [Section 13.5.3, \"Quantization Table](Encode.doc.html#51433).\"\                                                         [False for all three channels]{#58123}\
>
>   [hSamp\[0\],\[1\],\[2\]]{#57434}\      [Horizontal subsampling. See]{#57436} [Section 13.5.4, \"Horizontal and Vertical Subsampling](Encode.doc.html#51546).\"\                                          [1 for Y channel, 2 for Cb and Cr channels]{#57438}\
>
>   [vSamp\[0\],\[1\],\[2\]]{#57440}\      [Vertical subsampling. See]{#57574} [Section 13.5.4, \"Horizontal and Vertical Subsampling](Encode.doc.html#51546).\"\                                            [1 for Y channel, 2 for Cb and Cr channels]{#57528}\
>
>   [qual]{#57464}\                        [Quality setting. See]{#57466} [Section 13.5.5, \"Compression Quality](Encode.doc.html#51700).\"\                                                                 [0.75F]{#57468}\
>
>   [rstInterval]{#57470}\                 [Restart interval.]{#57472} [Section 13.5.6, \"Restart Interval](Encode.doc.html#57621).\"\                                                                       [0]{#57474}\
>
>   [writeImageOnly]{#57476}\              [Controls whether encoder writes only the compressed image data. See]{#57478} [Section 13.5.7, \"Writing an Abbreviated JPEG Stream](Encode.doc.html#56121).\"\   [False]{#57480}\
>   ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
>
>   :  **[*Table 13-2* ![](shared/sm-blank.gif) JPEG Encode
>   Parameters]{#57422}**
>
> []{#57417}
>
> ### 13.5.1 ![](shared/space.gif)JFIF Header
>
> The JPEG File Interchange Format (JFIF) is a minimal file format that
> enables JPEG bitstreams to be exchanged between a wide variety of
> platforms and applications. This minimal format does not include any
> of the advanced features found in the TIFF JPEG specification or any
> application-specific file format. The sole purpose of this simplified
> format is to allow the exchange of JPEG compressed images.
>
> The JFIF features are:
>
> -   Uses the JPEG baseline image compression algorithm
>
> <!-- -->
>
> -   Uses JPEG interchange format compressed image representation
>
> <!-- -->
>
> -   Compatible with most platforms (PC, Mac, or Unix)
>
> <!-- -->
>
> -   Standard color space: one or three components. For three
>     components, YC~b~C~r~ (CCIR 601-256 levels)
>
> An APP0 marker is used to identify a JFIF file. The marker provides
> information that is missing from the JPEG stream, such as version
> number, *x* and *y* pixel density (dots per inch or dots per cm.),
> pixel aspect ratio (derived from *x* and *y* pixel density), and
> thumbnail. The `setWriteJFIFHeader` method controls whether the
> encoder writes a JFIF header using the APP0 marker.``
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.JPEGEnco |
> |                                   | deParam`                          |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setWriteJFIFHeader(boolean writeJFIF)
>
> :   controls whether the encoder writes a JFIF header using the APP0
>     marker. By default an APP0 marker is written to create a JFIF
>     file.
>       -------------- ------------- --------------------------------
>       *Parameter*:   `writeJFIF`   If true, writes a JFIF header.
>       -------------- ------------- --------------------------------
>
>       : 
>
> []{#51386}
>
> ### 13.5.2 ![](shared/space.gif)JPEG DCT Compression Parameters
>
> JAI uses the JPEG baseline DCT coding process, shown in [Figure
> 13-1](Encode.doc.html#51423).
>
> []{#51421}
>
> ------------------------------------------------------------------------
>
> ![](Encode.doc.anc.gif)
>
> ------------------------------------------------------------------------
>
> []{#51423}
>
> ***Figure 13-1* ![](shared/sm-blank.gif) JPEG Baseline DCT Coding**
>
> For encoding, the image array is divided into 8 x 8 pixel blocks and a
> discrete cosine transform (DCT) is taken of each block, resulting in
> an 8 x 8array of transform coefficients. The DCT is a mathematical
> operation that takes the block of image samples as its input and
> converts the information from the spatial domain to the frequency
> domain. The 8 x 8 matrix input to the DCT represents brightness levels
> at specific *x*, *y* coordinates. The resulting 8 x 8 matrix values
> represent relative amounts of 64 spatial frequencies that make up the
> spectrum of the input data.
>
> The next stage in the encoder quantizes the transform coefficients by
> dividing each DCT coefficient by a value from a quantization table.
> The quantization operation discards the smaller-valued frequency
> components, leaving only the larger-valued components.
>
> After an image block has been quantized, it enters the entropy
> encoder, which creates the actual JPEG bitstream. The entropy encoder
> assigns a binary Huffman code to coefficient values. The length of
> each code is chosen to be inversely proportional to the expected
> probability of occurrence of a coefficient amplitude -
> frequently-occurring coefficient values get short code words,
> seldom-occurring coefficient values get long code words. The entropy
> encoder uses two tables, one for the AC frequency components and one
> for the DC frequency components.
>
> The JPEG decoding process is essentially the inverse of the encoding
> process. The compressed image array data stream passes through the
> entropy encoder, which recreates the quantized coefficient values.
> Then, the quantized coefficients are reconstructed by multiplication
> with the quantizer table values. Finally, an inverse DCT is performed
> and the reconstructed image array is produced.
>
> The following are the parameters that may be specified for JPEG DCT
> compression.
>
> []{#51433}
>
> ### 13.5.3 ![](shared/space.gif)Quantization Table
>
> The `setQTable` and `getQTable` methods are used to specify and
> retrieve the quantization table that will be used in encoding a
> particular band of the image. There are, by default, two quantizer
> tables:
>
>   ----------------------------------------------
>   [Table]{#51439}   [Band]{#51441}
>   ----------------- ----------------------------
>   [0]{#51443}\      [Band 0]{#51445}\
>
>   [1]{#51447}\      [All other bands]{#51449}\
>   ----------------------------------------------
>
>   : 
>
> The parameter `tableNum` is usually a value between 0 and 3. This
> value indicates which of four quantization tables you are specifying.
> Table 0 is designed to be used with the luminance band of eight-bit
> YCC images. Table 1 is designed to be used with the chrominance bands
> of eight-bit YCC images. The two tables can also be set individually
> using the `setLumaQTable` (table 0) and `setChromaQTable` (table 1)
> methods. Tables 2 and 3 are not normally used.
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.JPEGEnco |
> |                                   | deParam`                          |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setQTable(int component, int tableNum, int[] qTable)
>
> :   sets a quantization table to be used for a component. This method
>     allows up to four independent tables to be specified. This
>     disables any quality setting.
>     *Parameters*:
>     `component`
>     The band to which this table applies.
>     `tableNum`
>     The table number that this table is assigned to (0 to 3).
>     `qTable`
>     Quantization table values in \"zig-zag\" order.
>
> <!-- -->
>
>     int[] getQTable(int component)
>
> :   returns the contents of the quantization table used for a
>     component. If this method is called before the quantization table
>     is set, an error is thrown.
>
> <!-- -->
>
>     void setLumaQTable(int[] qTable)
>
> :   sets the quantization table to be used for luminance data. This is
>     a convenience method that explicitly sets the contents of
>     quantization table 0. The length of the table must be 64. This
>     disables any quality setting.
>
> <!-- -->
>
>     void setChromaQTable(int[] qTable)
>
> :   sets the quantization table to be used for luminance data. This is
>     a convenience method that explicitly sets the contents of
>     quantization table 0. The length of the table must be 64. This
>     method assumes that all chroma components will use the same table.
>     This disables any quality setting.
>
> <!-- -->
>
>     int getQTableSlot(int component)
>
> :   returns the quantization table slot used for a component. If this
>     method is called before the quantization table data is set, an
>     error is thrown.
>
> []{#51546}
>
> ### 13.5.4 ![](shared/space.gif)Horizontal and Vertical Subsampling
>
> JPEG allows the image components to be subsampled to reduce their
> resolution prior to encoding. This is typically done with YCC images,
> where the two chroma components can be subsampled, usually by a factor
> of two in both axes. This is possible due to the human visual
> system\'s low sensitivity to color images relative to luminance (Y)
> errors By default, the sampling factors for YCC input images are set
> to {1, 2, 2} for both horizontal and vertical axes.
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.JPEGEnco |
> |                                   | deParam`                          |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setHorizontalSubsampling(int component, int subsample)
>
> :   sets the horizontal subsampling to be applied to an image band.
>     Defaults to 1 for grayscale and (1,2,2) for RGB.
>     *Parameter*:
>     `component`
>     The band for which to set horizontal subsampling.
>     `subsample`
>     The horizontal subsampling factor.
>
> <!-- -->
>
>     void setVerticalSubsampling(int component, int subsample)
>
> :   sets the vertical subsampling to be applied to an image band.
>     Defaults to 1 for grayscale and (1,2,2) for RGB.
>
> <!-- -->
>
>     int getHorizontalSubsampling(int component)
>
> :   returns the horizontal subsampling factor for a band.
>
> <!-- -->
>
>     int getVerticalSubsampling(int component)
>
> :   returns the vertical subsampling factor for a band.
>
> []{#51700}
>
> ### 13.5.5 ![](shared/space.gif)Compression Quality
>
> Compression quality specifies a factor that relates to the desired
> tradeoff between image quality and the image data compression ratio.
> The quality value is a `float` between 0.0 and 1.0. A setting of 1.0
> produces the highest quality image at a lower compression ratio. A
> setting of 0.0 produces the highest compression ratio, with a
> sacrifice to image quality. The quality value is typically set to
> 0.75.
>
> The compression quality value controls image quality and compression
> ratio by determining a scale factor the encoder will use in creating
> scaled versions of the quantization tables. Some guidelines:
>
>   -------------------------------------------------------------------------------
>   [Quality Value]{#56086}   [Meaning]{#56088}
>   ------------------------- -----------------------------------------------------
>   [1.0]{#56104}\            [Highest quality, no compression]{#56106}\
>
>   [0.75]{#56090}\           [High quality, good compression ratio]{#56092}\
>
>   [0.5]{#56094}\            [Medium quality, medium compression ratio]{#56096}\
>
>   [0.25]{#56098}\           [Low quality, high compression ratio]{#56100}\
>   -------------------------------------------------------------------------------
>
>   : 
>
> ------------------------------------------------------------------------
>
> **Note:** The values stored in the quantization table also affect
> image quality and compression ratio. See also [Section 13.5.3,
> \"Quantization Table](Encode.doc.html#51433).\"
>
> ------------------------------------------------------------------------
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.JPEGEnco |
> |                                   | deParam`                          |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setQuality(float quality)
>
> :   sets the compression quality factor. Creates new quantization
>     tables that replace the currently-installed quantization tables.
>       -------------- ----------- ------------------------------------------------------------------------------
>       *Parameter*:   `quality`   The desired quality level; a value of 0.0 to 1.0. The default value is 0.75.
>       -------------- ----------- ------------------------------------------------------------------------------
>
>       : 
>
> <!-- -->
>
>     float getQuality()
>
> :   returns the quality setting for this encoding. This is a number
>     between 0.0 and 1.0.
>
> <!-- -->
>
>     boolean isQualitySet()
>
> :   tests if the quality parameter has been set in this
>     `JPEGEncodeParam`.
>
> []{#57621}
>
> ### 13.5.6 ![](shared/space.gif)Restart Interval
>
> JPEG images use restart markers to define multiple strips or tiles.
> The restart markers are inserted periodically into the image data to
> delineate image segments known as *restart intervals*. To limit the
> effect of bitstream errors to a single restart interval, JAI provides
> methods to set the restart interval in JPEG Minimum Coded Units
> (MCUs). The default is zero (no restart interval markers).
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.JPEGEnco |
> |                                   | deParam`                          |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setRestartInterval(int restartInterval)
>
> :   sets the restart interval in Minimum Coded Units (MCUs).
>       -------------- ------------------- -----------------------------------------
>       *Parameter*:   `restartInterval`   Number of MCUs between restart markers.
>       -------------- ------------------- -----------------------------------------
>
>       : 
>
> <!-- -->
>
>     int getRestartInterval()
>
> :   returns the restart interval.
>
> []{#56121}
>
> ### 13.5.7 ![](shared/space.gif)Writing an Abbreviated JPEG Stream
>
> Normally, both the JPEG table data and compressed (or uncompressed)
> image data is written to the output stream. However, it is possible to
> write just the table data or just the image data. The
> `setWriteTablesOnly` method instructs the encoder to write only the
> table data to the output stream. The `setWriteImageOnly` method
> instructs the encoder to write only the compressed image data to the
> output stream.
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.JPEGEnco |
> |                                   | deParam`                          |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setWriteTablesOnly(boolean tablesOnly)
>
> :   instructs the encoder to write only the table data to the output
>     stream.
>       -------------- -------------- -------------------------------------------
>       *Parameter*:   `tablesOnly`   If true, only the tables will be written.
>       -------------- -------------- -------------------------------------------
>
>       : 
>
> <!-- -->
>
>     void setWriteImageOnly(boolean imageOnly)
>
> :   instructs the encoder to write only the image data to the output
>     stream.
>       -------------- ------------- -----------------------------------------------------
>       *Parameter*:   `imageOnly`   If true, only the compressed image will be written.
>       -------------- ------------- -----------------------------------------------------
>
>       : 
>
> []{#54656}
>
> ### 13.5.8 ![](shared/space.gif)Example Code
>
> [Listing 13-3](Encode.doc.html#55335) shows a code sample for encoding
> a JPEG image.
>
> **[]{#55335}**
>
> ***Listing 13-3* ![](shared/sm-blank.gif) Encoding a JPEG Image**
>
> ------------------------------------------------------------------------
>
>          import java.awt.*;
>          import java.awt.event.*;
>          import java.awt.image.*;
>          import java.awt.image.renderable.*;
>          import java.io.*;
>          import javax.media.jai.*;
>          import javax.media.jai.widget.*;
>          import com.sun.media.jai.codec.*;
>
>          public class JPEGWriterTest extends WindowContainer {
>
>          private ImageEncoder encoder = null;
>          private JPEGEncodeParam encodeParam = null;
>
>          // Create some Quantization tables.
>              private static int[] qtable1 = {
>                  1,1,1,1,1,1,1,1,
>                  1,1,1,1,1,1,1,1,
>                  1,1,1,1,1,1,1,1,
>                  1,1,1,1,1,1,1,1,
>                  1,1,1,1,1,1,1,1,
>                  1,1,1,1,1,1,1,1,
>                  1,1,1,1,1,1,1,1,
>                  1,1,1,1,1,1,1,1
>              };
>
>              private static int[] qtable2 = {
>                  2,2,2,2,2,2,2,2,
>                  2,2,2,2,2,2,2,2,
>                  2,2,2,2,2,2,2,2,
>                  2,2,2,2,2,2,2,2,
>                  2,2,2,2,2,2,2,2,
>                  2,2,2,2,2,2,2,2,
>                  2,2,2,2,2,2,2,2,
>                  2,2,2,2,2,2,2,2
>              };
>
>              private static int[] qtable3 = {
>                  3,3,3,3,3,3,3,3,
>                  3,3,3,3,3,3,3,3,
>                  3,3,3,3,3,3,3,3,
>                  3,3,3,3,3,3,3,3,
>                  3,3,3,3,3,3,3,3,
>                  3,3,3,3,3,3,3,3,
>                  3,3,3,3,3,3,3,3,
>                  3,3,3,3,3,3,3,3
>              };
>
>              // Really rotten quality Q Table
>              private static int[] qtable4 = {
>                  200,200,200,200,200,200,200,200,
>                  200,200,200,200,200,200,200,200,
>                  200,200,200,200,200,200,200,200,
>                  200,200,200,200,200,200,200,200,
>                  200,200,200,200,200,200,200,200,
>                  200,200,200,200,200,200,200,200,
>                  200,200,200,200,200,200,200,200,
>                  200,200,200,200,200,200,200,200
>              };
>
>          public static void main(String args[]) {
>              JPEGWriterTest jtest = new JPEGWriterTest(args);
>              }
>
>          // Load the source image.
>          private PlanarImage loadImage(String imageName) {
>          ParameterBlock pb = (new
>                  ParameterBlock()).add(imageName);
>          PlanarImage src = JAI.create("fileload", pb);
>                  if (src == null) {
>                  System.out.println("Error in loading image " + imageName);
>                      System.exit(1);
>                  }
>                  return src;
>              }
>
>          // Create the image encoder.
>          private void encodeImage(PlanarImage img, FileOutputStream out)
>              {
>          encoder = ImageCodec.createImageEncoder("JPEG", out,
>                                                  encodeParam);
>                  try {
>                      encoder.encode(img);
>                      out.close();
>                  } catch (IOException e) {
>                      System.out.println("IOException at encoding..");
>                      System.exit(1);
>                  }
>              }
>
>          private FileOutputStream createOutputStream(String outFile) {
>                  FileOutputStream out = null;
>                  try {
>                      out = new FileOutputStream(outFile);
>                  } catch(IOException e) {
>                      System.out.println("IOException.");
>                      System.exit(1);
>                  }
>
>                  return out;
>              }
>
>          public JPEGWriterTest(String args[]) {
>          // Set parameters from command line arguments.
>          String inFile = "images/Parrots.tif";
>
>          FileOutputStream out1 = createOutputStream("out1.jpg");
>          FileOutputStream out2 = createOutputStream("out2.jpg");
>          FileOutputStream out3 = createOutputStream("out3.jpg");
>
>          // Create the source op image.
>          PlanarImage src = loadImage(inFile);
>
>             double[] constants = new double[3];
>             constants[0] = 0.0;
>             constants[1] = 0.0;
>             constants[2] = 0.0;
>             ParameterBlock pb = new ParameterBlock();
>             pb.addSource(src);
>             pb.add(constants);
>
>          // Create a new src image with weird tile sizes
>          ImageLayout layout = new ImageLayout();
>          layout.setTileWidth(57);
>          layout.setTileHeight(57);
>          RenderingHints hints = new RenderingHints(JAI.KEY_IMAGE_LAYOUT,
>                                                            layout);
>          PlanarImage src1 = JAI.create("addconst", pb, hints);
>
>          // ----- End src loading ------
>
>          // Set the encoding parameters if necessary.
>          encodeParam = new JPEGEncodeParam();
>
>          encodeParam.setQuality(0.1F);
>
>          encodeParam.setHorizontalSubsampling(0, 1);
>          encodeParam.setHorizontalSubsampling(1, 2);
>          encodeParam.setHorizontalSubsampling(2, 2);
>
>          encodeParam.setVerticalSubsampling(0, 1);
>          encodeParam.setVerticalSubsampling(1, 1);
>          encodeParam.setVerticalSubsampling(2, 1);
>
>          encodeParam.setRestartInterval(64);
>          //encodeParam.setWriteImageOnly(false);
>          //encodeParam.setWriteTablesOnly(true);
>          //encodeParam.setWriteJFIFHeader(true);
>
>          // Create the encoder.
>          encodeImage(src, out1);
>          PlanarImage dst1 = loadImage("out1.jpg");
>
>          //   ----- End first encode ---------
>
>          encodeParam.setLumaQTable(qtable1);
>          encodeParam.setChromaQTable(qtable2);
>
>          encodeImage(src, out2);
>          PlanarImage dst2 = loadImage("out2.jpg");
>
>          //   ----- End second encode ---------
>
>          encodeParam = new JPEGEncodeParam();
>          encodeImage(loadImage("images/BlackCat.tif"), out3);
>          PlanarImage dst3 = loadImage("out3.jpg");
>
>          //   ----- End third encode ---------
>
>          setTitle ("JPEGWriter Test");
>          setLayout(new GridLayout(2, 2));
>          ScrollingImagePanel panel1 = new ScrollingImagePanel(src, 512,
>                                                               400);
>          ScrollingImagePanel panel2 = new ScrollingImagePanel(dst1, 512,
>                                                               400);
>          ScrollingImagePanel panel3 = new ScrollingImagePanel(dst2, 512,
>                                                               400);
>          ScrollingImagePanel panel4 = new ScrollingImagePanel(dst3, 512,
>                                                               400);
>             add(panel1);
>             add(panel2);
>             add(panel3);
>             add(panel4);
>             pack();
>             show();    }
>          }
>
> ------------------------------------------------------------------------
>
> []{#51712}
>
> 13.6 ![](shared/space.gif)Writing PNG Image Files
> -------------------------------------------------
>
> The Portable Network Graphics (PNG) format is a file standard for
> compressed lossless bitmapped image files. A PNG file consists of an
> eight-byte PNG *signature* followed by several *chunks*. The signature
> identifies the file as a PNG file. The chunks provide additional
> information about the image. The JAI codec architecture supports PNG
> 1.1 and provides control over several of the chunks as described in
> this section.
>
> []{#51714}
>
> ### 13.6.1 ![](shared/space.gif)PNG Image Layout
>
> PNG images can be encoded in one of three pixel types, as defined by
> the subclass of `PNGEncodeParam`, as follows:
>
>   ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
>   [Pixel Type]{#51717}                [Description]{#51719}
>   ----------------------------------- ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
>   [PNGEncodeParam.Palette]{#51721}\   [Also known as *indexed-color*, where each pixel is represented by a single sample that is an index into a supplied color palette. The com.sun.media.jai.codec.PNGEncodeParam.Palette class supports the encoding of palette pixel images.]{#51723}\
>
>   [PNGEncodeParam.Gray]{#51725}\      [Each pixel is represented by a single sample that is a grayscale level. The com.sun.media.jai.codec.PNGEncodeParam.Gray class supports the encoding of grayscale pixel images.]{#51727}\
>
>   [PNGEncodeParam.RGB]{#51729}\       [Also known as *truecolor*, where each pixel is represented by three samples: red, green, and blue. The com.sun.media.jai.codec.PNGEncodeParam.RGB class supports the encoding of RGB pixel images.]{#51731}\
>   ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
>
>   : 
>
> Optionally, grayscale and RGB pixels can also include an alpha sample
> (see [Section 13.6.6.12, \"Transparency (tRNS
> Chunk)](Encode.doc.html#53950)\").
>
> A call to the `getDefaultEncodeParam` method returns an instance of:
>
> -   `PNGEncodeParam.Palette` for an image with an `IndexColorModel`.
>
> <!-- -->
>
> -   `PNGEncodeParam.Gray` for an image with only one or two bands.
>
> <!-- -->
>
> -   `PNGEncodeParam.RGB` for all other images.
>
> This method provides no guarantee that the image can be successfully
> encoded by the PNG encoder, since the encoder only performs a
> superficial analysis of the image structure.
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.PNGEncod |
> |                                   | eParam`                           |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     static PNGEncodeParam getDefaultEncodeParam(RenderedImage im)
>
> :   returns an instance of `PNGEncodeParam.Palette`,
>     `PNGEncodeParam.Gray`, or `PNGEncodeParam.RGB` appropriate for
>     encoding the given image.
>
> []{#53157}
>
> ### 13.6.2 ![](shared/space.gif)PNG Filtering
>
> The PNG file definition allows the image data to be filtered before it
> is compressed, which can improve the compressibility of the data. PNG
> encoding supports five filtering algorithms, including \"none,\" which
> indicates no filtering. The filtering algorithms are described below.
>
>   -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
>   [Parameter]{#53168}               [Description]{#53170}
>   --------------------------------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
>   [PNG\_FILTER\_NONE]{#53172}\      [No filtering - the scanline is transmitted unaltered.]{#53174}\
>
>   [PNG\_FILTER\_SUB]{#53176}\       [The filter transmits the difference between each byte and the value of the corresponding byte of the prior pixel.]{#53178}\
>
>   [PNG\_FILTER\_UP]{#53180}\        [Similar to the Sub filter, except that the pixel immediately above the current pixel, rather than just to its left, is used as the predictor.]{#53182}\
>
>   [PNG\_FILTER\_AVERAGE]{#53184}\   [The filter uses the average of the two neighboring pixels (left and above) to predict the value of a pixel.]{#53186}\
>
>   [PNG\_FILTER\_PAETH]{#53188}\     [The filter computes a simple linear function of the three neighboring pixels (left, above, upper left), then chooses as predictor the neighboring pixel closest to the computed value.]{#53190}\
>   -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
>
>   :  **[*Table 13-3* ![](shared/sm-blank.gif) PNG Filtering
>   Algorithms]{#53164}**
>
> The filtering can be different for each row of an image by using the
> `filterRow` method. The method can be overridden to provide a custom
> algorithm for choosing the filter type for a given row.
>
> The `filterRow` method is supplied with the current and previous rows
> of the image. For the first row of the image, or of an interlacing
> pass, the previous row array will be filled with zeros as required by
> the PNG specification.
>
> The method is also supplied with five scratch arrays. These arrays may
> be used within the method for any purpose. At method exit, the array
> at the index given by the return value of the method should contain
> the filtered data. The return value will also be used as the filter
> type.
>
> The default implementation of the method performs a trial encoding
> with each of the filter types, and computes the sum of absolute values
> of the differences between the raw bytes of the current row and the
> predicted values. The index of the filter producing the smallest
> result is returned.
>
> As an example, to perform only \"sub\" filtering, this method could be
> implemented (non-optimally) as follows:
>
> ------------------------------------------------------------------------
>
>          for (int i = bytesPerPixel; i < bytesPerRow + bytesPerPixel; i++)
>          {
>               int curr = currRow[i] & 0xff;
>               int left = currRow[i - bytesPerPixel] & 0xff;
>               scratchRow[PNG_FILTER_SUB][i] = (byte)(curr - left);
>          }
>          return PNG_FILTER_SUB;
>
> ------------------------------------------------------------------------
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.PNGEncod |
> |                                   | eParam`                           |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     int filterRow(byte[] currRow, byte[] prevRow, 
>            byte[][]  scratchRows, int bytesPerRow, int bytesPerPixel)
>
> :   returns the type of filtering to be used on a row of an image.
>     *Parameters*:
>     `currRow`
>     The current row as an array of `byte`s of length at least
>     `bytesPerRow` + `bytesPerPixel`. The pixel data starts at index
>     `bytesPerPixel`; the initial `bytesPerPixel` bytes are zero.
>     `prevRow`
>     The current row as an array of `byte`s. The pixel data starts at
>     index `bytesPerPixel`; the initial `bytesPerPixel` bytes are zero.
>     `scratchRows`
>     An array of 5 `byte` arrays of length at least `bytesPerRow` +
>     `bytesPerPixel`, usable to hold temporary results. The filtered
>     row will be returned as one of the entries of this array. The
>     returned filtered data should start at index `bytesPerPixel`; The
>     initial `bytesPerPixel` bytes are not used.
>     `bytesPerRow`
>     The number of bytes in the image row. This value will always be
>     greater than 0.
>     `bytesPerPixel`
>     The number of bytes representing a single pixel, rounded up to an
>     integer. This is the `bpp` parameter described in the PNG
>     specification.
>
> []{#53673}
>
> ### 13.6.3 ![](shared/space.gif)Bit Depth
>
> The PNG specification identifies the following bit depth restrictions
> for each of the color types:
>
>   -------------------------------------------------------------------------------------------------------------------------------------------------------
>   [Color Type]{#53683}   [Allowed Bit Depths]{#53685}   [Description]{#53687}
>   ---------------------- ------------------------------ -------------------------------------------------------------------------------------------------
>   [0]{#53689}\           [1, 2, 4, 8, 16]{#53691}\      [Grayscale. Each pixel is a grayscale sample.]{#53693}\
>
>   [2]{#53695}\           [8, 16]{#53697}\               [Truecolor (RGB) without alpha. Each pixel is an RGB triple.]{#53699}\
>
>   [3]{#53701}\           [1, 2, 4, 8]{#53703}\          [Indexed color (Palette). Each pixel is a palette index.]{#53705}\
>
>   [4]{#53707}\           [8, 16]{#53709}\               [Grayscale with alpha. Each pixel is a grayscale sample followed by an alpha sample.]{#53711}\
>
>   [6]{#53713}\           [8, 16]{#53715}\               [Truecolor (RGB) with alpha. Each pixel is an RGB triple followed by an alpha sample.]{#53717}\
>   -------------------------------------------------------------------------------------------------------------------------------------------------------
>
>   :  **[*Table 13-4* ![](shared/sm-blank.gif) PNG Bit Depth
>   Restrictions]{#53677}**
>
> The bit depth is specified by the `setBithDepth` method in the class
> type.
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.PNGEncod |
> |                                   | eParam.Palette`                   |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setBitDepth(int bitDepth)
>
> :   sets the desired bit depth for a palette image. The bit depth must
>     be 1, 2, 4, or 8.
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.PNGEncod |
> |                                   | eParam.Gray`                      |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     public void setBitDepth(int bitDepth)
>
> :   sets the desired bit depth for a grayscale image. The bit depth
>     must be 1, 2, 4, 8, or 16.
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.PNGEncod |
> |                                   | eParam.RGB`                       |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setBitDepth(int bitDepth)
>
> :   sets the desired bit depth for an RGB image. The bit depth must be
>     8 or 16.
>
> []{#53730}
>
> ### 13.6.4 ![](shared/space.gif)Interlaced Data Order
>
> The interlaced data order indicates the transmission order of the
> image data. Two settings are currently allowed: no interlace and Adam7
> interlace. With interlacing turned off, pixels are stored sequentially
> from left to right, and scanlines sequentially from top to bottom.
> Adam7 interlacing (named after its author, Adam M. Costello), consists
> of seven distinct passes over the image; each pass transmits a subset
> of the pixels in the image.
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.PNGEncod |
> |                                   | eParam`                           |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setInterlacing(boolean useInterlacing)
>
> :   turns Adam7 interlacing on or off.
>
> <!-- -->
>
>     boolean getInterlacing()
>
> :   returns `true` if Adam7 interlacing will be used.
>
> []{#53779}
>
> ### 13.6.5 ![](shared/space.gif)PLTE Chunk for Palette Images
>
> The PLTE chunk provides the palette information palette or
> indexed-color images. The PLTE chunk must be supplied for all palette
> (color type 3) images and is optional for RGB (color type 2 and 6)
> images.
>
> The PLTE chunk contains from 1 to 256 palette entries, each a
> three-byte series of the alternating red, green, and blue values, as
> follows:
>
> -   Red: one byte (0 = black, 255 = red)
>
> <!-- -->
>
> -   Green: one byte (0 = black, 255 = green)
>
> <!-- -->
>
> -   Blue: one byte (0 = black, 255 = blue)
>
> The number of elements in the palette must be a multiple of 3, between
> 3 and 768 (3 x 256). The first entry in the palette is referenced by
> pixel value 0, the second by pixel value 1, and so on.
>
> For RGB (color type 2 and 6) images, the PLTE chunk, if included,
> provides a suggested set of from 1 to 256 colors to which the RGB
> image can be quantized in case the viewing system cannot display RGB
> directly.
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.PNGEncod |
> |                                   | eParam`                           |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setPalette(int[] rgb)
>
> :   sets the RGB palette of the image to be encoded.
>       --------------- ------- ---------------------
>       *Parameters*:   `rgb`   An array of `int`s.
>       --------------- ------- ---------------------
>
>       : 
>
> <!-- -->
>
>     int[] getPalette()
>
> :   returns the current RGB palette.
>
> <!-- -->
>
>     void unsetPalette()
>
> :   suppresses the PLTE chunk from being output.
>
> <!-- -->
>
>     boolean isPaletteSet()
>
> :   returns true if a PLTE chunk will be output.
>
> []{#53197}
>
> ### 13.6.6 ![](shared/space.gif)Ancillary Chunk Specifications
>
> All ancillary PNG chunks are optional but are recommended. Most of the
> PNG chunks can be specified prior to encoding the image by `set`
> methods in the `PNGEncodeParam` class. The chunks that can be set and
> the methods used to set them are described in the following
> paragraphs.
>
> []{#51917}
>
> #### 13.6.6.1 ![](shared/space.gif)Background Color (bKGD Chunk)
>
> Methods are provided to set and read the suggested background color,
> which is encoded by the bKGD chunk.
>
> For Palette (indexed color) images, the bKGD chunk contains a single
> value, which is the palette index of the color to be used as the
> background.
>
> For Grayscale images, the bKGD chunk contains a single value, which is
> the gray level to be used as the background. The range of values is 0
> to 2^bitdepth^ - 1.
>
> For RGB (truecolor) images, the bKGD chunk contains three values, one
> each for red, green, and blue. Each value has the range of 0 to
> 2^bitdepth^ - 1.
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.PNGEncod |
> |                                   | eParam.Palette`                   |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setBackgroundPaletteIndex(int index)
>
> :   sets the palette index of the suggested background color.
>
> <!-- -->
>
>     int getBackgroundPaletteIndex()
>
> :   returns the palette index of the suggested background color.
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.PNGEncod |
> |                                   | eParam.Gray`                      |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setBackgroundGray(int gray)
>
> :   sets the suggested gray level of the background.
>
> <!-- -->
>
>     int getBackgroundGray()
>
> :   returns the suggested gray level of the background.
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.PNGEncod |
> |                                   | eParam.RGB`                       |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setBackgroundRGB(int[] rgb)
>
> :   sets the RGB value of the suggested background color. The `rgb`
>     parameter should have three entries.
>
> <!-- -->
>
>     int[] getBackgroundRGB()
>
> :   returns the RGB value of the suggested background color.
>
> []{#52342}
>
> #### 13.6.6.2 ![](shared/space.gif)Chromaticity (cHRM Chunk)
>
> Applications that need device-independent specification of colors in a
> PNG file can specify the 1931 CIE (*x*,*y*) chromaticities of the red,
> green, and blue primaries used in the image, and the referenced white
> point.
>
> The chromaticity parameter should be a `float` array of length 8
> containing the white point *X* and *Y*, red *X* and *Y*, green *X* and
> *Y*, and blue *X* and *Y* values in order.
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.PNGEncod |
> |                                   | eParam`                           |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setChromaticity(float[] chromaticity)
>
> :   sets the white point and primary chromaticities in CIE (*x*,*y*)
>     space.
>
> <!-- -->
>
>     void setChromaticity(float whitePointX, float whitePointY, 
>            float redX, float redY, float greenX, float greenY, 
>            float  blueX, float blueY)
>
> :   a convenience method that calls the array version.
>
> <!-- -->
>
>     float[] getChromaticity()
>
> :   returns the white point and primary chromaticities in CIE
>     (*x*,*y*) space.
>
> []{#52446}
>
> #### 13.6.6.3 ![](shared/space.gif)Gamma Correction (gAMA Chunk)
>
> The gamma value specifies the relationship between the image samples
> and the desired display output intensity as a power function:
>
> :   sample = light\_out^gamma^
>
> If the image\'s gamma value is unknown, the gAMA chunk should be
> suppressed. The absence of the gAMA chunk indicates that the gamma is
> unknown.
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.PNGEncod |
> |                                   | eParam`                           |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setGamma(float gamma)
>
> :   sets the gamma value for the image.
>
> <!-- -->
>
>     float getGamma()
>
> :   returns the gamma value for the image.
>
> <!-- -->
>
>     void unsetGamma()
>
> :   suppresses the gAMA chunk from being output.
>
> []{#52489}
>
> #### 13.6.6.4 ![](shared/space.gif)Palette Histogram (hIST Chunk)
>
> The palette histogram is a value that gives the approximate usage
> frequency of each color in the color palette. If the viewer is unable
> to provide all the colors listed in the palette, the histogram may
> help decide how to choose a subset of colors for display. The hIST
> chunk is only valid with Palette images.
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.PNGEncod |
> |                                   | eParam.Palette`                   |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setPaletteHistogram(int[] paletteHistogram)
>
> :   sets the palette histogram for the image.
>
> <!-- -->
>
>     int[] getPaletteHistogram()
>
> :   returns the palette histogram for the image.
>
> <!-- -->
>
>     void unsetPaletteHistogram()
>
> :   suppresses the hIST chunk from being output.
>
> []{#52533}
>
> #### 13.6.6.5 ![](shared/space.gif)Embedded ICC Profile Data (iCCP Chunk)
>
> You can specify that RGB image samples conform to the color space
> presented by the embedded International Color Consortium profile. The
> color space of the ICC profile must be an RGB color space.
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.PNGEncod |
> |                                   | eParam`                           |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setICCProfileData(byte[] ICCProfileData)
>
> :   sets the ICC profile data.
>
> <!-- -->
>
>     byte[] getICCProfileData()
>
> :   returns the ICC profile data.
>
> <!-- -->
>
>     void unsetICCProfileData()
>
> :   suppresses the iCCP chunk from being output.
>
> []{#52596}
>
> #### 13.6.6.6 ![](shared/space.gif)Physical Pixel Dimensions (pHYS Chunk)
>
> The intended pixel size or aspect ratio for display of the image may
> be specified in the pHYS chunk. The physical pixel dimensions
> information is presented as three integer values:
>
> -   Pixels per unit, *x* axis
>
> <!-- -->
>
> -   Pixels per unit, *y* axis
>
> <!-- -->
>
> -   Unit specifier
>
> The unit specifier may have one of two values:
>
> :   0 = Unit is unknown\
>     1 = Unit is meters
>
> When the unit specifier is 0, the pHYS chunk defines pixel aspect
> ratio only; the actual size of the pixels remains unspecified.
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.PNGEncod |
> |                                   | eParam`                           |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setPhysicalDimension(int[] physicalDimension)
>
> :   sets the physical pixel dimension.
>
> <!-- -->
>
>     void setPhysicalDimension(int xPixelsPerUnit, 
>            int  yPixelsPerUnit, int unitSpecifier)
>
> :   a convenience method that calls the array version.
>
> <!-- -->
>
>     int[] getPhysicalDimension()
>
> :   returns the physical pixel dimension.
>
> []{#52700}
>
> #### 13.6.6.7 ![](shared/space.gif)Significant Bits (sBIT Chunk)
>
> For PNG data that has been converted from a lower sample depth, the
> significant bits information in the sBIT chunk stores the number of
> significant bits in the original image. This value allows decoders to
> recover the original data losslessly, even if the data had a sample
> depth not directly supported by PNG.
>
> The number of entries in the `significantBits` array must be equal to
> the number of output bands in the image:
>
> -   1 - for a grayscale image
>
> <!-- -->
>
> -   2 - for a grayscale image with alpha
>
> <!-- -->
>
> -   3 - for palette or RGB images
>
> <!-- -->
>
> -   4 - for RGB images with alpha
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.PNGEncod |
> |                                   | eParam.RGB`                       |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setSignificantBits(int[] significantBits)
>
> :   sets the significant bits.
>
> <!-- -->
>
>     int[] getSignificantBits()
>
> :   returns the significant bits.
>
> <!-- -->
>
>     void unsetSignificantBits()
>
> :   suppresses the sBIT chunk from being output.
>
> []{#53982}
>
> #### 13.6.6.8 ![](shared/space.gif)Suggested Palette (sPLT Chunk)
>
> A suggested palette may be specified when the display device is not
> capable of displaying the full range of colors in the image. This
> palette provides a recommended set of colors, with alpha and frequency
> information, that can be used to construct a reduced palette to which
> the image can be quantized.
>
> The suggested palette, as defined by the `PNGSuggestedPaletteEntry`
> class, consists of the following:
>
> -   A palette name - a String that provides a convenient name for
>     referring to the palette
>
> <!-- -->
>
> -   A `sampleDepth` parameter - must be either 8 or 16
>
> <!-- -->
>
> -   Red sample
>
> <!-- -->
>
> -   Green sample
>
> <!-- -->
>
> -   Blue sample
>
> <!-- -->
>
> -   Alpha sample
>
> <!-- -->
>
> -   Frequency - the value is proportional to the fraction of pixels in
>     the image that are closest to that palette entry in RGBA space,
>     before the image has been composited against any background
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.PNGEncod |
> |                                   | eParam.Palette`                   |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setSuggestedPalette(PNGSuggestedPaletteEntry[] palette)
>
> :   sets the suggested palette.
>
> <!-- -->
>
>     PNGSuggestedPaletteEntry[] getSuggestedPalette()
>
> :   returns the suggested palette.
>
> <!-- -->
>
>     void unsetSuggestedPalette()
>
> :   suppresses the sPLT chunk from being output.
>
> []{#53898}
>
> #### 13.6.6.9 ![](shared/space.gif)PNG Rendering Intent (sRGB Chunk)
>
> If the PNG image includes an sRGB chunk, the image samples confirm to
> the sRGB color space and should be displayed using the specified
> rendering \"intent.\" The rendering intent specifies tradeoffs in
> colorimetric accuracy. There are four rendering intents:
>
>   ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
>   [Parameter]{#53906}             [Description]{#53908}
>   ------------------------------- -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
>   [INTENT\_PERCEPTUAL]{#53911}\   [The \"perceptual\" intent is for images that prefer good adaptation to the output device gamut at the expense of colorimetric accuracy, such as photographs.]{#53913}\
>
>   [INTENT\_RELATIVE]{#53916}\     [The \"relative colorimetric\" intent is for images that require color appearance matching.]{#53918}\
>
>   [INTENT\_SATURATION]{#53921}\   [The \"saturation\" intent is for images that prefer preservation of saturation at the expense of hue and lightness.]{#53923}\
>
>   [INTENT\_ABSOLUTE]{#53926}\     [The \"absolute colorimetric\" intent is for images that require absolute colorimetry.]{#53928}\
>   ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
>
>   :  **[*Table 13-5* ![](shared/sm-blank.gif) PNG Rendering
>   Intent]{#53902}**
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.PNGEncod |
> |                                   | eParam.RGB`                       |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setSRGBIntent(int SRGBIntent)
>
> :   sets the PNG rendering intent.
>       -------------- -------------- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------
>       *Parameter*:   `SRGBIntent`   The sRGB rendering intent to be stored with the image. The legal values are 0 = Perceptual, 1 = Relative colorimetric, 2 = Saturation, and 3 = Absolute colorimetric.
>       -------------- -------------- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------
>
>       : 
>
> <!-- -->
>
>     int getSRGBIntent()
>
> :   returns the rendering intent.
>
> <!-- -->
>
>     void unsetSRGBIntent()
>
> :   suppresses the sRGB chunk from being output.
>
> []{#52832}
>
> #### 13.6.6.10 ![](shared/space.gif)Textual Data (tEXt Chunk)
>
> Textual data can be encoded along with the image in the tEXt chunk.
> The information stored in this chunk can be an image description or
> copyright notice. A keyword indicates what the text string contains.
> The following keywords are defined:
>
>   ----------------- ----------------------------------------------
>   `Title`           A title or caption for the image
>   `Author`          The name of the image\'s creator
>   `Description`     A description of the image
>   `Copyright`       A copyright notice
>   `Creation Time`   The time the original image was created
>   `Software`        The software used to create the image
>   `Disclaimer`      A legal disclaimer
>   `Warning`         A warning of the nature of the image content
>   `Source`          The hardware device used to create the image
>   `Comment`         Miscellaneous information
>   ----------------- ----------------------------------------------
>
>   : 
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.PNGEncod |
> |                                   | eParam`                           |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setText(String[] text)
>
> :   sets the text string to be encoded with the image.
>
> <!-- -->
>
>     String[] getText()
>
> :   returns the text string to be encoded with the image.
>
> <!-- -->
>
>     void unsetText()
>
> :   suppresses the tEXt chunk from being output.
>
> []{#52880}
>
> #### 13.6.6.11 ![](shared/space.gif)Image Modification Timestamp (tIME Chunk)
>
> The tIME chunk provides information on the last time the image was
> modified. The tIME information is a `Date` and the internal storage
> format uses UTC regardless of how the `modificationTime` parameter was
> created.
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.PNGEncod |
> |                                   | eParam`                           |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setModificationTime(Date modificationTime)
>
> :   sets the image modification time as a `Date` that will be sent in
>     the tIME chunk.
>
> <!-- -->
>
>     Date getModificationTime()
>
> :   returns the image modification time data that will be sent in the
>     tIME chunk.
>
> <!-- -->
>
>     void unsetModificationTime()
>
> :   suppresses the tIME chunk from being output.
>
> []{#53950}
>
> #### 13.6.6.12 ![](shared/space.gif)Transparency (tRNS Chunk)
>
> The tRNS chunk specifies that the image uses simple transparency.
> Simple transparency means either alpha values associated with palette
> entries for Palette images, or a single transparent color, for
> Grayscale and RGB images.
>
> For Palette images, the tRNS chunk should contain a series of one-byte
> alpha values, one for each RGB triple in the palette. Each entry
> indicates that pixels of the corresponding palette index must be
> treated as having the specified alpha value.
>
> For grayscale images, the tRNS chunk should contain a single gray
> level value, stored as an int. Pixels of the specified gray value are
> treated as transparent. If the grayscale image has an alpha value,
> setting the gray level causes the image\'s alpha channel to be
> ignored.
>
> For RGB images, the tRNS chunk should an RGB color value, stored as an
> int. Pixels of the specified gray value are treated as transparent. If
> the RGB image has an alpha value, setting the gray level causes the
> image\'s alpha channel to be ignored.
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.PNGEncod |
> |                                   | eParam.Palette`                   |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setPaletteTransparency(byte[] alpha)
>
> :   sets the alpha values associated with each palette entry. The
>     alpha parameter should have as many entries as there are RGB
>     triples in the palette.
>
> <!-- -->
>
>     byte[] getPaletteTransparency()
>
> :   returns the alpha values associated with each palette entry.
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.PNGEncod |
> |                                   | eParam.Gray`                      |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setTransparentGray(int transparentGray)
>
> :   sets the gray value to be used to denote transparency. Setting
>     this attribute will cause the alpha channel of the input image to
>     be ignored.
>
> <!-- -->
>
>     int getTransparentGray()
>
> :   returns the gray value to be used to denote transparency.
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.PNGEncod |
> |                                   | eParam.RGB`                       |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setTransparentRGB(int[] transparentRGB)
>
> :   sets the RGB value to be used to denote transparency. Setting this
>     attribute will cause the alpha channel of the input image to be
>     ignored.
>
> <!-- -->
>
>     int[] getTransparentRGB()
>
> :   returns the RGB value to be used to denote transparency.
>
> []{#52924}
>
> #### 13.6.6.13 ![](shared/space.gif)Compressed Text Data (zTXt Chunk)
>
> Text data may be stored in the zTXt chunk, in addition to the text in
> the tEXt chunk. The zTXt chunk is intended for storing large blocks of
> text, since the text is compressed.
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.PNGEncod |
> |                                   | eParam`                           |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setCompressedText(String[] text)
>
> :   sets the compressed text to be sent in the zTXt chunk.
>
> <!-- -->
>
>     String[] getCompressedText()
>
> :   returns the compressed text to be sent in the zTXt chunk.
>
> <!-- -->
>
>     void unsetCompressedText()
>
> :   suppresses the zTXt chunk from being output.
>
> []{#54008}
>
> #### 13.6.6.14 ![](shared/space.gif)Private Chunks
>
> Private chunks may be added to the output file. These private chunks
> carry information that is not understood by most other applications.
> Private chunks should be given names with lowercase second letters to
> ensure that they do not conflict with any future public chunk
> information. See the PNG specification for more information on chunk
> naming conventions.
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.PNGEncod |
> |                                   | eParam`                           |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     synchronized void addPrivateChunk(String type, byte[] data)
>
> :   adds a private chunk to the output file.
>
> <!-- -->
>
>     synchronized int getNumPrivateChunks()
>
> :   returns the number of private chunks to be written to the output
>     file.
>
> <!-- -->
>
>     synchronized String getPrivateChunkType(int index)
>
> :   returns the type of the private chunk at a given index, as a
>     four-character `String`. The index must be smaller than the return
>     value of `getNumPrivateChunks`.
>
> <!-- -->
>
>     synchronized void removeUnsafeToCopyPrivateChunks()
>
> :   removes all private chunks associated with this parameter instance
>     whose \"safe-to-copy\" bit is not set. This may be advisable when
>     transcoding PNG images.
>
> <!-- -->
>
>     synchronized void removeAllPrivateChunks()
>
> :   remove all private chunks associated with this parameter instance.
>
> []{#52304}
>
> 13.7 ![](shared/space.gif)Writing PNM Image Files
> -------------------------------------------------
>
> The PNM format is one of the extensions of the PBM file format (PBM,
> PGM, and PPM). The portable bitmap format is a
> lowest-common-denominator monochrome file format. It was originally
> designed to make it reasonable to mail bitmaps between different types
> of machines. It now serves as the common language of a large family of
> bitmap conversion filters.
>
> The PNM format comes in six variants:
>
> -   PBM ASCII - three-banded images
>
> <!-- -->
>
> -   PBM raw - three-banded images
>
> <!-- -->
>
> -   PGM ASCII - single-banded images
>
> <!-- -->
>
> -   PGM raw - single-banded images
>
> <!-- -->
>
> -   PPM ASCII - single-banded images
>
> <!-- -->
>
> -   PPM raw - single-banded images
>
> The parameter values, then are `RAW` and `ASCII`.
>
> [Listing 13-4](Encode.doc.html#54619) shows a code sample for encoding
> a PNM image.
>
> **[]{#54619}**
>
> ***Listing 13-4* ![](shared/sm-blank.gif) Encoding a PNM Image**
>
> ------------------------------------------------------------------------
>
>          // Create the OutputStream.
>          OutputStream out = new FileOutputStream(fileToWriteTo);
>
>          // Create the ParameterBlock.
>          PNMEncodeParam param = new PNMEncodeParam();
>          param.setRaw(true.equals("raw"));
>
>          //Create the PNM image encoder.
>          ImageEncoder encoder = ImageCodec.createImageEncoder("PNM",
>                                                                out,
>                                                                param);
>
> ------------------------------------------------------------------------
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.PNMEncod |
> |                                   | eParam`                           |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setRaw(boolean raw)
>
> :   sets the RAWBITS option flag.
>
> <!-- -->
>
>     boolean getRaw()
>
> :   retrieves the RAWBITS option flag.
>
> []{#56754}
>
> 13.8 ![](shared/space.gif)Writing TIFF Image Files
> --------------------------------------------------
>
> The TIFF file format is a tag-based file format for storing and
> interchanging raster images. TIFF files typically come from scanners,
> frame grabbers, and paint- or photo-retouching programs.
>
> By default, TIFF images in JAI are encoded without any compression and
> are written out in strips rather than tiles. However, JAI does support
> image compression, and the writing of tiled TIFF images.
>
> []{#56811}
>
> ### 13.8.1 ![](shared/space.gif)TIFF Compression
>
> JAI currently does not support compression of TIFF images.
>
> []{#56916}
>
> ### 13.8.2 ![](shared/space.gif)TIFF Tiled Images
>
> By default, the JAI encoder organizes TIFF images into strips. For
> low- to medium-resolution images, this is adequate. However, for
> high-resolution (large) images, the images can be accessed more
> efficiently if the image is divided into roughly square tiles instead
> of strips.
>
> Writing of tiled TIFF images can be enabled by calling the
> `setWriteTiled` method.
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `com.sun.media.jai.codec.TIFFEnco |
> |                                   | deParam`                          |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
>     void setWriteTiled(boolean writeTiled)
>
> :   enables writing of TIFF images in tiles rather than in strips.
>       -------------- -------------- -------------------------------------------------------------------------
>       *Parameter*:   `writeTiled`   Specifies whether the image data should be written out in tiled format.
>       -------------- -------------- -------------------------------------------------------------------------
>
>       : 
>
> <!-- -->
>
>     boolean getWriteTiled()
>
> :   returns the value of the `writeTiled` parameter.
>
> ------------------------------------------------------------------------
>
> \
>
> [![Contents](shared/contents.gif)](JAITOC.fm.html)
> [![Previous](shared/previous.gif)](Client-server.doc.html)
> [![Next](shared/next.gif)](Extension.doc.html)
>
> *Programming in Java Advanced Imaging*
>
> \
>
> ##### [Copyright](copyright.html) © 1999, Sun Microsystems, Inc. All rights reserved.
