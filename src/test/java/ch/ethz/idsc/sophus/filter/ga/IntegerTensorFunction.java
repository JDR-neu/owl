// code by jph
package ch.ethz.idsc.sophus.filter.ga;

import java.io.Serializable;
import java.util.function.Function;

import ch.ethz.idsc.tensor.Tensor;

@FunctionalInterface
public interface IntegerTensorFunction extends Function<Integer, Tensor>, Serializable {
  // ---
}