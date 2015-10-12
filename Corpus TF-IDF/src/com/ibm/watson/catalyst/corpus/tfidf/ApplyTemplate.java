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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.watson.catalyst.corpus.tfidf.corpus.TermCorpus;
import com.ibm.watson.catalyst.corpus.tfidf.corpus.TermCorpusBuilder;
import com.ibm.watson.catalyst.corpus.tfidf.document.DocumentMatcher;
import com.ibm.watson.catalyst.corpus.tfidf.document.TermDocument;
import com.ibm.watson.catalyst.corpus.tfidf.term.TemplateMatch;

public final class ApplyTemplate {
  
  private static final ObjectMapper MAPPER = new ObjectMapper();
  
  public static void main(String[] args) {
    
    System.out.println("Loading Corpus.");
    JsonNode root;
    TermCorpus c;
    JsonNode documents;
    try (InputStream in = new FileInputStream(new File("tfidf-health-1.json"))) {
      root = MAPPER.readTree(in);
      documents = root.get("documents");
      TermCorpusBuilder cb = new TermCorpusBuilder();
      cb.setDocumentCombiner(0, 0);
      cb.setJson(new File("health-corpus.json"));
      c = cb.build();
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return;
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return;
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return;
    }
    System.out.println("Corpus loaded.");

    List<TemplateMatch> matches = new ArrayList<TemplateMatch>();
    Iterator<TermDocument> documentIterator = c.getDocuments().iterator();
    
    int index = 0;
    for (JsonNode document : documents) {
      Pattern p1 = Template.getTemplatePattern(document, "\\b(an? |the )?(\\w+ ){0,4}", "( \\w+)?(?= is (an?|one|the)\\b)");
      if (p1.toString().equals("\\b(an? |the )?(\\w+ ){0,4}()( \\w+)?(?= is (an?|one|the)\\b)")) continue;
      Pattern p2 = Template.getTemplatePattern(document, "^(\\w+ ){0,2}", "( \\w+){0,1}?(?=( can| may)? causes?\\b)");
      Pattern p3 = Template.getTemplatePattern(document, "(?<=the use of )(\\w+ ){0,3}", "( \\w+| ){0,2}?(?=( (and|does|in|for|can|is|as|to|of)\\b|\\.))");
      Pattern p4 = Template.getTemplatePattern(document, "^(\\w+ ){0,3}", "( \\w+){0,1}(?=( can| may) leads? to\\b)");
      Pattern p5 = Template.getTemplatePattern(document, "(?<=\\bthe risk of )(\\w+ ){0,3}", "( (disease|stroke|attack|cancer))?\\b");
      Pattern p6 = Template.getTemplatePattern(document, "(\\w{3,} ){0,3}", "( (disease|stroke|attack|cancer))?(?= is caused by\\b)");
      Pattern p7 = Template.getTemplatePattern(document, "(?<= is caused by )(\\w+ ){0,10}", "");
      Pattern p8 = Template.getTemplatePattern(document, "\\b", "( \\w{4,})(?= can be used)");
      Pattern p9 = Template.getTemplatePattern(document, "(?<= can be used )(\\w+ ){0,10}", "\\b");
      TermDocument d = documentIterator.next();
      
      DocumentMatcher dm = new DocumentMatcher(d);
      matches.addAll(dm.getParagraphMatches(p1, "What is ", "?"));
      matches.addAll(dm.getParagraphMatches(p2, "What does ", " cause?"));
      matches.addAll(dm.getParagraphMatches(p3, "How is ", " used?"));
      matches.addAll(dm.getParagraphMatches(p4, "What can ", " lead to?"));
      matches.addAll(dm.getParagraphMatches(p5, "What impacts the risk of ", "?"));
      matches.addAll(dm.getParagraphMatches(p6, "What causes ", "?"));
      matches.addAll(dm.getParagraphMatches(p7, "What is caused by ", "?"));
      matches.addAll(dm.getParagraphMatches(p8, "How can ", " be used?"));
      matches.addAll(dm.getParagraphMatches(p9, "What can be used ", "?"));
      System.out.print("Progress: " + ((100 * ++index) / documents.size()) + "%\r");
    }
    System.out.println();
    
    List<TemplateMatch> condensedMatches = new ArrayList<TemplateMatch>();
    
    for (TemplateMatch match : matches) {
      for (TemplateMatch baseMatch : condensedMatches) {
        if (match.sameQuestion(baseMatch)) {
          baseMatch.addAnswers(match);
          break;
        }
      }
      condensedMatches.add(match);
    }
    
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("health-questions.txt"))) {
      for (TemplateMatch match : condensedMatches) {
        bw.write(match.toString());
      }
      bw.write("\n");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    System.out.println("Done and generated: " + condensedMatches.size());
    
  }

}
