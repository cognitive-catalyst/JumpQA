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
package com.ibm.watson.catalyst.corpus.tfidf.document;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ibm.watson.catalyst.corpus.tfidf.document.TermDocument;

public class TermDocumentTest {

  @Test
  public void testAddParagraphs() {
    List<String> paragraphs = new ArrayList<String>();
    paragraphs.add("The apple.");
    TermDocument d = new TermDocument("A : B", paragraphs);
    List<String> newParagraphs = new ArrayList<String>();
    newParagraphs.add("The banana.");
    d.addParagraphs(newParagraphs);
    
    List<String> dParagraphs = d.getParagraphs();
    assertEquals(dParagraphs.size(), 2);
    assertEquals(d.getNumDocs(), 1);
    assertEquals(d.getParagraphs().get(0), "The apple.");
    assertEquals(d.getParagraphs().get(1), "The banana.");
    
  }

  @Test
  public void testTermDocumentStringListOfString() {
    List<String> paragraphs = new ArrayList<String>();
    paragraphs.add("The apple.");
    paragraphs.add("The banana.");
    TermDocument d = new TermDocument("A : B", paragraphs);
    
    assertEquals(d.getPauTitle(), "A : B");
    assertEquals(d.getParagraphs(), paragraphs);
    
  }

  @Test
  public void testContainsTerm() {
    List<String> paragraphs = new ArrayList<String>();
    paragraphs.add("The apple.");
    paragraphs.add("The banana.");
    TermDocument d = new TermDocument("A : B", paragraphs);
    
    assertTrue(d.containsTerm("the"));
    assertTrue(d.containsTerm("apple"));
    assertTrue(d.containsTerm("banana"));
    assertFalse(d.containsTerm("cantaloupe"));
  }

  @Test
  public void testFrequency() {
    List<String> paragraphs = new ArrayList<String>();
    paragraphs.add("The the apple.");
    TermDocument d = new TermDocument("A : B", paragraphs);
    
    assertEquals(d.frequency("apple"), 1);
    assertEquals(d.frequency("the"), 2);
    
    List<String> paragraphs2 = new ArrayList<String>();
    paragraphs2.add("The apple.");
    paragraphs2.add("The banana.");
    TermDocument d2 = new TermDocument("A : B", paragraphs2);
    
    assertEquals(d2.frequency("apple"), 1);
    assertEquals(d2.frequency("banana"), 1);
    assertEquals(d2.frequency("the"), 2);
  }

  @Test
  public void testAugmentedFrequency() {
    List<String> paragraphs = new ArrayList<String>();
    paragraphs.add("The apple.");
    paragraphs.add("The banana.");
    TermDocument d = new TermDocument("A : B", paragraphs);
    
    assertEquals(d.augmentedFrequency("apple"), 0.75, 0);
  }

  @Test
  public void testCombineWith() {
    List<String> paragraphs = new ArrayList<String>();
    paragraphs.add("The the apple.");
    TermDocument d = new TermDocument("A : B", paragraphs);

    List<String> paragraphs2 = new ArrayList<String>();
    paragraphs2.add("The apple.");
    paragraphs2.add("The banana.");
    TermDocument d2 = new TermDocument("A : B", paragraphs2);
    
    d.combineWith(d2);
    
    assertEquals(d.frequency("the"), 4);
    
  }

}
