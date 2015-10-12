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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests whether the WordSplitter class is working as intended.
 * 
 * @author Will Beason
 * @version 0.1.0
 * @since 0.1.0
 *
 */
@SuppressWarnings("javadoc")
public class WordSplitterTest {
  
  ISplitter ws = SplitterFactory.build("word");
  List<String> words;
  List<String> ab;
  
  @Before
  public void setUp() {
    words = new ArrayList<String>();
    ab = new ArrayList<String>();
    ab.add("a");
    ab.add("b");
  }
  
  @Test
  public void testSplitTwoWords() {
    final String aString = "Some words";
    words.add("Some");
    words.add("words");
    assertThat(ws.split(aString), equalTo(words));
  }
  
  @Test
  public void testSplitOneWord() {
    final String aString = "Word";
    words.add("Word");
    assertThat(ws.split(aString), equalTo(words));
  }
  
  @Test
  public void testSplitNoWords() {
    final String aString = "";
    assertThat(ws.split(aString), equalTo(words));
  }
  
  @Test
  public void testSplitQuestionMark() {
    assertThat(ws.split(punctSplit("?")), equalTo(ab));
  }
  
  @Test
  public void testSplitExclamation() {
    assertThat(ws.split(punctSplit("!")), equalTo(ab));
  }
  
  @Test
  public void testSplitComma() {
    assertThat(ws.split(punctSplit(",")), equalTo(ab));
  }
  
  @Test
  public void testSplitSemicolon() {
    assertThat(ws.split(punctSplit(";")), equalTo(ab));
  }
  
  @Test
  public void testSplitPeriod() {
    assertThat(ws.split(punctSplit(".")), equalTo(ab));
  }
  
  @Test
  public void testSplitMultiPunct() {
    assertThat(ws.split(punctSplit(".!!!?::;")), equalTo(ab));
  }
  
  @Test
  public void testSplitList() {
    final List<String> stringList = new ArrayList<String>();
    stringList.add("Some words.");
    stringList.add("more? words");
    words.add("Some");
    words.add("words");
    words.add("more");
    words.add("words");
    assertThat(ws.split(stringList), equalTo(words));
  }
  
  private String punctSplit(String split) {
    return "a" + split + "b" + split;
  }
  
}
