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
package com.ibm.watson.catalyst.corpus.tfidf.term;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@SuppressWarnings("serial")
public final class TermSet extends ArrayList<Term> implements Set<Term> {
  
  public TermSet() {
    super();
  }
  
  public int getMaxFrequency() {
    int result = 0;
    
    for (Term t : this) {
      result = Math.max(result, t.getFrequency());
    }
    
    return result;
  }
  
  @Override
  public boolean add(Term aTerm) {
    int index = -1;
    if ((index = this.indexOf(aTerm)) != -1) {
      this.set(index, new Term(this.get(index), aTerm));
      return false;
    }
    if (aTerm.equals("")) return false;
    super.add(new Term(aTerm));
    return true;
  }
  
  @Override
  public boolean contains(Object o) {
    for (Term t : this) {
      if (o==null ? t==null : o.equals(t)) return true;
    }
    return false;
  }
  
  @Override
  public boolean addAll(Collection<? extends Term> c) {
    for (Term t : c) {
      this.add(t);
    }
    return true;
  }
  
}
