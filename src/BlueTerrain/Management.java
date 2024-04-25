package BlueTerrain;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Management {
    public static void showManagementPopup(Stage primaryStage) {

        Stage managementPopup = Functions.createPopupWindow(primaryStage);

        VBox root = Functions.createRootVBox();
        root.setAlignment(Pos.TOP_CENTER);

        StackPane lightBluePadding = Functions.restuarantLabel();

        VBox leftBox = Functions.createButtonVBoxFields(Color.YELLOW, "Dine-In");
        VBox centreBox = Functions.createButtonVBoxFields(Color.YELLOW,"Take-Away");
        VBox rightBox = Functions.createButtonVBoxFields(Color.YELLOW,"Booking Status");

        HBox buttonsBox = new HBox(50); 
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(leftBox, centreBox, rightBox);

        Button closeButton = new Button("Close");
        closeButton.setAlignment(Pos.BOTTOM_RIGHT);
        closeButton.setOnAction(e -> {
        managementPopup.close();
        primaryStage.show();
        });

        root.getChildren().addAll(lightBluePadding, buttonsBox, closeButton);

        Scene scene = new Scene(root, 750, 550);
        managementPopup.setScene(scene);
        managementPopup.showAndWait();
    }
}
