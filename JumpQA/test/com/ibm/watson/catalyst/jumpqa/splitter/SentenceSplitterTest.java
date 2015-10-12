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
package com.ibm.watson.catalyst.jumpqa.splitter;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

@SuppressWarnings("javadoc")
public class SentenceSplitterTest {
  
  ISplitter ss = SplitterFactory.build("sentence");
  List<String> sentences;
  
  @Before
  public void setUp() {
    sentences = new ArrayList<String>();
  }
  
  @Test
  public void testSplitNoSentences() {
    String s = "";
    assertThat(ss.split(s), equalTo(sentences));
  }
  
  @Test
  public void testSplitOneSentence() {
    String s = "A sentence.";
    sentences.add("A sentence");
    assertThat(ss.split(s), equalTo(sentences));
  }
  
  @Test
  public void testSplitTwoSentences() {
    String s = "One sentence. Another sentence.";
    sentences.add("One sentence");
    sentences.add("Another sentence");
    assertThat(ss.split(s), equalTo(sentences));
  }
  
}
