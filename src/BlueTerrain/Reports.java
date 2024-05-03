package BlueTerrain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The Reports class provides functionality to display various reports.
 * These reports include information such as the most popular item, busiest period, 
 * most active customer, and highest hours worked by staff.
 * 
 * <p>This class contains methods to show each report in a popup window.</p>
 * 
 * @author Manasa Ramesh
 */
public class Reports {

    //SQL query to retrieve the most popular item.
    private static String MOST_POPULAR_ITEM_QUERY = "SELECT itemName FROM orders GROUP BY itemName ORDER BY COUNT(*) DESC LIMIT 1";
    //SQL query to retrieve the busiest period.
    private static String BUSIEST_PERIOD = "SELECT date, time FROM bookings GROUP BY date, time ORDER BY COUNT(*) DESC LIMIT 1";
    //SQL query to retrieve the most active customer.
    private static String MOST_ACTIVE_CUSTOMER = "SELECT CONCAT(first_name,' ',last_name) AS Customer_Name FROM customers GROUP BY first_name, last_name ORDER BY COUNT(*) DESC LIMIT 1";
    //SQL query to retrieve the staff with the highest hours worked.
    private static String HIGHEST_WORKED_STAFF = "SELECT CONCAT(first_name, ' ', last_name) AS Staff_Name, totalHoursWorked  FROM staffs ORDER BY totalHoursWorked DESC LIMIT 1";

    /**
     * Displays a popup window with various reports.
     * 
     * @param primaryStage The primary stage of the application.
     */
    public static void showReportsPopup(Stage primaryStage) {
        VBox root = Functions.commonHeader("/BlueTerrain/Images/BT_Common.jpeg");
     /* buttons created with display colour and Text to display on the buttons to generate reports 
        for Most popular item, Busiest Period, Most Active Customer and Highest Hours worked by Staff
     */
        Button mostPopularItemButton = Functions.createButtonMenu("Most \nPopular \nItem", Color.LAVENDER);
        Button busiestPeriodButton = Functions.createButtonMenu("Busiest \nPeriod", Color.LAVENDER);
        Button mostActiveCustomerButton = Functions.createButtonMenu("Most \nActive \nCustomer", Color.LAVENDER);
        Button highestHoursWorkedButton = Functions.createButtonMenu("Highest \nHours \nWorked \nby Staff", Color.LAVENDER);

        mostPopularItemButton.setOnAction(e -> showPopup("Most Popular Item", 
                                                     new String[]{"Sl. No.", "Item Name"}));

        busiestPeriodButton.setOnAction(e -> showPopup1("Busiest Period", 
                                                          new String[]{"Sl. No.", "Date", "Time"}));

        mostActiveCustomerButton.setOnAction(e -> showPopup2("Most Active Customer", 
                                                       new String[]{"Sl. No.","Customer Name"}));

        highestHoursWorkedButton.setOnAction(e -> showPopup3("Highest Hours Worked by Staff", 
                                                       new String[]{"Sl. No.","StaffName", "Total Hours Worked"}));

        HBox buttonsBox = new HBox(40);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(mostPopularItemButton, busiestPeriodButton, mostActiveCustomerButton, highestHoursWorkedButton);

        Functions.setMarginForNode(root, buttonsBox, new Insets(20, 20, 20, 0));

        Button closeButton = Functions.closeButton(primaryStage);

        root.getChildren().addAll(Functions.welcomePane(), buttonsBox, closeButton);
        Functions.setupAndShowScene(primaryStage, root);

    }

    /**
     * Displays a popup window with the most popular item report.
     * 
     * @param title        The title of the popup window.
     * @param columnTitles The titles of the columns in the table.
     */
    @SuppressWarnings({ "deprecation"})
    private static void showPopup(String title, String[] columnTitles) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(title);

        TableView<List<String>> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<List<String>> itemList = FXCollections.observableArrayList();

