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
package com.ibm.watson.catalyst.corpus.tfidf.term;

import java.util.ArrayList;
import java.util.List;

public final class TemplateMatch {
  
  public TemplateMatch(String aMatch, String aSentence, String qStart, String qEnd, String aAnswer) {
    _match = aMatch;
    _sentence = aSentence;
    _question = qStart + _match + qEnd;
    _answers.add(aAnswer);
  }
  
  public boolean addAnswer(String aAnswer) {
    if (_answers.contains(aAnswer)) {
      return false;
    } else {
      _answers.add(aAnswer);
      return true;
    }
  }
  
  public boolean sameQuestion(TemplateMatch tm) {
    return tm.getQuestion().equals(_question);
  }
  
  public void addAnswers(TemplateMatch tm) {
    for (String answer : tm.getAnswers()) {
      if (!hasAnswer(answer)) addAnswer(answer);
    }
  }
  
  public String toString() {
    StringBuffer result = new StringBuffer();
    
    for (String answer : _answers) {
      result.append("\"")
            .append(_question)
            .append("\",\"")
            .append(answer)
            .append("\"\n");
    }
    
    return result.toString();
  }
  
  public String getMatch() { return _match; }
  public String getSentence() { return _sentence; }
  public String getQuestion() { return _question; }
  public List<String> getAnswers() { return _answers; }
  
  // Private

  private final String _match;
  private final String _sentence;
  private final String _question;
  private final List<String> _answers = new ArrayList<String>();
  
  private boolean hasAnswer(String answer) {
    return _answers.contains(answer);
  }
  
}
