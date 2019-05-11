// code by astoll
package ch.ethz.idsc.owl.math.order;

import java.util.Collections;
import java.util.List;

import ch.ethz.idsc.owl.demo.order.DigitSumDivisibilityPreorder;
import ch.ethz.idsc.owl.demo.order.ScalarTotalOrder;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class TransitiveMinTrackerTest extends TestCase {
  public void testDigestNotEmptyList() {
    OrderComparator<Integer> orderComparator = DigitSumDivisibilityPreorder.INTEGER;
    TransitiveMinTracker<Integer> digitSumDivisibility = TransitiveMinTracker.withList(orderComparator);
    digitSumDivisibility.digest(123);
    assertFalse(digitSumDivisibility.getMinElements().isEmpty());
  }

  public void testDigestNotEmptySet() {
    OrderComparator<Integer> orderComparator = DigitSumDivisibilityPreorder.INTEGER;
    TransitiveMinTracker<Integer> digitSumDivisibility = TransitiveMinTracker.withSet(orderComparator);
    digitSumDivisibility.digest(123);
    assertFalse(digitSumDivisibility.getMinElements().isEmpty());
  }

  public void testPartial() {
    OrderComparator<Scalar> universalComparator = new Order<>(Scalars::divides);
    TransitiveMinTracker<Scalar> divisibility = TransitiveMinTracker.withList(universalComparator);
    divisibility.digest(RealScalar.of(10));
    assertTrue(divisibility.getMinElements().contains(RealScalar.of(10)));
    divisibility.digest(RealScalar.of(2));
    assertTrue(divisibility.getMinElements().contains(RealScalar.of(2)));
    assertFalse(divisibility.getMinElements().contains(RealScalar.of(10)));
    divisibility.digest(RealScalar.of(3));
    assertTrue(divisibility.getMinElements().contains(RealScalar.of(2)));
    assertTrue(divisibility.getMinElements().contains(RealScalar.of(3)));
    divisibility.digest(RealScalar.of(7));
    divisibility.digest(RealScalar.of(6));
    assertTrue(divisibility.getMinElements().contains(RealScalar.of(2)));
    assertTrue(divisibility.getMinElements().contains(RealScalar.of(3)));
    assertTrue(divisibility.getMinElements().contains(RealScalar.of(7)));
    assertFalse(divisibility.getMinElements().contains(RealScalar.of(6)));
  }

  public void testTotal() {
    OrderComparator<Scalar> universalComparator = ScalarTotalOrder.INSTANCE;
    TransitiveMinTracker<Scalar> lessEquals = TransitiveMinTracker.withList(universalComparator);
    lessEquals.digest(RealScalar.of(10));
    assertTrue(lessEquals.getMinElements().contains(RealScalar.of(10)));
    lessEquals.digest(RealScalar.of(2));
    assertTrue(lessEquals.getMinElements().contains(RealScalar.of(2)));
    assertFalse(lessEquals.getMinElements().contains(RealScalar.of(10)));
    lessEquals.digest(RealScalar.of(3));
    assertTrue(lessEquals.getMinElements().contains(RealScalar.of(2)));
    assertFalse(lessEquals.getMinElements().contains(RealScalar.of(3)));
    lessEquals.digest(RealScalar.of(7));
    lessEquals.digest(RealScalar.of(6));
    assertTrue(lessEquals.getMinElements().contains(RealScalar.of(2)));
    assertFalse(lessEquals.getMinElements().contains(RealScalar.of(3)));
    assertFalse(lessEquals.getMinElements().contains(RealScalar.of(7)));
    assertFalse(lessEquals.getMinElements().contains(RealScalar.of(6)));
    assertTrue(lessEquals.getMinElements().size() == 1);
  }

  public void testWithList() {
    OrderComparator<Integer> orderComparator = DigitSumDivisibilityPreorder.INTEGER;
    TransitiveMinTracker<Integer> digitSumDivisibility = TransitiveMinTracker.withList(orderComparator);
    digitSumDivisibility.digest(123);
    assertTrue(digitSumDivisibility.getMinElements().contains(123));
    digitSumDivisibility.digest(122);
    assertTrue(digitSumDivisibility.getMinElements().contains(123));
    assertTrue(digitSumDivisibility.getMinElements().contains(122));
    digitSumDivisibility.digest(426);
    assertFalse(digitSumDivisibility.getMinElements().contains(426));
    digitSumDivisibility.digest(1);
    assertTrue(digitSumDivisibility.getMinElements().contains(1));
    assertTrue(digitSumDivisibility.getMinElements().size() == 1);
  }

  public void testWithSet() {
    OrderComparator<Integer> orderComparator = DigitSumDivisibilityPreorder.INTEGER;
    TransitiveMinTracker<Integer> digitSumDivisibility = TransitiveMinTracker.withSet(orderComparator);
    digitSumDivisibility.digest(123);
    assertTrue(digitSumDivisibility.getMinElements().contains(123));
    digitSumDivisibility.digest(122);
    assertTrue(digitSumDivisibility.getMinElements().contains(123));
    assertTrue(digitSumDivisibility.getMinElements().contains(122));
    digitSumDivisibility.digest(426);
    assertFalse(digitSumDivisibility.getMinElements().contains(426));
    digitSumDivisibility.digest(1);
    assertTrue(digitSumDivisibility.getMinElements().contains(1));
    assertTrue(digitSumDivisibility.getMinElements().size() == 1);
  }

  public void testDuplicateEntriesList() {
    OrderComparator<Integer> orderComparator = DigitSumDivisibilityPreorder.INTEGER;
    TransitiveMinTracker<Integer> digitSumDivisibility = TransitiveMinTracker.withList(orderComparator);
    digitSumDivisibility.digest(333);
    digitSumDivisibility.digest(333);
    assertTrue(digitSumDivisibility.getMinElements().contains(333));
    assertTrue(digitSumDivisibility.getMinElements().size() == 1);
  }

  public void testDuplicateEntriesSet() {
    OrderComparator<Integer> orderComparator = DigitSumDivisibilityPreorder.INTEGER;
    TransitiveMinTracker<Integer> digitSumDivisibility = TransitiveMinTracker.withSet(orderComparator);
    digitSumDivisibility.digest(333);
    digitSumDivisibility.digest(333);
    assertTrue(digitSumDivisibility.getMinElements().contains(333));
    assertTrue(digitSumDivisibility.getMinElements().size() == 1);
  }

  public void testLexicographic() {
    List<OrderComparator> comparators = Collections.nCopies(2, ScalarTotalOrder.INSTANCE);
    Tensor tensorX = Tensors.fromString("{1,2}");
    Tensor tensorY = Tensors.fromString("{2,3}");
    LexicographicComparator genericLexicographicOrder = new LexicographicComparator(comparators);
    TransitiveMinTracker<Iterable<? extends Object>> lexTracker = TransitiveMinTracker.withSet(genericLexicographicOrder);
    lexTracker.digest(tensorX);
    lexTracker.digest(tensorY);
    assertTrue(lexTracker.getMinElements().contains(tensorX));
  }
}
