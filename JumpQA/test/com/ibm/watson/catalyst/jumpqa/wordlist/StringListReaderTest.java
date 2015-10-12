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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class StringListReaderTest {
  
  StringBuffer _testString1 = new StringBuffer("a line of text");
  StringBuffer _testString2 = new StringBuffer("another line of text");
  
  StringBuffer _sb;
  List<String> _multiLine;
  
  @Before
  public void setUp() {
    _multiLine = new ArrayList<String>();
    _multiLine.add(_testString1.toString());
    _multiLine.add(_testString2.toString());
    
    _sb = new StringBuffer();
    _sb.append(_testString1).append(System.lineSeparator()).append(_testString2);
  }
  
  @Test
  public void testReadInputStreamSingleLine() {
    List<String> singleLine = _multiLine.subList(0, 1);
    List<String> stringList = sb2StringList(_testString1);
    assertThat(singleLine, equalTo(stringList));
  }
  
  @Test
  public void testReadInputStreamMultiLine() {
    List<String> stringList = sb2StringList(_sb);
    assertThat(_multiLine, equalTo(stringList));
  }
  
  @Test
  public void testCommentAtBeginning() {
    _sb.insert(0, System.lineSeparator()).insert(0, "#comment");
    List<String> stringList = sb2StringList(_sb);
    assertThat(_multiLine, equalTo(stringList));
  }
  
  @Test
  public void testCommentInMiddle() {
    int insertPoint = _sb.indexOf(System.lineSeparator());
    _sb.insert(insertPoint, "#comment").insert(insertPoint, System.lineSeparator());
    List<String> stringList = sb2StringList(_sb);
    assertThat(_multiLine, equalTo(stringList));
  }
  
  @Test
  public void testCommentAtEnd() {
    _sb.append(System.lineSeparator()).append("#comment");
    List<String> stringList = sb2StringList(_sb);
    assertThat(_multiLine, equalTo(stringList));
  }
  
  @Test
  public void testEmptyAtBeginning() {
    _sb.insert(0, System.lineSeparator());
    List<String> stringList = sb2StringList(_sb);
    assertThat(_multiLine, equalTo(stringList));
  }
  
  @Test
  public void testEmptyInMiddle() {
    int insertPoint = _sb.indexOf(System.lineSeparator());
    _sb.insert(insertPoint, System.lineSeparator());
    List<String> stringList = sb2StringList(_sb);
    assertThat(_multiLine, equalTo(stringList));
  }
  
  @Test
  public void testEmptyAtEnd() {
    _sb.append(System.lineSeparator());
    List<String> stringList = sb2StringList(_sb);
    assertThat(_multiLine, equalTo(stringList));
  }
  
  private List<String> sb2StringList(StringBuffer sb) {
    return (new StringListReader()).read(sb.toString());
  }
  
}
