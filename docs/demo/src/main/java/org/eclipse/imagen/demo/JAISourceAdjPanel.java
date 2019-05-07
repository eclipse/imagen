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
package org.eclipse.imagen.demo;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import org.eclipse.imagen.*;
import javax.swing.*;

public abstract class JAISourceAdjPanel extends JPanel implements ActionListener {

    Vector sourceVec = null;
     /** Rendering hints to be used by subclasses. */


    protected RenderingHints renderHints = null;
    
    public JAISourceAdjPanel(Vector sourceVec) {
        this.sourceVec = (Vector)sourceVec.clone();
    }

    /**
     * Performs the standard setup.  Subclasses should call this method
     * immediately after calling super() in their constructors.
     * The main image pane is constructed and placed in a JScrollPane.
     * A control panel with a reset button is also constructed, and
     * the makeControls method of the subclass is called to fill in
     * the custom portion of the panel.
     */


    public void masterSetup() {
     
        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(this);

        controlPanel.add("West", resetButton);

        JPanel customPanel = new JPanel();
        controlPanel.add("Center", customPanel);
	JPanel adjPanel = new JPanel();
        makeControls(adjPanel);

        add("South", controlPanel);
	add("Center",adjPanel);
    }
    
    /** Returns the current source image. */


    public PlanarImage getSource(int index) {
        return (PlanarImage)sourceVec.elementAt(index);
    }

    /** Sets the source and performs processing. */


    public void setSource(int sourceNum, PlanarImage source) {
        sourceVec.setElementAt(source, sourceNum);
        repaint();
    }
    
    public void setRenderingHints(RenderingHints renderHints) {
        this.renderHints = renderHints;
	repaint();
    }
 

    /** Creates a control panel that will affect the operation parameters. */


    public void makeControls(JPanel controls) {
    }
  
    /**
     * Repaints the image panel.  The process() method is called
     * to generate a new output image, which is then wrapped by an
     * ImageIcon and set as the icon of the JLabel imageLabel.
     */


    public void repaint(){
      if (sourceVec == null) {
	return;
      }
      process();
    }
  
    /** Default method when any action is performed. */


    public void actionPerformed(ActionEvent e) {
        reset();
    }


    /** Returns the result of processing the source image. */


    public abstract PlanarImage process();

    /** Called when the reset button is pressed. */


    public void reset() {
    }
}
