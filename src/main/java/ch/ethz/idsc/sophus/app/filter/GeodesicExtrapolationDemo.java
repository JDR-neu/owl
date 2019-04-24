// code by ob
package ch.ethz.idsc.sophus.app.filter;

import java.awt.Graphics2D;

import ch.ethz.idsc.owl.gui.win.GeometricLayer;
import ch.ethz.idsc.sophus.app.api.AbstractDemo;
import ch.ethz.idsc.sophus.filter.GeodesicExtrapolation;
import ch.ethz.idsc.sophus.filter.GeodesicExtrapolationFilter;
import ch.ethz.idsc.sophus.sym.SymLinkImages;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.opt.TensorUnaryOperator;

/* package */ class GeodesicExtrapolationDemo extends DatasetKernelDemo {
  private Tensor refined = Tensors.empty();

  public GeodesicExtrapolationDemo() {
    updateState();
  }

  @Override
  protected void updateState() {
    super.updateState();
    // ---
    TensorUnaryOperator tensorUnaryOperator = //
        GeodesicExtrapolation.of(geodesicDisplay().geodesicInterface(), spinnerKernel.getValue());
    refined = GeodesicExtrapolationFilter.of(tensorUnaryOperator, geodesicDisplay().geodesicInterface(), spinnerRadius.getValue()).apply(control());
  }

  @Override // from RenderInterface
  protected Tensor protected_render(GeometricLayer geometricLayer, Graphics2D graphics) {
    if (jToggleSymi.isSelected())
      graphics.drawImage(SymLinkImages.extrapolation(spinnerKernel.getValue(), spinnerRadius.getValue()).bufferedImage(), 0, 0, null);
    return refined;
  }

  public static void main(String[] args) {
    AbstractDemo abstractDemo = new GeodesicExtrapolationDemo();
    abstractDemo.timerFrame.jFrame.setBounds(100, 100, 1000, 600);
    abstractDemo.timerFrame.jFrame.setVisible(true);
  }
}