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
package com.ibm.watson.catalyst.corpus.tfidf.util;

import java.util.ArrayList;

@SuppressWarnings("serial")
public final class SortedArrayList<E> extends ArrayList<E> {
  
  public SortedArrayList() {
    super();
    _min = 0.0;
  }
  
  public SortedArrayList(Integer min) {
    this((double) min.intValue());
  }
  
  public SortedArrayList(Double min) {
    super();
    _min = min;
  }
  
  public boolean sortedAdd(E element, Double value) {
    if (value < _min) return false;
    int index = 0;
    while (index < size()) {
      if (value > _values.get(index)) break;
      index++;
    }
    this.add(index, element);
    _values.add(index, value);
    return true;
  }
  
  public boolean sortedAdd(E element, Integer value) {
    return this.sortedAdd(element, (double) value.intValue());
  }
  
  public Double getValue(int index) {
    return _values.get(index);
  }
  
  private final Double _min;
  private final ArrayList<Double> _values = new ArrayList<Double>();
  
  
}
