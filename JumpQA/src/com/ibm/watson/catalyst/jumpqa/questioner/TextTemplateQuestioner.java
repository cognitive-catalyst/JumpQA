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
package com.ibm.watson.catalyst.jumpqa.questioner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.ibm.watson.catalyst.jumpqa.replacer.IReplacer;
import com.ibm.watson.catalyst.jumpqa.replacer.SequentialReplacer;
import com.ibm.watson.catalyst.jumpqa.replacer.VarReplacer;

/**
 * A class to compose questions based on how a question was split by a pattern.
 * 
 * @author Will Beason
 * @version 0.1.1
 * @since 0.1.0
 *
 */
public class TextTemplateQuestioner implements IQuestioner {
  
  private final String _question;
  
  private static final SequentialReplacer sr;
  
  static {
    List<IReplacer> replacers = new ArrayList<IReplacer>();
    replacers.add(new VarReplacer("\\[s0\\]"));
    replacers.add(new VarReplacer("\\[s1\\]"));
    replacers.add(new VarReplacer("\\[s2\\]"));
    sr = new SequentialReplacer(replacers);
  }
  
  /**
   * Instantiates a new RegexSplitQuestioner
   * 
   * @param aQuestion the generic question string
   */
  public TextTemplateQuestioner(final String aQuestion) {
    _question = aQuestion;
  }
  
  @Override
  public String makeQuestion(final List<String> splits) {
    return sr.replace(_question, splits);
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    TextTemplateQuestioner other = (TextTemplateQuestioner) obj;
    if (!Objects.equals(other._question, this._question)) return false;
    return true;
  }
  
  @Override
  public int hashCode() {
    return (new HashCodeBuilder(SEED, MULTIPLY)).append(_question).hashCode();
  }
  
  private static final int SEED = 940637261;
  private static final int MULTIPLY = 1836652957;
  
}
