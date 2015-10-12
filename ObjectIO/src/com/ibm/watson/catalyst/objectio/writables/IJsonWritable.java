/**
 * 
 */
package com.ibm.watson.catalyst.objectio.writables;

import com.fasterxml.jackson.databind.JsonNode;

/** TODO: Class description
 * 
 * @author Will Beason
 * @version 0.1.2
 * @since 0.1.2
 *
 */
public interface IJsonWritable {
  
  /** 
   * Converts the object to a Json Node
   * @return the Json representation of the object
   */
  public JsonNode toJson();
  
}
