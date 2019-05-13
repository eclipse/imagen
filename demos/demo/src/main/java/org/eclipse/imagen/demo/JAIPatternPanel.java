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
import java.awt.image.*;
import java.awt.image.renderable.ParameterBlock;
import java.util.Hashtable;
import java.util.Vector;
import org.eclipse.imagen.*;
import javax.swing.*;
import javax.swing.event.*;

public class JAIPatternPanel extends JAIDemoPanel
    implements ChangeListener {

    JSlider widthSlider;
    JSlider heightSlider;
    int width = 200;
    int height = 200;

    PlanarImage source;

    public JAIPatternPanel(Vector sourceVec) {
        super(sourceVec);
        
        source = getSource(0);
        masterSetup();
    }

    public String getDemoName() {
        return "Pattern";
    }

    public void makeControls(JPanel controls) {
        widthSlider = new JSlider(JSlider.HORIZONTAL, 0, 1000, 200);
        heightSlider = new JSlider(JSlider.HORIZONTAL, 0, 1000, 200);

        widthSlider.setMajorTickSpacing(100);
        widthSlider.setPaintTicks(true);
        widthSlider.setPaintLabels(true);

        heightSlider.setMajorTickSpacing(100);
        heightSlider.setPaintTicks(true);
        heightSlider.setPaintLabels(true);
        
        widthSlider.addChangeListener(this);
        heightSlider.addChangeListener(this);

        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.X_AXIS));

        JLabel widthLabel = new JLabel("Width");
        sliderPanel.add(widthLabel);
        sliderPanel.add(widthSlider);

        JLabel heightLabel = new JLabel("Height");
        sliderPanel.add(heightLabel);
        sliderPanel.add(heightSlider);

        controls.setLayout(new BorderLayout());
        controls.add("Center", sliderPanel);
    }

    public void setSource(int sourceNum, PlanarImage source) {
        if (sourceNum == 0) {
            this.source = source;
        }
        super.setSource(sourceNum, source);
    }

    public boolean supportsAutomatic() {
        return true;
    }

    public PlanarImage process() {
        ParameterBlock pb = new ParameterBlock();
        pb.addSource(source);
        pb.add(width);
        pb.add(height);
        return JAI.create("pattern", pb, renderHints);
    }

    public void startAnimation() {
    }

    int widthSliderDelta = 20;
    int heightSliderDelta = 20;

    public void animate() {
        int value = widthSlider.getValue();
        int newValue = value + widthSliderDelta;

        if (newValue < widthSlider.getMinimum() ||
            newValue > widthSlider.getMaximum()) {
            widthSliderDelta = -widthSliderDelta;
        }
        widthSlider.setValue(value + widthSliderDelta);

        value = heightSlider.getValue();
        newValue = value + heightSliderDelta;

        if (newValue < heightSlider.getMinimum() ||
            newValue > heightSlider.getMaximum()) {
            heightSliderDelta = -heightSliderDelta;
        }
        heightSlider.setValue(value + heightSliderDelta);
    }

    public void reset() {
        width = height = 200;
        widthSlider.setValue(width);
        heightSlider.setValue(height);
    }

    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider)e.getSource();
        if (slider == widthSlider) {
            width = slider.getValue();
            if (width == 0) {
                width = 1;
            }
        } else {
            height = slider.getValue();
            if (height == 0) {
                height = 1;
            }
        }
        repaint();
    }
}