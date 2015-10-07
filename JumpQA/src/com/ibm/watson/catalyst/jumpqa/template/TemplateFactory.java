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
package com.ibm.watson.catalyst.jumpqa.template;

import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.csv.CSVRecord;

/**
 * A factory class to read templates from a CSVRecord.
 * 
 * @author Will Beason
 * @version 0.1.1
 * @since 0.1.0
 *
 */
public class TemplateFactory implements ITemplateRecordReader {
  
  /**
   * Instantiates a TemplateFactory.
   */
  public TemplateFactory() {}
  
  @Override
  public ITemplate readRecord(final CSVRecord aRecord) {
    logger.fine("Reading record #" + aRecord.getRecordNumber());
    
    final String templateClass = aRecord.get("Template Class");
    logger.finer("Template class is" + templateClass);
    final ITemplateRecordReader trr = readers.get(templateClass);
    if (trr == null) throw new RuntimeException("Template Class not found: " + templateClass);
    
    return trr.readRecord(aRecord);
  }
  
  private final static Logger logger = Logger.getLogger(TemplateFactory.class.getName());
  
  private static final Map<String, ITemplateRecordReader> readers = new Hashtable<String, ITemplateRecordReader>();
  
  static {
    readers.put("TextTemplate", new TextTemplateRecordReader());
    // TODO: Add more templates
  }
  
}
