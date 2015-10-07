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
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.ibm.watson.catalyst.jumpqa.replacer.ConstReplacer;
import com.ibm.watson.catalyst.jumpqa.replacer.SequentialReplacer;

@SuppressWarnings("javadoc")
public class SequentialReplacerTest {
  
  ConstReplacer r1 = new ConstReplacer("a", "_");
  ConstReplacer r1b = new ConstReplacer("a", "_");
  ConstReplacer r2 = new ConstReplacer("b", "!");
  ConstReplacer r3 = new ConstReplacer("_", "c");
  List<ConstReplacer> replacers;
  
  VarReplacer r4 = new VarReplacer("\\[s0\\]");
  VarReplacer r5 = new VarReplacer("\\[s1\\]");
  VarReplacer r6 = new VarReplacer("\\[s2\\]");
  List<VarReplacer> varReplacers = new ArrayList<VarReplacer>();
  
  SequentialReplacer s0;
  SequentialReplacer s1;
  SequentialReplacer s1a;
  SequentialReplacer s1b;
  SequentialReplacer s2;
  SequentialReplacer s3;
  
  @Before
  public void setUp() {
    replacers = new ArrayList<ConstReplacer>();
    
    List<ConstReplacer> l0 = new ArrayList<ConstReplacer>();
    s0 = new SequentialReplacer(l0);
    
    List<ConstReplacer> l1 = new ArrayList<ConstReplacer>();
    l1.add(r1);
    s1 = new SequentialReplacer(l1);
    
    List<ConstReplacer> l1a = new ArrayList<ConstReplacer>();
    l1a.add(r1);
    s1a = new SequentialReplacer(l1a);
    
    List<ConstReplacer> l1b = new ArrayList<ConstReplacer>();
    l1b.add(r1b);
    s1b = new SequentialReplacer(l1b);
    
    List<ConstReplacer> l2 = new ArrayList<ConstReplacer>();
    l2.add(r2);
    s2 = new SequentialReplacer(l2);
    
  }
  
  @Test
  public void replace1() {
    replacers.add(r1);
    SequentialReplacer wr = new SequentialReplacer(replacers);
    assertThat(wr.replace("alpha"), equalTo("_lph_"));
  }
  
  @Test
  public void replace2() {
    replacers.add(r1);
    replacers.add(r2);
    SequentialReplacer wr = new SequentialReplacer(replacers);
    assertThat(wr.replace("beta"), equalTo("!et_"));
  }
  
  @Test
  public void replaceOver() {
    replacers.add(r1);
    replacers.add(r3);
    SequentialReplacer wr = new SequentialReplacer(replacers);
    assertThat(wr.replace("alpha"), equalTo("clphc"));
  }
  
  @Test
  public void testVarReplacers() {
    varReplacers.add(r4);
    varReplacers.add(r5);
    varReplacers.add(r6);
    SequentialReplacer sr = new SequentialReplacer(varReplacers);
    assertThat(sr.replace("[s0] [s1] [s2]", new String[] { "a", "b", "c" }), equalTo("a b c"));
  }
  
  @Test
  public void testEqualsReflexive() {
    assertThat(s1, equalTo(s1));
  }
  
  @Test
  public void testEqualsSymmetricIdentialList() {
    assertThat(s1, equalTo(s1a));
  }
  
  @Test
  public void testEqualsSymmetricIdentialElements() {
    assertThat(s1, equalTo(s1b));
  }
  
  @Test
  public void testNotEqualNull() {
    assertThat(s1, not(equalTo(null)));
  }
  
  @Test
  public void testNotEqualNullList() {
    assertThat(s1, not(equalTo(s0)));
  }
  
  @Test
  public void testNotEqualList() {
    assertThat(s1, not(equalTo(s2)));
  }
  
  @Test
  public void testHashCodeEqualsReflexive() {
    assertThat(s1.hashCode(), equalTo(s1.hashCode()));
  }
  
  @Test
  public void testHashCodeEqualsSymmetricIdentialList() {
    assertThat(s1.hashCode(), equalTo(s1a.hashCode()));
  }
  
  @Test
  public void testHashCodeEqualsSymmetricIdentialElements() {
    assertThat(s1.hashCode(), equalTo(s1b.hashCode()));
  }
  
  @Test
  public void testHashCodeNotEqualNullList() {
    assertThat(s1.hashCode(), not(equalTo(s0).hashCode()));
  }
  
  @Test
  public void testHashCodeNotEqualList() {
    assertThat(s1.hashCode(), not(equalTo(s2).hashCode()));
  }
  
}
