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
package com.ibm.watson.catalyst.corpus.tfidf.sentences;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.function.Consumer;

public final class FrequencyIterator<K> implements Iterator<K> {
  
  protected FrequencyIterator(Hashtable<K, ?> aHashtable) {
    _keys = aHashtable.keys();
  }
  
  @Override
  public boolean hasNext() {
    return _keys.hasMoreElements();
  }
  
  @Override
  public K next() {
    return _keys.nextElement();
  }
  
  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }
  
  // ------------------------------------------------------------------------------------------ //
  // Private
  // ------------------------------------------------------------------------------------------ //
  private final Enumeration<K> _keys;

@Override
public void forEachRemaining(Consumer<? super K> action) {
	// TODO Auto-generated method stub
	
}
  
}
