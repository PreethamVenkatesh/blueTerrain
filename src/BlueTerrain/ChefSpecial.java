package BlueTerrain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChefSpecial {
    public static void showChefSpecialPopup(Stage primaryStage) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);

        VBox root = Functions.commonHeader("/BlueTerrain/Images/BT_Common.jpeg");

        HBox loginTypeBox = new HBox(10);
        loginTypeBox.setAlignment(Pos.TOP_CENTER);
        HBox specialDishField = Functions.createLabeledField("\tChef Special", "Enter Chef Special");
        specialDishField.setAlignment(Pos.TOP_CENTER);

        HBox specialDishPrice = Functions.createLabeledField("\tItem Price (£)", "Enter Price of the Dish");
        specialDishPrice.setAlignment(Pos.TOP_CENTER);
        
        Button confirmButton = new Button("Confirm");

        HBox buttonBox = new HBox(confirmButton, Functions.closeButton(primaryStage));
        buttonBox.setAlignment(Pos.CENTER); // Align buttons at the center horizontally
        buttonBox.setSpacing(10);

        Functions.setMarginForNode(root, specialDishField, new Insets(5, 5, 5, 0));
        Functions.setMarginForNode(root, specialDishPrice, new Insets(5, 5, 5, 0));
        Functions.setMarginForNode(root, buttonBox, new Insets(10, 10, 10, 0));

        // Add action to the confirm button
        confirmButton.setOnAction(event -> {
            TextField specialDish = (TextField) specialDishField.getChildren().get(1);
            String specialDishText = specialDish.getText();

            TextField dishPrice = (TextField) specialDishPrice.getChildren().get(1);
            double specialItemPrice = Double.parseDouble(dishPrice.getText());

            insertIntoChefSpecial(specialDishText, specialItemPrice);

            // Display confirmation popup
            displayPopup("Chef Special Details confirmed");
        });

        // Add label, text field, and confirm button to the root VBox
        root.getChildren().addAll(Functions.welcomePane(), specialDishField, specialDishPrice, buttonBox);

        // Show the stage with the updated root
        Functions.setupAndShowScene(primaryStage, root);
    }

    private static void insertIntoChefSpecial(String itemName, double specialItemPrice) {
        try (Connection connection = Functions.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO Menu (ItemName, ItemValue, ItemType) VALUES (?, ?, 'Chef_Special');")) {
            // Set parameters for the prepared statement
            preparedStatement.setString(1, itemName);
            preparedStatement.setDouble(2, specialItemPrice);

            // Execute the update
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error: Failed to insert data into ChefSpecial - " + ex.getMessage());
        }
    }

    public static void displayPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Customizing the appearance of the alert dialog
        VBox vbox = new VBox(new Label(message));
        vbox.setAlignment(Pos.CENTER);
        vbox.setPrefWidth(400); // Set the preferred width
        vbox.setPrefHeight(200); // Set the preferred height
        alert.getDialogPane().setContent(vbox);
        
        // Show the popup
        alert.showAndWait();
    }
}
