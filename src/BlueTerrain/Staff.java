package BlueTerrain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Staff {

    private static String STAFF_QUERY = "SELECT CONCAT(first_name, ' ', last_name) AS result_column FROM staffs WHERE profile_type = ?";
    
    public static void showStaffPopup(Stage primaryStage) {

        VBox root = Functions.createRootVBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setBackground(Functions.backGroundImage("/BlueTerrain/Images/BT_Common.jpeg"));

        Button managerButton = Functions.createButtonMenu("MANAGERS", Color.LAVENDER);
        Button chefButton = Functions.createButtonMenu("CHEFS", Color.LAVENDER);
        Button waiterButton = Functions.createButtonMenu("WAITERS", Color.LAVENDER);
        Button driverButton = Functions.createButtonMenu("DELIVERY\nDRIVERS", Color.LAVENDER);

        managerButton.setOnAction(e -> showStaffPopup("Manager"));
        chefButton.setOnAction(e -> showStaffPopup("Chef"));
        waiterButton.setOnAction(e -> showStaffPopup("Waiter"));
        driverButton.setOnAction(e -> showStaffPopup("Delivery Driver"));

        VBox leftBox = Functions.createButtonVBoxMenu(managerButton, chefButton);
        VBox rightBox = Functions.createButtonVBoxMenu(waiterButton, driverButton);

        HBox buttonsBox = new HBox(10);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(leftBox, rightBox);

        Button closeButton = new Button("Close");
        closeButton.setAlignment(Pos.BOTTOM_RIGHT);
        closeButton.setOnAction(e -> {
            Restaurant restaurant = new Restaurant();
            restaurant.start(primaryStage, Menu.getFirstName(), Menu.getLastName(), Menu.getProfileType());
        });

        root.getChildren().addAll(Functions.welcomePane(), buttonsBox, closeButton);
        Functions.setupAndShowScene(primaryStage, root);
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
    private static void showStaffPopup(String profileType) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(profileType + " Profiles");
    
        TableView<Profile> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    
        TableColumn<Profile, Integer> slNoColumn = new TableColumn<>("Sl. No.");
        slNoColumn.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: CENTER;");
        slNoColumn.setCellValueFactory(new PropertyValueFactory<>("slNo"));
    
        TableColumn<Profile, String> staffNameColumn = new TableColumn<>("Staff Name");
        staffNameColumn.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        staffNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
    
        tableView.getColumns().addAll(slNoColumn, staffNameColumn);
    
        ObservableList<Profile> staffList = FXCollections.observableArrayList();
    
        try (Connection connection = Functions.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(STAFF_QUERY)) {
            preparedStatement.setString(1, profileType);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                int slNo = 1;
                while (resultSet.next()) {
                    String staffName = resultSet.getString("result_column");
                    staffList.add(new Profile(slNo++, staffName));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error: Failed to fetch staff profiles - " + ex.getMessage());
        }
    
        tableView.setItems(staffList);
    
        VBox popupRoot = new VBox(10);
        popupRoot.setAlignment(Pos.CENTER);
        popupRoot.setPadding(new Insets(20));
        popupRoot.getChildren().addAll(tableView);
    
        Scene popupScene = new Scene(popupRoot, 400, 400);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

}
