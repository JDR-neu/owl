// code by astoll
package ch.ethz.idsc.owl.math.order;

import java.util.function.Function;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import junit.framework.TestCase;

public class SemiorderTest extends TestCase {
  public void testIdentity() {
    OrderComparator<Scalar> semiorder = Semiorder.comparator(Function.identity(), RealScalar.ONE);
    assertTrue(semiorder.compare(RealScalar.ONE, RealScalar.of(1.5)).equals(OrderComparison.INDIFFERENT));
    assertTrue(semiorder.compare(RealScalar.of(21), RealScalar.of(21)).equals(OrderComparison.INDIFFERENT));
    assertTrue(semiorder.compare(RealScalar.of(2.4), RealScalar.of(1)).equals(OrderComparison.STRICTLY_SUCCEEDS));
    assertTrue(semiorder.compare(RealScalar.of(3), RealScalar.of(4)).equals(OrderComparison.INDIFFERENT));
  }

  public void testString() {
    OrderComparator<String> semiorder = Semiorder.comparator(s -> RealScalar.of(s.length()), RealScalar.ONE);
    assertTrue(semiorder.compare("ewrwer", "ewrwer").equals(OrderComparison.INDIFFERENT));
    assertTrue(semiorder.compare("ewrwerr", "ewrwer").equals(OrderComparison.INDIFFERENT));
    assertTrue(semiorder.compare("ewrwerrrrrrr", "ewrwer").equals(OrderComparison.STRICTLY_SUCCEEDS));
    assertTrue(semiorder.compare("e", "ewrwer").equals(OrderComparison.STRICTLY_PRECEDES));
  }
}
