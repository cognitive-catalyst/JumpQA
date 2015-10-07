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
package com.ibm.watson.catalyst.corpus.tfidf.conjugate;

public class Verb extends Word {
  
  public Verb(String aBaseWord, Regular isRegular) {
    super(aBaseWord);
    _isRegular = isRegular;
  }
  
  public Verb(String aBaseWord) {
    this(aBaseWord, Regular.YES);
  }
  
  public enum Regular { YES, NO }
  
  public String getConjugations() {
    if (isIrregular()) throw new UnsupportedOperationException();
    
    String[] conjs = new String[4];
    
    conjs[0] = getBaseWord();
    conjs[1] = getThirdPerson();
    conjs[2] = getPresentParticiple();
    conjs[3] = getPast();
    
    String result = conjs[0] + "\n" + conjs[1] + "\n" + conjs[2] + "\n" + conjs[3];
    return result;
  }
  
  public boolean isRegular() { return _isRegular.equals(Regular.YES); }
  public boolean isIrregular() { return _isRegular.equals(Regular.NO); }
  
  private final Regular _isRegular;
  
  public String getThirdPerson() {
    String baseWord = getBaseWord();
    int wordLength = baseWord.length();
    
    String result;
    switch (baseWord.charAt(wordLength - 1)) {
      case 'x':
        result = baseWord + "es";
        break;
      case 'y':
        switch (baseWord.charAt(wordLength - 2)) {
          case 'a': case 'e': case 'i': case 'o': case 'u':
            result = baseWord + "s";
            break;
          default:
            result = baseWord.substring(0, wordLength - 1) + "ies";
            break;
        }
        break;
      default:
        if (endsWithDipthong()) {
          result = baseWord + "es";
        } else {
          result = baseWord + "s";
        }
        break;
    }
    
    return result;
  }
  
  private String getPresentParticiple() {
    String baseWord = getBaseWord();
    int wordLength = baseWord.length();
    
    String result;
    if (isDoubled()) {
      result = baseWord + baseWord.charAt(wordLength - 1) + "ing";
    } else if (baseWord.endsWith("e") && !baseWord.endsWith("ee") && !baseWord.endsWith("ue")) {
      result = baseWord.substring(0, wordLength - 1);
      if (result.endsWith("i")) result = result.substring(0, result.length() - 1) + "y";
      result = result + "ing";
    } else {
      result = baseWord + "ing";
    }
    
    return result;
  }
  
  private String getPast() {
    String baseWord = getBaseWord();
    int wordLength = baseWord.length();
    
    String result;
    if (isDoubled()) {
      result = baseWord + baseWord.charAt(wordLength - 1) + "ed";
    } else if (baseWord.endsWith("e")) {
      result = baseWord.substring(0, wordLength - 1) + "ed";
    } else {
      result = baseWord + "ed";
    }
    
    return result;
  }
  
  public boolean isDoubled() {
    char[] chars = getBaseWord().toCharArray();
    int wordLength = chars.length;
    if (wordLength > 5) return false;
    if (isVowel(chars[wordLength - 1]) || isNoRepeat(chars[wordLength - 1])) return false;
    if (!isVowel(chars[wordLength - 2])) return false;
    if (isVowel(chars[wordLength - 3])) return false;
    if (wordLength >= 4) {
      if (isVowel(chars[wordLength - 4])) return false;
    }
    if (wordLength == 5) {
      if (isVowel(chars[0])) return false;
    }
    return true;
  }
  
  public boolean endsWithDipthong() {
    char[] chars = getBaseWord().toCharArray();
    int wordLength = chars.length;
    char lastChar = chars[wordLength - 1];
    char penChar = chars[wordLength - 2];
    
    if (isVowel(lastChar)) return false;
    if (isVowel(penChar)) return false;
    
    boolean result = false;
    
    switch(lastChar) {
      case 'h':
        switch(penChar) {
          case 'c':
          case 's':
            result = true;
          default: break;
        }
      case 's':
        switch(penChar) {
          case 's':
            result = true;
          default: break;
        }
      default: break;
    }
    
    return result;
  }

  private static char[] noRepeats = new char[] {'w', 'x', 'y'};
  private boolean isNoRepeat(char c) {
    for (char noRepeat : noRepeats) if (c == noRepeat) return true;
    return false;
  }
  
  private static char[] vowels = new char[] {'a', 'e', 'i', 'o', 'u'};
  private boolean isVowel(char c) {
    for (char vowel : vowels) if (c == vowel) return true;
    return false;
  }
  
}
