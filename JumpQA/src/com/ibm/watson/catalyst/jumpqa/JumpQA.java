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

package com.ibm.watson.catalyst.jumpqa;

import java.io.File;
import java.util.Collection;
import java.util.logging.Logger;

import com.ibm.watson.catalyst.jumpqa.entry.IGroundTruthEntry;
import com.ibm.watson.catalyst.jumpqa.matcher.GTEntryGenerator;
import com.ibm.watson.catalyst.jumpqa.template.ITemplate;
import com.ibm.watson.catalyst.jumpqa.template.TemplateReader;
import com.ibm.watson.catalyst.jumpqa.trec.Trec;
import com.ibm.watson.catalyst.jumpqa.trec.TrecReader;
import com.ibm.watson.catalyst.objectio.writers.CSVOutputWriter;
import com.ibm.watson.catalyst.util.baseproperties.BaseProperties;

/**
 * Reads a corpus in JSON format and creates ground truth.
 * 
 * Specifically, reads the properties file specified at the command line to load
 * two files: the templates and the corpus. The corpus is a JSON file containing
 * a set of documents and metadata. The templates file is a tab-delimited CSV
 * where each line represents configuration for a template. The most important
 * column is the first, containing the exact name of the template class to be
 * used.
 * 
 * The output is a CSV of questions, answers, and metadata necessary for
 * uploading the ground truth to a Watson Q&A instance.
 * 
 * See the README.md for more information and a tutorial.
 * 
 * @author Will Beason
 * @version 0.1.2
 * @since 0.1.0
 * 
 */
public final class JumpQA {
  
  private final static Logger logger = Logger.getLogger(JumpQA.class.getName());
  private static BaseProperties PROPERTIES;
  
  /**
   * Using the properties file specified.
   * 
   * @param args The first argument is the properties file to use. All others
   *          are ignored. Defaults to the test properties file if none is
   *          specified.
   * 
   */
  public static void main(final String[] args) {
    logger.info("JumpQA start");
    // TODO: Set to a copy of the test.properties which is in the main dir.
    PROPERTIES = BaseProperties.setInstance(args, "sample/test.properties");
    
    final Collection<ITemplate> templates = (new TemplateReader()).read(getFile("templates"));
    final Collection<Trec> trecs = (new TrecReader()).read(getFile("corpus"));
    final Collection<IGroundTruthEntry> matches = (new GTEntryGenerator()).genMatches(templates,
        trecs);
    
    (new CSVOutputWriter<IGroundTruthEntry>(getFile("output"))).write(matches);
    logger.info("JumpQA end");
  }
  
  private static File getFile(final String key) {
    return PROPERTIES.getFile(key);
  }
  
}
