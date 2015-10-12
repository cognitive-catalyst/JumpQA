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

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

@SuppressWarnings("serial")
public class FrequencyHashtable<K> extends Hashtable<K, Integer> implements Iterable<K> {
  
  @Override
  public Integer get(Object aKey) {
    return contains(aKey) ? super.get(aKey) : 0;
  }
  
  public Integer add(K aKey) {
    return add(aKey, 1);
  }
  
  public Integer add(K aKey, Integer aInteger) {
    return put(aKey, contains(aKey) ? get(aKey) + aInteger : aInteger);
  }
  
  public void addAll(List<K> aKeys) {
    for (K aKey : aKeys) {
      add(aKey);
    }
  }
  
  public void addAll(FrequencyHashtable<K> aFrequencyHashtable) {
    for (K key : aFrequencyHashtable) {
      add(key, aFrequencyHashtable.get(key));
    }
  }
  
  public Iterator<K> iterator() {
    return new FrequencyIterator<K>(this);
  }
  
  @Override
  public boolean contains(Object aObject) {
    for (K aKey : this) {
      if (aObject == null ? aKey == null : aKey.equals(aObject)) return true;
    }
    return false;
  }

@Override
public void forEach(Consumer<? super K> action) {
	// TODO Auto-generated method stub
	
}

@Override
public Spliterator<K> spliterator() {
	// TODO Auto-generated method stub
	return null;
}
  
}
