// code by jph
package ch.ethz.idsc.sophus.lie.se2;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class Se2ParametricDistanceTest extends TestCase {
  public void testSimple() {
    Scalar scalar = Se2ParametricDistance.INSTANCE.distance(Tensors.vector(1, 2, 3), Tensors.vector(1 + 3, 2 + 4, 3));
    assertEquals(scalar, RealScalar.of(5));
  }

  public void testHalf() {
    Scalar scalar = Se2ParametricDistance.INSTANCE.distance(Tensors.vector(1, 2, 3), Tensors.vector(1, 2 + 2 * 3, 3 + Math.PI));
    Chop._14.requireClose(scalar, RealScalar.of(3 * Math.PI));
  }

  public void testSimpleUnits() {
    Scalar scalar = Se2ParametricDistance.INSTANCE.distance(Tensors.fromString("{1[m], 2[m], 3}"), Tensors.fromString("{4[m], 6[m], 3}"));
    assertEquals(scalar, Quantity.of(5, "m"));
  }

  public void testOtherUnits() {
    Scalar scalar = Se2ParametricDistance.INSTANCE.distance(Tensors.fromString("{1[m], 2[m], 3}"), Tensors.fromString("{4[m], 6[m], 3.3}"));
    Chop._12.close(scalar, Quantity.of(5.018799335788676, "m"));
  }

  public void testOrigin() {
    assertEquals(Se2ParametricDistance.INSTANCE.norm(Tensors.vector(0, 0, 0)), RealScalar.of(0));
  }
}
