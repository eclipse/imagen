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

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.image.renderable.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import org.eclipse.imagen.*;
import org.eclipse.imagen.widgets.*;


public class Arithmetic extends JPanel
                        implements ActionListener {

    private PlanarImage src1 = null;
    private PlanarImage src2 = null;
    private ImageDisplay ic1 = null;
    private ImageDisplay ic2 = null;
    private ImageDisplay ic3 = null;
    private JButton b1;
    private JButton b2;
    private JButton b3;
    private JButton b4;

    public Arithmetic(String im1, String im2) {
        super(true);
        setLayout(new BorderLayout());

        // load files
        src1 = ReadImage.getImage(im1);
        src2 = ReadImage.getImage(im2);

        // alter mask image a bit
        Byte[] bandValues = new Byte[3];
        bandValues[0] = new Byte((byte)2);
        bandValues[1] = new Byte((byte)2);
        bandValues[2] = new Byte((byte)2);

        ParameterBlock pb = new ParameterBlock();
        pb.add((float)src2.getWidth());
        pb.add((float)src2.getHeight());
        pb.add(bandValues);
        PlanarImage temp = JAI.create("constant", pb, null);

        pb = new ParameterBlock();
        pb.addSource(src2);
        pb.addSource(temp);

        src2 = JAI.create("add", pb, null);

        ic1 = new ImageDisplay(src1);
        ic2 = new ImageDisplay(src2);
        ic3 = new ImageDisplay(src1.getWidth(),
                               src1.getHeight());

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(3,1,0,0));
        imagePanel.add(ic1);
        imagePanel.add(ic2);
        imagePanel.add(ic3);
        add(imagePanel, BorderLayout.CENTER);

        // control panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,4,4,4));
        panel.setBorder(new EmptyBorder(10,10,10,10));

        b1 = new JButton("Add");
        b2 = new JButton("Subtract");
        b3 = new JButton("Multiply");
        b4 = new JButton("Divide");

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);

        panel.add(b1);
        panel.add(b2);
        panel.add(b3);
        panel.add(b4);
        add(panel, BorderLayout.SOUTH);

        // GUI spacing
        add(new JLabel("          "), BorderLayout.WEST);
        add(new JLabel("          "), BorderLayout.EAST);
    }

    public void actionPerformed(ActionEvent e) {
        PlanarImage target = null;
        JButton b = (JButton)e.getSource();
        ParameterBlock pb = new ParameterBlock();
        pb.addSource(src1);
        pb.addSource(src2);

        if ( b == b1 ) {
            target = JAI.create("add", pb, null);
        } else if ( b == b2 ) {
            target = JAI.create("subtract", pb, null);
        } else if ( b == b3 ) {
            target = JAI.create("multiply", pb, null);
        } else if ( b == b4 ) {
            target = JAI.create("divide", pb, null);
        }

        ic3.set(target);
    }
}
