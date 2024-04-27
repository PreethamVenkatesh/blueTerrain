package BlueTerrain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BookingDetails {

    private static String BOOKING_QUERY = "SELECT b.*, CONCAT(c.first_name, ' ', c.last_name) AS customerName\r\n" + //
                "FROM bookings b\r\n" + //
                "JOIN customers c ON b.customerId = c.customer_id;";
    
    public static void showManagerPopup(Stage primaryStage) {

        VBox root = Functions.commonHeader("/BlueTerrain/Images/BT_Common.jpeg");

        VBox leftBox = Functions.createButtonVBox(Color.LAVENDER, "Booking\nDetails");

        root.getChildren().addAll(Functions.welcomePane(),leftBox);
        Functions.setupAndShowScene(primaryStage, root);

        Button bookTableButton = (Button) leftBox.getChildren().get(0); 
        bookTableButton.setOnAction(e -> showBookingPopup());
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
    private static void showBookingPopup() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("My Bookings");

        TableView<Map<String, Object>> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Map<String, Object>, Integer> slNoColumn = new TableColumn<>("Sl. No.");
        slNoColumn.setStyle("-fx-font-weight: bold; -fx-font-size: 12; -fx-alignment: CENTER;");
        slNoColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(tableView.getItems().indexOf(cellData.getValue()) + 1));

        TableColumn<Map<String, Object>, Integer> bookingIdColumn = new TableColumn<>("Booking ID");
        bookingIdColumn.setStyle("-fx-font-weight: bold; -fx-font-size: 12;");
        bookingIdColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>((Integer) cellData.getValue().get("bookingId")));

        TableColumn<Map<String, Object>, String> customerColumn = new TableColumn<>("Customer Name");
        customerColumn.setStyle("-fx-font-weight: bold; -fx-font-size: 12;");
        customerColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper((String) cellData.getValue().get("customerName")));

        TableColumn<Map<String, Object>, Integer> tableTypeColumn = new TableColumn<>("Table Type");
        tableTypeColumn.setStyle("-fx-font-weight: bold; -fx-font-size: 12; -fx-alignment: CENTER;");
        tableTypeColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>((Integer) cellData.getValue().get("tableType")));

        TableColumn<Map<String, Object>, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setStyle("-fx-font-weight: bold; -fx-font-size: 12;");
        dateColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper((String) cellData.getValue().get("date")));

        TableColumn<Map<String, Object>, String> timeColumn = new TableColumn<>("Time");
        timeColumn.setStyle("-fx-font-weight: bold; -fx-font-size: 12;");
        timeColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper((String) cellData.getValue().get("time")));

        TableColumn<Map<String, Object>, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setStyle("-fx-font-weight: bold; -fx-font-size: 12;");
        statusColumn.setCellValueFactory(cellData -> {
            Boolean status = (Boolean) cellData.getValue().get("isApproved");
            return new ReadOnlyStringWrapper(status ? "Booked" : "Pending");
        });

        tableView.getColumns().addAll(slNoColumn, bookingIdColumn, customerColumn, tableTypeColumn, dateColumn, timeColumn, statusColumn);

        ObservableList<Map<String, Object>> bookingList = FXCollections.observableArrayList();

        try (Connection connection = Functions.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(BOOKING_QUERY)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                int slNo = 1;
                while (resultSet.next()) {
                    Map<String, Object> bookingData = new HashMap<>();
                    bookingData.put("slNo", slNo++);
                    bookingData.put("bookingId", resultSet.getInt("bookingId"));
                    bookingData.put("customerName", resultSet.getString("customerName"));
                    bookingData.put("tableType", resultSet.getInt("tableType"));
                    bookingData.put("date", resultSet.getString("date"));
                    bookingData.put("time", resultSet.getString("time"));
                    bookingData.put("isApproved", resultSet.getBoolean("isApproved"));
                    bookingList.add(bookingData);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error: Failed to fetch booking data - " + ex.getMessage());
        }

        bookingList.sort(Comparator.comparing(m -> (String) m.get("date")));
        tableView.setItems(bookingList);

        VBox popupRoot = new VBox(10);
        popupRoot.setAlignment(Pos.CENTER);
        popupRoot.setPadding(new Insets(20));
        popupRoot.getChildren().addAll(tableView);

        Scene popupScene = new Scene(popupRoot, 600, 400);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }  

    





}

