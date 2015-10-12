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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.ibm.watson.catalyst.jumpqa.answer.Answer;
import com.ibm.watson.catalyst.jumpqa.answer.Candidate;
import com.ibm.watson.catalyst.jumpqa.answer.GroundTruthEntryBuilder;
import com.ibm.watson.catalyst.jumpqa.answer.GroundTruthEntry.GroundTruthState;
import com.ibm.watson.catalyst.jumpqa.answer.Pau;
import com.ibm.watson.catalyst.jumpqa.entry.IGroundTruthEntry;
import com.ibm.watson.catalyst.jumpqa.splitter.SplitterFactory.Size;
import com.ibm.watson.catalyst.jumpqa.trec.Trec;

/**
 * The basic implementation of a template.
 * 
 * @author Will Beason
 * @version 0.1.1
 * @since 0.1.0
 *
 */
public abstract class ATemplate implements ITemplate {
  
  private final String _templateId;
  private final Size _answerSize;
  private final Size _candidateSize;
  private final Predicate<Trec> _trecPredicate;
  private final Predicate<Answer> _answerPredicate;
  private final Predicate<Candidate> _candidatePredicate;
  
  /**
   * @param aTemplateId the template ID
   * @param aAnswerSize the size of the generated answers
   * @param aCandidateSize the size to generate questions from
   * @param aTrecPredicate a predicate to evaluate TRECs
   * @param aAnswerPredicate a predicate to evaluate answers
   * @param aCandidatePredicate a predicate to evaluate candidates
   */
  public ATemplate(final String aTemplateId, final Size aAnswerSize, final Size aCandidateSize,
      final Predicate<Trec> aTrecPredicate, final Predicate<Answer> aAnswerPredicate,
      final Predicate<Candidate> aCandidatePredicate) {
    _templateId = aTemplateId;
    _answerSize = aAnswerSize;
    _candidateSize = aCandidateSize;
    _trecPredicate = aTrecPredicate;
    _answerPredicate = aAnswerPredicate;
    _candidatePredicate = aCandidatePredicate;
  }
  
  @Override
  public final List<IGroundTruthEntry> genEntriesFromTrecs(final Collection<Trec> trecs) {
    gteb.setTemplateId(_templateId);
    gteb.setState(GroundTruthState.APPROVED);
    
    final List<Trec> goodTrecs = filter(trecs, _trecPredicate);
    final List<Answer> answers = trecs2answers(goodTrecs);
    final List<Answer> goodAnswers = filter(answers, _answerPredicate);
    final List<Candidate> candidates = answers2candidates(goodAnswers);
    final List<Candidate> goodCandidates = filter(candidates, _candidatePredicate);
    final List<IGroundTruthEntry> result = candidates2entries(goodCandidates);
    return result;
  }
  
  /**
   * TODO: Method description
   * 
   * @param candidates
   * @return
   */
  protected List<IGroundTruthEntry> candidates2entries(List<Candidate> candidates) {
    final List<IGroundTruthEntry> result = new ArrayList<IGroundTruthEntry>();
    candidates.forEach((candidate) -> result.addAll(candidate2entries(candidate)));
    return result;
  }
  
  /**
   * TODO: Method description
   * 
   * @param aCandidate the candidate to create entries from
   * @return the ground truth entries
   */
  public abstract List<IGroundTruthEntry> candidate2entries(Candidate aCandidate);
  
  private final List<Candidate> answers2candidates(Collection<Answer> answers) {
    final List<Candidate> result = new ArrayList<Candidate>();
    answers.stream().forEachOrdered((answer) -> result.addAll(answer.splitInto(_candidateSize)));
    return result;
  }
  
  private final List<Answer> trecs2answers(Collection<Trec> trecs) {
    final List<Answer> result = new ArrayList<Answer>();
    trecs.stream().forEachOrdered((t) -> result.addAll(t.splitInto(_answerSize)));
    return result;
  }
  
  private final <T> List<T> filter(Collection<T> aCollection, Predicate<T> aPredicate) {
    return aCollection.stream().filter(aPredicate).collect(Collectors.toList());
  }
  
  /**
   * Checks whether the id of this template matches a value.
   * 
   * @param anObject the id to check against
   * @return whether the id matches
   */
  public final boolean idMatches(final Object anObject) {
    return _templateId.equals(anObject);
  }
  
  /**
   * TODO: Method description
   * 
   * @param aString the string to generate matches from
   * @return the collection of ground truth entries
   */
  public List<IGroundTruthEntry> genEntriesFromString(String aString) {
    return genEntriesFromString(aString, new Pau());
  }
  
  @Override
  public abstract List<IGroundTruthEntry> genEntriesFromString(String aAnswerText, Pau aPau);
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    ATemplate other = (ATemplate) obj;
    if (!Objects.equals(other._templateId, this._templateId)) return false;
    if (!Objects.equals(other._answerSize, this._answerSize)) return false;
    if (!Objects.equals(other._candidateSize, this._candidateSize)) return false;
    return true;
  }
  
  @Override
  public int hashCode() {
    return (new HashCodeBuilder(SEED, MULTIPLY)).append(_templateId).append(_answerSize)
        .append(_candidateSize).hashCode();
  }
  
  private static final int SEED = 1697705719;
  private static final int MULTIPLY = 137315513;
  
  /**
   * Generates matches from a list of strings.
   * 
   * @param strings the strings to iterate through
   * @return
   */
  protected final List<IGroundTruthEntry> genMatchesFromStrings(final Pau aPau,
      final List<String> strings) {
    final List<IGroundTruthEntry> result = new ArrayList<IGroundTruthEntry>();
    strings.forEach((s) -> result.addAll(genEntriesFromString(s, aPau)));
    return result;
  }
  
  /**
   * Uses a single TemplateMatchFactory so that question IDs do not overlap.
   */
  protected static final GroundTruthEntryBuilder gteb = GroundTruthEntryBuilder.getInstance();
  
}
