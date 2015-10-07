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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.ibm.watson.catalyst.jumpqa.replacer.ConstReplacer;
import com.ibm.watson.catalyst.jumpqa.replacer.ReplacerReader;
import com.ibm.watson.catalyst.jumpqa.replacer.ReplacerReader.ReplacerType;

@SuppressWarnings("javadoc")
public class ReplacerReaderTest {
  
  String line;
  ConstReplacer r1;
  ConstReplacer r2;
  
  @Before
  public void setUp() {
    line = "a=_";
    r1 = new ConstReplacer("a", "_");
    r2 = new ConstReplacer("\\ba\\b", "_");
  }
  
  @Test
  public void testRead() {
    ConstReplacer r3 = (new ReplacerReader()).read(line).get(0);
    assertThat(r3, equalTo(r3));
  }
  
  @Test
  public void testReadWord() {
    ConstReplacer r3 = (new ReplacerReader(ReplacerType.WORD)).read(line).get(0);
    assertThat(r2, equalTo(r3));
  }
  
}
