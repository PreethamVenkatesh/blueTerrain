package BlueTerrain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The Management class handles management-related functionalities, such as viewing table information,
 * managing table allocation, and booking status.
 * It provides methods to display popup windows for each functionality.
 * 
 * @author Manasa Ramesh
 */
public class Management {
    private static String BOOKINGID_QUERY = "SELECT bookingId, tableType, tableAllocation FROM bookings";
    private static String BOOKINGSTATUS_QUERY = "SELECT bookingId FROM bookings";
    
    /**
     * Displays the management popup window.
     * 
     * @param primaryStage The primary stage of the application.
     */
    public static void showManagementPopup(Stage primaryStage) {

        VBox root = Functions.commonHeader("/BlueTerrain/Images/BT_Common.jpeg");

        Button tableInfoButton = Functions.createButtonMenu("Table \n Information", Color.LAVENDER);
        Button tableAllocationButton = Functions.createButtonMenu("Table Allocation", Color.LAVENDER);
        Button bookingStatusButton = Functions.createButtonMenu("Booking Status", Color.LAVENDER);

        tableInfoButton.setOnAction(e -> showPopup("Table Information", 
                                                     new String[]{"2 Seater", 
                                                                  "4 Seater", 
                                                                  "8 Seater", 
                                                                  "10 Seater"}));

        tableAllocationButton.setOnAction(e -> showPopup1("Table Allocation", 
                                                          new String[]{"Sl. No.", "Booking ID", "Guests", "Table Allocation"}));

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

    /**
     * Displays a popup window for table information.
     * 
     * @param title        The title of the popup window.
     * @param columnTitles The titles of the columns in the table.
     */
    @SuppressWarnings({ "deprecation", "unchecked" })
    private static void showPopup(String title, String[] columnTitles) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(title);

        TableView<List<String>> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<List<String>> itemList = FXCollections.observableArrayList();

        List<String> rowData1 = new ArrayList<>(Arrays.asList("\n Table 1", "\n Table 5", "\n Table 9", "\n Table 11"));
        List<String> rowData2 = new ArrayList<>(Arrays.asList("\n Table 2", "\n Table 6", "\n Table 10", "\n"));
        List<String> rowData3 = new ArrayList<>(Arrays.asList("\n Table 3", "\n Table 7", "\n ", "\n ")); 
        List<String> rowData4 = new ArrayList<>(Arrays.asList("\n Table 4", "\n Table 8", "\n ", "\n ")); 
           
        itemList.addAll(rowData1, rowData2, rowData3, rowData4);//adding all the row details to the tabelview created

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
     * Displays a popup window for table allocation.
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
                PreparedStatement preparedStatement = connection.prepareStatement(BOOKINGID_QUERY);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            int slNo = 1;
            while (resultSet.next()) {
                List<String> rowData = new ArrayList<>();
                rowData.add(String.valueOf(slNo++)); // Adding serial number
                rowData.add(resultSet.getString("bookingId")); // Fetching booking ID
                rowData.add(resultSet.getString("tableType"));
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

            if (i == 3) { 
                column.setCellFactory(tc -> new ComboBoxCell());
            } else {
                column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(index)));
            }

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

    // Custom cell factory for ComboBoxCell
    private static class ComboBoxCell extends TableCell<List<String>, String> {
        private final ComboBox<String> comboBox;
        private static final Set<Integer> disabledCells = new HashSet<>();
        
        public ComboBoxCell() {
            comboBox = new ComboBox<>();
            comboBox.getItems().addAll("Table 1", "Table 2", "Table 3", "Table 4", 
                                       "Table 5", "Table 6", "Table 7", "Table 8", "Table 9", "Table 10", "Table 11");
            comboBox.setOnAction(event -> {
                String selectedItem = comboBox.getValue();
                commitEdit(selectedItem);
            });
        }
    
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                if (disabledCells.contains(getIndex())) {
                    // If the cell is disabled, show a label with the selected table
                    Label label = new Label(item);
                    setGraphic(label);
                } else {
                    comboBox.setValue(item);
                    setGraphic(comboBox);
                }
            }
        }
    
        @Override
        public void startEdit() {
            super.startEdit();
            // Check if this cell is disabled before starting edit
            if (!disabledCells.contains(getIndex())) {
                comboBox.setDisable(false);
            }
        }
    
        @Override
        public void commitEdit(String newValue) {
            super.commitEdit(newValue);
            comboBox.setDisable(true); // Disable the ComboBox after selection
            disabledCells.add(getIndex()); // Add the index to the set of disabled cells
            
            // Update the database here with the new value
            List<String> rowData = getTableView().getItems().get(getIndex());
            String bookingId = rowData.get(1); 
            updateTableAllocation(bookingId, newValue);
        }
    
        private void updateTableAllocation(String bookingId, String newValue) {
            // Update the tableAllocation table in the database with the new table allocation
            try (Connection connection = Functions.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("UPDATE bookings SET tableAllocation = ? WHERE bookingId = ?")) {
                preparedStatement.setString(1, newValue);
                preparedStatement.setString(2, bookingId);
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.err.println("Error: Failed to update table allocation - " + ex.getMessage());
            }
        }
    }
    
    /**
     * Displays a popup window for booking status.
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
     * Custom cell factory for ButtonCell.
     */
    private static class ButtonCell extends TableCell<List<String>, Void> {
        private final Button approveButton = new Button("Approve");
        private boolean clicked = false;

        private ButtonCell() {
            approveButton.setOnAction(event -> {
                if (!clicked) {
                    List<String> rowData = getTableView().getItems().get(getIndex());
                    System.out.println("Approve clicked for booking ID: " + rowData.get(1));
                    clicked = true;
                    setStyle("-fx-background-color: green;");
                    setText("Approved");
                    setTextFill(Color.WHITE);
                    setAlignment(Pos.CENTER);
                    setGraphic(null); // Remove the button after clicking
                    updateBookingStatus(rowData.get(1), true); // Update booking status to true
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
                    setGraphic(approveButton);
                } else {
                    // If a button is clicked, set the text to "Approved" in white
                    setText("Approved");
                    setStyle("-fx-text-fill: white;");
                    setAlignment(Pos.CENTER);
                }
            }
        }

        // Method to update booking status in the database
        private void updateBookingStatus(String bookingId, boolean approved) {
            try (Connection connection = Functions.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("UPDATE bookings SET isApproved = ? WHERE bookingId = ?")) {
                preparedStatement.setBoolean(1, approved);
                preparedStatement.setString(2, bookingId);
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.err.println("Error: Failed to update booking status - " + ex.getMessage());
            }
        }
    }
}
