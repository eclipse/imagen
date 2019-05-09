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
package org.eclipse.imagen.demo.mpv;
 
import java.awt.Component;
import java.beans.PropertyChangeListener; 
import java.beans.PropertyChangeEvent; 
import javax.swing.JButton;
import javax.swing.JSplitPane;

/** The class to create synchronized split pane.
 *
 */



public class SynchronizedSplitPane extends JSplitPane 
				   implements PropertyChangeListener {

    private int synchronizedDividerSize = 4;

    public SynchronizedSplitPane(int newOrientation) {
	super(newOrientation, true, new JButton("Left button"),
	      new JButton("Right button"));
	setDividerSize(synchronizedDividerSize);
    }

    public SynchronizedSplitPane(int newOrientation,
				 Component newLeftComponent,
				 Component newRightComponent){
	super(newOrientation, true, newLeftComponent, newRightComponent);
	setDividerSize(synchronizedDividerSize);
    }

    public void addSynchronized(SynchronizedSplitPane c) {
	c.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, this);
	this.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, c);
    }

    public void propertyChange(PropertyChangeEvent event) {
	Object source = event.getSource();
	
	if (source instanceof JSplitPane) {
	    JSplitPane other = (JSplitPane)source;
	    if (other.getOrientation() != this.getOrientation())
		throw new IllegalArgumentException("Should have same orientation");
	    
	    if (event.getPropertyName().equals(DIVIDER_LOCATION_PROPERTY)) {
		Object newValue = event.getNewValue();
		if (newValue instanceof Double) {
		    double newLocation = ((Double)newValue).doubleValue();
		    synthronizeDividerLocation(newLocation);
		} else if (newValue instanceof Integer) {
		    int newLocation = ((Integer)newValue).intValue();
		    synthronizeDividerLocation(newLocation);
		}
	    }
	}
    }

    private void synthronizeDividerLocation(double proportionalLocation) {
	if (proportionalLocation < 0.0 ||
	    proportionalLocation > 1.0) {
	    throw new IllegalArgumentException("proportional location must " +
	                                       "be between 0.0 and 1.0.");
	}

	if (getOrientation() == VERTICAL_SPLIT) {
	    synthronizeDividerLocation((int)((double)(getHeight() - getDividerSize()) *
				       proportionalLocation));
	} else {
	    synthronizeDividerLocation((int)((double)(getWidth() - getDividerSize()) *
				       proportionalLocation));
	}
    }

    private void synthronizeDividerLocation(int location) {
	if (getLastDividerLocation() == location)
	    return;
	setDividerLocation(location);
    }
}

