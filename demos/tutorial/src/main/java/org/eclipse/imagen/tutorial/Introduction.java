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
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.*;
import org.eclipse.imagen.JAI;
import org.eclipse.imagen.widgets.*;
import org.eclipse.imagen.PlanarImage;

public class Introduction extends JPanel {

    private PlanarImage source = null;

    public Introduction(String filename) {
        File f = new File(filename);

        if ( f.exists() && f.canRead() ) {
            source = JAI.create("fileload", filename);
        } else {
            return;
        }

        ImageDisplay canvas = new ImageDisplay(source);
        canvas.setBackground(Color.blue);
        canvas.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        canvas.setBorder(new CompoundBorder(
                            new EtchedBorder(),
                            new LineBorder(Color.gray, 20)
                        ) );

        Font font = new Font("SansSerif", Font.BOLD, 12);
        JLabel title = new JLabel(" Use left mouse button to adjust contrast, middle to reset.");
        title.setFont(font);
        title.setLocation(0, 32);

        setOpaque(true);
        setLayout(new BorderLayout());
        setBackground(Color.white);

        // add window-level widget
        int borderWidth = 1;
        int width  = 128 + 2*borderWidth;
        int height = 128 + 2*borderWidth;

        Contrast contrast = new Contrast(source,
                                         width,
                                         height);

        // these next 2 calls are order dependent!!!
        contrast.setDisplay(canvas);
        contrast.setSliderLimits(0.0, 256.0, 0.0, 2.0*256.0);
        contrast.setSliderLocation(width/2, height/2);

        contrast.setBorder(new LineBorder(Color.white,
                                          borderWidth));

        contrast.setBackground(Color.black);
        contrast.setSliderColor(Color.green);
        contrast.setSliderOpaque(true);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        panel.add(new JLabel(" Window-Level"),
                             BorderLayout.NORTH);

        panel.add(contrast);
        canvas.add(panel, BorderLayout.NORTH);

        add(title,  BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);

        // set and label contrast widget axes
        contrast.setLayout(new BorderLayout());
        JLabel jx1 = new JLabel("0");
        JLabel jx2 = new JLabel("L");
        JLabel jy1 = new JLabel("0");
        JLabel jy2 = new JLabel("W");

        jx1.setForeground(Color.white);
        jx2.setForeground(Color.white);
        jy1.setForeground(Color.white);
        jy2.setForeground(Color.white);

        JPanel ptop = new JPanel();
        ptop.setBackground(Color.black);
        ptop.setLayout(new FlowLayout(FlowLayout.CENTER));
        ptop.add(jy1);

        JPanel pbot = new JPanel();
        pbot.setBackground(Color.black);
        pbot.setLayout(new FlowLayout(FlowLayout.CENTER));
        pbot.add(jy2);

        contrast.add(jx1, BorderLayout.WEST);
        contrast.add(jx2, BorderLayout.EAST);
        contrast.add(ptop, BorderLayout.NORTH);
        contrast.add(pbot, BorderLayout.SOUTH);
    }
}
