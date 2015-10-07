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
package com.ibm.watson.ecosystem.util.random.lists;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import com.ibm.watson.ecosystem.util.random.RandInt;

public class RandomIterator<E> implements Iterator<E> {
  
  public RandomIterator(List<E> aList) {
    _list = aList;
    _index = aList.size();
    _random = new RandInt();
  }
  
  public boolean hasNext() {
    return (_index > 0);
  }
  
  public E next() {
    _index--;
    swapRandomIndex();
    return _list.get(_index);
  }
  
  public void remove() {
    _list.remove(_index);
  }
  
  // Delegate seed setting to _random
  public void setSeed(long aSeed) {
    _random.setSeed(aSeed);
  }
  
  // ------------------------------------------------------------------------------------------ //
  // Private
  // ------------------------------------------------------------------------------------------ //
  private int _index;
  private final List<E> _list;
  private final RandInt _random; // the object which generates indexes
  
  private void swapRandomIndex() {
    int position = _random.randInt(_index + 1);
    Collections.swap(_list, position, _index);
  }

  @Override
  public void forEachRemaining(Consumer<? super E> action) {
	while(hasNext()) {
		action.accept(next());
	}
  }
  
}
