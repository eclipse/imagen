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
import java.awt.image.renderable.ParameterBlock;
import java.awt.image.renderable.RenderedImageFactory;
import java.util.Hashtable;
import java.util.Vector;
import org.eclipse.imagen.*;
import javax.swing.*;
import javax.swing.event.*;

public class JAIBlurSharpPanel extends JAIDemoPanel
    implements ChangeListener, ItemListener {

    int param1 = 150;
    int sliderDelta = 4;

    JSlider p1Slider;

    public JAIBlurSharpPanel(Vector sourceVec) {
        super(sourceVec);
        masterSetup();
    }

    public String getDemoName() {
        return "BlurSharp";
    }

    public void makeControls(JPanel controls) {
        p1Slider = new JSlider(JSlider.HORIZONTAL, 0, 300, 150);

        Hashtable labels = new Hashtable();
        labels.put(new Integer(0), new JLabel("Blurrier"));
	labels.put(new Integer(150), new JLabel("Normal"));
        labels.put(new Integer(300), new JLabel("Sharper"));
        p1Slider.setLabelTable(labels);
        p1Slider.setPaintLabels(true);
        
        p1Slider.addChangeListener(this);

        JPanel p1SliderPanel = new JPanel();
        p1SliderPanel.setLayout(new BoxLayout(p1SliderPanel,
                                              BoxLayout.X_AXIS));
        JLabel p1Label = new JLabel("Sharpness");
        p1SliderPanel.add(p1Label);
        p1SliderPanel.add(p1Slider);

        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));
        sliderPanel.add(p1SliderPanel);

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
        float kData[] = new float[9];

        float alpha;
        if (param1 > 150) {
            alpha = (param1-125.0f)/25.0f;
        } else {
            alpha = param1/150.0f;
        }
        float beta = (1.0f-alpha)/8.0f;
        for (int i = 0; i < 9; i++) {
            kData[i] = beta;
        }
        kData[4] = alpha;
 
        KernelJAI k = new KernelJAI(3,3,1,1,kData);
        pb.add(k);
        return JAI.create("convolve", pb, renderHints);
    }

    public void startAnimation() {
    }

    public void animate() {
        int value = p1Slider.getValue();
        int newValue = value + sliderDelta;

        if (newValue < p1Slider.getMinimum() ||
            newValue > p1Slider.getMaximum()) {
            sliderDelta = -sliderDelta;
        }
        p1Slider.setValue(value + sliderDelta);
    }

    public void reset() {
        param1 = 150;
        p1Slider.setValue(param1);
    }

    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        int value = source.getValue();

        if (source == p1Slider) {
             param1 = value;
        }
        repaint();
    }

    public void itemStateChanged(ItemEvent e) {
    }
}
