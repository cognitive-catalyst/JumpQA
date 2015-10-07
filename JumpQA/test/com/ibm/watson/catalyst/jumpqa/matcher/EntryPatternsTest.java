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
package com.ibm.watson.catalyst.jumpqa.matcher;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class EntryPatternsTest {
  
  Pattern p1 = Pattern.compile("a");
  EntryPatterns srm1 = new EntryPatterns("a");
  EntryPatterns srm1b = new EntryPatterns("a");
  EntryPatterns srm1c = TemplateMatcherFactory.textMatcher("a", 0);
  EntryPatterns srm2 = new EntryPatterns("b");
  EntryPatterns srm3 = TemplateMatcherFactory.textMatcher("a", Pattern.CASE_INSENSITIVE);
  
  List<String> expected;
  
  @Before
  public void setUp() {
    expected = new ArrayList<String>();
  }
  
  @Test
  public void testMatchesTrue() {
    assertTrue(srm1.matchesText("alpha"));
  }
  
  @Test
  public void testMatchesFalse() {
    assertFalse(srm1.matchesText("omicron"));
  }
  
  @Test
  public void testSplitCandidateTextWithNoMatches() {
    String s = "omicron";
    expected.add(s);
    assertThat(srm1.splitCandidateText(s), equalTo(expected));
  }
  
  @Test
  public void testSplitCandidateTextWithOneMatch() {
    String s = "If a dog";
    expected.add("If");
    expected.add("a");
    expected.add("dog");
    assertThat(srm1.splitCandidateText(s), equalTo(expected));
  }
  
  @Test
  public void testSplitCandidateTextWithOneUppercaseMatch() {
    String s = "If A dog";
    expected.add("If");
    expected.add("A");
    expected.add("dog");
    assertThat(srm3.splitCandidateText(s), equalTo(expected));
  }
  
  @Test
  public void testSplitCandidateTextWithTwoMatches() {
    String s = "If a cat";
    expected.add("If");
    expected.add("a");
    expected.add("cat");
    assertThat(srm1.splitCandidateText(s), equalTo(expected));
  }
  
  @Test
  public void testEqualsReflexive() {
    assertThat(srm1, equalTo(srm1));
  }
  
  @Test
  public void testEqualsSymmetric() {
    assertThat(srm1b, equalTo(srm1));
  }
  
  @Test
  public void testEqualsFlag() {
    assertThat(srm1c, equalTo(srm1));
  }
  
  @Test
  public void testNotEqualsRegex() {
    assertThat(srm2, not(equalTo(srm1)));
  }
  
  @Test
  public void testNotEqualsFlags() {
    assertThat(srm3, not(equalTo(srm1)));
  }
  
  @Test
  public void testHashCodeEqualsReflexive() {
    assertThat(srm1.hashCode(), equalTo(srm1.hashCode()));
  }
  
  @Test
  public void testHashCodeEqualsSymmetric() {
    assertThat(srm1b.hashCode(), equalTo(srm1.hashCode()));
  }
  
  @Test
  public void testHashCodeEqualsFlag() {
    assertThat(srm1c.hashCode(), equalTo(srm1.hashCode()));
  }
  
  @Test
  public void testNotHashCodeEqualsRegex() {
    assertThat(srm2.hashCode(), not(equalTo(srm1.hashCode())));
  }
  
  @Test
  public void testNotHashCodeEqualsFlags() {
    assertThat(srm3.hashCode(), not(equalTo(srm1.hashCode())));
  }
  
}
