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
package com.ibm.watson.catalyst.jumpqa.replacer;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.ibm.watson.catalyst.jumpqa.replacer.IReplacer;

/**
 * TODO: Class description
 * 
 * @author Will Beason
 * @version 0.1.1
 * @since 0.1.0
 *
 */
public class VarReplacer implements IReplacer {
  
  private final Pattern _pattern;
  
  /**
   * @param aPattern the pattern to replace
   */
  public VarReplacer(Pattern aPattern) {
    _pattern = aPattern;
  }
  
  /**
   * @param aPattern the regular expression replace
   */
  public VarReplacer(String aPattern) {
    this(Pattern.compile(aPattern));
  }
  
  @Override
  public String replace(String input, String... args) {
    return _pattern.matcher(input).replaceAll(Matcher.quoteReplacement(args[0]));
  }
  
  /**
   * TODO: Method description
   * 
   * @param input the input string
   * @param arg the argument
   * @return the replacement
   */
  public String replace(String input, String arg) {
    return _pattern.matcher(input).replaceAll(Matcher.quoteReplacement(arg));
  }
  
  @Override
  public int numArgs() {
    return 1;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    VarReplacer other = (VarReplacer) obj;
    if (!Objects.equals(other._pattern.toString(), this._pattern.toString())) return false;
    if (!Objects.equals(other._pattern.flags(), this._pattern.flags())) return false;
    return true;
  }
  
  @Override
  public int hashCode() {
    return (new HashCodeBuilder(SEED, MULTIPLY)).append(_pattern.toString())
        .append(_pattern.flags()).hashCode();
  }
  
  private static final int SEED = 1853186509;
  private static final int MULTIPLY = 1530150889;
  
}
