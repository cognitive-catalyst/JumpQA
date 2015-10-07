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

import java.util.List;
import java.util.regex.Pattern;

public class WordFrequencyBuilder {
  
  public WordFrequencyBuilder(List<String> aParagraphs) {
    _paragraphs = aParagraphs;
  }
  
  public WordFrequencyHashtable build() {
    WordFrequencyHashtable result = new WordFrequencyHashtable();
    for (String paragraph : _paragraphs) {
      result.addAll(genFrequenciesFromParagraph(paragraph));
    }
    return result;
  }
  
  // ------------------------------------------------------------------------------------------ //
  // Private
  // ------------------------------------------------------------------------------------------ //
  private List<String> _paragraphs;
  
  private static Pattern wordSplit = Pattern.compile("[\\s\\.!?\\(\\)\\[\\].\";:/,]+");
  private static Pattern letters = Pattern.compile("[A-Za-z]");
  private static WordFrequencyHashtable genFrequenciesFromParagraph(String paragraph) {
    WordFrequencyHashtable result = new WordFrequencyHashtable();
    
    String[] words = wordSplit.split(paragraph);
    for (String newWord : words) {
      if (newWord.length() <= 2) continue;
      if (!letters.matcher(newWord).find()) continue;
      result.add(newWord);
    }
    
    return result;
  }
  
  
}
