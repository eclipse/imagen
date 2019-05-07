# Multi-Panel Viewer (MPV) Demo for ImageN

This is a demo for loading, displaying, scrolling, and manipulating large
images using ImageN.

0) System requirements

This is a pure java appilication that runs on any platform with Java 8 or later.

Compile using the command:

    mvn install

Then following the instructions below to run this demo.

1) To start

This demo can be run with 1, 2, or 4 image pairs.  The image pairs are high and low 
resolution images of the same scene.

1.a) Single image case (two input images, one for primary view another for overview):

   mvn exec:java -Dexec.mainClass="org.eclipse.imagen.demo.mpv.MPVDemo" <PrimaryImage> <OverviewImage>
   java -jar mpvrun.jar <PrimaryImage> <OverviewImage>

```
    ------------   ---------   [] panner graphic indicating Primary view location
    |          |   |  []   |
    |          |   | Over- |   -- The overview is accessible using the popup menu
    | Primary  |   |  view |   -- The panner can be click-and-dragged to change views
    |  View    |   ---------   -- Left-mouse clicks in Primary View cause "translate
    |          |                  to center" image operations
    |          |               -- Mouse clicks in the Overview cause the panner to
    ------------                  move to the clicked location
                               -- Right mouse click in a view to expose popup menu
```

1.b) Double image case (four input images):

 java MPVDemo <PrimaryImage0> <OverviewImage0> <PrimaryImage1> <OverviewImage1>

```
 * 
    ------------------------  Double image case has same capabilities as single image case
    |          ||          |  and includes (eg., each Primary View has an Overview, etc.):
    |          ||          |  -- The divider between views can be click-and-dragged
    | Primary  || Primary  |    (split pane)
    |  View0   ||  View1   |  -- popup exposed in View1 for Overview1, etc.
    |          ||          |
    |          ||          |
    ------------------------
 * 
```

1.c) Four image case (eight input images):

 java MPVDemo <PrimaryImage0> <OverviewImage0> <PrimaryImage1> <OverviewImage1> \
              <PrimaryImage2> <OverviewImage2> <PrimaryImage3> <OverviewImage3>

```
 * 
    ------------------------  Four image case has same capabilities as double image case
    |          ||          |  and includes:
    |          ||          |  -- Horizontal and vertical dividers between views can
    | Primary  ||  Primary |     be click-and-dragged (split panes)
    |  View0   ||   View1  |
    |          ||          |
    |          ||          |
    ========================
    |          ||          |
    |          ||          |
    | Primary  || Primary  |
    |  View2   ||  View3   |
    |          ||          |
    |          ||          |
    ------------------------
```

2.) Further notes on operation

2.a) The mapping tool widget looks roughly like:

```

  -----------------------------
  | <---slider---------+++->  |
  -----------------------------
  | ----------------------| ^ |
  | |                   *-| s |
  | |                  /  | l |
  | |                 /   | i |
  | |                /    | d |
  | |  Mapping      /     | e |
  | |  Tool        /      | r |
  | |  Widget     /       | | |
  | |            /        | + |
  | |          *          | + |
  | |        /            | + |
  | |      /              | | |
  | |    /                | | |
  | |---*                 | | |
  | ----------------------| v |
  -----------------------------
  | <--++-+--------slider-->  |
  -----------------------------
  |<Invt> <Rset> <Help> <Hide>|
  -----------------------------

```

The window level widget (class MappingToolWidget) is actually not a true window level operation.
It is a hybrid piecewise linear and window level operation.  The sliders on the top, bottom, and
side change the locations of the top point, center point, and bottom point, respectively.  Think
of the x-axis as representing input intensities and the y-axis as representing output mapped
intensities.  The buttons at the bottom are used to invert the curve (reverse video), reset the
curve, offer some help, and to hide the widget.

2.b) Resizing the displays by using the split panes is useful for comparing
two or more images.

2.c) A sharpening widget can be activated using the popup in any of the 
primary view displays.  Sliding the slider to the left blurs, to the right
sharpens.
