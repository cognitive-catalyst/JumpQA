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
package com.ibm.watson.ecosystem.util.random;

import java.util.Random;

// An extension of the Random class which makes generating integers easy.
public class RandInt extends Random {
  
  // ------------------------------------------------------------------------------------------ //
  // Public
  // ------------------------------------------------------------------------------------------ //
  
  //Not inclusive of max value.
  public int randInt(int min, int max) {
    return this.nextInt((max - min)) + min;
  }
  
  public int randInt(int max) {
    return randInt(0, max);
  }
  
  // ------------------------------------------------------------------------------------------ //
  // Private
  // ------------------------------------------------------------------------------------------ //
  private static final long serialVersionUID = 2755108680686263216L;
  
}
