package BlueTerrain;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReportData {

    private ReportData() {
    }

    public static List<Customer> getActiveCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT customer_id, first_name, last_name, email, address, number_of_visits FROM customers ORDER BY number_of_visits DESC";
        try (Connection connection = ReportUtility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int customerId = resultSet.getInt("customer_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                int noOfVisits = resultSet.getInt("number_of_visits");

                customers.add(new Customer(customerId, firstName, lastName, email, address, noOfVisits));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error: Failed to fetch customers - " + ex.getMessage());
        }
        return customers;
    }

    public static List<Order> getOrders() {
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

    public static List<MenuItem> getPopularItems() {
        List<MenuItem> items = new ArrayList<>();
        String query = "SELECT MenuId, ItemName, ItemValue, ItemType, purchaseCount FROM Menu ORDER BY purchaseCount DESC";
        try (Connection connection = ReportUtility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int menuId = resultSet.getInt("MenuId");
                String itemName = resultSet.getString("ItemName");
                double itemValue = resultSet.getDouble("ItemValue");
                String itemType = resultSet.getString("ItemType");
                int purchaseCount = resultSet.getInt("purchaseCount");

                items.add(new MenuItem(menuId, itemValue, itemName, itemType, purchaseCount));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error: Failed to fetch menu items - " + ex.getMessage());
        }
        return items;
    }

    public static List<StaffWorkRecord> getStaffWorkRecords() {
        List<StaffWorkRecord> staffWorkRecords = new ArrayList<>();
        String query = "SELECT staff_id, first_name, last_name, email, profile_type, isApproved, hours_worked FROM staffs ORDER BY hours_worked";
        try (Connection connection = ReportUtility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int staffId = resultSet.getInt("staff_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String profileType = resultSet.getString("profile_type");
                boolean isApproved = resultSet.getBoolean("isApproved");
                int hoursWorked = resultSet.getInt("hours_worked");

                staffWorkRecords.add(new StaffWorkRecord(staffId, firstName, lastName, email, profileType, isApproved, hoursWorked));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error: Failed to fetch staff work records - " + ex.getMessage());
        }
        return staffWorkRecords;
    }
}
