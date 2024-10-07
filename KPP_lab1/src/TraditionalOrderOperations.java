import java.util.*;

public class TraditionalOrderOperations implements OrderOperations {
    private List<Order> orders;

    public TraditionalOrderOperations(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public Map<PaymentMethod, List<Order>> partitionByPaymentMethod() {
        Map<PaymentMethod, List<Order>> partitionedOrders = new HashMap<>();
        for (Order order : orders) {
            PaymentMethod paymentMethod = order.getPaymentMethod();
            partitionedOrders.putIfAbsent(paymentMethod, new ArrayList<>());
            partitionedOrders.get(paymentMethod).add(order);
        }
        return partitionedOrders;
    }

    @Override
    public Map<String, List<Order>> groupByCustomer() {
        Map<String, List<Order>> ordersByCustomer = new HashMap<>();
        for (Order order : orders) {
            String customerName = order.getCustomerName();
            ordersByCustomer.putIfAbsent(customerName, new ArrayList<>());
            ordersByCustomer.get(customerName).add(order);
        }
        return ordersByCustomer;
    }

    @Override
    public Map<String, Integer> countTotalUnitsSold() {
        Map<String, Integer> productCounts = new HashMap<>();
        for (Order order : orders) {
            for (Product product : order.getProducts()) {
                productCounts.put(product.getName(), productCounts.getOrDefault(product.getName(), 0) + product.getQuantity());
            }
        }
        return productCounts;
    }

    @Override
    public List<Order> sortOrdersByTotalValue() {
        List<Order> sortedOrders = new ArrayList<>(orders);
        sortedOrders.sort(Comparator.comparingDouble(Order::calculateTotalValue));
        return sortedOrders;
    }

    @Override
    public Set<String> getUniqueProducts() {
        Set<String> uniqueProducts = new HashSet<>();
        for (Order order : orders) {
            for (Product product : order.getProducts()) {
                uniqueProducts.add(product.getName());
            }
        }
        return uniqueProducts;
    }

    @Override
    public Optional<Order> findHighestValueOrder() {
        if (orders.isEmpty()) {
            return Optional.empty();
        }
        Order highestValueOrder = orders.get(0);
        for (Order order : orders) {
            if (order.calculateTotalValue() > highestValueOrder.calculateTotalValue()) {
                highestValueOrder = order;
            }
        }
        return Optional.of(highestValueOrder);
    }
}
