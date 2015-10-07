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
public class ParagraphSplitterTest {
  
  ISplitter ps = SplitterFactory.build("paragraph");
  List<String> expected;
  
  @Before
  public void setUp() {
    expected = new ArrayList<String>();
  }
  
  @Test
  public void testSplitStringNoParagraphs() {
    String s = "";
    assertThat(ps.split(s), equalTo(expected));
  }
  
  @Test
  public void testSplitStringOneParagraph() {
    String s = "A sentence.";
    expected.add(s);
    assertThat(ps.split(s), equalTo(expected));
  }
  
  @Test
  public void testSplitStringTwoParagraphs() {
    String s = "A paragraph.\nAnother paragraph.\n";
    String s1 = "A paragraph.";
    String s2 = "Another paragraph.";
    expected.add(s1);
    expected.add(s2);
    assertThat(ps.split(s), equalTo(expected));
  }
  
  @Test
  public void testSplitList() {
    String s1 = "A paragraph.";
    String s2 = "Another paragraph.\nYet another.";
    List<String> s = new ArrayList<String>();
    s.add(s1);
    s.add(s2);
    expected.add(s1);
    expected.add("Another paragraph.");
    expected.add("Yet another.");
    assertThat(ps.split(s), equalTo(expected));
  }
  
}
