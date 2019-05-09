# Medical Image Demo for ImageN

This is a demo for loading, displaying and manipulating the medical image
using ImageN. In this demo, a group of functions including layout, operation
scope, transform, window/level, annotation/measurement, and 
statistics/histogram are demonstrated.

This demo is built using maven:

   mvn install

To run you may use maven:

   mvn exec:java -Dexec.mainClass="org.eclipse.imagen.demo.medical.MedicalApp"

The application jar is executable:

1. To start

To start the medical image demo for JAI, use command:

    java -Xmx100m -jar target/imagen-medical-*.jar

in the working directory. 

2. Data set

This demo loads DICOM image data set.  Each image data set should be stored in
an individual directory.  To download DICOM image data sets to test this 
application, please refer to:

ftp://ftp.erl.wustl.edu/pub/dicom/images/version3/RSNA96/.

The DICOM reader in this application is very simple so it may not load all
DICOM image data sets.  For example, it supports only little-endian, 12-bit
DICOM image.  The user may modify the DICOM reader to extend its
capability to fit their own needs.  If the user would like to use the data
sets at the above link to test this demo, suggest to use the GE data sets 
at the above ftp address which are the basic data sets the author used to 
develop this demo.

To load a data set, choose the menu item "Open" in the "File" menu, or single
click on the "Open" icon in the tool bar or press "Ctrl-O" to pop the 
FileChooser dialog up.  Browse through the directory hirarchy to locate the
directory for the data set, single click to highlight the directory, then 
press the "Open" button in the FileChooser dialog to start the loading.

After loading, the default layout is 4-view layout.  Thus four images
are displayed in a 2 x 2 grid layout and a utility menu is located at 
the right of the frame.

3. Layout

The user may change the layout by choosing one of the layouts in the pull-down 
combo list after the label "Layout" in the "General" sub-menu.  Also, the user 
may add new layout by defining them in the property file.

4. Change operation scope

The user may toggle between "AllViews" and "CurrentView" to choose the 
operation scope.  If the "AllViews" is chosen, the operations will be 
implemented on all the displayed images.  If the "CurrentView" is chosen, 
the operations will be only implemented on the currently focused image.

5. Transform

The user may change the zoom factor and the rotation angle using the sliders 
located in the transform sub-menu.  All the images in the operation scope will 
be zoomed or rotated.

6. Cining-loop

Cining will allow the user to quickly browsing through all the images in a 
data set. After turn the radio button "start" on, it begins to cine through 
the data set.  Choose "stop" to stop the cining-loop.  The user may change 
the cine speed by changing the number in the text field.  This speed is 
defined in frame-per-second.  The program will try its best to reach and 
keep the user-defined frame rate if possible.

7. Window/Level

The user may change the window/level for the images in the operation scope 
by sliding the window/level sliders.

8. Annotation/Measurement

To annotate on the current image, turn on the "Annotation" toggle button, 
click the left mouse button to choose the start point, then type the words
on the screen and end it with "Return".  Before "Return" is typed, the user 
may re-anchor the string by single clicking the left mouse button.  In this 
demo, the "Annotation" is transient.  When the user turns off the "Annotation" 
toggle button, the annotations will be cleaned.

After turns on the "Measurement" toggle button, the user may measure the 
distance between any two points by dragging the mouse from one point to the 
other.  The distance in mm is reported at the tail of the line.  Also, this 
measurement is transient.

9. Statistics

After turns on the "Statistics" button, the user may do statistics on a ROI 
defined by mouse dragging.  A table containing the area, width, height, 
minimum pixel value, maximum pixel value, standard deviation and entropy of 
this ROI is listed in a table shown in a new window.

10. Histogram

Similar to Statistics function, the histogram of the ROI is shown in a
separate window.  The color of each bar is the pixel color on the screen
after window/level.

11. Internationalization 

All the strings are defined in the property file.  By providing new property
files in the desired locale, the users may easily to use this demo in their
mother language.  The gap between the interface components are also adjustable
by changing the proper properties defined in the property file.

