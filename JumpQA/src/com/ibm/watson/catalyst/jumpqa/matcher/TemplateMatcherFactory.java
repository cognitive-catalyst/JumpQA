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
package com.ibm.watson.catalyst.jumpqa.matcher;

import java.util.regex.Pattern;

/** TODO: Class description
 * 
 * @author Will Beason
 * @version 0.1.2
 * @since 0.1.2
 *
 */
public class TemplateMatcherFactory {

  /** 
   * TODO: Method description
   * @param aString the title match regular expression
   * @param flags the pattern flags
   * @return the TemplateMatcher
   */
  public static EntryPatterns titleMatcher(String aString, int flags) {
    Pattern p = Pattern.compile(aString, flags);
    return new EntryPatterns(p, null, null);
  }
  
  /** 
   * TODO: Method description
   * @param aString the title match regular expression
   * @return the TemplateMatcher
   */
  public static EntryPatterns titleMatcher(String aString) {
    return titleMatcher(aString, 0);
  }
  
  /** 
   * TODO: Method description
   * @param aString the answer match regular expression
   * @param flags the pattern flags
   * @return the TemplateMatcher
   */
  public static EntryPatterns answerMatcher(String aString, int flags) {
    Pattern p = Pattern.compile(aString, flags);
    return new EntryPatterns(null, p, null);
  }

  /** 
   * TODO: Method description
   * @param aString the answer match regular expression
   * @return the TemplateMatcher
   */
  public static EntryPatterns answerMatcher(String aString) {
    return answerMatcher(aString, 0);
  }
  
  /** 
   * TODO: Method description
   * @param aString the text match regular expression
   * @param flags the pattern flags
   * @return the TemplateMatcher
   */
  public static EntryPatterns textMatcher(String aString, int flags) {
    Pattern p = Pattern.compile(aString, flags);
    return new EntryPatterns(null, null, p);
  }

  /** 
   * TODO: Method description
   * @param aString the text match regular expression
   * @return the TemplateMatcher
   */
  public static EntryPatterns textMatcher(String aString) {
    return textMatcher(aString, 0);
  }
  
  
  
}
