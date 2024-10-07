import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class OrderOperationsTest {

    @Test
    public void testPartitionByPaymentMethod() {
        Product product1 = new Product("Book", 2, 10.0);
        Order order1 = new Order("1", "Alice", Arrays.asList(product1), PaymentMethod.CARD);
        Order order2 = new Order("2", "Bob", Arrays.asList(product1), PaymentMethod.CASH);

        OrderOperations streamOperations = new StreamOrderOperations(Arrays.asList(order1, order2));
        OrderOperations traditionalOperations = new TraditionalOrderOperations(Arrays.asList(order1, order2));

        Map<PaymentMethod, List<Order>> resultStream = streamOperations.partitionByPaymentMethod();
        Map<PaymentMethod, List<Order>> resultTraditional = traditionalOperations.partitionByPaymentMethod();

        assertEquals(1, resultStream.get(PaymentMethod.CARD).size());
        assertEquals(1, resultStream.get(PaymentMethod.CASH).size());
        assertEquals(1, resultTraditional.get(PaymentMethod.CARD).size());
        assertEquals(1, resultTraditional.get(PaymentMethod.CASH).size());
    }

    @Test
    public void testGroupByCustomer() {
        Product product1 = new Product("Book", 2, 10.0);
        Order order1 = new Order("1", "Alice", Arrays.asList(product1), PaymentMethod.CARD);
        Order order2 = new Order("2", "Bob", Arrays.asList(product1), PaymentMethod.CASH);
        Order order3 = new Order("3", "Alice", Arrays.asList(product1), PaymentMethod.CARD);

        OrderOperations streamOperations = new StreamOrderOperations(Arrays.asList(order1, order2, order3));
        OrderOperations traditionalOperations = new TraditionalOrderOperations(Arrays.asList(order1, order2, order3));

        Map<String, List<Order>> resultStream = streamOperations.groupByCustomer();
        Map<String, List<Order>> resultTraditional = traditionalOperations.groupByCustomer();

        assertEquals(2, resultStream.size());
        assertEquals(2, resultStream.get("Alice").size());
        assertEquals(1, resultStream.get("Bob").size());
        assertEquals(2, resultTraditional.size());
        assertEquals(2, resultTraditional.get("Alice").size());
        assertEquals(1, resultTraditional.get("Bob").size());
    }

    @Test
    public void testCountTotalUnitsSold() {
        Product product1 = new Product("Book", 2, 10.0);
        Product product2 = new Product("Pen", 1, 1.5);
        Order order1 = new Order("1", "Alice", Arrays.asList(product1), PaymentMethod.CARD);
        Order order2 = new Order("2", "Bob", Arrays.asList(product2, product1), PaymentMethod.CASH);

        OrderOperations streamOperations = new StreamOrderOperations(Arrays.asList(order1, order2));
        OrderOperations traditionalOperations = new TraditionalOrderOperations(Arrays.asList(order1, order2));

        Map<String, Integer> resultStream = streamOperations.countTotalUnitsSold();
        Map<String, Integer> resultTraditional = traditionalOperations.countTotalUnitsSold();

        assertEquals(4, resultStream.get("Book"));
        assertEquals(1, resultStream.get("Pen"));
        assertEquals(4, resultTraditional.get("Book"));
        assertEquals(1, resultTraditional.get("Pen"));
    }

    @Test
    public void testSortOrdersByTotalValue() {
        Product product1 = new Product("Book", 2, 10.0);
        Product product2 = new Product("Pen", 1, 1.5);
        Order order1 = new Order("1", "Alice", Arrays.asList(product1), PaymentMethod.CARD);
        Order order2 = new Order("2", "Bob", Arrays.asList(product2), PaymentMethod.CASH);

        OrderOperations streamOperations = new StreamOrderOperations(Arrays.asList(order1, order2));
        OrderOperations traditionalOperations = new TraditionalOrderOperations(Arrays.asList(order1, order2));

        List<Order> resultStream = streamOperations.sortOrdersByTotalValue();
        List<Order> resultTraditional = traditionalOperations.sortOrdersByTotalValue();

        assertEquals(order2, resultStream.getFirst());
        assertEquals(order2, resultTraditional.getFirst());
    }

    @Test
    public void testGetUniqueProducts() {
        Product product1 = new Product("Book", 2, 10.0);
        Product product2 = new Product("Pen", 1, 1.5);
        Order order1 = new Order("1", "Alice", Arrays.asList(product1), PaymentMethod.CARD);
        Order order2 = new Order("2", "Bob", Arrays.asList(product2, product1), PaymentMethod.CASH);

        OrderOperations streamOperations = new StreamOrderOperations(Arrays.asList(order1, order2));
        OrderOperations traditionalOperations = new TraditionalOrderOperations(Arrays.asList(order1, order2));

        Set<String> resultStream = streamOperations.getUniqueProducts();
        Set<String> resultTraditional = traditionalOperations.getUniqueProducts();

        assertEquals(2, resultStream.size());
        assertTrue(resultStream.contains("Book"));
        assertTrue(resultStream.contains("Pen"));
        assertEquals(2, resultTraditional.size());
        assertTrue(resultTraditional.contains("Book"));
        assertTrue(resultTraditional.contains("Pen"));
    }

    @Test
    public void testFindHighestValueOrder() {
        Product product1 = new Product("Book", 2, 10.0);
        Product product2 = new Product("Pen", 1, 1.5);
        Order order1 = new Order("1", "Alice", Arrays.asList(product1), PaymentMethod.CARD);
        Order order2 = new Order("2", "Bob", Arrays.asList(product2), PaymentMethod.CASH);

        OrderOperations streamOperations = new StreamOrderOperations(Arrays.asList(order1, order2));
        OrderOperations traditionalOperations = new TraditionalOrderOperations(Arrays.asList(order1, order2));

        Optional<Order> highestOrderStream = streamOperations.findHighestValueOrder();
        Optional<Order> highestOrderTraditional = traditionalOperations.findHighestValueOrder();

        assertTrue(highestOrderStream.isPresent());
        assertEquals(order1, highestOrderStream.get());
        assertTrue(highestOrderTraditional.isPresent());
        assertEquals(order1, highestOrderTraditional.get());

        // Test with no orders
        OrderOperations emptyStreamOperations = new StreamOrderOperations(Collections.emptyList());
        OrderOperations emptyTraditionalOperations = new TraditionalOrderOperations(Collections.emptyList());

        assertFalse(emptyStreamOperations.findHighestValueOrder().isPresent());
        assertFalse(emptyTraditionalOperations.findHighestValueOrder().isPresent());
    }
}
