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
package com.ibm.watson.catalyst.corpus2json;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.ibm.watson.catalyst.corpus.BasicCorpus;
import com.ibm.watson.catalyst.corpus.BasicCorpusBuilder;
import com.ibm.watson.catalyst.corpus.Corpus;
import com.ibm.watson.catalyst.util.baseproperties.BaseProperties;

// Converts a directory of PAUs to a single JSON
public final class Corpus2Json {
  
  public static void toJsonFile(Corpus<?> c, String f) {
    try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8"))) {
      bw.write(c.toObjectNode().toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  private static BaseProperties PROPERTIES;
  
  public static void main(String[] args) {
    BaseProperties.setInstance(args, "sample/test.properties");
    PROPERTIES = BaseProperties.getInstance();
    
    String input = PROPERTIES.getProperty("input", "sample/");
    BasicCorpus c = (new BasicCorpusBuilder()).setDirectory(input).build();
    
    String output = PROPERTIES.getProperty("output", "sample/output.json");
    toJsonFile(c, output);
  }
  
}
