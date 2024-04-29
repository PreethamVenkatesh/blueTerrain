package BlueTerrain;

public class Order {

    private int orderId;

    private int customerId;

    private String status;

    private int chefStatus;

    private long startTime;

    private long pickupTime;

    private String deliveryStatus;

    private long deliveryTime;

    public Order() {

    }

    public Order(int orderId, int customerId, String status, int chefStatus, long startTime, long pickupTime, String deliveryStatus, long deliveryTime) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.status = status;
        this.chefStatus = chefStatus;
        this.startTime = startTime;
        this.pickupTime = pickupTime;
        this.deliveryStatus = deliveryStatus;
        this.deliveryTime = deliveryTime;
    }

    public void handleOrderType() {
        System.out.println("-- Order handled ----");
    }

    public String updateStatus(String status) {
        this.status = status;
        return "Order status updated to: " + status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getChefStatus() {
        return chefStatus;
    }

    public void setChefStatus(int chefStatus) {
        this.chefStatus = chefStatus;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(long pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public long getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(long deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

}
