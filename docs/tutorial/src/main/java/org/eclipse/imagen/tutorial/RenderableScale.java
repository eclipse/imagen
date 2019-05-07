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

import java.util.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.image.renderable.*;
import javax.swing.*;
import javax.swing.event.*;
import org.eclipse.imagen.*;
import org.eclipse.imagen.widgets.*;


public class RenderableScale extends JPanel
                             implements ChangeListener {

    private RenderableDisplay canvas = null;
    private final int width  = 185;
    private final int height = 173;

    public RenderableScale(String base) {

        Vector sources = new Vector();

        for (int i = 3; i >= 0; i-- ) {
            int ext = i + 1;
            String file = base + ext + ".jpg";
            RenderedImage im = JAI.create("fileload", file);
            sources.addElement(im);
        }

        RenderableImage r = new MultiResolutionRenderableImage(sources,
                                                               0.0F,
                                                               0.0F,
                                                               1.0F);

        setLayout(new BorderLayout());

        JLabel label = new JLabel("Scalable Image");
        add(label, BorderLayout.NORTH);

        canvas = new RenderableDisplay(r);
        add(canvas, BorderLayout.CENTER);
        setVisible(true);

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 30, 30);
        slider.addChangeListener(this);

        JPanel borderedPanel = new JPanel();
        borderedPanel.setLayout(new BorderLayout());
        borderedPanel.setBorder(BorderFactory.createTitledBorder("Zoom"));
        borderedPanel.add(slider, BorderLayout.NORTH);

        add(borderedPanel, BorderLayout.SOUTH);

        canvas.setSize(new Dimension(18, 17));
    }

    public final void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        int scale = slider.getValue();

        int nw = (scale * width) / 10;
        int nh = (scale * height) / 10;

        canvas.setSize(new Dimension(nw, nh));
    }
}
