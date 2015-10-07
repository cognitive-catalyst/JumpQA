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
package com.ibm.watson.catalyst.jumpqa.answer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.ibm.watson.catalyst.jumpqa.entry.IGroundTruthEntry;
import com.ibm.watson.catalyst.objectio.misc.IPrintable;
import com.ibm.watson.catalyst.objectio.writers.IWritable;

/**
 * Holds data needed to ingest a question into a Watson Q&A instance.
 * 
 * @author Will Beason
 * @version 0.1.1
 * @since 0.1.0
 *
 */
public class GroundTruthEntry implements IGroundTruthEntry, IWritable, IPrintable {
  
  private final String _id;
  private final QuestionAnswerPair _qaPair;
  private final GroundTruthState _state;
  private final String _templateId;
  
  /**
   * The states a question may be in on a Watson Q&A instance
   * 
   * @author Will Beason
   * @version 0.1.1
   * @since 0.1.1
   *
   */
  public enum GroundTruthState {
    /**
     * The question has no linked PAU and no answer
     */
    DRAFT,
    /**
     * The question is not linked to a document, but has an answer
     */
    NEEDCORPUS,
    /**
     * The question needs no further work
     */
    APPROVED,
    /**
     * The question should be reviewed and approved by a human
     */
    NOT_REVIEWED,
    /**
     * The question has been rejected by a human
     */
    REJECTED
  }
  
  /**
   * @param id the id of the ground truth entry
   * @param aQaPair the question/answer pair
   * @param aState the state of the question
   */
  public GroundTruthEntry(String id, QuestionAnswerPair aQaPair, GroundTruthState aState) {
    _id = id;
    _qaPair = aQaPair;
    _state = aState;
    _templateId = "";
  }
  
  /**
   * @param id the id of the ground truth entry
   * @param aQaPair the question/answer pair
   * @param aState the state of the question
   * @param rest the remaining arguments
   */
  public GroundTruthEntry(String id, QuestionAnswerPair aQaPair, GroundTruthState aState,
      String... rest) {
    _id = id;
    _qaPair = aQaPair;
    _state = aState;
    _templateId = (rest.length > 0) ? rest[0] : "";
  }
  
  @Override
  public StringBuilder toStringBuilder() {
    StringBuilder sb = new StringBuilder();
    sb.append(_id).append(",").append(_qaPair.toStringBuilder()).append(",").append(_state);
    return sb;
  }
  
  @Override
  public String toString() {
    return this.toStringBuilder().toString();
  }
  
  private static List<String> headerList;
  static {
    List<String> hList = new ArrayList<String>();
    hList.add("ID");
    hList.addAll((new QuestionAnswerPair()).getHeaderList());
    hList.add("State");
    headerList = Collections.unmodifiableList(hList);
  }
  
  @Override
  public List<String> getHeaderList() {
    return headerList;
  }
  
  @Override
  public List<String> toList() {
    List<String> result = new ArrayList<String>();
    result.add(_id);
    result.addAll(_qaPair.toList());
    result.add(_state.toString());
    return result;
  }
  
  @Override
  public Iterator<String> iterator() {
    return this.toList().iterator();
  }
  
  @Override
  public int hashCode() {
    return (new HashCodeBuilder(SEED, MULTIPLY)).append(_id).append(_qaPair).append(_state)
        .append(_templateId).hashCode();
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    GroundTruthEntry other = (GroundTruthEntry) obj;
    if (!Objects.equals(other._id, this._id)) return false;
    if (!Objects.equals(other._qaPair, this._qaPair)) return false;
    if (!Objects.equals(other._state, this._state)) return false;
    if (!Objects.equals(other._templateId, this._templateId)) return false;
    return true;
  }
  
  private static final int SEED = 354469711;
  private static final int MULTIPLY = 1363866467;
  
}
