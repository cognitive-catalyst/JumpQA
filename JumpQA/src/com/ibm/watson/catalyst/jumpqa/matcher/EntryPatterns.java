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
package com.ibm.watson.catalyst.jumpqa.matcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.ibm.watson.catalyst.jumpqa.answer.Answer;
import com.ibm.watson.catalyst.jumpqa.answer.Candidate;
import com.ibm.watson.catalyst.jumpqa.trec.Trec;

/**
 * StringRegexMatcher holds a compiled string to match against. It delegates
 * common operations which would ordinarily result in boilerplate code related
 * to the Pattern class everywhere.
 * 
 * @author Will Beason
 * @version 0.1.2
 * @since 0.1.0
 *
 */
public class EntryPatterns implements IMatcher {
  
  private final Optional<Pattern> _titlePattern;
  private final Optional<Pattern> _answerPattern;
  private final Optional<Pattern> _textPattern;

  /**
   * Matches every TREC
   */
  @Override
  public boolean matches(Trec aTrec) {
    if (!_titlePattern.isPresent()) return true;
    String pauTitle = aTrec.getPauTitle();
    return _titlePattern.get().matcher(pauTitle).find();
  }
  
  @Override
  public boolean matches(Answer aAnswer) {
    if (!_answerPattern.isPresent()) return true;
    String answerText = aAnswer.getAnswerText();
    return _answerPattern.get().matcher(answerText).find();
  }
  
  @Override
  public boolean matches(Candidate aCandidate) {
    if (!_textPattern.isPresent()) return true;
    String candidateText = aCandidate.getMatchText();
    return _textPattern.get().matcher(candidateText).find();
  }
  
  /** 
   * TODO: Method description
   * @param aText the text to match
   * @return whether the text is matched by the text pattern
   */
  public boolean matchesText(String aText) {
    if (!_textPattern.isPresent()) return true;
    return _textPattern.get().matcher(aText).find();
  }
  
  /**
   * Instantiates a new StringRegexMatcher
   * @param aTitlePattern the pattern to check the PAU Title for
   * @param aAnswerPattern the pattern to check the answer for
   * @param aTextPattern the pattern to check the candidate for
   * 
   */
  public EntryPatterns(final Pattern aTitlePattern, final Pattern aAnswerPattern,
      final Pattern aTextPattern) {
    _titlePattern = Optional.ofNullable(aTitlePattern);
    _answerPattern = Optional.ofNullable(aAnswerPattern);
    _textPattern = Optional.ofNullable(aTextPattern);
  }
  
  /**
   * Instantiates a new StringRegexMatcher with no flags set.
   * 
   * @param aRegex A regular expression to match
   */
  public EntryPatterns(final String aRegex) {
    this(null, null, Pattern.compile(aRegex));
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    EntryPatterns other = (EntryPatterns) obj;
    if (!patternsEqual(other._titlePattern, this._titlePattern)) return false;
    if (!patternsEqual(other._answerPattern, this._answerPattern)) return false;
    if (!patternsEqual(other._textPattern, this._textPattern)) return false;
    return true;
  }
  
  private boolean patternsEqual(Optional<Pattern> op1, Optional<Pattern> op2) {
    if (op1.isPresent() ^ op2.isPresent()) return false;
    if (op1.isPresent() & op2.isPresent())  {
      Pattern p1 = op1.get();
      Pattern p2 = op2.get();
      if (!Objects.equals(p1.pattern(), p2.pattern())) return false;
      if (!Objects.equals(p1.flags(), p2.flags())) return false;
    }
    return true;
  }
  
  @Override
  public int hashCode() {
    HashCodeBuilder result = new HashCodeBuilder(SEED, MULTIPLY);
    if (_titlePattern.isPresent()) {
      Pattern p1 = _titlePattern.get();
      result.append(p1.pattern());
      result.append(p1.flags());
    }
    if (_answerPattern.isPresent()) {
      Pattern p2 = _answerPattern.get();
      result.append(p2.pattern());
      result.append(p2.flags());
    }
    if (_textPattern.isPresent()) {
      Pattern p3 = _textPattern.get();
      result.append(p3.pattern());
      result.append(p3.flags());
    }
    return result.hashCode();
  }
  
  /**
   * Splits a string about the regular expression.
   * 
   * @param aString the string to split
   * @return an array of the substring before the regular expression, the
   *         substring matched by the regular expression, and the substring
   *         after the regular expression.
   */
  public List<String> splitCandidateText(final String aString) {
    if (!_textPattern.isPresent()) throw new IllegalArgumentException();
    final Pattern p = _textPattern.get();
    return splitText(aString, p);
  }

  /** 
   * TODO: Method description
   * @param aString
   * @param p
   * @return
   */
  private static List<String> splitText(final String aString, final Pattern p) {
    List<String> result = new ArrayList<String>();
    final String[] beforeAfter = p.split(aString, 2);
    result.add(beforeAfter[0].trim());
    
    final Matcher m = p.matcher(aString);
    if (m.find()) {
      result.add(m.group().trim());
      result.add(beforeAfter[1].trim());
    }
    return result;
  }
  
  private static final int SEED = 657839053;
  private static final int MULTIPLY = 893379931;
  
  
}
