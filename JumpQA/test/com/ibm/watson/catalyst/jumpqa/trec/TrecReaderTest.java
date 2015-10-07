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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.ibm.watson.catalyst.jumpqa.answer.Pau;

@SuppressWarnings("javadoc")
public class TrecReaderTest {
  
  StringBuffer trecs;
  List<String> pars1;
  
  @Before
  public void setUp() {
    trecs = new StringBuffer();
    trecs.append("{\"corpus-name\":\"\",\"documents\":[").append("{")
        .append("\"file\":\"sample\\\\sampletrec1.xml\",")
        .append("\"pauID\":\"792D9A2361B65155B2B882C36766701D\",")
        .append("\"pauTitle\":\"New_York_City\",").append("\"sourceDoc\":\"New_York_City.html\",")
        .append("\"paragraphs\":[\"New York City is big.\", \"Very big.\"]").append("}")
        .append("]}");
    
    pars1 = new ArrayList<String>();
    pars1.add("New York City is big.");
    pars1.add("Very big.");
  }
  
  @Test
  public void testReadInputStream() {
    TrecReader tr = new TrecReader();
    Pau p = new Pau("792D9A2361B65155B2B882C36766701D", "New_York_City");
    Trec t1 = new Trec("sample\\sampletrec1.xml", p, "New_York_City.html", pars1);
    Trec t2 = tr.read(trecs.toString()).get(0);
    
    assertEquals(t1, t2);
  }
  
}
