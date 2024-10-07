import java.util.List;

public class Order {
    private String orderNumber;
    private String customerName;
    private List<Product> products;
    private PaymentMethod paymentMethod;

    public Order(String orderNumber, String customerName, List<Product> products, PaymentMethod paymentMethod) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.products = products;
        this.paymentMethod = paymentMethod;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public double calculateTotalValue() {
        return products.stream()
                .mapToDouble(product -> product.getQuantity() * product.getPrice())
                .sum();
    }
}