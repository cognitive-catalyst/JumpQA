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
package com.ibm.watson.catalyst.objectio.readers;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * For readers
 * 
 * @author Will Beason
 * @version 0.1.0
 * @since 0.1.0
 *
 */
public interface IReader {
  
  /**
   * Reads an InputStream and returns a list of objects
   * 
   * @param strings the strings to read into objects
   * @return the list of objects read
   */
  public List<?> read(final List<String> strings);
  
  /**
   * Given a file, returns an list containing objects.
   * 
   * @param aFile the file to read
   * @return an Iterable of objects
   * @throws IOException if an error is enocuntered reading the file
   */
  public List<?> read(final File aFile) throws IOException;
  
  /**
   * Reads a string and returns a list of object, treating each line as a new
   * object.
   * 
   * @param aString the string to parse
   * @return the list of objects
   */
  public List<?> read(final String aString);
  
  /**
   * Reads a file to generate a list of objects
   * 
   * @param aFile the path of the file to read
   * @return the list of strings read from the file
   */
  public List<?> readFile(final String aFile);
  
}
