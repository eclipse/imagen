/*
 * Copyright (c) 2019, Oracle and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.eclipse.imagen.demo.medical;

/**
 * An interface contains the public constants used in this application.
 *
 */



interface MedicalAppConstants {
    // Currently the zoom slider is not linear, so the position for
    // zoom factor 1.0 is defined in the property file.
    int nozoom =
	(new Integer(JaiI18N.getString("NoZoomTickPosition")).intValue() -1);

    // The command name and/or message names
    String setLayoutCommand	    = "SetLayout";
    String allViewsCommand	    = "AllViews";
    String currentViewCommand	    = "CurrentView";
    String zoomCommand		    = "zoom";
    String rotationCommand	    = "rotation";
    String speedCommand		    = "cinespeed";
    String startCommand		    = "cinestart";
    String stopCommand		    = "cinestop";
    String windowCommand	    = "window";
    String levelCommand		    = "level";
    String annotationCommand	    = "anotation";
    String measurementCommand	    = "measurement";
    String statisticsCommand	    = "statistics";
    String histogramCommand	    = "histogram";
    String paramSync		    = "paramSync";

    // The default values for window/level.
    int smallestWindow		    = 0;
    int largestWindow		    = 4096;
    int defaultWindow		    = 700;
    int smallestLevel		    = 0;
    int largestLevel		    = 4096;
    int defaultLevel		    = 300;
}
