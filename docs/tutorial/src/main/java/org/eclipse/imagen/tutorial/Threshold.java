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
package org.eclipse.imagen.demos;

import java.util.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.image.renderable.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import org.eclipse.imagen.*;
import org.eclipse.imagen.widgets.*;


public class Threshold extends JPanel
                       implements ChangeListener {

    private PlanarImage source = null;
    private PlanarImage target = null;
    private ImageDisplay display = null;
    private double low[];
    private double high[];
    private double map[];

    public Threshold(String filename) {

        setLayout(new BorderLayout());

        add(new JLabel("CT Scan"), BorderLayout.NORTH);

        source = JAI.create("fileload", filename);

        display = new ImageDisplay(source);
        add(display, BorderLayout.CENTER);

        low  = new double[1];
        high = new double[1];
        map  = new double[1];

        low[0]  = 0.0F;
        high[0] = 0.0F;
        map[0]  = 0.0F;

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
        Hashtable labels = new Hashtable();
        labels.put(new Integer(0), new JLabel("0"));
        labels.put(new Integer(255), new JLabel("255"));
        slider.setLabelTable(labels);
        slider.setPaintLabels(true);
        slider.addChangeListener(this);
        slider.setEnabled(true);

        JPanel borderedPane = new JPanel();
        borderedPane.setLayout(new BorderLayout());
        borderedPane.setBorder(BorderFactory.createTitledBorder("Threshold"));
        borderedPane.add(slider, BorderLayout.NORTH);

        add(borderedPane, BorderLayout.SOUTH);
    }

    public final void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider)e.getSource();
        high[0] = (double) slider.getValue();

        ParameterBlock pb = new ParameterBlock();
        pb.addSource(source);
        pb.add(low);
        pb.add(high);
        pb.add(map);
        target = JAI.create("threshold", pb, null);
        display.set(target);
    }
}
