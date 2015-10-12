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

import java.util.ArrayList;
import java.util.List;

import com.ibm.watson.catalyst.objectio.readers.AReader;
import com.ibm.watson.catalyst.objectio.readers.IReader;

/**
 * Reads files to creates hashtables of search and replacement strings
 * 
 * @author Will Beason
 * @version 0.1.1
 * @since 0.1.0
 *
 */
public class ReplacerReader extends AReader<ConstReplacer> implements IReader {
  
  /**
   * @param aType the type of replacers to create
   */
  public ReplacerReader(ReplacerType aType) {
    _type = aType;
  }
  
  /**
   * 
   */
  public ReplacerReader() {
    this(ReplacerType.NORMAL);
  }
  
  /**
   * TODO: Class description
   * 
   * @author Will Beason
   * @version 0.1.0
   * @since 0.1.0
   *
   */
  public enum ReplacerType {
    /**
     * No changes are made to the passed patterns
     */
    NORMAL,
    /**
     * Word boundaries are appended and prepended to passed patterns.
     */
    WORD
  }
  
  @Override
  public List<ConstReplacer> read(final List<String> strings) {
    final List<ConstReplacer> result = new ArrayList<ConstReplacer>();
    
    strings.stream().forEachOrdered((s) -> {
      if (!isCommentOrEmpty(s)) result.add(string2Object(s));
    });
    
    return result;
  }
  
  @Override
  protected ConstReplacer string2Object(String aString) {
    final String[] entry = aString.split("=", 2);
    final String replacement = entry[1];
    String pattern;
    switch (_type) {
      case NORMAL:
        pattern = entry[0];
        break;
      case WORD:
        pattern = "\\b" + entry[0] + "\\b";
        break;
      default:
        throw new IllegalArgumentException();
    }
    return new ConstReplacer(pattern, replacement);
  }
  
  private final ReplacerType _type;
  
}
