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

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ibm.watson.catalyst.corpus.document.Document;
import com.ibm.watson.catalyst.corpus.util.JsonUtil;

/**
 * An object to hold a corpus. Essentially, this is a collection of Document objects with a name.
 * 
 * @author wabeason
 *
 * @param <E>
 */
public class Corpus<E extends Document> {
  
  public Corpus(String aName, List<E> aDocuments) {
    _name = aName;
    _documents = aDocuments;
  }
  
  public int size() {
    return _documents.size();
  }
  
  /**
   *  Converts the corpus to JSON form
   * @return a JSON representation of the Corpus
   */
  public ObjectNode toObjectNode() {
    ObjectNode result = MAPPER.createObjectNode();
    result.put("corpus-name", _name);
    
    result.set("documents", JsonUtil.toArrayNodeJsonable(_documents));
    
    return result;
  }
  
  public E getDocument(int index) { return _documents.get(index); }
  public List<E> getDocuments() { return _documents; }
  
  // ------------------------------------------------------------------------------------------ //
  // Private
  // ------------------------------------------------------------------------------------------ //
  private final String _name;
  protected final List<E> _documents;
  private static final ObjectMapper MAPPER = new ObjectMapper();
  
}
