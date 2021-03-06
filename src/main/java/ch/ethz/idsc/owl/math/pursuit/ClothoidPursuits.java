// code by gjoel
package ch.ethz.idsc.owl.math.pursuit;

import java.util.Optional;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

public enum ClothoidPursuits {
  ;
  /** @param tensor waypoints
   * @param entryFinder strategy
   * @param var
   * @return GeodesicPursuit */
  public static GeodesicPursuitInterface fromTrajectory( //
      Tensor tensor, TrajectoryEntryFinder entryFinder, Scalar var) {
    Optional<Tensor> lookAhead = entryFinder.on(tensor).apply(var).point;
    return lookAhead.isPresent() //
        ? new ClothoidPursuit(lookAhead.get())
        : VoidPursuit.INSTANCE;
  }
}
