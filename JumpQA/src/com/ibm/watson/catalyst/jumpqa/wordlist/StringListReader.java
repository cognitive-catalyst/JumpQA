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

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import com.ibm.watson.catalyst.objectio.readers.AReader;
import com.ibm.watson.catalyst.objectio.readers.IReader;

/**
 * Creates a list of strings where each entry is a line of the supplied file or
 * input stream.
 * 
 * @author Will Beason
 * @version 0.1.1
 * @since 0.1.0
 *
 */
public class StringListReader extends AReader<String> implements IReader {
  
  private static final Logger logger = Logger.getLogger(StringListReader.class.getName());
  
  @Override
  protected String string2Object(String aString) {
    return aString;
  }
  
  @Override
  public List<String> read(File aFile) {
    logger.info("Reading string list from " + aFile);
    return super.read(aFile);
  }
  
}
