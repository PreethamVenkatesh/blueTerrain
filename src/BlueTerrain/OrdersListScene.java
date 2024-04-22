package BlueTerrain;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OrdersListScene extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        ListView<String> ordersListView = new ListView<>();
        ObservableList<String> orders = FXCollections.observableArrayList(
                "Order 1: Pizza, Soda - Delivered",
                "Order 2: Burger, Fries - Preparing",
                "Order 3: Sushi - Awaiting Pickup"
                // Add more orders here, fetched from an actual Orders collection
        );
        ordersListView.setItems(orders);

        root.getChildren().add(ordersListView);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Orders List");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
