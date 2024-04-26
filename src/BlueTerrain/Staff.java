package BlueTerrain;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Staff {

    private static String STAFF_QUERY = "SELECT CONCAT(first_name, ' ', last_name) AS result_column FROM staffs WHERE profile_type = ?";
    
    public static void showStaffPopup(Stage primaryStage) {
        Stage staffPopup = Functions.createPopupWindow(primaryStage);

        VBox root = Functions.createRootVBox();
        root.setAlignment(Pos.TOP_CENTER);

        Button managerButton = Functions.createButtonMenu("MANAGERS", Color.LAVENDER);
        Button chefButton = Functions.createButtonMenu("CHEFS", Color.LAVENDER);
        Button waiterButton = Functions.createButtonMenu("WAITERS", Color.LAVENDER);
        Button driverButton = Functions.createButtonMenu("DELIVERY\nDRIVERS", Color.LAVENDER);

        Functions.setTooltip(managerButton, Functions.fetchDBdata("Manager", STAFF_QUERY));
        Functions.setTooltip(chefButton, Functions.fetchDBdata("Chef", STAFF_QUERY));
        Functions.setTooltip(waiterButton, Functions.fetchDBdata("Waiter", STAFF_QUERY));
        Functions.setTooltip(driverButton, Functions.fetchDBdata("Delivery Driver", STAFF_QUERY));

        VBox leftBox = Functions.createButtonVBoxMenu(managerButton, chefButton);
        VBox rightBox = Functions.createButtonVBoxMenu(waiterButton, driverButton);

        HBox buttonsBox = new HBox(10);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(leftBox, rightBox);

        Button closeButton = new Button("Close");
        closeButton.setAlignment(Pos.BOTTOM_RIGHT);
        closeButton.setOnAction(e -> {
            staffPopup.close();
            primaryStage.show();
        });

        root.getChildren().addAll(buttonsBox, closeButton);
        Scene scene = new Scene(root, 750, 550);
        staffPopup.setScene(scene);
        staffPopup.showAndWait();
    }

}
