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
package com.ibm.watson.catalyst.jumpqa.trec;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.watson.catalyst.objectio.readers.AReader;
import com.ibm.watson.catalyst.objectio.readers.IReader;

/**
 * A class which reads Trec objects from a JSON file.
 * 
 * @author Will Beason
 * @version 0.1.1
 * @since 0.1.0
 *
 */
public class TrecReader extends AReader<Trec> implements IReader {
  
  private static final Logger logger = Logger.getLogger(TrecReader.class.getName());
  
  @Override
  public List<Trec> read(List<String> strings) {
    final List<Trec> result = new ArrayList<Trec>();
    
    strings.stream().forEachOrdered((s) -> {
      try {
        final JsonNode corpus = MAPPER.readValue(s, JsonNode.class);
        final JsonNode trecs = corpus.get("documents");
        trecs.forEach((trec) -> result.add(json2trec(trec)));
      } catch (final JsonParseException e) {
        throw new RuntimeException("Error while parsing.", e);
      } catch (final JsonMappingException e) {
        throw new RuntimeException("Error while mapping.", e);
      } catch (final IOException e) {
        throw new RuntimeException("IOError while parsing", e);
      }
    });
    
    logger.fine("Read " + result.size() + " trecs");
    return result;
  }
  
  @Override
  protected Trec string2Object(String aString) {
    throw new UnsupportedOperationException();
  }
  
  @Override
  public List<Trec> read(File aFile) {
    logger.info("Reading TRECs from " + aFile);
    return super.read(aFile);
  }
  
  private static final ObjectMapper MAPPER = new ObjectMapper();
  
  private static List<String> getParagraphs(final JsonNode trecJson) {
    final JsonNode paragraphs = trecJson.get("paragraphs");
    final List<String> result = new ArrayList<String>();
    paragraphs.forEach((par) -> result.add(par.asText()));
    return result;
  }
  
  private static Trec json2trec(final JsonNode trecJson) {
    final TrecBuilder tb = new TrecBuilder();
    if (trecJson.has("file")) {
      tb.setFile(trecJson.get("file").asText());
    }
    tb.setPauId(trecJson.get("pauID").asText());
    tb.setPauTitle(trecJson.get("pauTitle").asText());
    tb.setSourceDoc(trecJson.get("sourceDoc").asText());
    tb.setParagraphs(getParagraphs(trecJson));
    return tb.build();
  }
  
}
