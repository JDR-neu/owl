// code by jph
package ch.ethz.idsc.owl.bot.se2.glc;

import java.util.ArrayList;
import java.util.List;

import ch.ethz.idsc.owl.bot.se2.Se2StateSpaceModel;
import ch.ethz.idsc.owl.gui.ren.GridRender;
import ch.ethz.idsc.owl.gui.win.OwlyAnimationFrame;
import ch.ethz.idsc.owl.math.StateSpaceModels;
import ch.ethz.idsc.owl.math.flow.Flow;
import ch.ethz.idsc.owl.math.state.StateTime;
import ch.ethz.idsc.owl.math.state.TrajectorySample;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.lie.AngleVector;
import ch.ethz.idsc.tensor.opt.Pi;
import ch.ethz.idsc.tensor.qty.Degree;

/* package */ class PursuitSimulation extends Se2Demo {
  @Override
  protected void configure(OwlyAnimationFrame owlyAnimationFrame) {
    CarEntity carEntity = new CarEntity( //
        new StateTime(Tensors.vector(2, 0, Math.PI / 2), RealScalar.ZERO), //
        // new PurePursuitControl(CarEntity.LOOKAHEAD, CarEntity.MAX_TURNING_RATE), //
        new ClothoidFixedControl(CarEntity.LOOKAHEAD, CarEntity.MAX_TURNING_RATE), //
        CarEntity.PARTITIONSCALE, CarEntity.CARFLOWS, CarEntity.SHAPE);
    List<TrajectorySample> trajectory = new ArrayList<>();
    int t = 0;
    // Scalar q = Pi.VALUE.n
    for (Tensor angle : Subdivide.of(Degree.of(0), Degree.of(360), 100)) {
      Tensor x = AngleVector.of(angle.Get()).multiply(RealScalar.of(2)).append(angle.add(Pi.HALF));
      StateTime stateTime = new StateTime(x, RealScalar.of(++t));
      Flow flow = StateSpaceModels.createFlow(Se2StateSpaceModel.INSTANCE, Tensors.vector(1, 0, 0));
      TrajectorySample trajectorySample = new TrajectorySample(stateTime, flow);
      trajectory.add(trajectorySample);
    }
    carEntity.trajectory(trajectory);
    owlyAnimationFrame.add(carEntity);
    owlyAnimationFrame.addBackground(new GridRender(Subdivide.of(0, 10, 5)));
  }

  public static void main(String[] args) {
    new PursuitSimulation().start().jFrame.setVisible(true);
  }
}
