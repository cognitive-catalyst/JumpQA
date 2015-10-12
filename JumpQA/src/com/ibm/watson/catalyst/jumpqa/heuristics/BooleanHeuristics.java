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
package com.ibm.watson.catalyst.jumpqa.heuristics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * A class which holds a set of true or false heuristics. Can test if enough
 * heuristics are met.
 * 
 * @author Will Beason
 * @version 0.1.1
 * @since 0.1.0
 *
 * @param <T> The class of object to evaluate. The input class of the
 *          predicates.
 */
public class BooleanHeuristics<T> implements IHeuristics<T, Boolean> {
  
  private final Collection<Predicate<T>> _predicates;
  
  /**
   * Instantiates a BooleanHeuristics object
   */
  public BooleanHeuristics() {
    this(new ArrayList<Predicate<T>>());
  }
  
  /**
   * Instantiates a BooleanHeuristics objects with a list of predicates.
   * 
   * @param predicates the list of predicates to instantiate with
   */
  public BooleanHeuristics(Collection<Predicate<T>> predicates) {
    _predicates = (predicates == null) ? new ArrayList<Predicate<T>>() : predicates;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    
    BooleanHeuristics<?> other = (BooleanHeuristics<?>) obj;
    if (!Objects.equals(other._predicates, this._predicates)) return false;
    return true;
  }
  
  @Override
  public int hashCode() {
    return (new HashCodeBuilder(SEED, MULTIPLY)).append(_predicates).hashCode();
  }
  
  private static final int SEED = 1368181723;
  private static final int MULTIPLY = 674123509;
  
  /**
   * Adds a new predicate to test
   * 
   * @param aPredicate a new boolean test
   * @return whether the predicate was added successfully
   */
  public boolean add(final Predicate<T> aPredicate) {
    return _predicates.add(aPredicate);
  }
  
  /**
   * Whether all predicates return true
   * 
   * @param input the object to evaluate
   * @return whether all predicates returned true
   */
  public final boolean allTrue(final T input) {
    for (final Predicate<T> p : _predicates) {
      if (!p.test(input)) return false;
    }
    return true;
  }
  
  /**
   * Whether any predicate returns true
   * 
   * @param input the object to evaluate
   * @return whether any predicate returned true
   */
  public final boolean anyTrue(final T input) {
    for (final Predicate<T> p : _predicates) {
      if (p.test(input)) return true;
    }
    return false;
  }
  
  @Override
  public final List<Boolean> evaluate(final T input) {
    final List<Boolean> result = new ArrayList<Boolean>();
    _predicates.forEach((p) -> p.test(input));
    return result;
  }
  
}
