import java.util.*;
import java.util.stream.Collectors;

public class StreamOrderOperations implements OrderOperations {
    private List<Order> orders;

    public StreamOrderOperations(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public Map<PaymentMethod, List<Order>> partitionByPaymentMethod() {
        return orders.stream()
                .collect(Collectors.partitioningBy(order -> order.getPaymentMethod() == PaymentMethod.CARD))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey() ? PaymentMethod.CARD : PaymentMethod.CASH,
                        Map.Entry::getValue
                ));
    }

    @Override
    public Map<String, List<Order>> groupByCustomer() {
        return orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomerName));
    }

    @Override
    public Map<String, Integer> countTotalUnitsSold() {
        return orders.stream()
                .flatMap(order -> order.getProducts().stream())
                .collect(Collectors.groupingBy(Product::getName, Collectors.summingInt(Product::getQuantity)));
    }

    @Override
    public List<Order> sortOrdersByTotalValue() {
        return orders.stream()
                .sorted(Comparator.comparingDouble(Order::calculateTotalValue))
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> getUniqueProducts() {
        return orders.stream()
                .flatMap(order -> order.getProducts().stream())
                .map(Product::getName)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<Order> findHighestValueOrder() {
        return orders.stream()
                .max(Comparator.comparingDouble(Order::calculateTotalValue));
    }
}
