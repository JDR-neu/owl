// code by jph
package ch.ethz.idsc.owl.math.order;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import ch.ethz.idsc.owl.demo.order.IntegerTotalOrder;
import ch.ethz.idsc.owl.demo.order.SetPartialOrder;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class GenericLexicographicComparatorTest extends TestCase {
  public void testSimple() {
    List<Scalar> list = new LinkedList<>();
    list.add(RealScalar.ONE);
    list.add(RealScalar.of(3));
    List<OrderComparator> comparators = Arrays.asList( //
        Order.comparator(Scalars::lessEquals), //
        Order.comparator(Scalars::lessEquals)); //
    GenericLexicographicComparator genericLexicographicOrder = new GenericLexicographicComparator(comparators);
    OrderComparison orderComparison = genericLexicographicOrder.compare(list, list);
    assertEquals(orderComparison, OrderComparison.INDIFFERENT);
  }

  public void testMixed2() {
    List<Object> listX = new LinkedList<>();
    listX.add(123);
    listX.add(Arrays.asList(2, 3, 4));
    List<Object> listY = new LinkedList<>();
    listY.add(123);
    listY.add(Arrays.asList(3, 4));
    List<OrderComparator> comparators = Arrays.asList( //
        IntegerTotalOrder.INSTANCE, //
        SetPartialOrder.INSTANCE); //
    GenericLexicographicComparator genericLexicographicOrder = new GenericLexicographicComparator(comparators);
    OrderComparison orderComparison = genericLexicographicOrder.compare(listX, listY);
    assertEquals(orderComparison, OrderComparison.STRICTLY_SUCCEEDS);
  }

  public void testTensor() {
    Tensor tensorX = Tensors.fromString("{{1,2,3}, 2}");
    Tensor tensorY = Tensors.fromString("{{2,3,4,5},-2}");
    BinaryRelation<Tensor> relation1 = (x, y) -> x.length() <= y.length();
    List<OrderComparator> comparators = Arrays.asList( //
        Order.comparator(relation1), //
        Order.comparator(Scalars::lessEquals)); //
    GenericLexicographicComparator genericLexicographicOrder = new GenericLexicographicComparator(comparators);
    OrderComparison orderComparison = genericLexicographicOrder.compare(tensorX, tensorY);
    assertEquals(orderComparison, OrderComparison.STRICTLY_PRECEDES);
  }
}
