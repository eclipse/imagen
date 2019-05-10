---
layout: default
title: Eclipse ImageN Programming Guide
---
# Eclipse ImageN Programming Guide

## A simple ImageN Program

Before proceeding any further, let's take a look at an example ImageN program to get an idea of what it looks like. The Listing below shows a simple example of a complete JAI program. This example reads an image, passed to the program as a command line argument, scales the image by 2x with bilinear interpolation, then displays the result.

**Simple Example JAI Program**

{% highlight java linenos%}
{% include src/ImageNSampleProgram.java %}
{% endhighlight %}