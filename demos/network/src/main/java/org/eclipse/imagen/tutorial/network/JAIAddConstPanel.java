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
package org.eclipse.imagen.tutorial.network;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.renderable.ParameterBlock;
import java.util.Hashtable;
import java.util.Vector;
import org.eclipse.imagen.*;
import org.eclipse.imagen.remote.RemoteJAI;
import javax.swing.*;
import javax.swing.event.*;

public class JAIAddConstPanel extends JAIDemoPanel
    implements ChangeListener, ItemListener {

    boolean zeroState = true;

    int param1 = 0;
    int param2 = 0;
    int param3 = 0;

    JSlider p1Slider;
    JSlider p2Slider;
    JSlider p3Slider;

    public JAIAddConstPanel(Vector sourceVec, RemoteJAI pClient) {
        super(sourceVec, pClient);
        masterSetup();
    }

    public String getDemoName() {
        return "AddConst";
    }


    public void makeControls(JPanel controls) {
        p1Slider = new JSlider(JSlider.HORIZONTAL, -255, 255, 0);
        p2Slider = new JSlider(JSlider.HORIZONTAL, -255, 255, 0);
        p3Slider = new JSlider(JSlider.HORIZONTAL, -255, 255, 0);

        Hashtable labels = new Hashtable();
        labels.put(new Integer(-255), new JLabel("-255"));
        labels.put(new Integer(0), new JLabel("0"));
        labels.put(new Integer(255), new JLabel("255"));
        p1Slider.setLabelTable(labels);
        p2Slider.setLabelTable(labels);
        p3Slider.setLabelTable(labels);

        p1Slider.setPaintLabels(true);
        p2Slider.setPaintLabels(true);
        p3Slider.setPaintLabels(true);
        
        p1Slider.addChangeListener(this);
        p2Slider.addChangeListener(this);
        p3Slider.addChangeListener(this);

        JPanel p1SliderPanel = new JPanel();
        p1SliderPanel.setLayout(new BoxLayout(p1SliderPanel, BoxLayout.X_AXIS));
        JLabel p1Label = new JLabel("Red");
        p1SliderPanel.add(p1Label);
        p1SliderPanel.add(p1Slider);

        JPanel p2SliderPanel = new JPanel();
        p2SliderPanel.setLayout(new BoxLayout(p2SliderPanel, BoxLayout.X_AXIS));
        JLabel p2Label = new JLabel("Green");
        p2Label.setPreferredSize(p1Label.getPreferredSize());
        p2SliderPanel.add(p2Label);
        p2SliderPanel.add(p2Slider);

        JPanel p3SliderPanel = new JPanel();
        p3SliderPanel.setLayout(new BoxLayout(p3SliderPanel, BoxLayout.X_AXIS));
        JLabel p3Label = new JLabel("Blue");
        p3Label.setPreferredSize(p1Label.getPreferredSize());
        p3SliderPanel.add(p3Label);
        p3SliderPanel.add(p3Slider);

        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));
        sliderPanel.add(p1SliderPanel);
        sliderPanel.add(p2SliderPanel);
        sliderPanel.add(p3SliderPanel);

        controls.setLayout(new BorderLayout());
        controls.add("Center", sliderPanel);
    }

    public boolean supportsAutomatic() {
        return true;
    }

    public PlanarImage process() {
        PlanarImage im = getSource(0);
 
        ParameterBlock pb = new ParameterBlock();
        pb.addSource(im);
        double consts[] = {param1, 
                           param2,
                           param3};
        pb.add(consts);
        return client.create("addconst", pb, renderHints);
    }

    public void startAnimation() {
    }

    int sliderDelta1 = 1;
    int sliderDelta2 = 1;
    int sliderDelta3 = 1;
    boolean isAutoInit = false;

    public void animate() {
        if(zeroState) {
            int delta =
                (int)((float)(p1Slider.getMaximum() -
                              p1Slider.getMinimum())/3.0F);
            p2Slider.setValue(Math.max(p2Slider.getMinimum(),
                                       p1Slider.getValue() - delta));
            p3Slider.setValue(Math.min(p3Slider.getMaximum(),
                                       p1Slider.getValue() + delta));
            zeroState = false;
        }

        int value = p1Slider.getValue();
        int newValue = value + sliderDelta1;

        if (newValue < p1Slider.getMinimum() ||
            newValue > p1Slider.getMaximum()) {
            sliderDelta1 = -sliderDelta1;
        }
        p1Slider.setValue(value + sliderDelta1);



































    }

    public void reset() {
        param1 = 0;
        param2 = 0;
        param3 = 0;
        p1Slider.setValue(param1);
        p2Slider.setValue(param2);
        p3Slider.setValue(param2);
        zeroState = true;
    }

    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        int value = source.getValue();

        if (source == p1Slider) {
             param1 = value;
        } else if (source == p2Slider) {
             param2 = value;
        } else if (source == p3Slider) {
             param3 = value;
        }
        repaint();
    }

    public void itemStateChanged(ItemEvent e) {
    }
}
