package BlueTerrain;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OrderCreationScene extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        TextField menuItemsInput = new TextField();
        menuItemsInput.setPromptText("Enter menu items separated by commas");

        Button submitOrderButton = new Button("Submit Order");
        submitOrderButton.setOnAction(e -> {
            String[] menuItems = menuItemsInput.getText().split(",");
            Order newOrder = new Order();
            // Here you would add the order to your application's orders collection
            // And possibly switch back to the OrdersListScene to show the updated list
        });

        root.getChildren().addAll(menuItemsInput, submitOrderButton);

        Scene scene = new Scene(root, 400, 200);
        primaryStage.setTitle("Create New Order");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
