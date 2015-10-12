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
package com.ibm.watson.catalyst.jumpqa.trec;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.ibm.watson.catalyst.jumpqa.answer.Answer;
import com.ibm.watson.catalyst.jumpqa.answer.Pau;
import com.ibm.watson.catalyst.jumpqa.splitter.ISplitter;
import com.ibm.watson.catalyst.jumpqa.splitter.SplitterFactory;
import com.ibm.watson.catalyst.jumpqa.splitter.SplitterFactory.Size;
import com.ibm.watson.catalyst.jumpqa.util.ISplittable;

/**
 * A class for holding information about TRECs
 * 
 * @author Will Beason
 * @version 0.1.2
 * @since 0.1.0
 *
 */
public class Trec implements ISplittable<Answer> {
  
  private final String _file;
  private final Pau _pau;
  private final String _sourceDoc;
  private final List<String> _paragraphs;
  
  /**
   * Instantiates a Trec object, a representation of a TREC document
   * 
   * @param aFile the original file which held the TREC in xml form
   * @param aPau the TREC's PAU information
   * @param aSourceDoc the document from which the TREC was generated
   * @param paragraphs a list of paragraphs in the TREC
   */
  public Trec(final String aFile, final Pau aPau, final String aSourceDoc,
      final List<String> paragraphs) {
    _file = aFile;
    _pau = new Pau(aPau);
    _sourceDoc = aSourceDoc;
    _paragraphs = paragraphs;
  }
  
  /**
   * @param aPau the TREC's PAU information
   * @param paragraphs the TREC's text
   */
  public Trec(final Pau aPau, final List<String> paragraphs) {
    _file = "";
    _pau = aPau;
    _paragraphs = paragraphs;
    _sourceDoc = "";
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Trec other = (Trec) obj;
    if (!Objects.equals(other._file, this._file)) return false;
    if (!Objects.equals(other._pau, this._pau)) return false;
    if (!Objects.equals(other._sourceDoc, this._sourceDoc)) return false;
    if (!Objects.equals(other._paragraphs, this._paragraphs)) return false;
    return true;
  }
  
  /**
   * Gets the file which held the TREC
   * 
   * @return the file
   */
  public String getFile() {
    return _file;
  }
  
  /**
   * Gets the list of paragraphs in the TREC
   * 
   * @return the paragraphs
   */
  public List<String> getParagraphs() {
    return _paragraphs;
  }
  
  /**
   * Gets the PAU ID of the TREC
   * 
   * @return the PAU ID
   */
  public Pau getPau() {
    return new Pau(_pau);
  }
  
  /** 
   * TODO: Method description
   * @return the PAU Title
   */
  public String getPauTitle() {
    return _pau.getPauTitle();
  }
  
  /** 
   * TODO: Method description
   * @return the PAU ID
   */
  public String getPauId() {
    return _pau.getPauId();
  }
  
  /**
   * Gets the document from which the TREC was generated
   * 
   * @return the document from which the TREC was generated
   */
  public String getSourceDoc() {
    return _sourceDoc;
  }
  
  /**
   * TODO: Method description
   * 
   * @param aSize the size of the answers to split the TREC into
   * @return the answers
   */
  @Override
  public List<Answer> splitInto(Size aSize) {
    ISplitter splitter = SplitterFactory.build(aSize);
    List<String> strings = splitter.split(this._paragraphs);
    List<Answer> result = new ArrayList<Answer>();
    strings.stream().forEachOrdered((s) -> result.add(new Answer(s, _pau)));
    return result;
  }
  
  @Override
  public int hashCode() {
    return (new HashCodeBuilder(SEED, MULTIPLY)).append(_file).append(_pau).append(_sourceDoc)
        .append(_paragraphs).hashCode();
  }
  
  private static final int SEED = 1559724181;
  private static final int MULTIPLY = 400986757;
  
}
