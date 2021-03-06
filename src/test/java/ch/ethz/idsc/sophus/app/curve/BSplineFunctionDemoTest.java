// code by jph
package ch.ethz.idsc.sophus.app.curve;

import java.awt.image.BufferedImage;

import ch.ethz.idsc.sophus.app.api.AbstractDemoHelper;
import ch.ethz.idsc.sophus.sym.SymLinkImage;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import junit.framework.TestCase;

public class BSplineFunctionDemoTest extends TestCase {
  public void testSimple() {
    AbstractDemoHelper.brief(new BSplineFunctionDemo());
  }

  public void testDeBoorRational() {
    Scalar parameter = RationalScalar.of(9, 4);
    SymLinkImage symLinkImage = BSplineFunctionDemo.symLinkImage(4, 20, parameter);
    BufferedImage bufferedImage = symLinkImage.bufferedImage();
    assertTrue(300 < bufferedImage.getWidth());
    assertTrue(200 < bufferedImage.getHeight());
  }

  public void testDeBoorDecimal() {
    Scalar parameter = RealScalar.of(5.1);
    SymLinkImage symLinkImage = BSplineFunctionDemo.symLinkImage(5, 20, parameter);
    BufferedImage bufferedImage = symLinkImage.bufferedImage();
    assertTrue(300 < bufferedImage.getWidth());
    assertTrue(200 < bufferedImage.getHeight());
  }
}
