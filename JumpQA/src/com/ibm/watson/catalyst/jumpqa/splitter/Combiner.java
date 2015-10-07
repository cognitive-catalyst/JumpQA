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
/**
 * 
 */
package com.ibm.watson.catalyst.jumpqa.splitter;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Class description
 * 
 * @author Will Beason
 * @version 0.1.1
 * @since 0.1.1
 *
 */
public class Combiner implements ISplitter {
  
  @Override
  public List<String> split(List<String> strings) {
    StringBuilder sb = new StringBuilder();
    strings.stream().forEachOrdered((s) -> sb.append(s).append(System.lineSeparator()));
    List<String> result = new ArrayList<String>();
    result.add(sb.toString());
    return result;
  }
  
  @Override
  public List<String> split(String aString) {
    List<String> result = new ArrayList<String>();
    result.add(aString);
    return result;
  }
  
}
