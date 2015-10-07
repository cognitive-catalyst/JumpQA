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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ibm.watson.catalyst.corpus.document.Document;

public class CombineableDocument extends Document {
  
  public CombineableDocument(Document aDocument) {
    super(aDocument);
    _context = genContext(_pauTitle);
  }
  
  public CombineableDocument(String aPauTitle) {
    this(aPauTitle, new ArrayList<String>());
  }
  
  public CombineableDocument(String aPauTitle, List<String> aParagraphs) {
    this(null, null, aPauTitle, aParagraphs);
  }
  
  public CombineableDocument(File aFile, String aPauID, String aPauTitle, List<String> aParagraphs) {
    super(aFile, aPauID, aPauTitle, null, aParagraphs);
    _context = genContext(_pauTitle);
  }
  
  public int getNumDocs() { return _numDocs; }
  
  public void combineWith(CombineableDocument d) {
    addParagraphs(d.getParagraphs());
    _numDocs += d.getNumDocs();
  }

  public String[] getContext() { return _context; }

  public String getContext(Integer level) {
    if (level >= _context.length) {
      return "";
    } else {
      return _context[level];
    }
  }
  
  // ------------------------------------------------------------------------------------------ //
  // Private
  // ------------------------------------------------------------------------------------------ //
  private int _numDocs = 1;
  private final String[] _context;
  
  public static String[] genContext(String aPauTitle) {
    String[] result = aPauTitle.split(" : ");
    if ((result.length > 1) && (result[0].equals(result[1]))) {
      result = Arrays.copyOfRange(result, 1, result.length);
    }
    return result;
  }
  
  protected void addParagraphs(List<String> newParagraphs) {
    _paragraphs.addAll(newParagraphs);
  }
  
}
