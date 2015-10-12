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

import com.ibm.watson.catalyst.jumpqa.splitter.ISplitter;
import com.ibm.watson.catalyst.jumpqa.splitter.SplitterFactory;
import com.ibm.watson.catalyst.jumpqa.splitter.SplitterFactory.Size;
import com.ibm.watson.catalyst.jumpqa.util.ISplittable;
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
public class Answer implements IWritable, IPrintable, ISplittable<Candidate> {
  
  /**
   * @param aAnswerText the answer
   * @param aPau the pau from which the answer originates
   */
  public Answer(String aAnswerText, Pau aPau) {
    _answerText = aAnswerText;
    _pau = aPau;
  }
  
  /**
   * @param aAnswerText the answer
   */
  public Answer(String aAnswerText) {
    this(aAnswerText, new Pau());
  }
  
  /**
   * 
   */
  public Answer() {
    this("", new Pau());
  }
  
  /**
   * @param aAnswer the answer
   */
  public Answer(Answer aAnswer) {
    this(aAnswer._answerText, aAnswer._pau);
  }
  
  /**
   * TODO: Method description
   * 
   * @return the answer text
   */
  public String getAnswerText() {
    return _answerText;
  }
  
  @Override
  public StringBuilder toStringBuilder() {
    StringBuilder sb = new StringBuilder();
    sb.append(_answerText).append(",").append(_pau.toStringBuilder());
    return sb;
  }
  
  private static List<String> headerList;
  static {
    List<String> hList = new ArrayList<String>();
    hList.add("Answer Text");
    hList.addAll((new Pau()).getHeaderList());
    headerList = Collections.unmodifiableList(hList);
  }
  
  @Override
  public List<String> getHeaderList() {
    return headerList;
  }
  
  @Override
  public List<String> toList() {
    List<String> result = new ArrayList<String>();
    result.add(_answerText);
    result.addAll(_pau.toList());
    return result;
  }
  
  @Override
  public Iterator<String> iterator() {
    return this.toList().iterator();
  }
  
  @Override
  public List<Candidate> splitInto(Size aSize) {
    ISplitter splitter = SplitterFactory.build(aSize);
    List<String> strings = splitter.split(_answerText);
    List<Candidate> result = new ArrayList<Candidate>();
    strings.stream().forEachOrdered((s) -> result.add(new Candidate(s, this)));
    return result;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Answer other = (Answer) obj;
    if (!Objects.equals(other._answerText, this._answerText)) return false;
    if (!Objects.equals(other._pau, this._pau)) return false;
    return true;
  }
  
  @Override
  public int hashCode() {
    return (new HashCodeBuilder(SEED, MULTIPLY)).append(_answerText).append(_pau).hashCode();
  }
  
  private static final int SEED = 1669124357;
  private static final int MULTIPLY = 964809889;
  
  private final String _answerText;
  private final Pau _pau;
  
}
