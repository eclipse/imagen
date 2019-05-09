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


import java.awt.geom.Dimension2D;

/** A class to contain a dimension with floating-point size.
 *
 */



public class DimensionFloat extends Dimension2D {
   
    public float width;
    public float height;

    public DimensionFloat(float width, float height) {
	this.width = width;
	this.height = height;
    }

    public DimensionFloat(double width, double height) {
	this.width = (float)width;
	this.height = (float)height;
    }

    public Object clone() {
	return new DimensionFloat(this.width, this.height);
    }

    public double getWidth() {
	return this.width;
    }

    public double getHeight() {
	return this.height;
    }

    public void setSize(Dimension2D size) {
	this.width = (float)size.getWidth();
	this.height = (float)size.getHeight();
    }

    public void setSize(double width, double height) {
	this.width = (float)width;
	this.height = (float)height;
    }

    public String toString() {
	return "(" + width +", " + height + ")";
    }
}

