package BlueTerrain;

import javafx.stage.Stage;

public class Order {
    private int orderId;
    private String itemName;
    private double itemPrice;
    private String orderStatus;

    public Order(int orderId, String orderStatus, String itemName, double itemPrice) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.orderStatus = orderStatus;
    }

    public int getOrderId() { return orderId; }
    public String getItemName() { return itemName; }
    public double getItemPrice() { return itemPrice; }
    public String getOrderStatus() { return orderStatus; }

    public static Object showOrder(Stage primaryStage, String firstName, String lastName) {
        throw new UnsupportedOperationException("Unimplemented method 'showOrder'");
    }

    public static Object showOrderPopup(Stage primaryStage, String firstName, String lastName) {
        throw new UnsupportedOperationException("Unimplemented method 'showOrderPopup'");
    }
}

