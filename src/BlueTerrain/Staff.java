package BlueTerrain;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Staff {
    
    public static void showStaffPopup(Stage primaryStage) {
        Stage menuPopup = new Stage();
        menuPopup.initOwner(primaryStage);
        menuPopup.initStyle(StageStyle.UTILITY);
        menuPopup.initModality(Modality.APPLICATION_MODAL);

        VBox root = Functions.createRootVBox();
        root.setAlignment(Pos.TOP_CENTER);

        StackPane lightBluePadding = Functions.restuarantLabel();

        Button managerButton = Functions.createButtonMenu("MANAGERS", Color.LAVENDER);
        Button chefButton = Functions.createButtonMenu("CHEFS", Color.LAVENDER);
        Button waiterButton = Functions.createButtonMenu("WAITERS", Color.LAVENDER);
        Button driverButton = Functions.createButtonMenu("DELIVERY\nDRIVERS", Color.LAVENDER);

        setTooltipStaff(managerButton, fetchNames("Manager"));
        setTooltipStaff(chefButton, fetchNames("Chef"));
        setTooltipStaff(waiterButton, fetchNames("Waiter"));
        setTooltipStaff(driverButton, fetchNames("Delivery Driver"));

        VBox leftBox = Functions.createButtonVBoxMenu(managerButton, chefButton);
        VBox rightBox = Functions.createButtonVBoxMenu(waiterButton, driverButton);

        HBox buttonsBox = new HBox(10);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(leftBox, rightBox);

        Button closeButton = new Button("Close");
        closeButton.setAlignment(Pos.BOTTOM_RIGHT);
        closeButton.setOnAction(e -> {
            menuPopup.close();
            primaryStage.show();
        });

        root.getChildren().addAll(lightBluePadding, buttonsBox, closeButton);

        Scene scene = new Scene(root, 750, 550);
        menuPopup.setScene(scene);
        menuPopup.showAndWait();
    }

    private static ArrayList<String> fetchNames(String profileType) {
        String query = "SELECT CONCAT(first_name, ' ', last_name) AS full_name " +
                          "FROM staffs " +
                          "WHERE profile_type = ?";
        
        ArrayList<String> tooltipTextList = new ArrayList<>();
        try (Connection connection = Functions.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, profileType);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String fullName = resultSet.getString("full_name");
                    tooltipTextList.add(fullName);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error: Failed to fetch names");
        }
        
        return tooltipTextList;
    }

    private static void setTooltipStaff(Button button, ArrayList<String> tooltipTextList) {
        if (!tooltipTextList.isEmpty()) {
            StringBuilder tooltipText = new StringBuilder();
            for (String name : tooltipTextList) {
                tooltipText.append(name).append("\n");
            }
            Tooltip tooltip = new Tooltip(tooltipText.toString());
            tooltip.setAutoHide(false);
            button.setTooltip(tooltip);
        }
    }

}
