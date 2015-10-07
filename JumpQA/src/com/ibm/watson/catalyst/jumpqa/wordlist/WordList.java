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
package com.ibm.watson.catalyst.jumpqa.wordlist;

import java.util.Iterator;
import java.util.List;

import com.ibm.watson.catalyst.jumpqa.splitter.ISplitter;
import com.ibm.watson.catalyst.jumpqa.splitter.SplitterFactory;

/**
 * A class for holding a list of words and performing tests with them.
 * 
 * @author Will Beason
 * @version 0.1.1
 * @since 0.1.0
 *
 */
public class WordList implements IWordList, Iterable<String> {
  
  private final List<String> _wordList;
  
  /**
   * Instantiates a WordList from a list of strings
   * 
   * @param aStringList the list of strings
   */
  public WordList(final List<String> aStringList) {
    _wordList = aStringList;
  }
  
  /**
   * Instantiates a WordList by reading strings from a file
   * 
   * @param aFile the file to read strings from
   */
  public WordList(final String aFile) {
    this((new StringListReader()).readFile(aFile));
  }
  
  @Override
  public boolean contains(final Object o) {
    return _wordList.contains(o.toString().toLowerCase());
  }
  
  /**
   * Tests whether the first word of the string is in the word list
   * 
   * @param s the string to test
   * @return whether the first word of the string is in the word list
   */
  public boolean containsFirstWord(final String s) {
    final List<String> words = ws.split(s);
    return contains(words.get(0));
  }
  
  /**
   * Tests whether the last word of the string is in the word list
   * 
   * @param s the string to test
   * @return whether the last word of the string is in the word list
   */
  public boolean containsLastWord(final String s) {
    final List<String> words = ws.split(s);
    return contains(words.get(words.size() - 1));
  }
  
  @Override
  public Iterator<String> iterator() {
    return _wordList.iterator();
  }
  
  /**
   * Gets the list of words.
   * 
   * @return the list of words
   */
  protected List<String> getList() {
    return _wordList;
  }
  
  private static final ISplitter ws = SplitterFactory.build("word");
  
}
