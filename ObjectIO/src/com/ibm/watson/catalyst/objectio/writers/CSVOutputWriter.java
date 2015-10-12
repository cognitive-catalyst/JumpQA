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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 * Writes a collection of writable objects in CSV form
 * 
 * @author Will Beason
 * @version 0.1.0
 * @param <V> the type of object written to a CSV
 * @since 0.1.0
 *
 */
public class CSVOutputWriter<V> implements IOutputWriter {
  
  private final String _encoding;
  
  private final File _outFile;
  
  /**
   * Instantiates a new CSVOutputWriter and defaults to UTF-8 encoding.
   * 
   * @param aOutFile the file to write to
   */
  public CSVOutputWriter(final File aOutFile) {
    this(aOutFile, "UTF-8");
  }
  
  /**
   * Instantiates a new CSVOutputWriter with specified encoding.
   * 
   * @param aOutFile the file to write to
   * @param aEncoding the encoding to use
   */
  public CSVOutputWriter(final File aOutFile, final String aEncoding) {
    this(aOutFile, aEncoding, null);
  }
  
  /**
   * @param aOutFile the file to write to
   * @param aEncoding the encoding to use
   * @param aHeader the header to include
   */
  public CSVOutputWriter(final File aOutFile, final String aEncoding, final List<String> aHeader) {
    _outFile = aOutFile;
    _encoding = aEncoding;
    _header = Optional.ofNullable(aHeader);
  }
  
  @Override
  public void write(final Collection<? extends IWritable> writables) {
    try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(_outFile),
        _encoding)) {
      try (CSVPrinter printer = new CSVPrinter(writer, FORMAT);) {
        if (_header.isPresent()) printer.printRecord(_header.get());
        printer.printRecords(writables);
      } catch (final IOException e) {
        throw new RuntimeException("IOError writing to " + _outFile, e);
      }
    } catch (final IOException e) {
      throw new RuntimeException("IOError opening " + _outFile + " for writing.");
    }
  }
  
  private static final CSVFormat FORMAT = CSVFormat.RFC4180.withDelimiter('\t');
  private final Optional<List<String>> _header;
  
}
