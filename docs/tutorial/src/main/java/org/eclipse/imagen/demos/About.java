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

import java.io.File;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.*;
import org.eclipse.imagen.JAI;
import org.eclipse.imagen.PlanarImage;
import org.eclipse.imagen.widgets.*;

public class About extends JPanel {

    private PlanarImage source = null;

    public About(String filename) {
        File f = new File(filename);

        if ( f.exists() && f.canRead() ) {
            source = JAI.create("fileload", filename);
        } else {
            return;
        }

        ImageDisplay canvas = new ImageDisplay(source);
        canvas.setBackground(Color.blue);

        canvas.setBorder(new CompoundBorder(
                            new EtchedBorder(),
                            new LineBorder(Color.gray, 20)
                        ) );

        Font font = new Font("SansSerif", Font.BOLD, 12);
        JLabel title = new JLabel(" Use the mouse to position magnifier.");
        title.setFont(font);
        title.setLocation(0, 32);

        setOpaque(true);
        setLayout(new BorderLayout());
        setBackground(Color.white);

        add(title,  BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);

        JLabel label = new JLabel(" Magnifier");
        label.setForeground(Color.white);

        Magnifier mag = new Magnifier();
        mag.setSource(canvas);
        mag.setMagnification(3.0F);
        mag.setSize(128, 128);
        mag.setLocation(150, 150);
        mag.setBorder(new LineBorder(Color.white,1));
        mag.setLayout(new BorderLayout());

        mag.add(label, BorderLayout.NORTH);
        canvas.add(mag);
    }
}
