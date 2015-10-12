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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ibm.watson.catalyst.corpus.document.Document;
import com.ibm.watson.catalyst.corpus.util.JsonUtil;
import com.ibm.watson.catalyst.corpus.util.Jsonable;

public class Document implements Jsonable {
  
  public Document(Document aDocument) {
    this(aDocument.getFile(), aDocument.getPauID(), aDocument.getPauTitle(), aDocument.getSourceDoc(), aDocument.getParagraphs());
  }
  
  public Document(String aPauTitle) {
    this(aPauTitle, new ArrayList<String>());
  }
  
  public Document(String aPauTitle, List<String> aParagraphs) {
    this(null, null, aPauTitle, null, aParagraphs);
  }
  
  public Document(File aFile, String aPauID, String aPauTitle, String aSourceDoc, List<String> aParagraphs) {
    _file = aFile;
    _pauID = aPauID;
    _pauTitle = aPauTitle;
    _sourceDoc = aSourceDoc;
    _paragraphs = aParagraphs;
  }
  
  public ObjectNode toJson() {
    ObjectNode result = MAPPER.createObjectNode();
    
    result.put("file", _file.toString());
    result.put("pauID", _pauID);
    result.put("pauTitle", _pauTitle);
    result.put("sourceDoc", _sourceDoc);
    result.set("paragraphs", JsonUtil.toArrayNodeString(_paragraphs));
    
    return result;
  }
  
  public File getFile() { return _file; }
  public String getPauID() { return _pauID; }
  public String getPauTitle() { return _pauTitle; }
  public String getSourceDoc() { return _sourceDoc; }
  public List<String> getParagraphs() { return _paragraphs; }
  
  // ------------------------------------------------------------------------------------------ //
  // Protected
  // ------------------------------------------------------------------------------------------ //
  private final File _file;
  private final String _pauID;
  private final String _sourceDoc;
  protected final String _pauTitle;
  protected final List<String> _paragraphs;
  
  private static final ObjectMapper MAPPER = new ObjectMapper();
  
}
