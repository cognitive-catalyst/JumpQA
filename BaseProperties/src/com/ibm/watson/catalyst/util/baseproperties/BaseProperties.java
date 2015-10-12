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
package com.ibm.watson.catalyst.util.baseproperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class BaseProperties {
  
  private final static Logger logger = Logger.getLogger(BaseProperties.class.getName());
  
  private BaseProperties(File aFile) {
    logger.config("Loading properties from " + aFile);
    _properties = new Properties();
    
    try (FileInputStream is = new FileInputStream(aFile);) {
      _properties.load(is);
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Unable to find properties file: " + aFile.toString(), e);
    } catch (IOException e) {
      throw new RuntimeException("IOError reading properties file: " + aFile.toString(), e);
    }
  }
  
  public String getProperty(String key) { return _properties.getProperty(key); }
  public String getProperty(String key, String aDefault) { 
    return _properties.getProperty(key, aDefault);
  }
  public File getFile(String key) { return new File(getProperty(key)); }
  
  public static synchronized final BaseProperties setInstance(String[] args, String aDefault) {
    if (instance == null) {
      File aFile;
      if (args.length == 0) {
        logger.config("No properties specified. Loading default.");
        aFile = new File(aDefault);
      } else {
        aFile = new File(args[0]);
      }
      instance = new BaseProperties(aFile);
    } else {
      logger.info("Properties already loaded.");
    }
    return instance;
  }
  
  public static BaseProperties getInstance() {
    return instance;
  }
  
  private final Properties _properties;
  
  private static BaseProperties instance = null;
  
}
