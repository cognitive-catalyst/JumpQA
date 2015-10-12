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
package com.ibm.watson.catalyst.jumpqa.heuristics;

import java.util.List;

/**
 * Runs heuristics on an object of class T and return values of class U.
 * 
 * @author Will Beason
 * @version 0.1.1
 * @since 0.1.0
 *
 * @param <T> the input object class
 * @param <U> the class of heuristic output
 */
public interface IHeuristics<T, U> {
  
  /**
   * Given a set of heuristics, runs them on the input and returns a list of the
   * results.
   * 
   * @param input the input object to evaluate
   * @return a list of the results
   */
  public List<U> evaluate(T input);
  
}
