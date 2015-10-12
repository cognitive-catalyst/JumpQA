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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ibm.watson.catalyst.corpus.tfidf.sentences.WordFrequencyBuilder;
import com.ibm.watson.catalyst.corpus.tfidf.sentences.WordFrequencyHashtable;

public class WordFrequencyBuilderTest {

  @Test
  public void testBuild() {
    
    List<String> strings = new ArrayList<String>();
    
    strings.add("the apple");
    strings.add("The banana");
    
    WordFrequencyBuilder wfb = new WordFrequencyBuilder(strings);
    
    WordFrequencyHashtable wfh = wfb.build();
    
    assertEquals(wfh.get("the"), (Integer) 2);
    assertEquals(wfh.get("apple"), (Integer) 1);
    
  }

}
