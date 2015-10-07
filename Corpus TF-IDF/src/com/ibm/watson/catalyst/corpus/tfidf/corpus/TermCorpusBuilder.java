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
package com.ibm.watson.catalyst.corpus.tfidf.corpus;

import java.io.File;

import com.fasterxml.jackson.databind.JsonNode;
import com.ibm.watson.catalyst.corpus.CorpusBuilder;
import com.ibm.watson.catalyst.corpus.tfidf.document.DocumentCombiner;
import com.ibm.watson.catalyst.corpus.tfidf.document.TermDocument;
import com.ibm.watson.catalyst.corpus.tfidf.document.TermDocumentFactory;

public final class TermCorpusBuilder extends CorpusBuilder<TermDocument, TermCorpus> {
  
  public TermCorpusBuilder() { }
  public TermCorpusBuilder(File aDirectory) {
    _directory = aDirectory;
  }
  
  public TermCorpus build() {
    if (_documents.size() == 0) {
      _documents = getDocumentsFromDirectory(_directory);
    }
    System.out.println("Combining Documents.");
    _documents = _documentCombiner.combine(_documents);
    TermCorpus result = new TermCorpus(_name, _documents);
    return result;
  }
  
  public TermCorpusBuilder setDocumentCombiner(int aLevel, int aMin) {
    return setDocumentCombiner(new DocumentCombiner<TermDocument>(aLevel, aMin));
  }
  
  public TermCorpusBuilder setDocumentCombiner(DocumentCombiner<TermDocument> aDocumentCombiner) {
    _documentCombiner = aDocumentCombiner;
    return this;
  }
  
  // ------------------------------------------------------------------------------------------ //
  // Private
  // ------------------------------------------------------------------------------------------ //
  private File _directory = new File("");
  private String _name = "";
  private DocumentCombiner<TermDocument> _documentCombiner = new DocumentCombiner<TermDocument>(-1, 0);
  
  @Override
  protected TermDocument getDocumentFromJson(JsonNode aNode) {
    return tdf.getDocument(aNode);
  }
  
  private static TermDocumentFactory tdf = new TermDocumentFactory();

  @Override
  protected TermDocument getDocumentFromFile(File aFile) {
    return new TermDocument(dr.read(aFile));
  }
  
  
  
}