        try (Connection connection = Functions.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(MOST_POPULAR_ITEM_QUERY);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            int slNo = 1;
            while (resultSet.next()) {
                List<String> rowData = new ArrayList<>();
                rowData.add(String.valueOf(slNo++)); // Adding serial number
                rowData.add(resultSet.getString("itemName")); 
                rowData.add("");
                itemList.add(rowData);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error: Failed to fetch data - " + ex.getMessage());
        }

        for (int i = 0; i < columnTitles.length; i++) {
            final int index = i;
            TableColumn<List<String>, String> column = new TableColumn<>(columnTitles[i]);
            column.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: CENTER;");
            column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(index)));
            tableView.getColumns().add(column);
        }

        tableView.setItems(itemList);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> popupStage.close());

        VBox popupRoot = new VBox(10);
        popupRoot.setAlignment(Pos.CENTER);
        popupRoot.setPadding(new Insets(20));
        popupRoot.getChildren().addAll(tableView, closeButton);

        Scene popupScene = new Scene(popupRoot, 800, 400);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
        
    }
    
    /**
     * Displays a popup window with the busiest period report.
     * 
     * @param title        The title of the popup window.
     * @param columnTitles The titles of the columns in the table.
     */
    @SuppressWarnings({ "deprecation" })
    private static void showPopup1(String title, String[] columnTitles) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(title);

        TableView<List<String>> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<List<String>> itemList = FXCollections.observableArrayList();

        try (Connection connection = Functions.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(BUSIEST_PERIOD);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            int slNo = 1;
            while (resultSet.next()) {
                List<String> rowData = new ArrayList<>();
                rowData.add(String.valueOf(slNo++)); // Adding serial number
                rowData.add(resultSet.getString("date")); 
                rowData.add(resultSet.getString("time")); 
                // Placeholder for table allocation data (initially empty)
                rowData.add("");
                itemList.add(rowData);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error: Failed to fetch data - " + ex.getMessage());
        }

        for (int i = 0; i < columnTitles.length; i++) {
            final int index = i;
            TableColumn<List<String>, String> column = new TableColumn<>(columnTitles[i]);
            column.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: CENTER;");
            column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(index)));
            tableView.getColumns().add(column);
        }

        tableView.setItems(itemList);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> popupStage.close());

        VBox popupRoot = new VBox(10);
        popupRoot.setAlignment(Pos.CENTER);
        popupRoot.setPadding(new Insets(20));
        popupRoot.getChildren().addAll(tableView, closeButton);

        Scene popupScene = new Scene(popupRoot, 800, 400);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
        
    }

    /**
     * Displays a popup window with the most active customer report.
     * 
     * @param title        The title of the popup window.
     * @param columnTitles The titles of the columns in the table.
     */
    @SuppressWarnings({ "deprecation" })
    private static void showPopup2(String title, String[] columnTitles) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(title);

        TableView<List<String>> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<List<String>> itemList = FXCollections.observableArrayList();

        try (Connection connection = Functions.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(MOST_ACTIVE_CUSTOMER);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            int slNo = 1;
            while (resultSet.next()) {
                List<String> rowData = new ArrayList<>();
                rowData.add(String.valueOf(slNo++)); // Adding serial number
                rowData.add(resultSet.getString("Customer_Name"));
                // Placeholder for table allocation data (initially empty)
                rowData.add("");
                itemList.add(rowData);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error: Failed to fetch data - " + ex.getMessage());
        }

        for (int i = 0; i < columnTitles.length; i++) {
            final int index = i;
            TableColumn<List<String>, String> column = new TableColumn<>(columnTitles[i]);
            column.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: CENTER;");
            column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(index)));
            tableView.getColumns().add(column);
        }

        tableView.setItems(itemList);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> popupStage.close());

        VBox popupRoot = new VBox(10);
        popupRoot.setAlignment(Pos.CENTER);
        popupRoot.setPadding(new Insets(20));
        popupRoot.getChildren().addAll(tableView, closeButton);

        Scene popupScene = new Scene(popupRoot, 800, 400);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
        
    }

    /**
     * Displays a popup window with the highest hours worked by staff report.
     * 
     * @param title        The title of the popup window.
     * @param columnTitles The titles of the columns in the table.
     */
    @SuppressWarnings({ "deprecation" })
    private static void showPopup3(String title, String[] columnTitles) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(title);

        TableView<List<String>> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<List<String>> itemList = FXCollections.observableArrayList();

        try (Connection connection = Functions.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(HIGHEST_WORKED_STAFF);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            int slNo = 1;
            while (resultSet.next()) {
                List<String> rowData = new ArrayList<>();
                rowData.add(String.valueOf(slNo++)); // Adding serial number
                rowData.add(resultSet.getString("Staff_Name"));
                rowData.add(resultSet.getString("totalHoursWorked"));
                // Placeholder for table allocation data (initially empty)
                rowData.add("");
                itemList.add(rowData);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error: Failed to fetch data - " + ex.getMessage());
        }

        for (int i = 0; i < columnTitles.length; i++) {
            final int index = i;
            TableColumn<List<String>, String> column = new TableColumn<>(columnTitles[i]);
            column.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: CENTER;");
            column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(index)));
            tableView.getColumns().add(column);
        }

        tableView.setItems(itemList);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> popupStage.close());

        VBox popupRoot = new VBox(10);
        popupRoot.setAlignment(Pos.CENTER);
        popupRoot.setPadding(new Insets(20));
        popupRoot.getChildren().addAll(tableView, closeButton);

        Scene popupScene = new Scene(popupRoot, 800, 400);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
        
    }
}
