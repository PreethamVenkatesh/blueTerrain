package BlueTerrain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Management {
    private static String BOOKINGID_QUERY = "SELECT bookingId, tableName FROM tableAllocation";
    private static String BOOKINGSTATUS_QUERY = "SELECT bookingId FROM tableAllocation";
    
    public static void showManagementPopup(Stage primaryStage) {

        VBox root = Functions.commonHeader("/BlueTerrain/Images/BT_Common.jpeg");

        Button tableInfoButton = Functions.createButtonMenu("Table \n Information", Color.LAVENDER);
        Button tableAllocationButton = Functions.createButtonMenu("Table Allocation", Color.LAVENDER);
        Button bookingStatusButton = Functions.createButtonMenu("Booking Status", Color.LAVENDER);

        tableInfoButton.setOnAction(e -> showPopup("Table Information", 
                                                     new String[]{" Type-1 \n 2-Seater", 
                                                                  " Type-2 \n 4-Seater", 
                                                                  " Type-3 \n 8-Seater", 
                                                                  " Type-4 \n 10-Seater"}));

        tableAllocationButton.setOnAction(e -> showPopup1("Table Allocation", 
                                                          new String[]{"Sl. No.", "Booking ID", "Table Allocation"}));

        bookingStatusButton.setOnAction(e -> showPopup2("Booking Status", 
                                                       new String[]{"Sl. No.","Booking ID", "Approve", "Cancel"}));

        HBox buttonsBox = new HBox(40);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(tableInfoButton, tableAllocationButton, bookingStatusButton);

        Functions.setMarginForNode(root, buttonsBox, new Insets(20, 20, 20, 0));

        Button closeButton = Functions.closeButton(primaryStage);

        root.getChildren().addAll(Functions.welcomePane(), buttonsBox, closeButton);
        Functions.setupAndShowScene(primaryStage, root);
    }

    @SuppressWarnings({ "deprecation", "unchecked" })
    private static void showPopup(String title, String[] columnTitles) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(title);

        TableView<List<String>> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<List<String>> itemList = FXCollections.observableArrayList();

        // Sample hardcoded data for the table columns
        List<String> rowData1 = new ArrayList<>(Arrays.asList("\n Table 1", "\n Table 5", "\n Table 9", "\n Table 11"));
        List<String> rowData2 = new ArrayList<>(Arrays.asList("\n Table 2", "\n Table 6", "\n Table 10", "\n"));
        List<String> rowData3 = new ArrayList<>(Arrays.asList("\n Table 3", "\n Table 7", "\n ", "\n ")); 
        List<String> rowData4 = new ArrayList<>(Arrays.asList("\n Table 4", "\n Table 8", "\n ", "\n ")); 
           
           itemList.addAll(rowData1, rowData2, rowData3, rowData4);

        for (int i = 0; i < columnTitles.length; i++) {
            final int index = i;
            TableColumn<List<String>, String> column = new TableColumn<>(columnTitles[i]);
            column.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: CENTER;");
            column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(index)));
            tableView.getColumns().add(column);
        }

        tableView.setItems(itemList);

        VBox popupRoot = new VBox(10);
        popupRoot.setAlignment(Pos.CENTER);
        popupRoot.setPadding(new Insets(20));
        popupRoot.getChildren().addAll(tableView);

        Scene popupScene = new Scene(popupRoot, 800, 400);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    @SuppressWarnings({ "deprecation" })
    private static void showPopup1(String title, String[] columnTitles) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(title);

        TableView<List<String>> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<List<String>> itemList = FXCollections.observableArrayList();

        try (Connection connection = Functions.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(BOOKINGID_QUERY);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            int slNo = 1;
            while (resultSet.next()) {
                List<String> rowData = new ArrayList<>();
                rowData.add(String.valueOf(slNo++)); // Adding serial number
                rowData.add(resultSet.getString("bookingId")); // Fetching booking ID
                rowData.add(resultSet.getString("tableName")); // Placeholder for table allocation data
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

        VBox popupRoot = new VBox(10);
        popupRoot.setAlignment(Pos.CENTER);
        popupRoot.setPadding(new Insets(20));
        popupRoot.getChildren().addAll(tableView);

        Scene popupScene = new Scene(popupRoot, 800, 400);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    @SuppressWarnings({ "deprecation" })
private static void showPopup2(String title, String[] columnTitles) {
    Stage popupStage = new Stage();
    popupStage.initModality(Modality.APPLICATION_MODAL);
    popupStage.setTitle(title);

    TableView<List<String>> tableView = new TableView<>();
    tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    ObservableList<List<String>> itemList = FXCollections.observableArrayList();

    try (Connection connection = Functions.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(BOOKINGSTATUS_QUERY);
        ResultSet resultSet = preparedStatement.executeQuery()) {
        int slNo = 1;
        while (resultSet.next()) {
            List<String> rowData = new ArrayList<>();
            rowData.add(String.valueOf(slNo++));
            String bookingId = resultSet.getString("bookingId");
            rowData.add(bookingId); // Adding booking ID
            itemList.add(rowData);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        System.err.println("Error: Failed to fetch data - " + ex.getMessage());
    }

    TableColumn<List<String>, String> slNoColumn = new TableColumn<>("SL. No.");
    slNoColumn.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: CENTER;");
    slNoColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
    tableView.getColumns().add(slNoColumn);

    TableColumn<List<String>, String> bookingIdColumn = new TableColumn<>("BookingID");
    bookingIdColumn.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: CENTER;");
    bookingIdColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
    tableView.getColumns().add(bookingIdColumn);

    TableColumn<List<String>, Void> approveColumn = new TableColumn<>("Booking Approvals");
    approveColumn.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: CENTER;");
    approveColumn.setCellFactory(tc -> new ButtonCell());
    tableView.getColumns().add(approveColumn);

    tableView.setItems(itemList);

    VBox popupRoot = new VBox(10);
    popupRoot.setAlignment(Pos.CENTER);
    popupRoot.setPadding(new Insets(20));
    popupRoot.getChildren().addAll(tableView);

    Scene popupScene = new Scene(popupRoot, 800, 400);
    popupStage.setScene(popupScene);
    popupStage.showAndWait();
}

    private static class ButtonCell extends TableCell<List<String>, Void> {
        private final Button approveButton = new Button("Approve");
        private final Button rejectButton = new Button("Reject");
        private boolean clicked = false;

        private ButtonCell() {
            approveButton.setOnAction(event -> {
                if (!clicked) {
                    List<String> rowData = getTableView().getItems().get(getIndex());
                    System.out.println("Approve clicked for booking ID: " + rowData.get(1));
                    clicked = true;
                    setStyle("-fx-background-color: green;"); 
                    setGraphic(null);
                    setText("Approved");
                    setTextFill(Color.WHITE);
                    setAlignment(Pos.CENTER);
                }
            });
    
            rejectButton.setOnAction(event -> {
                if (!clicked) {
                    List<String> rowData = getTableView().getItems().get(getIndex());
                    System.out.println("Reject clicked for booking ID: " + rowData.get(1));
                    clicked = true;
                    setStyle("-fx-background-color: red;");
                    setGraphic(null);
                    setText("Cancelled");
                    setTextFill(Color.WHITE);
                    setAlignment(Pos.CENTER);           
                }
            });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                if (!clicked) { 
                    HBox buttons = new HBox(10);
                    buttons.setAlignment(Pos.CENTER);
                    buttons.getChildren().addAll(approveButton, rejectButton);
                    setGraphic(buttons);
                } else {
                    // If a button is clicked, set the text based on the background color
                    String status = getStyle().contains("green") ? "Approved" : "Cancelled";
                    setText(status);
                    setStyle("-fx-text-fill: white;");
                }
            }
        }
    }

}

