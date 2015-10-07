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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class WordSet {
  
  private static Pattern wordBoundary = Pattern.compile("[\\(\\)\\s\\.,]+");
  public WordSet(String aSentence) {
    String[] words = wordBoundary.split(aSentence.toLowerCase());
    for (String word : words) {
      if (!isBadWord(word)) {
        add(word);
      }
    }
  }
  
  public List<Bigram> genBigrams() {
    List<Bigram> result = new ArrayList<Bigram>();
    
    for (int i = 0; i < (_words.size() - 1); i++) {
      for (int j = i + 1; j < _words.size(); j++) {
        result.add(new Bigram(_words.get(i), _words.get(j)));
      }
    }
    
    return result;
  }
  
  public int size() {
    return _words.size();
  }
  
  // ------------------------------------------------------------------------------------------ //
  // Private
  // ------------------------------------------------------------------------------------------ //
  private final List<String> _words = new ArrayList<String>();
  private static final Pattern cite = Pattern.compile("[\\(\\[][-\\d]+[\\]\\)]");
  
  private boolean isBadWord(String aWord) {
    return cite.matcher(aWord).find();
  }
  
  private boolean add(String s) {
    return _words.contains(s) ? false : _words.add(s);
  }
  
}
