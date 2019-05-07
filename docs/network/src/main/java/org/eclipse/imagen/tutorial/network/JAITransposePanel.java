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
package org.eclipse.imagen.demos.network;

import java.awt.*;
import java.awt.image.renderable.ParameterBlock;
import java.awt.event.*;
import java.util.Vector;
import org.eclipse.imagen.*;
import org.eclipse.imagen.operator.TransposeDescriptor;
import org.eclipse.imagen.remote.RemoteJAI;
import javax.swing.*;
import javax.swing.event.*;

public class JAITransposePanel extends JAIDemoPanel implements ItemListener {

    JComboBox box;
    EnumeratedParameter type = null;
    String[] labels = { "Original Image",
                        "FLIP_VERTICAL",
                        "FLIP_HORIZONTAL",
                        "FLIP_DIAGONAL",
                        "FLIP_ANTIDIAGONAL",
                        "ROTATE_90",
                        "ROTATE_180",
                        "ROTATE_270"
    };

    EnumeratedParameter[] transposeTypes = {
        null,
        TransposeDescriptor.FLIP_VERTICAL,
        TransposeDescriptor.FLIP_HORIZONTAL,
        TransposeDescriptor.FLIP_DIAGONAL,
        TransposeDescriptor.FLIP_ANTIDIAGONAL,
        TransposeDescriptor.ROTATE_90,
        TransposeDescriptor.ROTATE_180,
        TransposeDescriptor.ROTATE_270
    };


    public JAITransposePanel(Vector sourceVec, RemoteJAI pClient) {
        super(sourceVec, pClient);
        masterSetup();
    }

    public String getDemoName() {
        return "Transpose";
    }

    public void makeControls(JPanel controls) {
        box = new JComboBox();
        for (int i = 0; i < labels.length; i++) {
            box.addItem(labels[i]);
        }

        box.addItemListener(this);
        controls.add(box);
    }

    public boolean supportsAutomatic() {
        return true;
    }

    public PlanarImage process() {
        PlanarImage im = getSource(0);

        if (type == null) {
            // Original image
            return im;
        } else {
            // Transpose operation
            ParameterBlock pb = new ParameterBlock();
            pb.addSource(im);
            pb.add(type);
            return client.create("transpose", pb, renderHints);
        }
    }

    public void startAnimation() {
    }

    public void animate() {
        int current = box.getSelectedIndex() + 1;

        if ( current >= labels.length ) {
            current = 0;
        }

        box.setSelectedIndex(current);
    }

    public void reset() {
        box.setSelectedIndex(0);
        type = null;
    }

    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.DESELECTED) {
            return;
        }

        for (int i = 0; i < labels.length; i++) {
            if (e.getItem().equals(labels[i])) {
                type = transposeTypes[i];
                break;
            }
        }
        
        repaint();
    }
}
