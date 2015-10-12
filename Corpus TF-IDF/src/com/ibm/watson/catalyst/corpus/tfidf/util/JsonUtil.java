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
package com.ibm.watson.catalyst.corpus.tfidf.util;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ibm.watson.catalyst.corpus.tfidf.term.Term;

public class JsonUtil {
  public static void main(String[] args) 
  {
    
    
    Term t = new Term("aterm", 100, 5.236);
    ObjectNode on = t.toObjectNode();
    System.out.println(on.toString());
    
  }
}
