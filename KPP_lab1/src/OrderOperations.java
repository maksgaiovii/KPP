import java.util.*;

public interface OrderOperations {
    Map<PaymentMethod, List<Order>> partitionByPaymentMethod();
    Map<String, List<Order>> groupByCustomer();
    Map<String, Integer> countTotalUnitsSold();
    List<Order> sortOrdersByTotalValue();
    Set<String> getUniqueProducts();
    Optional<Order> findHighestValueOrder();
}
