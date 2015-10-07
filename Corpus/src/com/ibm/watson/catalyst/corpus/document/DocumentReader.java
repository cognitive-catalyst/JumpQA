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
package com.ibm.watson.catalyst.corpus.document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ibm.watson.catalyst.corpus.document.Document;
import com.ibm.watson.catalyst.corpus.document.DocumentBuilder;

public class DocumentReader {
  
  public Document read(File file) {
    
    String pauTitle;
    List<String> paragraphs;
    String sourceDoc;
    String pauID;
    DocumentBuilder db = new DocumentBuilder();
    db.setFile(file);
    Document d;
    
    try (BufferedReader br = new BufferedReader(new FileReader (file))) {
      pauTitle = getPauTitle(br);
      db.setPauTitle(pauTitle);
      
      String line = br.readLine();
      while (!textStart(line) && ((line = br.readLine()) != null)) continue;
      paragraphs = new ArrayList<String>();
      while (((line = br.readLine()) != null) && !textEnd(line)) {
        paragraphs.add(line);
      }
      db.setParagraphs(paragraphs);
      
      sourceDoc = getSourceDoc(br);
      db.setSourceDoc(sourceDoc);
      
      pauID = getPauID(br);
      db.setPauID(pauID);
      
      br.close();
    } catch (FileNotFoundException e) {
      System.out.println("File " + file + " not found");
    } catch (IOException e) {
      System.out.println("Error reading " + file);
    } finally {
      d = db.build();
    }
    
    return d;
  }
  
  private static final Pattern sourceDocPattern = Pattern.compile("<meta:key:sourcename>(.*)</meta:key:sourcename>");
  private static final String getSourceDoc(String line) {
    return matches(sourceDocPattern, line) ?
        replace(sourceDocPattern, line, "$1") : "";
  }
  
  // Gets the PAU ID from a line if the line contains a PAU ID
  private static final Pattern pauIDPattern = Pattern.compile("<meta:key:pauTid>(.*)</meta:key:pauTid>");
  private static final String getPauID(String line) {
    return matches(pauIDPattern, line) ?
        replace(pauIDPattern, line, "$1") : "";
  }
  
  private static final String getSourceDoc(BufferedReader br) throws IOException {
    String result = "";
    String line;
    while (((line = br.readLine()) != null) && ((result = getSourceDoc(line)) == "")) continue;
    return result;
  }
  
  private static final String getPauID(BufferedReader br) throws IOException {
    String result = "";
    String line;
    while (((line = br.readLine()) != null) && ((result = getPauID(line)) == "")) continue;
    return result;
  }
  
  // Gets the PAU Title from a line if the line contains a PAU Title
  private static final Pattern pauTitlePattern = Pattern.compile("^<title>.*</title>$");
  private static final String getPauTitle(String line) {
    return matches(pauTitlePattern, line) ? line.substring(7, line.length() - 8) : "";
  }
  
  private static final String getPauTitle(BufferedReader br) throws IOException {
    String result = "";
    String line;
    while (((line = br.readLine()) != null) && ((result = getPauTitle(line)).equals(""))) continue;
    return result;
  }
  
  // Returns whether the current line begins the TREC text field.
  private static final Pattern openTextPattern = Pattern.compile("<text>");
  private static final boolean textStart(String line) {
    return matches(openTextPattern, line);
  }
  
  private static final Pattern closeTextPattern = Pattern.compile("</text>");
  private static final boolean textEnd(String line) {
    return matches(closeTextPattern, line);
  }

  private static boolean matches(Pattern p, String s) {
    return p.matcher(s).find();
  }
  
  private static String replace(Pattern p, String s, String replace) {
    Matcher m = p.matcher(s);
    StringBuffer sb = new StringBuffer();
    while (m.find()) { m.appendReplacement(sb, replace); }
    m.appendTail(sb);
    return sb.toString();
  }
}
