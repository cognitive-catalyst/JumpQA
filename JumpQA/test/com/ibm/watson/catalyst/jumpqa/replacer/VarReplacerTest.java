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
package com.ibm.watson.catalyst.jumpqa.replacer;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@SuppressWarnings("javadoc")
public class VarReplacerTest {
  
  VarReplacer r1 = new VarReplacer("a");
  
  @Before
  public void setUp() {
    
  }
  
  @Test
  public void testReplaceEmpty() {
    assertThat(r1.replace("", "_"), equalTo(""));
  }
  
  @Test
  public void testReplace() {
    assertThat(r1.replace("alpha", "_"), equalTo("_lph_"));
  }
  
  @Test
  public void testEqualsRelexive() {
    assertThat(r1, equalTo(r1));
  }
  
  @Test
  public void testEqualsSymmetric() {
    VarReplacer r2 = new VarReplacer("a");
    assertThat(r1, equalTo(r2));
  }
  
  @Test
  public void testNotEquals() {
    VarReplacer r2 = new VarReplacer("another pattern");
    assertThat(r1, not(equalTo(r2)));
  }
  
  @Test
  public void testHashRelexive() {
    assertThat(r1.hashCode(), equalTo(r1.hashCode()));
  }
  
  @Test
  public void testHashSymmetric() {
    VarReplacer r2 = new VarReplacer("a");
    assertThat(r1.hashCode(), equalTo(r2.hashCode()));
  }
  
  @Test
  public void testHashNotEqualsPattern() {
    VarReplacer r2 = new VarReplacer("another pattern");
    assertThat(r1.hashCode(), not(equalTo(r2.hashCode())));
  }
  
}
