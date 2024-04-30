package BlueTerrain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Staff {

    private static String STAFF_QUERY = "SELECT CONCAT(first_name, ' ', last_name) AS result_column FROM staffs WHERE profile_type = ?";
    
    public static void showStaffPopup(Stage primaryStage) {
        VBox root = Functions.commonHeader("/BlueTerrain/Images/BT_Common.jpeg");

        Button managerButton = Functions.createButtonMenu("MANAGERS", Color.LAVENDER);
        Button chefButton = Functions.createButtonMenu("CHEFS", Color.LAVENDER);
        Button waiterButton = Functions.createButtonMenu("WAITERS", Color.LAVENDER);
        Button driverButton = Functions.createButtonMenu("DELIVERY\nDRIVERS", Color.LAVENDER);

        managerButton.setOnAction(e -> showStaffPopup("Manager"));
        chefButton.setOnAction(e -> showStaffPopup("Chef"));
        waiterButton.setOnAction(e -> showStaffPopup("Waiter"));
        driverButton.setOnAction(e -> showStaffPopup("Delivery Driver"));

        HBox buttonsBox = new HBox(60);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(managerButton, chefButton, waiterButton, driverButton);

        Button closeButton = Functions.closeButton(primaryStage);

        VBox addStaffButton = new VBox();
        addStaffButton.setAlignment(Pos.BOTTOM_RIGHT);
        addStaffButton.setSpacing(10); 
        addStaffButton.setPadding(new Insets(20));

        Hyperlink addStaffLink = new Hyperlink("Add Staff");
        addStaffLink.setStyle("-fx-underline: true; -fx-text-fill: white; -fx-font-size: 20; -fx-padding: 5px 10px; -fx-border-radius: 5px;");
        addStaffLink.setOnAction(e -> {
            Stage addStaffPopup = new Stage();
            addStaffPopup.initModality(Modality.APPLICATION_MODAL);
            addStaffPopup.setTitle("Add Staff");

            HBox firstNameBox = Functions.staffField("First Name", "Enter your First Name");
            HBox lastNameBox = Functions.staffField("Last Name", "Enter your Last Name");

            HBox profileTypeBox = new HBox(10);
            profileTypeBox.setAlignment(Pos.CENTER);
            Label profileTypeLabel = new Label("Select Profile Type");
            profileTypeLabel.setFont(new Font(15));
            profileTypeLabel.setStyle("-fx-text-fill: darkBlue;");
            ChoiceBox<String> profileTypeChoiceBox = new 
                ChoiceBox<>(FXCollections.observableArrayList("Waiter", "Chef", "Delivery Driver", "Manager"));
            profileTypeChoiceBox.getSelectionModel().selectFirst(); 
            profileTypeChoiceBox.setStyle("-fx-text-fill: black;");
            profileTypeBox.getChildren().addAll(profileTypeLabel, profileTypeChoiceBox);

            Button addButton = new Button("Add");
            addButton.setOnAction(event -> {
                String firstName = ((TextField) firstNameBox.getChildren().get(1)).getText();
                String lastName = ((TextField) lastNameBox.getChildren().get(1)).getText();
                String profileType = profileTypeChoiceBox.getValue();
                String tableName = "staffs";

                try {
                    Connection connection = Functions.getConnection();
                    String query = "INSERT INTO " + tableName + " (first_name, last_name, profile_type) VALUES (?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, firstName);
                    preparedStatement.setString(2, lastName);
                    preparedStatement.setString(3, profileType);
                    preparedStatement.executeUpdate();
    
                    System.out.println("User signed up successfully!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                addStaffPopup.close();
            });

            Button cancelButton = new Button("Cancel");
            cancelButton.setOnAction(event -> addStaffPopup.close());

            HBox buttonBox = new HBox(10);
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.getChildren().addAll(addButton, cancelButton);

            VBox addStaffRoot = new VBox(10);
            addStaffRoot.setAlignment(Pos.CENTER);
            addStaffRoot.setPadding(new Insets(20));
            addStaffRoot.getChildren().addAll(firstNameBox, lastNameBox, profileTypeBox, buttonBox);

            Scene addStaffScene = new Scene(addStaffRoot, 500, 400);
            addStaffPopup.setScene(addStaffScene);
            addStaffPopup.showAndWait();
        });

        Hyperlink deleteStaffLink = new Hyperlink("Delete Staff");
        deleteStaffLink.setStyle("-fx-underline: true; -fx-text-fill: white; -fx-font-size: 20; -fx-padding: 5px 10px; -fx-border-radius: 5px;");
        deleteStaffLink.setOnAction(e -> {
            Stage deleteStaffPopup = new Stage();
            deleteStaffPopup.initModality(Modality.APPLICATION_MODAL);
            deleteStaffPopup.setTitle(" Staff");
        });

        addStaffButton.getChildren().addAll(addStaffLink);

        root.getChildren().addAll(Functions.welcomePane(), buttonsBox, addStaffButton, closeButton);
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

        TableColumn<Profile, Void> deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: CENTER;");
        Callback<TableColumn<Profile, Void>, TableCell<Profile, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Profile, Void> call(final TableColumn<Profile, Void> param) {
                final TableCell<Profile, Void> cell = new TableCell<>() {
                    private final Button deleteButton = new Button("Delete");
                    {
                        deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white");
                        deleteButton.setOnAction((ActionEvent event) -> {
                            Profile profile = getTableView().getItems().get(getIndex());
                            deleteStaffProfile(profile);
                            refreshTableView(profileType, tableView);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(deleteButton);
                        }
                    }
                };
                return cell;
            }
        };

        deleteColumn.setCellFactory(cellFactory);

        tableView.getColumns().addAll(slNoColumn, staffNameColumn, deleteColumn);

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

    private static void deleteStaffProfile(Profile profile) {
        try (Connection connection = Functions.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM staffs WHERE CONCAT(first_name, ' ', last_name) = ?")) {
            preparedStatement.setString(1, profile.getFullName());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error: Failed to delete staff profile - " + ex.getMessage());
        }
    }

    private static void refreshTableView(String profileType, TableView<Profile> tableView) {
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
    }


}
