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

import com.ibm.watson.catalyst.corpus.Corpus;
import com.ibm.watson.catalyst.corpus.document.Document;

/**
 * A basic representation of a corpus populated with basic document objects.
 * @author wabeason
 *
 */
public final class BasicCorpus extends Corpus<Document> {

  public BasicCorpus(String aName, List<Document> aDocuments) {
    super(aName, aDocuments);
  }

}
