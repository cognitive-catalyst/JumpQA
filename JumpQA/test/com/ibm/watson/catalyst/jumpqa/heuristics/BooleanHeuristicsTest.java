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
package com.ibm.watson.catalyst.jumpqa.heuristics;

import java.util.function.Predicate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class BooleanHeuristicsTest {
  
  BooleanHeuristics<Integer> b0;
  BooleanHeuristics<Integer> b1;
  BooleanHeuristics<Integer> b1a;
  BooleanHeuristics<Integer> b2;
  
  Predicate<Integer> pFalse = (s) -> false;
  Predicate<Integer> pTrue = (s) -> true;
  
  @Before
  public void setUp() {
    b0 = new BooleanHeuristics<Integer>();
    
    b1 = new BooleanHeuristics<Integer>();
    b1.add(pTrue);
    
    b1a = new BooleanHeuristics<Integer>();
    b1a.add(pTrue);
    
    b2 = new BooleanHeuristics<Integer>();
    b2.add(pFalse);
  }
  
  @Test
  public void testAllTrueTrue() {
    for (int i = 0; i < 10; i++)
      b0.add(pTrue);
    assertTrue(b0.allTrue(0));
  }
  
  @Test
  public void testAllTrueFalse() {
    b0.add(pFalse);
    for (int i = 0; i < 10; i++)
      b0.add(pTrue);
    assertFalse(b0.allTrue(0));
  }
  
  @Test
  public void testAnyTrueFalse() {
    for (int i = 0; i < 10; i++)
      b0.add(pFalse);
    assertFalse(b0.anyTrue(0));
  }
  
  @Test
  public void testAnyTrueTrue() {
    b0.add(pTrue);
    for (int i = 0; i < 10; i++)
      b0.add(pFalse);
    assertTrue(b0.anyTrue(0));
  }
  
  @Test
  public void testAnyTrueEmpty() {
    assertFalse(b0.anyTrue(0));
  }
  
  @Test
  public void testAllTrueEmpty() {
    assertTrue(b0.allTrue(0));
  }
  
  @Test
  public void testNull() {
    BooleanHeuristics<Integer> bh = new BooleanHeuristics<Integer>(null);
    assertTrue(bh.allTrue(0));
  }
  
  @Test
  public void testEqualsReflexive() {
    assertThat(b1, equalTo(b1));
  }
  
  @Test
  public void testEqualsSymmetric() {
    assertThat(b1, equalTo(b1a));
  }
  
  @Test
  public void testNotEquals() {
    assertThat(b1, not(equalTo(b2)));
  }
  
  @Test
  public void testHashCodeEqualsReflexive() {
    assertThat(b1.hashCode(), equalTo(b1.hashCode()));
  }
  
  @Test
  public void testHashCodeEqualsSymmetric() {
    assertThat(b1.hashCode(), equalTo(b1a.hashCode()));
  }
  
  @Test
  public void testHashCodeNotEquals() {
    assertThat(b1.hashCode(), not(equalTo(b2).hashCode()));
  }
  
}
