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
package com.ibm.watson.catalyst.corpus.tfidf.document;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ibm.watson.catalyst.corpus.tfidf.term.TemplateMatch;

public final class DocumentMatcher {
  
  public DocumentMatcher(TermDocument aDocument) {
    _document = aDocument;
  }
  
  public List<TemplateMatch> getParagraphMatches(Pattern aPattern, String qStart, String qEnd) {
    List<TemplateMatch> result = new ArrayList<TemplateMatch>();
    List<String> paragraphs = _document.getParagraphs();
    
    for (String paragraph : paragraphs) {
      result.addAll(getSentenceMatches(aPattern, paragraph, qStart, qEnd));
    }
    
    return result;
  }
  
  private static Pattern sentenceBoundary = Pattern.compile("(?<=(\\)|[a-z]{2}|[A-Z]{2})[\\.\\?\\!])(\\[\\d+\\])?\\s+");
  private static List<TemplateMatch> getSentenceMatches(Pattern aPattern, String aParagraph,
      String qStart, String qEnd) {
    List<TemplateMatch> result = new ArrayList<TemplateMatch>();
    String[] sentences = sentenceBoundary.split(aParagraph);
    for (String sentence : sentences) {
      sentence = sentence.trim();
      Matcher m = aPattern.matcher(sentence);
      if (m.find()) {
        String match = sentence.substring(m.start(), m.end());
        //if (match.split(" ").length > 4) continue;
        if (match.startsWith("But ")) match = match.substring(4);
        if (match.startsWith("(")) match = match.substring(1);
        result.add(new TemplateMatch(match, sentence, qStart, qEnd, sentence));
      }
    }
    return result;
  }
  
  
  private final TermDocument _document;
  
}
