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
package com.ibm.watson.catalyst.jumpqa.splitter;

import java.util.List;

/**
 * An interface for things which split strings.
 * 
 * @author Will Beason
 * @version 0.1.1
 * @since 0.1.0
 *
 */
public interface ISplitter {
  
  /**
   * Given a list of strings to split, returns a list of the all of the splits.
   * 
   * @param strings the strings to split
   * @return a list of all of the splits
   */
  public List<String> split(List<String> strings);
  
  /**
   * Splits a string.
   * 
   * @param aString the string to split
   * @return the splits of aString
   */
  public List<String> split(String aString);
  
}
