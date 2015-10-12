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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class NNetBuilder {
  
  public NNetBuilder(File aFile) {
    _file = aFile;
  }
  
  public NNetBuilder(String aFilename) {
    this(new File(aFilename));
  }
  
  public NNet build() {
    Path p = _file.toPath();
    List<String> lines = new ArrayList<String>();
    
    try {
      Files.lines(p).forEach((s) -> lines.add(s));
    } catch (IOException e) {
      throw new RuntimeException("Unable to read" + _file, e);
    }
    
    return readNet(lines);
  }
  
  private final File _file;
  
  private static final Pattern sep = Pattern.compile(",");
  private NNode readNode(String aString) {
    String[] strWeights = sep.split(aString);
    double[] weights = new double[strWeights.length];
    for (int i = 0; i < weights.length; i++) {
      weights[i] = Double.parseDouble(strWeights[i]);
    }
    return new NNode(weights);
  }
  
  private NNet readNet(List<String> aStrings) {
    List<NLayer> layers = new ArrayList<NLayer>();
    List<NNode> newLayer = new ArrayList<NNode>();
    for (String aString : aStrings) {
      if (aString.equals("")) {
        layers.add(new NLayer(newLayer));
        newLayer = new ArrayList<NNode>();
      } else {
        newLayer.add(readNode(aString));
      }
    }
    if (newLayer.size() > 0) {
      layers.add(new NLayer(newLayer));
    }
    
    return new NNet(layers);
  }
  
  public static void main(String[] args) {
    args = new String[] { "isnnet.csv" };
    
    double[] features1 = new double[] {4,5.149953068,1.287488267,0.4129387459,2.40308343,8,6.749630689,0.8437038362,1.055759762,1.632609547};
    double[] features2 = new double[] {1,1,1,0,1,0.9991756141};
    
    NNet nnet = new NNetBuilder(args[0]).build();
    System.out.println(nnet.compute(0, 3, features1));
    System.out.println(nnet.compute(0, 5, features1));
    System.out.println(nnet.compute(1, 0, features2));
    System.out.println(nnet.compute(features1)[0]);
  }
  
}
