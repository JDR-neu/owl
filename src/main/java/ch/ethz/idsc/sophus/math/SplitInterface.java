// code by jph
package ch.ethz.idsc.sophus.math;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

@FunctionalInterface
public interface SplitInterface {
  /** @param p
   * @param q
   * @param scalar <em>not</em> constrained to the interval [0, 1]
   * @return point on curve/geodesic that connects p and q at parameter scalar
   * for scalar == 0 the function returns p, for scalar == 1 the function returns q */
  Tensor split(Tensor p, Tensor q, Scalar scalar);
}
