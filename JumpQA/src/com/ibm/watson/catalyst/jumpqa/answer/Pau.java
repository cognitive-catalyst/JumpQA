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
/**
 * 
 */
package com.ibm.watson.catalyst.jumpqa.answer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.ibm.watson.catalyst.objectio.misc.IPrintable;
import com.ibm.watson.catalyst.objectio.writers.IWritable;

/**
 * TODO: Class description
 * 
 * @author Will Beason
 * @version 0.1.2
 * @since 0.1.1
 *
 */
public class Pau implements IWritable, IPrintable {
  
  /**
   * 
   */
  public Pau() {
    this("", "");
  }
  
  /**
   * @param aPauTitle the title of the PAU
   * @param aPauId the ID of the PAU
   */
  public Pau(String aPauTitle, String aPauId) {
    _pauTitle = aPauTitle;
    _pauId = aPauId;
  }
  
  /**
   * @param aPau the PAU to recreate
   */
  public Pau(Pau aPau) {
    _pauTitle = aPau._pauTitle;
    _pauId = aPau._pauId;
  }
  
  /** 
   * TODO: Method description
   * @return the PAU Title
   */
  public String getPauTitle() {
    return _pauTitle;
  }
  
  /** 
   * TODO: Method description
   * @return the PAU ID
   */
  public String getPauId() {
    return _pauId;
  }
  
  private static List<String> headerList;
  static {
    List<String> hList = new ArrayList<String>();
    hList.add("PAU Title");
    hList.add("PAU ID");
    headerList = Collections.unmodifiableList(hList);
  }
  
  @Override
  public List<String> getHeaderList() {
    return headerList;
  }
  
  @Override
  public List<String> toList() {
    List<String> result = new ArrayList<String>();
    result.add(_pauTitle);
    result.add(_pauId);
    return result;
  }
  
  @Override
  public Iterator<String> iterator() {
    return this.toList().iterator();
  }
  
  @Override
  public StringBuilder toStringBuilder() {
    StringBuilder sb = new StringBuilder();
    sb.append(_pauTitle).append(",").append(_pauId);
    return sb;
  }
  
  @Override
  public String toString() {
    return toStringBuilder().toString();
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Pau other = (Pau) obj;
    if (!Objects.equals(other._pauTitle, this._pauTitle)) return false;
    if (!Objects.equals(other._pauId, this._pauId)) return false;
    return true;
  }
  
  @Override
  public int hashCode() {
    return (new HashCodeBuilder(SEED, MULTIPLY)).append(_pauTitle).append(_pauId).hashCode();
  }
  
  private static final int SEED = 54878867;
  private static final int MULTIPLY = 152811037;
  
  private final String _pauTitle;
  private final String _pauId;
  
}
