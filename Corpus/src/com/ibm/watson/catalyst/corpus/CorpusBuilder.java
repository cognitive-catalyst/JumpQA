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
package com.ibm.watson.catalyst.corpus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.watson.catalyst.corpus.Corpus;
import com.ibm.watson.catalyst.corpus.CorpusBuilder;
import com.ibm.watson.catalyst.corpus.document.Document;
import com.ibm.watson.catalyst.corpus.document.DocumentReader;

/**
 * An object which can build a corpus. Takes care of the overhead.
 * @author wabeason
 *
 * @param <E> the Document objects for the corpus
 * @param <C> the type of Corpus to be built
 */
public abstract class CorpusBuilder<E extends Document, C extends Corpus<E>> {
  
  public CorpusBuilder() { }
  public CorpusBuilder(File aDirectory) {
    _directory = aDirectory;
  }
  
  public abstract C build();
  
  public CorpusBuilder<E, C> setDirectory(String aDirectory) {
    setDirectory(new File(aDirectory));
    return this;
  }
  
  public CorpusBuilder<E, C> setName(String aName) {
    _name = aName;
    return this;
  }
  
  public CorpusBuilder<E, C> setJson(File aJson) {
    _documents = getDocumentsFromJson(aJson);
    return this;
  }
  public CorpusBuilder<E, C> setJson(String aJson) { return setJson(new File(aJson)); }
  
  public CorpusBuilder<E, C> setDirectory(File aDirectory) {
    _directory = aDirectory;
    return this;
  }
  
  // ------------------------------------------------------------------------------------------ //
  // Private
  // ------------------------------------------------------------------------------------------ //
  protected File _directory = new File("");
  protected String _name = "";
  protected List<E> _documents = new ArrayList<E>();
  protected static final ObjectMapper MAPPER = new ObjectMapper();

  protected static final DocumentReader dr = new DocumentReader();
  
  /**
   * Gets the documents node from the corpus JSON.
   * @param aJson
   * @return
   */
  private static JsonNode getDocumentsNode(File aJson) {
    JsonNode root;
    try {
      System.out.println("Reading tree.");
      root = MAPPER.readTree(aJson);
      System.out.println("Tree read.");
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("Corpus not found: " + aJson, e);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      throw new IllegalStateException();
    } catch (IOException e) {
      throw new IllegalStateException();
    }
    JsonNode documentsNode = root.get("documents");
    return documentsNode;
  }
  
  /**
   * Gets the documents from the JSON file holding the corpus.
   * @param aJson
   * @return the documents.
   */
  protected List<E> getDocumentsFromJson(File aJson) {
    JsonNode documentsNode = getDocumentsNode(aJson);
    
    List<E> result = new ArrayList<E>();
    System.out.println("Converting documents to objects.");
    int numDocs = 0;
    for (JsonNode document : documentsNode) {
      result.add(getDocumentFromJson(document));
      if (++numDocs % 100 == 0) System.out.print(numDocs + " documents read.\r");
    }
    System.out.println(numDocs + " documents read.");
    return result;
  }
  
  protected abstract E getDocumentFromJson(JsonNode aNode);
  protected abstract E getDocumentFromFile(File aFile);
  
  /**
   * Reads corpus documents from a directory.
   * @param aDirectory
   * @return
   */
  protected List<E> getDocumentsFromDirectory(File aDirectory) {
    if (!aDirectory.isDirectory()) {
      System.out.println("Full path: " + aDirectory.getAbsolutePath());
      throw new IllegalArgumentException(aDirectory + " is not a directory.");
    }
    
    List<E> result = new ArrayList<E>();
    File[] files = aDirectory.listFiles();
    assert(files != null);
    
    if (files.length == 0) {
      throw new IllegalArgumentException("No files in directory: " + aDirectory);
    }
    
    int numFiles = 0;
    for (File f : aDirectory.listFiles()) {
      if (!f.toString().endsWith(".xml")) continue;
      E d = getDocumentFromFile(f);
      //if (!d.isComplete()) continue;
//      if (!goodDocs.contains(d.getSourceDoc())) {
//        continue;
//      }
      result.add(d);
      if (++numFiles % 1000 == 0) {
        System.out.print(numFiles + " documents loaded.\r");
      }
    }
    System.out.println();
    
    if (result.size() == 0) {
      throw new IllegalStateException("No valid documents in directory: " + aDirectory);
    }
    
    return result;
  }
  
//  private final List<String> goodDocs = new ArrayList<String>();
//  {
//    File[] goodFiles = (new File("C:/Users/IBM_ADMIN/Downloads/healthfinder")).listFiles();
//    for (File file : goodFiles) {
//      goodDocs.add(file.getName());
//    }
//  }
  
}
