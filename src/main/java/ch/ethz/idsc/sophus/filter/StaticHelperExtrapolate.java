// code by ob
package ch.ethz.idsc.sophus.filter;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Last;
import ch.ethz.idsc.tensor.red.Total;
import ch.ethz.idsc.tensor.sca.Chop;

/* package */ enum StaticHelperExtrapolate {
  ;
  /** @param mask of weights of affine combination [a1, a2, ..., an]
   * @return weights for iterative geodesic averages from left to right
   * @throws Exception if mask is not a vector, or empty, or entries do not add up to 1 */
  public static Tensor splits(Tensor mask) {
    // Check for affinity
    Chop._12.requireClose(Total.of(mask), RealScalar.ONE);
    // no extrapolation possible
    if (mask.length() == 1) {
      return Tensors.vector(1);
    }
    Tensor splits = Tensors.empty();
    Scalar factor = mask.Get(0);
    // Calculate interpolation splits
    for (int index = 1; index < mask.length() - 1; ++index) {
      factor = factor.add(mask.get(index));
      Scalar lambda = mask.Get(index).divide(factor);
      splits.append(lambda);
    }
    splits.append(Last.of(mask));
    // Calculate extrapolation splits
    Scalar temp = RealScalar.ZERO;
    for (int index = 0; index < splits.length(); ++index)
      temp = (temp.add(RealScalar.ONE)).multiply(RealScalar.ONE.subtract(splits.Get(index)));
    splits.append(temp.reciprocal().add(RealScalar.ONE));
    return splits;
  }
}
