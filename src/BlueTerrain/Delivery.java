package BlueTerrain;

public class Delivery {
    private int orderId;
    private String orderType;

    public Delivery(int orderId, String orderType) {
        this.orderId = orderId;
        this.orderType = orderType;
    }

    public int getOrderId() { return orderId; }
    public String getOrderType() { return orderType; }

}

