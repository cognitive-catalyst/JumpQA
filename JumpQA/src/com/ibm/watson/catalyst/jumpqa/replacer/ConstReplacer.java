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

import java.util.Objects;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * A class which takes strings and makes a set replacement.
 * 
 * @author Will Beason
 * @version 0.1.1
 * @since 0.1.0
 *
 */
public class ConstReplacer implements IReplacer {
  
  /**
   * Instantiates a new Replacer
   * 
   * @param aPattern the pattern to search for
   * @param aReplacement what to replace the pattern with
   */
  public ConstReplacer(Pattern aPattern, String aReplacement) {
    _pattern = aPattern;
    _replacement = aReplacement;
  }
  
  /**
   * Instantiates a new Replacer with the pattern as a string
   * 
   * @param aPattern the pattern to search for
   * @param aReplacement what to replace the pattern with
   */
  public ConstReplacer(String aPattern, String aReplacement) {
    this(Pattern.compile(aPattern), aReplacement);
  }
  
  /**
   * Takes a string and
   * 
   * @param aString the string to match and make replacements
   * @return the string with replacements made
   */
  public String replace(String aString) {
    return _pattern.matcher(aString).replaceAll(_replacement);
  }
  
  @Override
  public String replace(String aString, String... args) {
    return replace(aString);
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    ConstReplacer other = (ConstReplacer) obj;
    if (!Objects.equals(other._replacement, this._replacement)) return false;
    if (!Objects.equals(other._pattern.toString(), this._pattern.toString())) return false;
    if (!Objects.equals(other._pattern.flags(), this._pattern.flags())) return false;
    return true;
  }
  
  @Override
  public int hashCode() {
    return (new HashCodeBuilder(SEED, MULTIPLY)).append(_replacement).append(_pattern.toString())
        .append(_pattern.flags()).hashCode();
  }
  
  private final Pattern _pattern;
  private final String _replacement;
  
  private static final int SEED = 465630923;
  private static final int MULTIPLY = 730394177;
  
  @Override
  public int numArgs() {
    return 0;
  }
  
}
