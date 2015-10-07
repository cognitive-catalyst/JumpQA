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

import com.ibm.watson.catalyst.jumpqa.answer.GroundTruthEntry.GroundTruthState;

/**
 * A class which builds template matches, the Q&A pairs to be ingested by a
 * Watson Q&A instance.
 * 
 * @author Will Beason
 * @version 0.1.1
 * @since 0.1.0
 *
 */
public class GroundTruthEntryBuilder {
  
  private GroundTruthEntryBuilder() {}
  
  /**
   * Builds a TemplateMatch from the current data in the TemplateMatchFactory
   * 
   * @return a new TemplateMatch
   */
  public GroundTruthEntry build() {
    id++;
    return new GroundTruthEntry(id.toString(), qaPair, state, templateId);
  }
  
  /**
   * TODO: Method description
   */
  public void reset() {
    templateId = "";
    qaPair = new QuestionAnswerPair("", "");
    id = 1000000;
  }
  
  /**
   * TODO: Method description
   * 
   * @param aId the template Id
   * @return the builder
   */
  public GroundTruthEntryBuilder setTemplateId(String aId) {
    templateId = aId;
    return this;
  }
  
  /**
   * TODO: Method description
   * 
   * @param aQaPair the question/answer pair
   * @return the builder
   */
  public GroundTruthEntryBuilder setQaPair(QuestionAnswerPair aQaPair) {
    qaPair = new QuestionAnswerPair(aQaPair);
    return this;
  }
  
  /**
   * TODO: Method description
   * 
   * @param aState the state
   * @return the builder
   */
  public GroundTruthEntryBuilder setState(GroundTruthState aState) {
    state = aState;
    return this;
  }
  
  private String templateId = "";
  private QuestionAnswerPair qaPair = new QuestionAnswerPair();
  private GroundTruthState state = GroundTruthState.APPROVED;
  
  private static volatile Integer id = 1000000;
  private static final GroundTruthEntryBuilder INSTANCE;
  static {
    try {
      INSTANCE = new GroundTruthEntryBuilder();
    } catch (final RuntimeException e) {
      throw new RuntimeException("Error making GroundTruthEntryBuilder instance", e);
    }
  }
  
  /**
   * Returns the TemplateMatchFactory singleton instance
   * 
   * @return the TemplateMatchFactory singleton
   */
  public static GroundTruthEntryBuilder getInstance() {
    return INSTANCE;
  }
  
}
