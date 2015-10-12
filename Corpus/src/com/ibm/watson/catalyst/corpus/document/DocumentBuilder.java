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
package com.ibm.watson.catalyst.corpus.document;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.ibm.watson.catalyst.corpus.document.Document;
import com.ibm.watson.catalyst.corpus.document.DocumentBuilder;

public class DocumentBuilder {
  
  public DocumentBuilder setFile(File aFile) {
    _file = aFile;
    return this;
  }
  
  public DocumentBuilder setPauID(String pauID) {
    _pauID = pauID;
    return this;
  }
  
  public DocumentBuilder setPauTitle(String pauTitle) {
    _pauTitle = pauTitle;
    return this;
  }
  
  public DocumentBuilder setSourceDoc(String aSourceDoc) {
    _sourceDoc = aSourceDoc;
    return this;
  }
  
  public DocumentBuilder setParagraphs(List<String> paragraphs) {
    _paragraphs = paragraphs;
    return this;
  }
  
  public Document build() {
    return new Document(_file, _pauID, _pauTitle, _sourceDoc, _paragraphs);
  }
  
  // ------------------------------------------------------------------------------------------ //
  // Private
  // ------------------------------------------------------------------------------------------ //
  private File _file = new File("");
  private String _pauID = "";
  private String _pauTitle = "";
  private String _sourceDoc = "";
  private List<String> _paragraphs = new ArrayList<String>();
  
}
