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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.ibm.watson.catalyst.corpus.tfidf.conjugate.Verb.Regular;

public class Conjugator {
  
  public static void main(String[] args) {
    
    try (BufferedReader br = new BufferedReader(new FileReader("verbs.words"))) {
      
      BufferedWriter bw = new BufferedWriter(new FileWriter("regularverbs.words"));
      int i = 0;
      while (br.ready() && i++ < 1000) {
        Verb verb = new Verb(br.readLine(), Regular.YES);
        bw.write(verb.getConjugations() + "\n");
      }
      
      bw.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
  }
  
}
