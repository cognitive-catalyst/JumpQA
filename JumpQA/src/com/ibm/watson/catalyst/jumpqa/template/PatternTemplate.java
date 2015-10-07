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
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.ibm.watson.catalyst.jumpqa.answer.Answer;
import com.ibm.watson.catalyst.jumpqa.answer.Candidate;
import com.ibm.watson.catalyst.jumpqa.answer.Pau;
import com.ibm.watson.catalyst.jumpqa.answer.QuestionAnswerPair;
import com.ibm.watson.catalyst.jumpqa.entry.IGroundTruthEntry;
import com.ibm.watson.catalyst.jumpqa.matcher.EntryPatterns;
import com.ibm.watson.catalyst.jumpqa.questioner.IQuestioner;
import com.ibm.watson.catalyst.jumpqa.replacer.SequentialReplacer;
import com.ibm.watson.catalyst.jumpqa.splitter.SplitterFactory.Size;
import com.ibm.watson.catalyst.jumpqa.stringcleaner.StringCleaner;
import com.ibm.watson.catalyst.jumpqa.stringcleaner.StringCleaner.Clean;

/**
 * A template which matches patterns to portions of a TREC to evaluate candidates.
 * Can test against PAU Titles, answer text, and candidate text.
 * 
 * @author Will Beason
 * @version 0.1.1
 * @since 0.1.0
 *
 */
public class PatternTemplate extends ATemplate {
  
  private final IQuestioner _questioner;
  private final EntryPatterns _patterns;
  private final SequentialReplacer _replacer;
  private final Clean _clean;
  
  /**
   * @param aTemplateId the id of the template
   * @param aAnswerSize the size of the answer
   * @param aCandidateSize the size of the match
   * @param aQuestioner an object to take a good sentence and create questions
   *          to be asked about it
   * @param aPatterns an object to determine if a sentence is a match
   * @param aSequentialReplacer an object to sequentially make replacements on
   *          the text before transformation into a question. May be null.
   * @param aClean whether to clean strings
   */
  public PatternTemplate(
      final String aTemplateId,
      final Size aAnswerSize,
      final Size aCandidateSize,
      final IQuestioner aQuestioner,
      final SequentialReplacer aSequentialReplacer,
      final Clean aClean,
      final EntryPatterns aPatterns) {
    super(
        aTemplateId,
        aAnswerSize,
        aCandidateSize,
        (trec) -> aPatterns.matches(trec),
        (answer) -> aPatterns.matches(answer),
        (candidate) -> aPatterns.matches(candidate));
    if (aQuestioner == null) throw new IllegalArgumentException("Questioner must not be null.");
    _questioner = aQuestioner;
    _replacer = (aSequentialReplacer == null) ? new SequentialReplacer() : aSequentialReplacer;
    _patterns = aPatterns;
    _clean = aClean;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (!super.equals(obj)) return false;
    PatternTemplate other = (PatternTemplate) obj;
    if (!Objects.equals(other._questioner, this._questioner)) return false;
    if (!Objects.equals(other._replacer, this._replacer)) return false;
    if (!Objects.equals(other._patterns, this._patterns)) return false;
    if (!Objects.equals(other._clean, this._clean)) return false;
    return true;
  }
  
  @Override
  public int hashCode() {
    return (new HashCodeBuilder(SEED, MULTIPLY)).append(super.hashCode()).append(_questioner).append(_replacer).append(_patterns)
        .append(_clean).hashCode();
  }
  
  private static final int SEED = 836815957;
  private static final int MULTIPLY = 1846517969;
  
  /**
   * Generates matches from string
   * 
   * @param matchText
   * @return
   */
  protected List<IGroundTruthEntry> genEntriesFromAnswer(final String matchText,
      final Answer aAnswer) {
    return candidate2entries(new Candidate(matchText, aAnswer));
  }
  
  @Override
  public List<IGroundTruthEntry> genEntriesFromString(String answerText, Pau aPau) {
    return genEntriesFromAnswer(answerText, new Answer(answerText, aPau));
  }
  
  @Override
  public List<IGroundTruthEntry> genEntriesFromString(String aString) {
    return genEntriesFromString(aString, new Pau());
  }
  
  @Override
  protected List<IGroundTruthEntry> candidates2entries(List<Candidate> candidates) {
    final List<IGroundTruthEntry> result = new ArrayList<IGroundTruthEntry>();
    candidates.forEach((c) -> result.addAll(candidate2entries(c)));
    return result;
  }
  
  /**
   * TODO: Method description
   * 
   * @param aCandidate the candidate to create entries for
   * @return the ground truth entries
   */
  @Override
  public List<IGroundTruthEntry> candidate2entries(Candidate aCandidate) {
    final List<IGroundTruthEntry> result = new ArrayList<IGroundTruthEntry>();
    final String cleanedString = StringCleaner.clean(aCandidate.getMatchText(), _clean);
    final List<String> splits = _patterns.splitCandidateText(cleanedString);
    splits.set(1, _replacer.replace(splits.get(1)));
    
    String questionText = _questioner.makeQuestion(splits);
    QuestionAnswerPair qaPair = new QuestionAnswerPair(questionText, aCandidate.getAnswer());
    gteb.setQaPair(qaPair);
    result.add(gteb.build());
    return result;
  }
  
}
