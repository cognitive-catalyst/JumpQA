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
package com.ibm.watson.catalyst.corpus.util;

import java.util.Collection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class JsonUtil {
  private static final ObjectMapper MAPPER = new ObjectMapper();
  
  public static ArrayNode toArrayNodeString(Collection<String> aCollection) {
    ArrayNode result = MAPPER.createArrayNode();
    aCollection.forEach((aString) -> result.add(aString));
    return result;
  }
  
  public static ArrayNode toArrayNodeJsonable(Collection<? extends Jsonable> aCollection) {
    ArrayNode result = MAPPER.createArrayNode();
    aCollection.forEach((aJsonable) -> result.add(aJsonable.toJson()));
    return result;
  }
  
}
