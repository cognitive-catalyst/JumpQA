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

import java.io.File;

import org.apache.commons.csv.CSVRecord;

import com.ibm.watson.catalyst.jumpqa.matcher.EntryPatterns;
import com.ibm.watson.catalyst.jumpqa.questioner.TextTemplateQuestioner;
import com.ibm.watson.catalyst.jumpqa.replacer.SequentialReplacer;
import com.ibm.watson.catalyst.jumpqa.splitter.SplitterFactory.Size;
import com.ibm.watson.catalyst.jumpqa.stringcleaner.StringCleaner;
import com.ibm.watson.catalyst.jumpqa.stringcleaner.StringCleaner.Clean;

/**
 * Creates a TextTemplate from a CSVRecord.
 * 
 * @author Will Beason
 * @version 0.1.1
 * @since 0.1.0
 *
 */
public class TextTemplateRecordReader implements ITemplateRecordReader {
  
  @Override
  public PatternTemplate readRecord(final CSVRecord aRecord) {
    final String templateId = aRecord.get("Template ID");
    final Size matchSize = Size.valueOf(aRecord.get("Match Size").toUpperCase());
    final Size answerSize = Size.valueOf(aRecord.get("Answer Size").toUpperCase());
    final TextTemplateQuestioner questioner = new TextTemplateQuestioner(aRecord.get("Question"));
    final EntryPatterns matcher = new EntryPatterns(aRecord.get("Search 1"));
    // final String words1 = aRecord.get("Words 1");
    final SequentialReplacer sr = new SequentialReplacer(new File(aRecord.get("Words 3")));
    final Clean clean = StringCleaner.getClean(aRecord.get("Clean"));
    return new PatternTemplate(templateId, answerSize, matchSize, questioner, sr, clean, matcher);
  }
  
}
