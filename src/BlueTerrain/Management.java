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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Management {
    private static String BOOKINGID_QUERY = "SELECT bookingId FROM bookings";

    public static void showManagementPopup(Stage primaryStage) {

        VBox root = Functions.commonHeader("/BlueTerrain/Images/BT_Common.jpeg");

        Button tableInfoButton = Functions.createButtonMenu("Table \n Information", Color.LAVENDER);
        Button tableAllocationButton = Functions.createButtonMenu("Table Allocation", Color.LAVENDER);
        Button bookingStatusButton = Functions.createButtonMenu("Booking Status", Color.LAVENDER);

        tableInfoButton.setOnAction(e -> showPopup("Table Information", 
                                                     new String[]{" Type-1 \n 2-Seater", 
                                                                  " Type-2 \n 4-Seater", 
                                                                  " Type-3 \n 8-Seater", 
                                                                  " Type-4 \n 10-Seater"}, 
                                                     BOOKINGID_QUERY));

        tableAllocationButton.setOnAction(e -> showPopup("Table Allocation", 
                                                          new String[]{"Sl. No.", "Booking ID", "Table Allocation"}, 
                                                          BOOKINGID_QUERY));

        bookingStatusButton.setOnAction(e -> showPopup("Booking Status", 
                                                       new String[]{"Booking ID", "Approval", "Cancellation"}, 
                                                       BOOKINGID_QUERY));

        HBox buttonsBox = new HBox(40);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(tableInfoButton, tableAllocationButton, bookingStatusButton);

        Functions.setMarginForNode(root, buttonsBox, new Insets(20, 20, 20, 0));

        Button closeButton = Functions.closeButton(primaryStage);

        root.getChildren().addAll(Functions.welcomePane(), buttonsBox, closeButton);
        Functions.setupAndShowScene(primaryStage, root);
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
    private static void showPopup(String title, String[] columnTitles, String query) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(title);

        TableView<List<String>> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<List<String>> itemList = FXCollections.observableArrayList();

        // Table Data view for the available tables in the restaurant
        List<String> rowData1 = new ArrayList<>(Arrays.asList("\n Table 1", "\n Table 5", "\n Table 9", "\n Table 11"));
        List<String> rowData2 = new ArrayList<>(Arrays.asList("\n Table 2", "\n Table 6", "\n Table 10", "\n"));
        List<String> rowData3 = new ArrayList<>(Arrays.asList("\n Table 3", "\n Table 7", "\n ", "\n ")); 
        List<String> rowData4 = new ArrayList<>(Arrays.asList("\n Table 4", "\n Table 8", "\n ", "\n ")); 
           
           itemList.addAll(rowData1, rowData2, rowData3, rowData4);

        // try (Connection connection = Functions.getConnection();
        //         PreparedStatement preparedStatement = connection.prepareStatement(query);
        //         ResultSet resultSet = preparedStatement.executeQuery()) {
        //     int slNo = 1;
        //     while (resultSet.next()) {
        //         List<String> rowData = new ArrayList<>();
        //         for (int i = 1; i <= columnTitles.length; i++) {
        //             rowData.add(resultSet.getString(i));
        //         }
        //         itemList.add(rowData);
        //         slNo++;
        //     }
        // } catch (SQLException ex) {
        //     ex.printStackTrace();
        //     System.err.println("Error: Failed to fetch data - " + ex.getMessage());
        // }

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
}

