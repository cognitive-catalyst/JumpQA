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
package com.ibm.watson.catalyst.objectio.writers;

import java.util.Iterator;
import java.util.List;

/**
 * Objects which may be written to files.
 * 
 * @author Will Beason
 * @version 0.1.0
 * @since 0.1.0
 *
 */
public interface IWritable extends Iterable<String> {
  
  /**
   * A list of the headers for the generated list.
   * @return the list of column headers
   */
  public List<String> getHeaderList();
  
  /**
   * Converts the object to a list.
   * 
   * @return the list representation of the object.
   */
  public List<String> toList();
  
  @Override
  public Iterator<String> iterator();
  
}
