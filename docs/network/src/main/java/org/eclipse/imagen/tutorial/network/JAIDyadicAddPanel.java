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
package org.eclipse.imagen.tutorial.network;

import java.awt.image.renderable.ParameterBlock;
import java.util.Vector;
import org.eclipse.imagen.*;
import org.eclipse.imagen.remote.RemoteJAI;

public class JAIDyadicAddPanel extends JAIDyadicPanel {
  
    public JAIDyadicAddPanel(JAINetworkDemo demo, 
			     Vector sourceVec, 
			     RemoteJAI pClient) {
        super(demo, sourceVec, pClient);
    }  
    
    public String getDemoName() {
        return "Add";
    }
    
    public PlanarImage process() {
        PlanarImage im0 = getSource(0);
        PlanarImage im1 = getSource(1);
        
        ParameterBlock pb = new ParameterBlock();
        pb.addSource(im1);
        pb.addSource(im0); 
   
        return client.create("add", pb, renderHints);
    }
}
