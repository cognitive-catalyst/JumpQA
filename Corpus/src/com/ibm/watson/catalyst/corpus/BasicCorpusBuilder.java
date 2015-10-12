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
package com.ibm.watson.catalyst.corpus;

import java.io.File;

import com.fasterxml.jackson.databind.JsonNode;
import com.ibm.watson.catalyst.corpus.BasicCorpus;
import com.ibm.watson.catalyst.corpus.CorpusBuilder;
import com.ibm.watson.catalyst.corpus.document.BasicDocumentFactory;
import com.ibm.watson.catalyst.corpus.document.Document;

/**
 * A builder object which creates a BasicCorpus object.
 * @author wabeason
 *
 */
public class BasicCorpusBuilder extends CorpusBuilder<Document, BasicCorpus> {
  
  @Override
  public BasicCorpus build() {
    if (_documents.size() == 0) {
      _documents = getDocumentsFromDirectory(_directory);
    }
    return new BasicCorpus(_name, _documents);
  }
  
  @Override
  protected Document getDocumentFromJson(JsonNode aNode) {
    return bdf.getDocument(aNode);
  }
  
  private static BasicDocumentFactory bdf = new BasicDocumentFactory();

  @Override
  protected Document getDocumentFromFile(File aFile) {
    return dr.read(aFile);
  }
  
}
