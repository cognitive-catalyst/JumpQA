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
package com.ibm.watson.ecosystem.util.random.lists;

import static org.junit.Assert.*;

import org.junit.Test;

public class RandomArrayListTest {

  @Test
  public void test() {
    
    RandomArrayList<Integer> ral = new RandomArrayList<Integer>();
    for (int i = 0; i < 5; i++) {
      ral.add(i);
    }
    
    RandomIterator<Integer> rali = ral.iterator();
    rali.setSeed(0);
    assertTrue(rali.next() == 0);
    assertTrue(rali.next() == 3);
    assertTrue(rali.next() == 1);
    assertTrue(rali.next() == 2);
    assertTrue(rali.next() == 4);
    assertFalse(rali.hasNext());
    
    rali = ral.iterator();
    rali.setSeed(1);
    assertTrue(rali.next() == 4);
    assertTrue(rali.next() == 0);
    assertTrue(rali.next() == 2);
    assertTrue(rali.next() == 3);
    assertTrue(rali.next() == 1);
    assertFalse(rali.hasNext());
    
    
  }

}
