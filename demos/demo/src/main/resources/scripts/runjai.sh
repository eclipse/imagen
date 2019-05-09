#!/bin/sh
#
# A script file to run the demo.
#

if [ $# = 0 ]
then
IMAGES="images/*.*"
else
IMAGES=$*
fi

java -Xmx48m JAIDemo $IMAGES
