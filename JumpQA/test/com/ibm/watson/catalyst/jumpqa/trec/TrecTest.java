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
package com.ibm.watson.catalyst.jumpqa.trec;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.ibm.watson.catalyst.jumpqa.answer.Pau;

@SuppressWarnings("javadoc")
public class TrecTest {
  
  Trec t1;
  Trec t1b;
  Trec t2;
  Trec t3;
  Trec t4;
  Trec t5;
  Trec t6;
  List<String> pars1;
  
  @Before
  public void setUp() {
    pars1 = new ArrayList<String>();
    pars1.add("New York City is big.");
    pars1.add("Very big.");
    
    t1 = new Trec("sample\\sampletrec1.xml", new Pau("792D9A2361B65155B2B882C36766701D",
        "New_York_City"), "New_York_City.html", pars1);
    
    t1b = new Trec("sample\\sampletrec1.xml", new Pau("792D9A2361B65155B2B882C36766701D",
        "New_York_City"), "New_York_City.html", pars1);
    
    t2 = new Trec("sample\\sampletrec2.xml", new Pau("792D9A2361B65155B2B882C36766701D",
        "New_York_City"), "New_York_City.html", pars1);
    
    t3 = new Trec("sample\\sampletrec1.xml", new Pau("0B05E0CD39F145EC66ADB26BE6CCECD1",
        "New_York_City"), "New_York_City.html", pars1);
    
    t4 = new Trec("sample\\sampletrec1.xml", new Pau("792D9A2361B65155B2B882C36766701D", "Austin"),
        "New_York_City.html", pars1);
    
    t5 = new Trec("sample\\sampletrec1.xml", new Pau("792D9A2361B65155B2B882C36766701D",
        "New_York_City"), "Austin.html", pars1);
    
    List<String> pars2 = new ArrayList<String>(pars1);
    pars2.remove(0);
    t6 = new Trec("sample\\sampletrec1.xml", new Pau("792D9A2361B65155B2B882C36766701D",
        "New_York_City"), "New_York_City.html", pars2);
  }
  
  @Test
  public void testHashCodeReflexive() {
    assertThat(t1.hashCode(), equalTo(t1b.hashCode()));
  }
  
  @Test
  public void testHashCodeEqualsSymmetric() {
    assertThat(t1.hashCode(), equalTo(t1b.hashCode()));
  }
  
  @Test
  public void testHashCodeNotEqualsFile() {
    assertThat(t1.hashCode(), not(equalTo(t2.hashCode())));
  }
  
  @Test
  public void testHashCodeNotEqualsPauId() {
    assertThat(t1.hashCode(), not(equalTo(t3.hashCode())));
  }
  
  @Test
  public void testHashCodeNotEqualsPauTitle() {
    assertThat(t1.hashCode(), not(equalTo(t4.hashCode())));
  }
  
  @Test
  public void testHashCodeNotEqualsSourceDoc() {
    assertThat(t1.hashCode(), not(equalTo(t5.hashCode())));
  }
  
  @Test
  public void testHashCodeNotEqualsParagraphs() {
    assertThat(t1.hashCode(), not(equalTo(t6.hashCode())));
  }
  
  @Test
  public void testEqualsReflexive() {
    assertThat(t1, equalTo(t1));
  }
  
  @Test
  public void testEqualsSymmetric() {
    assertThat(t1, equalTo(t1b));
  }
  
  @Test
  public void testNotEqualsFile() {
    assertThat(t1, not(equalTo(t2)));
  }
  
  @Test
  public void testNotEqualsPauId() {
    assertThat(t1, not(equalTo(t3)));
  }
  
  @Test
  public void testNotEqualsPauTitle() {
    assertThat(t1, not(equalTo(t4)));
  }
  
  @Test
  public void testNotEqualsSourceDoc() {
    assertThat(t1, not(equalTo(t5)));
  }
  
  @Test
  public void testNotEqualsParagraphs() {
    assertThat(t1, not(equalTo(t6)));
  }
  
}
