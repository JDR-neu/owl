// code by ob, jph
package ch.ethz.idsc.sophus.group;

import ch.ethz.idsc.sophus.math.PseudoDistance;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.opt.Pi;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.Mod;

public enum Se2PseudoDistance implements PseudoDistance {
  INSTANCE;
  // ---
  private static final Mod MOD_DISTANCE = Mod.function(Pi.TWO, Pi.VALUE.negate());

  @Override // from PseudoDistance
  public Tensor pseudoDistance(Tensor p, Tensor q) {
    Tensor tensor = Se2Differences.INSTANCE.pair(p, q);
    return Tensors.of( //
        Norm._2.ofVector(tensor.extract(0, 2)), //
        MOD_DISTANCE.apply(tensor.Get(2).abs()));
  }
}
