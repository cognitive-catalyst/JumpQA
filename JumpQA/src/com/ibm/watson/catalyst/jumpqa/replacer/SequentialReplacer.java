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
package com.ibm.watson.catalyst.jumpqa.replacer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.ibm.watson.catalyst.jumpqa.replacer.ReplacerReader.ReplacerType;

/**
 * A class for replacing matched regular expressions with given strings. The
 * keys in the hashtable are the regular expressions, and the mapped values are
 * the replacements.
 * 
 * @author Will Beason
 * @version 0.1.1
 * @since 0.1.0
 *
 */
public class SequentialReplacer implements IReplacer {
  
  private final List<? extends IReplacer> _replacers;
  private final int argSize;
  
  /**
   * 
   */
  public SequentialReplacer() {
    _replacers = new ArrayList<IReplacer>();
    argSize = 0;
  }
  
  /**
   * Instantiates a new WordReplacer with the given hashtable
   * 
   * @param replacements the hashtable of searches and replacements
   */
  public SequentialReplacer(final List<? extends IReplacer> replacements) {
    _replacers = replacements;
    argSize = _replacers.stream().mapToInt(IReplacer::numArgs).sum();
  }
  
  /**
   * @param replacements the replacements
   */
  public SequentialReplacer(final IReplacer... replacements) {
    _replacers = Arrays.asList(replacements);
    argSize = _replacers.stream().mapToInt(IReplacer::numArgs).sum();
  }
  
  /**
   * Instantiates a new WordReplacer by reading the hashtable from a file
   * 
   * @param aFile the file to read
   */
  public SequentialReplacer(final File aFile) {
    this((new ReplacerReader(ReplacerType.WORD)).read(aFile));
  }
  
  @Override
  public String replace(final String input, final String... args) {
    final List<String> argsDeque = new ArrayList<String>(Arrays.asList(args));
    return replace(input, argsDeque);
  }
  
  /**
   * If no arguments are required, make replacements.
   * 
   * @param input the input string
   * @return the replacement string
   */
  public String replace(final String input) {
    return replace(input, new String[] {});
  }
  
  /**
   * Makes replacements with arguments passed as an array
   * 
   * @param input the input string
   * @param args the arguments
   * @return the replacement string
   */
  public String replace(final String input, final List<String> args) {
    if (args.size() < argSize) throw new IllegalArgumentException();
    String result = input;
    for (final IReplacer replacer : _replacers) {
      switch (replacer.getClass().getSimpleName()) {
        case "ConstReplacer":
          result = replacer.replace(result);
          break;
        case "VarReplacer":
          result = replacer.replace(result, args.remove(0));
          break;
        default:
          throw new IllegalArgumentException();
      }
    }
    return result;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    SequentialReplacer other = (SequentialReplacer) obj;
    if (!Objects.equals(other._replacers, this._replacers)) return false;
    return true;
  }
  
  @Override
  public int hashCode() {
    return (new HashCodeBuilder(SEED, MULTIPLY)).append(_replacers).hashCode();
  }
  
  @Override
  public int numArgs() {
    return argSize;
  }
  
  private static final int SEED = 785423467;
  private static final int MULTIPLY = 744176351;
  
}
