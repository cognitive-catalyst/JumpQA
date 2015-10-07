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
package com.ibm.watson.catalyst.jumpqa.answer;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.ibm.watson.catalyst.jumpqa.answer.GroundTruthEntry.GroundTruthState;

@SuppressWarnings("javadoc")
public class GroundTruthEntryTest {
  
  String id1 = "0";
  String id1a = "0";
  String id2 = "1";
  
  QuestionAnswerPair qa1 = new QuestionAnswerPair("Question 1", "Answer 1");
  QuestionAnswerPair qa1a = new QuestionAnswerPair("Question 1", "Answer 1");
  QuestionAnswerPair qa3 = new QuestionAnswerPair("Question 2", "Answer 2");
  
  GroundTruthEntry e1;
  GroundTruthEntry e1a;
  GroundTruthEntry e2;
  GroundTruthEntry e3;
  GroundTruthEntry e4;
  
  @Before
  public void setUp() {
    e1 = new GroundTruthEntry(id1, qa1, GroundTruthState.APPROVED);
    e1a = new GroundTruthEntry(id1a, qa1a, GroundTruthState.APPROVED);
    e2 = new GroundTruthEntry(id2, qa1, GroundTruthState.APPROVED);
    e3 = new GroundTruthEntry(id1, qa3, GroundTruthState.APPROVED);
    e4 = new GroundTruthEntry(id1, qa1, GroundTruthState.DRAFT);
  }
  
  @Test
  public void testEqualsReflexive() {
    assertThat(e1, equalTo(e1));
  }
  
  @Test
  public void testEqualsSymmetric() {
    assertThat(e1, equalTo(e1a));
  }
  
  @Test
  public void testNotEqualsId() {
    assertThat(e1, not(equalTo(e2)));
  }
  
  @Test
  public void testNotEqualsQaPair() {
    assertThat(e1, not(equalTo(e3)));
  }
  
  @Test
  public void testNotEqualsState() {
    assertThat(e1, not(equalTo(e4)));
  }
  
}
