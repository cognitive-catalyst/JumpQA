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
package com.ibm.watson.catalyst.corpus.tfidf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.ibm.watson.catalyst.corpus.tfidf.corpus.TermCorpus;
import com.ibm.watson.catalyst.corpus.tfidf.corpus.TermCorpusBuilder;
import com.ibm.watson.catalyst.corpus.tfidf.document.DocumentMatcher;
import com.ibm.watson.catalyst.corpus.tfidf.document.TermDocument;
import com.ibm.watson.catalyst.corpus.tfidf.sentences.WordFrequencyHashtable;
import com.ibm.watson.catalyst.corpus.tfidf.term.TemplateMatch;

public final class SearchTemplate {

  public static void main(String[] args) {
    
    System.out.println("Loading Corpus.");
    TermCorpusBuilder cb = new TermCorpusBuilder();
    cb.setDocumentCombiner(0, 0);
    cb.setJson(new File("health-corpus.json"));
    TermCorpus c = cb.build();
    
    List<TermDocument> termDocuments = c.getDocuments();
    List<TemplateMatch> matches = new ArrayList<TemplateMatch>();

    Pattern p3 = Template.getTemplatePattern(new File("verbs-list.words"), "\\b(\\w+ )", "( \\w+)\\b");
    
    int index = 0;
    for (TermDocument termDocument : termDocuments) {
      DocumentMatcher dm = new DocumentMatcher(termDocument);
      
      matches.addAll(dm.getParagraphMatches(p3, "", ""));
      
      double progress = ((double) ++index / (double) termDocuments.size());
      System.out.print("Progress " + progress + "\r");
    }
    System.out.println();
    
    WordFrequencyHashtable f = new WordFrequencyHashtable();
    
    for (TemplateMatch match : matches) {
      f.put(match.getMatch(), 1);
    }
    
    JsonNode jn = f.toJsonNode(5);
    
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("health-trigrams.json"))) {
      bw.write(jn.toString());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }

}
