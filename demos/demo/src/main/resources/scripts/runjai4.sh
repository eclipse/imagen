#!/bin/sh
#
# A script file to run the demo.
#

if [ $# = 0 ]
then
IMAGE="images/the_nut.tif"
else
IMAGE=$*
fi

java -Xmx48m JAIWarpDemo $IMAGE
