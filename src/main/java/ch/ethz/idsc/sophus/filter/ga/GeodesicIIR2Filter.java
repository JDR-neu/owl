// code by ob
package ch.ethz.idsc.sophus.filter.ga;

import java.util.Objects;

import ch.ethz.idsc.sophus.math.SplitInterface;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.opt.TensorUnaryOperator;

/** filter blends extrapolated value with measurement */
public class GeodesicIIR2Filter implements TensorUnaryOperator {
  private static final Scalar TWO = RealScalar.of(2);
  // ---
  private final SplitInterface splitInterface;
  private final Scalar alpha;
  // ---
  private Tensor p = null;
  private Tensor q = null;

  public GeodesicIIR2Filter(SplitInterface splitInterface, Scalar alpha) {
    this.splitInterface = splitInterface;
    this.alpha = alpha;
  }

  public GeodesicIIR2Filter(SplitInterface splitInterface, Scalar alpha, Tensor p, Tensor q) {
    this(splitInterface, alpha);
    this.p = p;
    this.q = q;
  }

  /** @return extrapolated "best guess" value from the previous predictions */
  public synchronized Tensor extrapolate() {
    if (Objects.isNull(p))
      return q;
    return splitInterface.split(p, q, TWO);
  }

  @Override
  public synchronized Tensor apply(Tensor tensor) {
    if (Objects.isNull(q)) {
      q = tensor.copy();
      return q.copy();
    }
    Tensor result = splitInterface.split(extrapolate(), tensor, alpha);
    p = q.copy();
    q = result.copy();
    return result;
  }
}
