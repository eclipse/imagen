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
package org.eclipse.imagen.demo;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.renderable.ParameterBlock;
import java.util.Hashtable;
import java.util.Vector;
import org.eclipse.imagen.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.DataBuffer;
import java.awt.image.SampleModel;

public class JAIOpPanel extends JAIDemoPanel {
  
  String opName;
  int id;
  Icon imageIcon = null;
  JLabel imageLabel = null;
  Vector sourceVec;
  JAIDyadicPanel demo;

  public JAIOpPanel(JAIDyadicPanel demo, int id, String opName,Vector sourceVec ){
    super(sourceVec);
    this.opName = opName;
    this.sourceVec = sourceVec;
    masterSetup();
    this.id = id;
    this.demo = demo;
  }  
   
  public String getDemoName(){
    return opName;
  }
  public void makeControls(JPanel controls){ 
    controls.setLayout(new BorderLayout());
  }
 
 public PlanarImage process() {
   PlanarImage im,dst1;
   double[] constants;
//   SampleModel sm;
   int nBands;
   Rectangle rect;
  // ImageLayout il;
//   RenderingHints rh;
   
   PlanarImage im0 = getSource(0);
   PlanarImage im1 = getSource(1);
   
   ParameterBlock pb = new ParameterBlock();
   pb.addSource(im1);
   pb.addSource(im0); 

   
   switch(id){
   case 0: return im0;
   case 1: return im1;
     
   case 2:
     return JAI.create("add", pb, renderHints);
   case 3:
     return JAI.create("subtract", pb, renderHints);
   case 4:
     rect = im0.getBounds().intersection(im1.getBounds());
     nBands = Math.min(im0.getSampleModel().getNumBands(),
			   im1.getSampleModel().getNumBands());
     
     pb = new ParameterBlock();
     pb.addSource(im0);
     pb.addSource(im1);
     im = JAI.create("multiply",pb,getRenderingHints(DataBuffer.TYPE_USHORT,
						     rect,nBands));

     // Constants
     constants = new double[3];
     constants[0] = 255.0;
     constants[1] = 255.0;
     constants[2] = 255.0;
 
     pb = new ParameterBlock();
     pb.addSource(im);
     pb.add(constants);
     dst1 = (PlanarImage)JAI.create("dividebyconst", pb,
				    getRenderingHints(DataBuffer.TYPE_BYTE,
						      rect,nBands));     
     return dst1;
     
     
   case 5:
     
     rect = im0.getBounds().intersection(im1.getBounds());
     nBands = Math.min(im0.getSampleModel().getNumBands(),
			   im1.getSampleModel().getNumBands());
    
     
     pb = new ParameterBlock();
     pb.addSource(im0);
     pb.addSource(im1);
     im = JAI.create("divide",pb,getRenderingHints(DataBuffer.TYPE_FLOAT,
						     rect,nBands));


     // Constants
     constants = new double[3];
     constants[0] = 255.0;
     constants[1] = 255.0;
     constants[2] = 255.0;
  
     pb = new ParameterBlock();
     pb.addSource(im);
     pb.add(constants);
     dst1 = (PlanarImage)JAI.create("multiplyconst", pb,
				    getRenderingHints(DataBuffer.TYPE_BYTE,
						      rect,nBands));
     return dst1;
   
   default:
     return im0;
   }
    
    }
  
  RenderingHints getRenderingHints(int dType,Rectangle rect,int nBands){
    SampleModel sm =
      RasterFactory.createPixelInterleavedSampleModel(dType,
						      rect.width,
						      rect.height,
						      nBands);
    
    ImageLayout il = new ImageLayout();
    il.setSampleModel(sm);
    return new RenderingHints(JAI.KEY_IMAGE_LAYOUT, il);
  }

  public void reset(){
   demo.reset();
  }

}
