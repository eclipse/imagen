---
layout: default
title: Preface
parent: Programming Guide
nav_order: 1
---


*Programming in Java Advanced Imaging*

\

[]{#48128}

+----------------------------------------------------------------------:+
| Preface                                                               |
|                                                                       |
| -------------------------------------------------------------------   |
+-----------------------------------------------------------------------+

> \
> \
> \
>
> **T**HIS document introduces the Java Advanced Imaging API and how to
> program in it. This document is intended for serious programmers who
> want to use Java Advanced Imaging for real projects. To best
> understand this document and the examples, you need a solid background
> in the Java programming language and some experience with imaging. In
> addition, you will need a working knowledge of other Java Extension
> APIs, depending on your intended application:
>
> -   Java 2D for simple graphics, text, and fundamental image
>     manipulation
>
> <!-- -->
>
> -   Java Media Framework for components to play and control time-based
>     media such as audio and video
>
> <!-- -->
>
> -   Java Sound
>
> <!-- -->
>
> -   Java 3D
>
> <!-- -->
>
> -   Java Telephony
>
> <!-- -->
>
> -   Java Speech
>
> []{#48649}
>
> Disclaimer
> ----------
>
> This version of *Programming in Java Advanced Imaging* is based on
> release 1.0.1 of the Java Advanced Imaging API. Please do not rely on
> this document or the Java Advanced Imaging software for
> production-quality or mission-critical applications. If any
> discrepancies between this book and the javadocs are noted, always
> consider the javadocs to be the most accurate, since they are
> generated directly from the JAI files and are always the most up to
> date.
>
> []{#48300}
>
> About This Book
> ---------------
>
> **[Chapter 1, \"Introduction to Java Advanced
> Imaging](Introduction.doc.html#47285),\"** gives an overview of the
> Java Advanced Imaging API, how it evolved from the original Java
> Advanced Windowing Toolkit (AWT), some of its features, and introduces
> the imaging operations.
>
> **[Chapter 2, \"Java AWT Imaging](J2D-concepts.doc.html#47285),\"**
> reviews the imaging portions of the Java AWT and examines the imaging
> features of the Java 2D API.
>
> **[Chapter 3, \"Programming in Java Advanced
> Imaging](Programming-environ.doc.html#47285),\"** describes how to get
> started programming with the Java Advanced Imaging API.
>
> **[Chapter 4, \"Image Acquisition and
> Display](Acquisition.doc.html#81550),\"** describes the Java Advanced
> Imaging API image data types and the API constructors and methods for
> image acquisition and display.
>
> **[Chapter 5, \"Color Space](Color.doc.html#47285),\"** describes the
> JAI color space, transparency, and the color conversion operators.
>
> **[Chapter 6, \"Image
> Manipulation](Image-manipulation.doc.html#47285),\"** describes the
> basics of manipulating images to prepare them for processing and
> display.
>
> **[Chapter 7, \"Image Enhancement](Image-enhance.doc.html#47285),\"**
> describes the basics of improving the visual appearance of images
> through enhancement techniques.
>
> **[Chapter 8, \"Geometric Image
> Manipulation](Geom-image-manip.doc.html#51140),\"** describes the
> basics of Java Advanced Imaging\'s geometric image manipulation
> functions.
>
> **[Chapter 9, \"Image Analysis](Analysis.doc.html#55366),\"**
> describes the Java Advanced Imaging API image analysis operators.
>
> **[Chapter 10, \"Graphics Rendering](Graphics.doc.html#51143),\"**
> describes the Java Advanced Imaging presentation of shapes and text.
>
> **[Chapter 11, \"Image Properties](Properties.doc.html#47285),\"**
> describes the tools that allow a programmer to add a simple database
> of arbitrary data that can be attached to images.
>
> **[Chapter 12, \"Client-Server
> Imaging](Client-server.doc.html#47285),\"** describes Java Advanced
> Imaging\'s client-server imaging system.
>
> **[Chapter 13, \"Writing Image Files](Encode.doc.html#47285),\"**
> describes Java Advanced Imaging\'s codec system for encoding image
> data files.
>
> **[Chapter 14, \"Extending the API](Extension.doc.html#47285),\"**
> describes how the Java Advanced Imaging API is extended.
>
> **[Appendix A, \"Program Examples](Examples.doc.html#72446),\"**
> contains fully-operational Java Advanced Imaging program examples.
>
> **[Appendix B, \"Java Advanced Imaging API
> Summary](API-summary.doc.html#72446),\"** summarizes the imaging
> interfaces, and classes, including the `java.awt`, `java.awt.Image`,
> and `javax.media.jai` classes.
>
> The **[Glossary](Glossary.doc.html#47771)** contains descriptions of
> significant terms that appear in this book.
>
> []{#47778}
>
> Related Documentation
> ---------------------
>
> To obtain a good understanding of the Java programming language, we
> suggest you start with the SunSoft Press series of books:
>
> -   *Instant Java*, by John A. Pew
>
> <!-- -->
>
> -   *Java in a Nutchell: A Desktop Quick Reference*, by David Flanagan
>
> <!-- -->
>
> -   *Java by Example*, by Jerry R. Jackson and Alan L. McClellan
>
> <!-- -->
>
> -   *Just Java*, by Peter van der Linden
>
> <!-- -->
>
> -   *Core Java*, by Gary Cornell and Gay S. Horstmann
>
> <!-- -->
>
> -   *Java Distributed Computing*, by Jim Farley
>
> For more information on digital imaging, we suggest you refer to the
> following books:
>
> -   *Fundamentals of Digital Image Processing*, by Anil K. Jain
>
> <!-- -->
>
> -   *Digital Image Processing: Principles and Applications*, by
>     Gregory A. Baxes
>
> <!-- -->
>
> -   *Digital Image Processing*, by Kenneth R. Castleman
>
> <!-- -->
>
> -   *Digital Image Processing*, 2nd. ed., by William K. Pratt
>
> []{#48699}
>
> Additional Information
> ----------------------
>
> Since Java Advanced Imaging continues to evolve and periodically add
> new operators, it is always a good idea to occasionally check the
> JavaSoft JAI web site for the latest information.
>
> :   `http://java.sun.com/products/java-media/jai/`
>
> The web site contains links to the latest version of JAI, email
> aliases for obtaining help from the community of JAI developers, and a
> tutorial that includes examples of the use of many JAI operators.
>
> []{#47925}
>
> Style Conventions
> -----------------
>
> The following style conventions are used in this document:
>
> -   `Lucida type` is used to represent computer code and the names of
>     files and directories.
>
> <!-- -->
>
> -   **Bold Lucida type** is used for Java 3D API declarations.
>
> <!-- -->
>
> -   *Italic type* is used for emphasis and for equations.
>
> Throughout the book, we introduce many API calls with the following
> format:
>
> +-----------------------------------+-----------------------------------+
> | ![](shared/cistine.gif)           | -------------------------------   |
> |                                   |                                   |
> |                                   | **API:**                          |
> |                                   | `javax.media.jai.TiledImage`      |
> |                                   |                                   |
> |                                   | -------------------------------   |
> +-----------------------------------+-----------------------------------+
>
> When introducing an API call for the first time, we add a short
> summary of the methods, tagged with the API heading.
>
> ------------------------------------------------------------------------
>
> \
>
> [![Contents](shared/contents.gif)](JAITOC.fm.html)
> [![Previous](shared/previous.gif)](JAITOC.fm.html)
> [![Next](shared/next.gif)](Introduction.doc.html)
>
> *Programming in Java Advanced Imaging*
>
> \
>
> ##### [Copyright](copyright.html) © 1999, Sun Microsystems, Inc. All rights reserved.
