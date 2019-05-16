---
layout: default
title: Preface
parent: Programming Guide
nav_order: 1
---

# Preface

This document introduces the Eclipse ImageN API and how to
program in it. This document is intended for serious programmers who
want to use ImageN for real projects. To best
understand this document and the examples, you need a solid background
in the Java programming language and some experience with imaging. In
addition, you will need a working knowledge of other Java APIs,
depending on your intended application:

- Java ImageIO for image encoding and decoding

- Java 2D for simple graphics, text, and fundamental image manipulation

This library is an Apache License v 2.0 release of the former Java Advanced Imaging API
codebase. Method compatibility has been maintained using new packages and library name.

Disclaimer
----------

This version of is based on release {{ version }} of the Eclipse ImageN API.

If any discrepancies between this guide and the javadocs are noted, always
consider the javadocs to be the most accurate, since they are
generated directly from the source files and are always the most up to
date.

About This Book
---------------

**[Chapter 1, \"Introduction to Java Advanced
Imaging\"](../introduction/index.html),** gives an overview of the
Java Advanced Imaging API, how it evolved from the original Java
Advanced Windowing Toolkit (AWT), some of its features, and introduces
the imaging operations.

**[Chapter 2, \"Java AWT Imaging\"](J2D-concepts.doc.html#47285),"**
reviews the imaging portions of the Java AWT and examines the imaging
features of the Java 2D API.

**[Chapter 3, \"Programming in Java Advanced
Imaging\"](Programming-environ.doc.html#47285),** describes how to get
started programming with the Java Advanced Imaging API.

**[Chapter 4, \"Image Acquisition and
Display\"](Acquisition.doc.html#81550),** describes the Java Advanced
Imaging API image data types and the API constructors and methods for
image acquisition and display.

**[Chapter 5, \"Color Space\"](Color.doc.html#47285),** describes the
 color space, transparency, and the color conversion operators.

**[Chapter 6, \"Image
Manipulation\"](Image-manipulation.doc.html#47285),** describes the
basics of manipulating images to prepare them for processing and
display.

**[Chapter 7, \"Image Enhancement\"](Image-enhance.doc.html#47285),**
describes the basics of improving the visual appearance of images
through enhancement techniques.

**[Chapter 8, \"Geometric Image
Manipulation\"](Geom-image-manip.doc.html#51140),** describes the
basics of Java Advanced Imaging\'s geometric image manipulation
functions.

**[Chapter 9, \"Image Analysis\"](Analysis.doc.html#55366),**
describes the Java Advanced Imaging API image analysis operators.

**[Chapter 10, \"Graphics Rendering\"](Graphics.doc.html#51143),**
describes the Java Advanced Imaging presentation of shapes and text.

**[Chapter 11, \"Image Properties\"](Properties.doc.html#47285),**
describes the tools that allow a programmer to add a simple database
of arbitrary data that can be attached to images.

**[Chapter 12, \"Client-Server
Imaging\"](Client-server.doc.html#47285),** describes Java Advanced
Imaging\'s client-server imaging system.

**[Chapter 13, \"Writing Image Files\"](Encode.doc.html#47285),**
describes Java Advanced Imaging\'s codec system for encoding image
data files.

**[Chapter 14, \"Extending the API\"](Extension.doc.html#47285),**
describes how the Java Advanced Imaging API is extended.

**[Appendix A, \"Program Examples\"](Examples.doc.html#72446),**
contains fully-operational Java Advanced Imaging program examples.

**[Appendix B, \"API
Summary\"](API-summary.doc.html#72446),** summarizes the imaging
interfaces, and classes, including the `java.awt`, `java.awt.Image`,
and `javax.media.jai` classes.

The **[Glossary](Glossary.doc.html#47771)** contains descriptions of
significant terms that appear in this book.


Related Documentation
---------------------

To obtain a good understanding of the Java programming language, we
suggest you start with the SunSoft Press series of books:

-   *Instant Java*, by John A. Pew


-   *Java in a Nutchell: A Desktop Quick Reference*, by David Flanagan


-   *Java by Example*, by Jerry R. Jackson and Alan L. McClellan


-   *Just Java*, by Peter van der Linden


-   *Core Java*, by Gary Cornell and Gay S. Horstmann


-   *Java Distributed Computing*, by Jim Farley

For more information on digital imaging, we suggest you refer to the
following books:

-   *Fundamentals of Digital Image Processing*, by Anil K. Jain


-   *Digital Image Processing: Principles and Applications*, by
    Gregory A. Baxes


-   *Digital Image Processing*, by Kenneth R. Castleman


-   *Digital Image Processing*, 2nd. ed., by William K. Pratt


Additional Information
----------------------

Since Eclipse ImageN continues to evolve and periodically add
new operators, it is always a good idea to occasionally check the
project page for the latest information.

:   `https://projects.eclipse.org/projects/technology.imagen`

The web site contains links to the latest version of ImageN, email
list to take part in the community, and tutorial and demos 
including examples of the use of many ImageN operators.

Style Conventions
-----------------

The following style conventions are used in this document:

-   `Lucida type` is used to represent computer code and the names of
    files and directories.


-   **Bold Lucida type** is used for Java 3D API declarations.


-   *Italic type* is used for emphasis and for equations.

Throughout the book, we introduce many API calls with the following
format:

**API:** `org.eclipse.imagen.jai.TiledImage`

When introducing an API call for the first time, we add a short
list of the methods, tagged with the API heading.
