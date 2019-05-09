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
package org.eclipse.imagen.tutorial;

import java.io.File;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.renderable.*;
import javax.swing.*;
import javax.swing.border.*;
import org.eclipse.imagen.*;
import org.eclipse.imagen.widgets.*;



public class RenderableDemo extends JPanel
                            implements ActionListener {

    private PlanarImage source = null;
    private ImageDisplay canvas = null;
    private boolean state = false;

    public RenderableDemo(String filename) {
        File f = new File(filename);

        if ( f.exists() && f.canRead() ) {
            source = JAI.create("fileload", filename);
        } else {
            return;
        }

        ImageDisplay display = new ImageDisplay(source);
        display.setLayout(new FlowLayout(FlowLayout.RIGHT, 2, 2));

        Panner panner = new Panner(display, source, 128);
        panner.setBackground(Color.white);
        panner.setBorder(new EtchedBorder());
        display.add(panner);

        ParameterBlock pb = new ParameterBlock();
        pb.addSource(source);
        pb.add(null).add(null).add(null).add(null).add(null);

        RenderableImage ren = JAI.createRenderable("renderable", pb);

        pb = new ParameterBlock();
        pb.addSource(ren);

        RenderableImage inv = JAI.createRenderable("invert", pb);

        int w = source.getWidth()/4;
        int h = source.getHeight()/4;

        PlanarImage dst = (PlanarImage)inv.createScaledRendering(w, h, null);

        canvas = new ImageDisplay(dst);
        canvas.setBackground(Color.blue);

        canvas.setBorder(new CompoundBorder(
                                           new EtchedBorder(),
                                           new LineBorder(Color.gray, 5)
                                           ) );

        JPanel yap = new JPanel();  // yet another panel
        yap.setLayout(new BorderLayout());
        yap.add(new JLabel("Destination"), BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 6));
        panel.add(canvas);

        // space the button
        JLabel spacer = new JLabel("      ");
        panel.add(spacer);

        JButton button = new JButton("Invert/Scale");
        button.setMargin(new Insets(2, 5, 2, 5));
        panel.add(button);

        button.addActionListener(this);

        Font font = new Font("SansSerif", Font.BOLD, 12);
        JLabel title = new JLabel(" Renderable Imaging Source");
        title.setFont(font);
        title.setLocation(0, 32);

        setOpaque(true);
        setLayout(new BorderLayout());
        setBackground(Color.white);

        yap.add(panel,   BorderLayout.CENTER);
        add(title,   BorderLayout.NORTH);
        add(display, BorderLayout.CENTER);
        add(yap,   BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
        RenderableImage inv;

        ParameterBlock pb = new ParameterBlock();
        pb.addSource(source);
        pb.add(null).add(null).add(null).add(null).add(null);

        RenderableImage ren = JAI.createRenderable("renderable", pb);

        pb = new ParameterBlock();
        pb.addSource(ren);

        if ( state == false ) {
            inv = ren;
            state = true;
        } else {
            inv = JAI.createRenderable("invert", pb);
            state = false;
        }

        int w = source.getWidth()/4;
        int h = source.getHeight()/4;

        PlanarImage dst = (PlanarImage)inv.createScaledRendering(w, h, null);
        canvas.set(dst);
    }
}
