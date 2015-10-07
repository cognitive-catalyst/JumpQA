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
/**
 * 
 */
package com.ibm.watson.catalyst.jumpqa.replacer;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.ibm.watson.catalyst.jumpqa.replacer.ConstReplacer;

@SuppressWarnings("javadoc")
public class ConstReplacerTest {
  
  ConstReplacer r1;
  
  @Before
  public void setUp() {
    r1 = new ConstReplacer("pattern", "replacement");
  }
  
  @Test
  public void testReplace() {
    ConstReplacer r = new ConstReplacer("a", "__");
    String testInput = "alpha";
    String expectedOutput = "__lph__";
    assertThat(r.replace(testInput), equalTo(expectedOutput));
  }
  
  @Test
  public void testEqualsRelexive() {
    assertThat(r1, equalTo(r1));
  }
  
  @Test
  public void testEqualsSymmetric() {
    ConstReplacer r2 = new ConstReplacer("pattern", "replacement");
    assertThat(r1, equalTo(r2));
  }
  
  @Test
  public void testNotEqualsPattern() {
    ConstReplacer r2 = new ConstReplacer("another pattern", "replacement");
    assertThat(r1, not(equalTo(r2)));
  }
  
  @Test
  public void testNotEqualsReplacement() {
    ConstReplacer r2 = new ConstReplacer("pattern", "another replacement");
    assertThat(r1, not(equalTo(r2)));
  }
  
  @Test
  public void testHashRelexive() {
    assertThat(r1.hashCode(), equalTo(r1.hashCode()));
  }
  
  @Test
  public void testHashSymmetric() {
    ConstReplacer r2 = new ConstReplacer("pattern", "replacement");
    assertThat(r1.hashCode(), equalTo(r2.hashCode()));
  }
  
  @Test
  public void testHashNotEqualsPattern() {
    ConstReplacer r2 = new ConstReplacer("another pattern", "replacement");
    assertThat(r1.hashCode(), not(equalTo(r2.hashCode())));
  }
  
  @Test
  public void testHashNotEqualsReplacement() {
    ConstReplacer r2 = new ConstReplacer("pattern", "another replacement");
    assertThat(r1.hashCode(), not(equalTo(r2.hashCode())));
  }
  
}
