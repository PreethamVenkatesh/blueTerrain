package BlueTerrain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class SampleReportData {

    private SampleReportData() {
    }

    // Sample data for customers
    public static List<Customer> getActiveCustomers() {
        return Arrays.asList(
                new Customer(1, "Chinedu Okoro", "chinedu.okoro@example.com", "555-0100", "Cheese Pizza", "Extra cheese"),
                new Customer(2, "Amaka Njoku", "amaka.njoku@example.com", "555-0101", "Vegan Burger", "No onions"),
                new Customer(3, "Tolu Badejo", "tolu.badejo@example.com", "555-0102", "Tiramisu", "Less sweet"),
                new Customer(4, "Ifeanyi Chukwu", "ifeanyi.chukwu@example.com", "555-0103", "Grilled Chicken", "Extra spicy"),
                new Customer(5, "Yemi Adeola", "yemi.adeola@example.com", "555-0104", "Seafood Pasta", ""),
                new Customer(6, "Uche Mbakwe", "uche.mbakwe@example.com", "555-0105", "Margherita Pizza", "No extra salt"),
                new Customer(7, "Ngozi Eze", "ngozi.eze@example.com", "555-0106", "Beef Stew", "Low sodium"),
                new Customer(8, "Emeka Okafor", "emeka.okafor@example.com", "555-0107", "Sushi Deluxe", "Wasabi on the side"),
                new Customer(9, "Adaobi Mbanefo", "adaobi.mbanefo@example.com", "555-0108", "Vegetarian Pizza", "Gluten-free crust"),
                new Customer(10, "Kunle Adebayo", "kunle.adebayo@example.com", "555-0109", "Martini", "Shaken, not stirred")
        );
    }

    // Sample data for orders
    public static List<Order> getSampleOrders() {
        return Arrays.asList(
                new Order(1, 101, "Placed", 1, System.currentTimeMillis(), System.currentTimeMillis() + 1800000, "Pending", 0),
                new Order(2, 102, "Preparing", 2, System.currentTimeMillis() - 900000, System.currentTimeMillis() + 900000, "On the way", System.currentTimeMillis() + 1800000),
                new Order(3, 103, "Ready to Pickup", 3, System.currentTimeMillis() - 1800000, System.currentTimeMillis(), "Delivered", System.currentTimeMillis()),
                new Order(4, 104, "Placed", 1, System.currentTimeMillis(), System.currentTimeMillis() + 1800000, "Pending", 0),
                new Order(5, 105, "Completed", 4, System.currentTimeMillis() - 3600000, System.currentTimeMillis() - 1800000, "Delivered", System.currentTimeMillis() - 1800000),
                new Order(6, 106, "Preparing", 2, System.currentTimeMillis() - 1200000, System.currentTimeMillis() + 600000, "On the way", System.currentTimeMillis() + 1200000),
                new Order(7, 107, "Ready to Pickup", 3, System.currentTimeMillis() - 2400000, System.currentTimeMillis() - 600000, "Delivered", System.currentTimeMillis() - 600000),
                new Order(8, 108, "Placed", 1, System.currentTimeMillis(), System.currentTimeMillis() + 1800000, "Pending", 0),
                new Order(9, 109, "Completed", 4, System.currentTimeMillis() - 4500000, System.currentTimeMillis() - 2700000, "Delivered", System.currentTimeMillis() - 2700000),
                new Order(10, 110, "Preparing", 2, System.currentTimeMillis() - 300000, System.currentTimeMillis() + 1500000, "On the way", System.currentTimeMillis() + 1500000)
        );
    }

    // Sample data for items
    public static List<Item> getPopularItems() {
        return Arrays.asList(
                new Item(1, "Seafood Pasta", "Rich and creamy pasta with shrimp, scallops, and calamari.", 19.99, "Main Course", 120),
                new Item(2, "Vegan Lasagna", "Layers of fresh vegetables, tofu ricotta, and gluten-free pasta.", 15.50, "Main Course", 90),
                new Item(3, "Beef Wellington", "Tender beef wrapped in puff pastry with a mushroom duxelles.", 29.99, "Main Course", 40),
                new Item(4, "Chicken Salad", "Grilled chicken with mixed greens, nuts, and a vinaigrette.", 12.99, "Salad", 150),
                new Item(5, "Chocolate Lava Cake", "Molten chocolate cake with a gooey center, served with ice cream.", 9.99, "Dessert", 180),
                new Item(6, "Margherita Pizza", "Classic pizza with fresh mozzarella, tomatoes, and basil.", 14.99, "Main Course", 200)
        );
    }

    // Sample data for staff work records
    public static List<StaffWorkRecord> getStaffWorkRecords() {
        return Arrays.asList(
                new StaffWorkRecord(1, "Alice Johnson", LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(17, 0), "Efficient and fast."),
                new StaffWorkRecord(2, "Bob Lee", LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(18, 0), "Very helpful with customers."),
                new StaffWorkRecord(3, "Chris Green", LocalDate.now(), LocalTime.of(12, 0), LocalTime.of(20, 0), "Excellent leadership."),
                new StaffWorkRecord(4, "Patricia Bright", LocalDate.now(), LocalTime.of(8, 30), LocalTime.of(16, 30), "Great attention to detail."),
                new StaffWorkRecord(5, "Oscar Wilde", LocalDate.now(), LocalTime.of(14, 0), LocalTime.of(22, 0), "Creative with menu ideas."),
                new StaffWorkRecord(6, "Grace Lee", LocalDate.now(), LocalTime.of(13, 0), LocalTime.of(21, 0), "Positive attitude."),
                new StaffWorkRecord(7, "Samuel Adams", LocalDate.now(), LocalTime.of(11, 0), LocalTime.of(19, 0), "Strong work ethic."),
                new StaffWorkRecord(8, "Emma Watson", LocalDate.now(), LocalTime.of(15, 0), LocalTime.of(23, 0), "Exceptional customer service."),
                new StaffWorkRecord(9, "James Bond", LocalDate.now(), LocalTime.of(9, 30), LocalTime.of(17, 30), "Always on time."),
                new StaffWorkRecord(10, "Lola Ayantoye", LocalDate.now(), LocalTime.of(7, 0), LocalTime.of(15, 0), "Dedicated and reliable.")
        );
    }
}
