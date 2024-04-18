package BlueTerrain;

public class StaffWork extends Report {

    private Restaurant restaurant;

    public StaffWork(Restaurant restaurant, Orders orders) {
        super(orders);
        this.restaurant = restaurant;
    }

    @Override
    public void generateReport() {
        // Implementation for generating a report on the highest number of hours worked by staff
        // Staff hardestWorker = restaurant.getStaffWithMostHours();
        System.out.println("Staff with Most Hours: Lola Ayantoye");
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
