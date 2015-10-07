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

import com.ibm.watson.catalyst.jumpqa.answer.Answer;
import com.ibm.watson.catalyst.jumpqa.answer.Candidate;
import com.ibm.watson.catalyst.jumpqa.trec.Trec;

/**
 * A class for testing if an array of strings meets criteria. Intended for cases
 * such as making sure both the sentence is long enough and the PAU title
 * contains a specific word.
 * 
 * @author Will Beason
 * @version 0.1.2
 * @since 0.1.0
 *
 */
public interface IMatcher {
  
  /**
   * TODO: Method description
   * 
   * @param aTrec the TREC to evaluate
   * @return whether the TREC is matched
   */
  public boolean matches(Trec aTrec);
  
  /**
   * TODO: Method description
   * 
   * @param aAnswer the answer to evaluate
   * @return whether the answer is matched
   */
  public boolean matches(Answer aAnswer);
  
  /**
   * TODO: Method description
   * 
   * @param aCandidate the candidate to evaluate
   * @return whether the candidate is matched
   */
  public boolean matches(Candidate aCandidate);
  
}
