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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ibm.watson.catalyst.corpus.tfidf.util.SortedArrayList;

@SuppressWarnings("serial")
public final class WordFrequencyHashtable extends FrequencyHashtable<String> {
  
  @Override
  public Integer add(String aKey) {
    return add(aKey, 1);
  }
  
  @Override
  public Integer add(String aKey, Integer aInteger) {
    String key = normalizeKey(aKey);
    _df.put(key, _df.containsKey(key) ? _df.get(key) + 1 : 1);
    return put(key, contains(key) ? get(key) + aInteger : aInteger);
  }
  
  @Override
  public Integer get(Object aKey) {
    return super.get(normalizeKey(aKey));
  }
  
  @Override
  public Integer put(String aKey, Integer aValue) {
    return super.put(normalizeKey(aKey), aValue);
  }
  
  private String normalizeKey(Object aKey) {
    return aKey.toString().toLowerCase();
  }
  
  public Iterator<String> sortedIterator(Integer min) {
    SortedArrayList<String> result = new SortedArrayList<String>(min);
    Enumeration<String> keys = keys();
    while (keys.hasMoreElements()) {
      String key = keys.nextElement();
      result.sortedAdd(key, get(key));
    }
    return result.iterator();
  }
  
  public Iterator<String> sortedIterator() {
    return sortedIterator(0);
  }
  
  public JsonNode toJsonNode(int min) {
    ObjectNode result = MAPPER.createObjectNode();
    
    Iterator<String> strings = sortedIterator(min);
    
    ArrayNode stringsArrayNode = MAPPER.createArrayNode();
    while (strings.hasNext()) {
      ObjectNode aStringNode = MAPPER.createObjectNode();
      String s = strings.next();
      aStringNode.put("string", s);
      aStringNode.put("frequency", get(s));
      stringsArrayNode.add(aStringNode);
    }
    result.set("strings", stringsArrayNode);
    
    return result;
  }
  
  public int getMaxFrequency() {
    int result = 0;
    for (String key : this) result = Math.max(result, get(key));
    return result;
  }
  
  public int getDf(String aString) {
    return _df.containsKey(aString) ? _df.get(aString) : 0;
  }
  
  @Override
  public boolean contains(Object aKey) {
    return super.contains(normalizeKey(aKey));
  }
  
  private final Hashtable<String, Integer> _df = new Hashtable<String, Integer>();
  private static final ObjectMapper MAPPER = new ObjectMapper();
  
}
