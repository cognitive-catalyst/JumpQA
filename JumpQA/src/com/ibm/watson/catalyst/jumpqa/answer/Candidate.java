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

/**
 * TODO: Class description
 * 
 * @author Will Beason
 * @version 0.1.1
 * @since 0.1.1
 *
 */
public class Candidate {
  
  private final Answer _answer;
  private final String _matchText;
  
  /**
   * @param matchText the text to test for matches
   * @param aAnswer the answer
   */
  public Candidate(String matchText, Answer aAnswer) {
    _answer = new Answer(aAnswer);
    _matchText = matchText;
  }
  
  /**
   * TODO: Method description
   * 
   * @return the match text
   */
  public String getMatchText() {
    return _matchText;
  }
  
  /**
   * TODO: Method description
   * 
   * @return the answer
   */
  public Answer getAnswer() {
    return new Answer(_answer);
  }
  
}
