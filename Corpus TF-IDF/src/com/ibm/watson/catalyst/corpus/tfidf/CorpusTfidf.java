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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ibm.watson.catalyst.corpus.tfidf.corpus.TermCorpus;
import com.ibm.watson.catalyst.corpus.tfidf.corpus.TermCorpusBuilder;
import com.ibm.watson.catalyst.corpus.tfidf.document.TermDocument;
import com.ibm.watson.catalyst.corpus.tfidf.sentences.WordFrequencyHashtable;
import com.ibm.watson.catalyst.corpus.tfidf.util.SortedArrayList;
import com.ibm.watson.catalyst.util.baseproperties.BaseProperties;

public final class CorpusTfidf {
  
  public enum SortedBy { FREQ, TFIDF }
  
  private static BaseProperties PROPERTIES;
  private static final ObjectMapper MAPPER = new ObjectMapper();
  
//  private static final File corpusDir = new File("TRECs/WikiVoyageTREC/xml-splitTrecTrim");
//  private static final File corpusDir2 = new File("TRECs/Health/xml-splitTrecTrim");
  //private static final File corpusDir3 = new File("W:/JumpQA/TRECs/WikiVoyageTRECFull");
  
  private static final SortedBy sortedBy = SortedBy.TFIDF;
  
  private static double tfidf(String term, TermDocument d, TermCorpus c) {
    return d.augmentedFrequency(term) * c.getIdf(term);
  }
  
  private static ObjectNode printGoodWords(TermDocument d, TermCorpus c) {
    ObjectNode result = MAPPER.createObjectNode();
    result.put("document", d.getContext(0));
    WordFrequencyHashtable f = d.getFrequencies();
    
    SortedArrayList<String> importantWords =  new SortedArrayList<String>();
    
    {
      Iterator<String> words = f.sortedIterator(1);
      while(words.hasNext()) {
        String word = words.next();
        //if (d.frequency(word) < 5) continue;
        importantWords.sortedAdd(word, tfidf(word, d, c));
      }
    }
    
    SortedArrayList<String> wordList;
    switch(sortedBy) {
      case TFIDF:
        wordList = importantWords;
        break;
      case FREQ:
        wordList = new SortedArrayList<String>();
        for (String word : importantWords) wordList.sortedAdd(word, f.get(word));
        break;
      default:
        throw new IllegalStateException();
    }
    
    ArrayNode wordNodes = MAPPER.createArrayNode();
    for (String word : wordList) {
      ObjectNode wordNode = MAPPER.createObjectNode();
      wordNode.put("string", word);
      wordNode.put("frequency", f.get(word));
      wordNode.put("tfidf", tfidf(word, d, c));
      wordNodes.add(wordNode);
    }
    
    result.set("words", wordNodes);
    return result;
  }
  
  public static ObjectNode getCorpusTfidfs(TermCorpus c) {
    ObjectNode root = MAPPER.createObjectNode();
    root.put("corpus-name", "Health");
    ArrayNode root2 = MAPPER.createArrayNode();
    int index = 0;
    for (TermDocument d : c.getDocuments()) {
      root2.add(printGoodWords(d, c));
      if (++index % 100 == 0) System.out.print(index + " documents processed.\r");
    }
    System.out.println(index + " documents processed.");
    root.set("documents", root2);
    return root;
  }
  
  public static void main(String[] args) {
    PROPERTIES = BaseProperties.setInstance(args, "sample/test.properties");
    
    String input = PROPERTIES.getProperty("input", "sample/test-check.json");
    
    TermCorpusBuilder cb = new TermCorpusBuilder();
    cb.setJson(input);
    
    System.out.println("Building corpus.");
    TermCorpus c = cb.build();
    System.out.println(c.size());
    
    System.out.println("Generating terms.");
    c.genTerms();
    System.out.println("Generating idfs.");
    c.genIdfs();
    System.out.println(c.numTerms());
    System.out.println("Terms generated.");
    
    ObjectNode tfidfs = getCorpusTfidfs(c);
    
    String output = PROPERTIES.getProperty("output", "sample/test-tfidf-output.json");
    try (BufferedWriter bw =new BufferedWriter(new OutputStreamWriter(
        new FileOutputStream(output), "UTF-8"))) {
      bw.write(tfidfs.toString());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }

}
