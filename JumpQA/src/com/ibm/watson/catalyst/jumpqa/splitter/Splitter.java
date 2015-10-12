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
package com.ibm.watson.catalyst.jumpqa.splitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * TODO: Class description
 * 
 * @author Will Beason
 * @version 0.1.1
 * @since 0.1.0
 *
 */
public class Splitter implements ISplitter {
  
  private final Pattern _p;
  
  /**
   * @param aRegex the regular expression to split around
   */
  public Splitter(String aRegex) {
    _p = Pattern.compile(aRegex);
  }
  
  /**
   * @param p the pattern to split with
   */
  public Splitter(Pattern p) {
    _p = p;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Splitter other = (Splitter) obj;
    if (!Objects.equals(other._p.toString(), this._p.toString())) return false;
    if (!Objects.equals(other._p.flags(), this._p.flags())) return false;
    return true;
  }
  
  @Override
  public int hashCode() {
    return (new HashCodeBuilder(SEED, MULTIPLY)).append(_p.toString()).append(_p.flags())
        .hashCode();
  }
  
  private static final int SEED = 860127911;
  private static final int MULTIPLY = 505854299;
  
  @Override
  public final List<String> split(final List<String> strings) {
    final List<String> result = new ArrayList<String>();
    strings.forEach((string) -> {
      result.addAll(split(string));
    });
    return result;
  }
  
  /**
   * Removes empty strings from a list of strings.
   * 
   * @param strings
   * @return
   */
  protected List<String> removeEmpty(List<String> strings) {
    final List<String> result = new ArrayList<String>(strings);
    result.removeIf((s) -> s.equals(""));
    return result;
  }
  
  /**
   * Removes empty strings from an array of strings.
   * 
   * @param strings
   * @return
   */
  protected List<String> removeEmpty(String[] strings) {
    return removeEmpty(Arrays.asList(strings));
  }
  
  @Override
  public List<String> split(final String aString) {
    return removeEmpty(_p.split(aString));
  }
  
}
