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
package com.ibm.watson.catalyst.jumpqa.stringcleaner;

import java.util.regex.Pattern;

/**
 * A class for cleaning strings.
 * 
 * @author Will Beason
 * @version 0.1.1
 * @since 0.1.0
 *
 */
public class StringCleaner {
  
  /**
   * TODO: Class description
   * 
   * @author Will Beason
   * @version 0.1.1
   * @since 0.1.1
   *
   */
  public enum Clean {
    /**
     * Do clean strings
     */
    YES,
    /**
     * Do not clean strings
     */
    NO
  }
  
  /**
   * TODO: Method description
   * 
   * @param aString the string to clean
   * @return the cleaned string
   */
  public static String clean(final String aString) {
    String result = aString;
    result = removePattern(result, PARENTHETICAL);
    result = removePattern(result, ETAL);
    result = replacePattern(result, DOUBLEFIRST, "$1");
    result = removePattern(result, APPOSITIVE);
    // result = removePattern(result, COMMA);
    result = removePattern(result, STARTQUOTE);
    result = replaceUppercaseArticle(result);
    return result.trim();
  }
  
  /**
   * TODO: Method description
   * 
   * @param aString the string to clean
   * @param aClean whether to clean the string
   * @return the cleaned string
   */
  public static String clean(final String aString, final Clean aClean) {
    switch (aClean) {
      case YES:
        return clean(aString);
      case NO:
        return aString;
      default:
        throw new IllegalArgumentException();
    }
  }
  
  /**
   * TODO: Method description
   * 
   * @param aString the string parse into a Clean enum
   * @return the chosen Clean
   */
  public static Clean getClean(final String aString) {
    if (Boolean.parseBoolean(aString)) {
      return Clean.YES;
    } else {
      return Clean.valueOf(aString);
    }
  }
  
  private static final Pattern APPOSITIVE = Pattern.compile(",[^,]+,");
  
  private static final Pattern DOUBLEFIRST = Pattern.compile("^(.*) \\1\\b");
  
  private static final Pattern ETAL = Pattern.compile("^.*(et al\\.|[A-Z]{1,2}):");
  private static final Pattern PARENTHETICAL = Pattern.compile("\\s\\([^\\)]*\\)");
  // private static final Pattern COMMA = Pattern.compile("^.*, ");
  private static final Pattern STARTQUOTE = Pattern.compile("^['\"]");
  private static final Pattern UPPERCASEARTICLE = Pattern.compile("^(An?|The|One)\\b");
  
  private static final String removePattern(final String aString, final Pattern aPattern) {
    return replacePattern(aString, aPattern, "");
  }
  
  private static final String replacePattern(final String aString, final Pattern aPattern,
      final String replacement) {
    return aPattern.matcher(aString).replaceAll(replacement);
  }
  
  private static final String replaceUppercaseArticle(final String aString) {
    if (UPPERCASEARTICLE.matcher(aString).find())
      return aString.substring(0, 1).toLowerCase() + aString.substring(1);
    else
      return aString;
  }
  
}
