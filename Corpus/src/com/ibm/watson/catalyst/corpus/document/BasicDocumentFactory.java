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

import com.fasterxml.jackson.databind.JsonNode;
import com.ibm.watson.catalyst.corpus.document.Document;
import com.ibm.watson.catalyst.corpus.document.DocumentFactory;

public class BasicDocumentFactory extends DocumentFactory<Document> {
  
  public Document getDocument(JsonNode aNode) {
    File aFile = new File(aNode.get("file").asText());
    String aPauID = aNode.get("pauID").asText();
    String aPauTitle = aNode.get("pauTitle").asText();
    String aSourceDoc = aNode.get("sourceDoc").asText();
    List<String> aParagraphs = getParagraphs(aNode);
    return new Document(aFile, aPauID, aPauTitle, aSourceDoc, aParagraphs);
  }
  
  private static List<String> getParagraphs(JsonNode aNode) {
    List<String> result = new ArrayList<String>();
    for (JsonNode paragraph : aNode.get("paragraphs")) {
      result.add(paragraph.asText());
    }
    return result;
  }
  
}
