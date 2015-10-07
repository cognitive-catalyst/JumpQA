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
package com.ibm.watson.catalyst.jumpqa.wordlist;

import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class WordListTest {
  
  List<String> words;
  WordList wl;
  
  @Before
  public void setUp() {
    words = new ArrayList<String>();
    words.add("stuff");
    words.add("more");
    
    wl = new WordList(words);
  }
  
  @Test
  public void testWordListListConstuctor() {
    assertThat(words, equalTo(wl.getList()));
  }
  
  @Test
  public void testContains() {
    assertTrue(wl.contains("stuff"));
  }
  
  @Test
  public void testContainsCaps() {
    assertTrue(wl.contains("StuFf"));
  }
  
  @Test
  public void testContainsFirstWord() {
    assertTrue(wl.containsFirstWord("Stuff is a"));
  }
  
  @Test
  public void testContainsFirstWordPunctBefore() {
    assertTrue(wl.containsFirstWord("? Stuff is a "));
  }
  
  @Test
  public void testContainsLastWord() {
    assertTrue(wl.containsLastWord("some more"));
  }
  
  @Test
  public void testContainsLastWordPunctAfter() {
    assertTrue(wl.containsLastWord("some more?"));
  }
  
}
