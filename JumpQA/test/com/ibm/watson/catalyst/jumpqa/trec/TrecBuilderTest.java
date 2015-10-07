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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.ibm.watson.catalyst.jumpqa.answer.Pau;

@SuppressWarnings("javadoc")
public class TrecBuilderTest {
  
  Trec t1;
  final String file = "file";
  final String pauId = "id";
  final String pauTitle = "title";
  final String sourceDoc = "doc";
  List<String> pars;
  
  @Before
  public void setUp() {
    pars = new ArrayList<String>();
    pars.add("some text");
    t1 = new Trec(file, new Pau(pauId, pauTitle), sourceDoc, pars);
  }
  
  @Test
  public void testBuild() {
    TrecBuilder tb = new TrecBuilder();
    tb.setFile(file).setPauId(pauId).setPauTitle(pauTitle).setSourceDoc(sourceDoc)
        .setParagraphs(pars);
    Trec t2 = tb.build();
    assertThat(t1, equalTo(t2));
  }
  
}
