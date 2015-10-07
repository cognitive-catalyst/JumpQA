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
package com.ibm.watson.catalyst.corpus.tfidf.sentences;

import static org.junit.Assert.*;

import org.junit.Test;

public class BigramHashtableTest {

  @Test
  public void testPutBigramInt() {
    BigramHashtable bh = new BigramHashtable();
    
    Bigram b = new Bigram("a", "b");
    bh.add(b);
    assertTrue(b.equals(b));
    assertTrue(bh.contains(b));
    assertEquals(bh.get(b), (Integer) 1);
    bh.add(b);
    assertEquals(bh.get(b), (Integer) 2);
    
  }

  @Test
  public void testPutStringStringInt() {
  }

  @Test
  public void testGetStringString() {
  }

  @Test
  public void testAddStringString() {
  }

  @Test
  public void testBfipf() {
  }

  @Test
  public void testToJson() {
  }

}
