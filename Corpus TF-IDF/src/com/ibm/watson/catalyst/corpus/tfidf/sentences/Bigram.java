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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public final class Bigram {
  
  public Bigram(String aWord1, String aWord2) {
    _word1 = aWord1;
    _word2 = aWord2;
  }
  
  public boolean equals(Bigram aBigram) {
    return aBigram.containsWord(_word1) && aBigram.containsWord(_word2);
  }
  
  public boolean containsWord(String aWord) {
    return (aWord.equals(_word1) || aWord.equals(_word2));
  }
  
  public List<String> getWords() {
    List<String> result = new ArrayList<String>();
    result.add(_word1);
    result.add(_word2);
    return result;
  }
  
  public ObjectNode toJson() {
    ObjectNode result = MAPPER.createObjectNode();
    
    result.put("word1", _word1);
    result.put("word2", _word2);
    
    return result;
  }
  
  private final String _word1;
  private final String _word2;
  private static final ObjectMapper MAPPER = new ObjectMapper();
  
}
