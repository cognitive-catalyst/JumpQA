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
package com.ibm.watson.catalyst.jumpqa.questioner;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class TextTemplateQuestionerTest {
  
  TextTemplateQuestioner ttq0 = new TextTemplateQuestioner("");
  TextTemplateQuestioner ttq1 = new TextTemplateQuestioner("a [s0] b");
  TextTemplateQuestioner ttq2 = new TextTemplateQuestioner("a [s1] b");
  TextTemplateQuestioner ttq3 = new TextTemplateQuestioner("a [s2] b");
  TextTemplateQuestioner ttq4 = new TextTemplateQuestioner("a [s0] b [s1] c [s2] d");
  
  List<String> splits;
  
  @Before
  public void setUp() {
    splits = new ArrayList<String>();
    splits.add("one");
    splits.add("two");
    splits.add("three");
  }
  
  @Test
  public void testMakeEmptyQuestion() {
    assertThat(ttq0.makeQuestion(splits), equalTo(""));
  }
  
  @Test
  public void testMakeQuestionOne() {
    assertThat(ttq1.makeQuestion(splits), equalTo("a one b"));
  }
  
  @Test
  public void testMakeQuestionTwo() {
    assertThat(ttq2.makeQuestion(splits), equalTo("a two b"));
  }
  
  @Test
  public void testMakeQuestionThree() {
    assertThat(ttq3.makeQuestion(splits), equalTo("a three b"));
  }
  
  @Test
  public void testMakeQuestionAll() {
    assertThat(ttq4.makeQuestion(splits), equalTo("a one b two c three d"));
  }
  
}
