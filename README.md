# ImageN

The Eclipse ImageN project provides an extensible, on-demand image processing library with no artificial
restrictions on raster size or number of bands.

ImageN provides:

* Modern Java API using literate programming style
* Pure Java implementation
* Clear image processing operations, allowing installations to use native libs to accelerate processing if available
* On demand processing of large raster content staging tiles in memory for parallel processing
* No artificial limitation on raster size or number of bands to support multi-spectral imagery

This is a [Eclipse Foundation](https://www.eclipse.org) open source project using the [Apache License v 2.0](LICENSE.md).

For more information:

* [ImageN](https://projects.eclipse.org/projects/technology.imagen) - Eclipse Project Page
* [Replace JAI](https://github.com/geotools/geotools/wiki/Replace-JAI) - GeoTools Wiki

## Maven Build

Use maven to build on the command line:

    mvn install

The build uses the `javac` compiler argument `-XDignore.symbol.file` to reference JDK codecs directly. This functionality is only available from the `javac` command line and requires maven (or your IDE) to fork each call to `javac`.

### MediaLab Profile

MediaLib integration requires `mlibwrapper_jai.jar` and is available using:

    mvn install -Pmlib

To install `mlibwrapper_jai.jar` into your local repository use:

    mvn install:install-file -Dfile=mlibwrapper_jai.jar \
        -DgroupId=javax.media -DartifactId=mlibwrapper_jai \
        -Dversion=1.1.3 -Dpackaging=jar -DgeneratePom=true