package BlueTerrain;

import javafx.stage.Stage;

public class Cook {

    private int orderId;
    private String itemName;
    private double itemPrice;
    private String orderType;

    public Cook(int orderId, String itemName, double itemPrice, String orderType) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.orderType = orderType;
    }

    public int getOrderId() { return orderId; }
    public String getItemName() { return itemName; }
    public double getItemPrice() { return itemPrice; }
    public String getOrderType() { return orderType; }

    public static Object showCook(Stage primaryStage, String firstName, String lastName) {
        throw new UnsupportedOperationException("Unimplemented method 'showOrder'");
    }

    public static Object showCookPopup(Stage primaryStage, String firstName, String lastName) {
        throw new UnsupportedOperationException("Unimplemented method 'showOrderPopup'");
    }
    
}
