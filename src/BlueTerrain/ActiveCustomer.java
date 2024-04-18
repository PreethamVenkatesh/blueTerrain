package BlueTerrain;

public class ActiveCustomer extends Report {

    private Restaurant restaurant;

    public ActiveCustomer(Restaurant restaurant, Orders orders) {
        super(orders);
        this.restaurant = restaurant;
    }

    @Override
    public void generateReport() {
        // Implementation for generating a report on the most active customer
        // Customer activeCustomer = bookings.getMostActiveCustomer();
        System.out.println("Most Active Customer: James Bond");
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

}
