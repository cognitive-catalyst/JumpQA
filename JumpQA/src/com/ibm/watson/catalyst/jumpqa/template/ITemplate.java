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
package com.ibm.watson.catalyst.jumpqa.template;

import java.util.Collection;
import java.util.List;

import com.ibm.watson.catalyst.jumpqa.answer.Pau;
import com.ibm.watson.catalyst.jumpqa.entry.IGroundTruthEntry;
import com.ibm.watson.catalyst.jumpqa.trec.Trec;

/**
 * TODO: Class description
 * 
 * @author Will Beason
 * @version 0.1.1
 * @since 0.1.0
 *
 */
public interface ITemplate {
  
  /**
   * Generates a collection of matches from a collection of TRECs
   * 
   * @param trecs the TRECs to generate matches for
   * @return a collection of ground truth entries
   */
  public Collection<IGroundTruthEntry> genEntriesFromTrecs(Collection<Trec> trecs);
  
  /**
   * Generates matches from a given string.
   * 
   * @param aPau the originating PAU
   * @param aString a string to search through and generate matches
   * @return a collection of matches
   */
  public List<IGroundTruthEntry> genEntriesFromString(final String aString, final Pau aPau);
  
}
