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
package org.eclipse.imagen.widgets;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.geom.*;
import org.eclipse.imagen.*;
import javax.swing.*;

/**
 * A class to plot histograms (primarily)
 * Single band per object (bar chart)
 *
 * @author Dennis Sigel
 */

public class XYPlot extends JComponent {

    private int[] data;
    private int max;

   /**
    * Default constructor
    */
    public XYPlot() {
        super();
    }

    private void setData(int[] array) {
        data = new int[array.length];
        max = -1;

        for ( int i = 0; i < array.length; i++ ) {
            data[i] = array[i];

            if ( data[i] > max ) {
                max = data[i];
            }
        }
    }

    public void plot(int[] array) {
        setData(array);
        repaint();
    }

    /**
     * Plotter
     */
    public synchronized void paintComponent(Graphics g) {

        Graphics2D g2D = null;
        if (g instanceof Graphics2D) {
            g2D = (Graphics2D)g;
        } else {
            return;
        }

        if ( data == null ) return;

        int width = getSize().width;
        int height = getSize().height;

        g2D.setColor(getBackground());
        g2D.fillRect(0, 0, width, height);
        g2D.setColor(Color.white);

        int length = data.length;
        float slope_x = (float) width / (float) length;
        float slope_y = (float) height / (float) max;

        for ( int i = 0; i < length; i++ ) {
           int x = (int) ((float)i*slope_x);
           int y = (int) ((float)data[i]*slope_y);
           g.drawLine(x, height, x, height - y);
        }
    }
}
