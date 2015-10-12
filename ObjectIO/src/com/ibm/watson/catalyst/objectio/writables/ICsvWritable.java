/**
 * 
 */
package com.ibm.watson.catalyst.objectio.writables;

import java.util.List;

/** TODO: Class description
 * 
 * @author Will Beason
 * @version 0.1.2
 * @since 0.1.2
 *
 */
public interface ICsvWritable extends Iterable<String> {
  
  /** 
   * Gets a list of headers
   * @return a list of headers to include at the beginning of the CSV
   */
  public List<String> getHeader();
  
  /**
   * Turns the object into a CSV record
   * @return a list of strings which can be printed with a CSVPrinter
   */
  public List<String> toCsvRecord();
  
}
