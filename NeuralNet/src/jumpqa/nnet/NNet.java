/*******************************************************************************
 * Copyright 2015 IBM Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package jumpqa.nnet;

import java.util.List;

public final class NNet {
  
  public NNet (NLayer... aLayers) {
    _layers = aLayers;
  }
  
  public NNet (List<NLayer> aLayers) {
    this(aLayers.toArray(new NLayer[aLayers.size()]));
  }
  
  public double[] compute(double... features) {
    double[] result = features;
    
    for (NLayer aLayer : _layers) {
      result = aLayer.compute(result);
    }
    
    return result;
  }
  
  public double[] compute(int layerNum, double... features) {
    return _layers[layerNum].compute(features);
  }
  
  public double compute(int layerNum, int nodeNum, double... features) {
    return _layers[layerNum].compute(nodeNum, features);
  }
  
  private final NLayer[] _layers;
  
}
