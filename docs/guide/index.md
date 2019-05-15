---
layout: default
title: Programming Guide
nav_order: 4
has_children: true
has_toc: false
permalink: /guide/
---
# Eclipse ImageN Programming Guide

* [Preface](preface/index.html)

Contents:

1. [Introduction](introduction)

2. [Java AWT Imaging](j2d-concepts)

3. [Programming Environment](programming-environment)

4. [Image Acquisition and Display](acquisition)

5. [Color Space](color)

6. [Image Manipulation](image-manipulation)

7. [Image Enhancement](image-enhancement)

8. [Geometric Image Manipulation](geom-image-manip)

9. [Image Analysis](analysis)

10. [Graphics Rendering](graphics)

11. [Image Properties](properties)

12. [Client Server Imaging](client-server)

13. [Writing Image Files](encode)

14. [Extending the API](extension)

Appendix:

* [Programming Examples](examples)

* [API Summary](api-summary)

## A simple ImageN Program

Before proceeding any further, let's take a look at an example ImageN program to get an idea of what it looks like. The Listing below shows a simple example of a complete JAI program. This example reads an image, passed to the program as a command line argument, scales the image by 2x with bilinear interpolation, then displays the result.

**Simple Example JAI Program**

{% highlight java linenos%}
{% include src/ImageNSampleProgram.java %}
{% endhighlight %}