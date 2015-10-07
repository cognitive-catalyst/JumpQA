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
package com.ibm.watson.catalyst.corpus.tfidf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.ibm.watson.catalyst.corpus.tfidf.term.Term;

public final class Template {
  
  public static Pattern getTemplatePattern(JsonNode document, String before, String after) {
    JsonNode terms = document.get("words");
    List<String> words = new ArrayList<String>();
    for (JsonNode termNode : terms) {
      if (termNode.get("frequency").asInt() < 5) continue;
      if (termNode.get("string").asText().length() < 2) continue;
      Term t = new Term(termNode);
      words.add(t.getTerm());
    }
    
    StringBuffer sb = new StringBuffer("");
    sb.append(before)
      .append(makeOr(words))
      .append(after);
    
    Pattern p = Pattern.compile(sb.toString(), Pattern.CASE_INSENSITIVE);
    return p;
  }
  
  public static Pattern getTemplatePattern(File file, String before, String after) {
    StringBuffer sb = new StringBuffer();
    
    List<String> verbs = new ArrayList<String>();
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      while (br.ready()) verbs.add(br.readLine());
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("Could not find " + file.toString());
    } catch (IOException e) {
      throw new IllegalStateException("IO Error reading " + file.toString());
    }
    
    sb.append(before)
      .append(makeOr(verbs))
      .append(after);
    
    Pattern p = Pattern.compile(sb.toString(), Pattern.CASE_INSENSITIVE);
    return p;
  }
  
  private static StringBuffer makeOr(List<String> aList) {
    StringBuffer result = new StringBuffer();
    result.append("(");
    Iterator<String> wordsIterator = aList.iterator();
    while(wordsIterator.hasNext()) {
      result.append(wordsIterator.next());
      if (wordsIterator.hasNext()) result.append("|");
    }
    result.append(")");
    return result;
  }
  
}
