#
# Copyright (c) 2019, Oracle and/or its affiliates. All rights reserved.
# 
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
#   http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

SRCS1 = \
	JAIDemo.java \
	JAIDemoPanel.java \
	JAIAddConstPanel.java \
	JAIBlurSharpPanel.java \
	JAIConvolvePanel.java \
	JAIGradientPanel.java \
	JAIMedianPanel.java \
	JAIPatternPanel.java \
	JAIRotatePanel.java \
	JAISamplePanel.java \
	JAIScalePanel.java \
	JAITransposePanel.java \
	JAIDyadicPanel.java             \
	JAIDyadicAddPanel.java \
	JAIDyadicDividePanel.java \
	JAIDyadicMultiplyPanel.java \
	JAIDyadicSubtractPanel.java \
	JAIDyadicSource0Panel.java \
	JAIDyadicSource1Panel.java \
	JAISourceAdjPanel.java\
	JAIOpPanel.java \
	JAIImageReader.java \
	SampleDescriptor.java \
	IconJAI.java

SRCS2 = \
        SampleDescriptor.java \
	JAIImageReader.java \
	ImageCanvas.java \
        JAIExampleApp.java

SRCS3 = \
        JAIFileBrowser.java \
	JAIImageReader.java \
        IconJAI.java

SRCS4 = \
        JAIWarpDemo.java


all:  JAIDemo JAIExampleApp JAIFileBrowser JAIWarpDemo

JAIDemo: 
	javac  ${SRCS1}

JAIExampleApp: 
	javac ${SRCS2}

JAIFileBrowser: $(CLASS3)
	javac ${SRCS3}

JAIWarpDemo: 
	javac ${SRCS4}

clean:
	/bin/rm -f *.class 
