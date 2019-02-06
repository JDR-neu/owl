// code by jph
package ch.ethz.idsc.owl.math.order;

import java.util.function.BiPredicate;

/** a binary relation has a single function test(x, y)
 * that returns whether x and y satisfy the relation.
 * 
 * The input parameters x and y are of the same type.
 * Therefore BinaryRelation is a special example of a
 * {@link BiPredicate}.
 * 
 * @param <T> */
@FunctionalInterface
public interface BinaryRelation<T> extends BiPredicate<T, T> {
  // ---
}
