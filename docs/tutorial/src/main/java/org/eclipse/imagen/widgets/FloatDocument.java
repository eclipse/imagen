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
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;


public class FloatDocument extends PlainDocument {

    public void insertString(int offset,
                             String s,
                             AttributeSet as) throws BadLocationException {

        if ( s == null || s.length() == 0 ) {
            return;
        }

        try {
            if ( s.equals(".") == false && s.equals("-") == false ) {
                try {
                    Integer.parseInt(s);
                } catch( NumberFormatException e ) {
                    Float.parseFloat(s);
                }
            }
        } catch( Exception e ) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }

        super.insertString(offset, s, as);
    }
}
