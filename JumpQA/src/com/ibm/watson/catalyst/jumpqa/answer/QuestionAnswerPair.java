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
/**
 * 
 */
package com.ibm.watson.catalyst.jumpqa.answer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.ibm.watson.catalyst.objectio.misc.IPrintable;
import com.ibm.watson.catalyst.objectio.writers.IWritable;

/**
 * TODO: Class description
 * 
 * @author Will Beason
 * @version 0.1.1
 * @since 0.1.1
 *
 */
public class QuestionAnswerPair implements IWritable, IPrintable {
  
  /**
   * @param aQuestionText the text of the question
   * @param aAnswer the answer
   */
  public QuestionAnswerPair(String aQuestionText, Answer aAnswer) {
    _questionText = aQuestionText;
    _answer = new Answer(aAnswer);
  }
  
  /**
   * 
   */
  public QuestionAnswerPair() {
    this("", "");
  }
  
  /**
   * @param aQaPair the question/answer pair
   */
  public QuestionAnswerPair(QuestionAnswerPair aQaPair) {
    _questionText = aQaPair._questionText;
    _answer = aQaPair._answer;
  }
  
  /**
   * @param aQuestionText the text of the question
   * @param aAnswer the text of the answer
   */
  public QuestionAnswerPair(String aQuestionText, String aAnswer) {
    _questionText = aQuestionText;
    _answer = new Answer(aAnswer);
  }
  
  @Override
  public StringBuilder toStringBuilder() {
    StringBuilder sb = new StringBuilder();
    sb.append(_questionText).append(",").append(_answer.toStringBuilder());
    return sb;
  }
  
  @Override
  public String toString() {
    return this.toStringBuilder().toString();
  }
  
  private static List<String> headerList;
  static {
    List<String> hList = new ArrayList<String>();
    hList.add("Question Text");
    hList.addAll((new Answer()).getHeaderList());
    headerList = Collections.unmodifiableList(hList);
  }
  
  @Override
  public List<String> getHeaderList() {
    return headerList;
  }
  
  @Override
  public List<String> toList() {
    List<String> result = new ArrayList<String>();
    result.add(_questionText);
    result.addAll(_answer.toList());
    return result;
  }
  
  @Override
  public Iterator<String> iterator() {
    return this.toList().iterator();
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    QuestionAnswerPair other = (QuestionAnswerPair) obj;
    if (!Objects.equals(other._answer, this._answer)) return false;
    if (!Objects.equals(other._questionText, this._questionText)) return false;
    return true;
  }
  
  @Override
  public int hashCode() {
    return (new HashCodeBuilder(SEED, MULTIPLY)).append(_answer).append(_questionText).hashCode();
  }
  
  private static final int SEED = 1390668661;
  private static final int MULTIPLY = 761396927;
  
  private final String _questionText;
  private final Answer _answer;
  
}
