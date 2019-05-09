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
 * A output widget used to display a colormap, derived from the
 * javax.swing.JComponent, and can be used in any context that calls for a
 * JComponent.
 *
 * @author Dennis Sigel
 */

public class Colorbar extends JComponent {

    protected int componentWidth;
    protected int componentHeight;
    protected int direction = SwingConstants.HORIZONTAL;

    /** Brightness control */
    protected byte[][] lut;

    /**
     * Default constructor
     */
    public Colorbar() {
        lut = new byte[3][256];

        for ( int i = 0; i < 256; i++ ) {
            lut[0][i] = (byte) i;
            lut[1][i] = (byte) i;
            lut[2][i] = (byte) i;
        }
    }

    /** 
     * Constructs a Colorbar object 
     *
     * @param d used for orientation
     */
    public Colorbar(int d) {
        lut = new byte[3][256];

        for ( int i = 0; i < 256; i++ ) {
            lut[0][i] = (byte) i;
            lut[1][i] = (byte) i;
            lut[2][i] = (byte) i;
        }

        direction = d;
    }

    /** changes the contents of the lookup table */
    public synchronized void setLut(byte[][] newlut) {
        for ( int i = 0; i < newlut[0].length; i++ ) {
            lut[0][i] = newlut[0][i];
            lut[1][i] = newlut[1][i];
            lut[2][i] = newlut[2][i];
        }

        repaint();
    }

    /** Records a new size.  Called by the AWT. */
    public void setBounds(int x, int y, int width, int height) {
        componentWidth  = width;
        componentHeight = height;
        super.setBounds(x, y, width, height);
    }

    /**
     * Paint the Colorbar onto a Graphics object.
     */
    public synchronized void paintComponent(Graphics g) {

        Graphics2D g2D = null;
        if (g instanceof Graphics2D) {
            g2D = (Graphics2D)g;
        } else {
            return;
        }

        g2D.setColor(getBackground());
        g2D.fillRect(0, 0, componentWidth, componentHeight);

        if ( direction == SwingConstants.HORIZONTAL ) {
            float slope = (float)componentWidth / 256.0F;

            for ( int n = 0; n < lut[0].length; n++ ) {
                int w = componentWidth - (int)((float)n*slope);
                int v = lut[0].length - n - 1;
                int red   = lut[0][v]&0xFF;
                int green = lut[1][v]&0xFF;
                int blue  = lut[2][v]&0xFF;
                g.setColor(new Color(red, green, blue));
                g.fillRect(0, 0, w, componentHeight);
            }
        } else if ( direction == SwingConstants.VERTICAL ) {
            float slope = (float)componentHeight / 256.0F;

            for ( int n = 0; n < lut[0].length; n++ ) {
                int h = componentHeight - (int)((float)n*slope);
                int red   = lut[0][n]&0xFF;
                int green = lut[1][n]&0xFF;
                int blue  = lut[2][n]&0xFF;
                g.setColor(new Color(red, green, blue));
                g.fillRect(0, 0, componentWidth, h);
            }
        }
    }
}
