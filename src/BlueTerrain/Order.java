package BlueTerrain;

import javafx.stage.Stage;

public class Order {
    private int orderId;
    private String itemName;
    private double itemPrice;

    public Order(int orderId, String itemName, double itemPrice) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    public int getOrderId() { return orderId; }
    public String getItemName() { return itemName; }
    public double getItemPrice() { return itemPrice; }

    public static Object showOrder(Stage primaryStage, String firstName, String lastName) {
        throw new UnsupportedOperationException("Unimplemented method 'showOrder'");
    }

    public static Object showOrderPopup(Stage primaryStage, String firstName, String lastName) {
        throw new UnsupportedOperationException("Unimplemented method 'showOrderPopup'");
    }
}

