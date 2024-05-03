package BlueTerrain;

import javafx.stage.Stage;

/**
 * The Order class represents an order placed by a customer.
 * It contains information such as order ID, item name, item price, and order status.
 * 
 * <p>This class provides methods to retrieve order details and display orders.</p>
 * 
 * @author Aravind Sivakumar, Clinton Ekhameye
 */
public class Order {
    private int orderId;
    private String itemName;
    private double itemPrice;
    private String orderStatus;

    /**
     * Constructs an Order object with the specified details.
     * 
     * @param orderId     The unique identifier for the order.
     * @param orderStatus The status of the order.
     * @param itemName    The name of the item in the order.
     * @param itemPrice   The price of the item in the order.
     */
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

     /**
     * Displays the order details for a specific customer.
     * 
     * @param primaryStage The primary stage of the application.
     * @param firstName    The first name of the customer.
     * @param lastName     The last name of the customer.
     * @return An object representing the order.
     */
    public static Object showOrder(Stage primaryStage, String firstName, String lastName) {
        throw new UnsupportedOperationException("Unimplemented method 'showOrder'");
    }

    /**
     * Displays a popup window with order details for a specific customer.
     * 
     * @param primaryStage The primary stage of the application.
     * @param firstName    The first name of the customer.
     * @param lastName     The last name of the customer.
     * @return An object representing the order.
     */
    public static Object showOrderPopup(Stage primaryStage, String firstName, String lastName) {
        throw new UnsupportedOperationException("Unimplemented method 'showOrderPopup'");
    }
}

