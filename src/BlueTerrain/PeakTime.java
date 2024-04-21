package Bluetrain;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PeakTime extends Report {

    private final List<Order> orders;

    public PeakTime(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public void generateReport() {
        // This method could be used to analyze the data to determine the peak time
        System.out.println("Busiest Period: 11AM to 3PM");
    }

    @Override
    public void showReport(Stage primaryStage) {
        TableView<Order> table = new TableView<>();
        ObservableList<Order> data = FXCollections.observableArrayList(orders);

        // Configure columns for orderId, startTime, and status
        TableColumn<Order, Integer> orderIdColumn = new TableColumn<>("Order ID");
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        orderIdColumn.setMinWidth(100);

        TableColumn<Order, Long> startTimeColumn = new TableColumn<>("Start Time");
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        startTimeColumn.setMinWidth(200);

        TableColumn<Order, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setMinWidth(150);

        table.getColumns().addAll(orderIdColumn, startTimeColumn, statusColumn);
        table.setItems(data);

        // Create a title label for the report
        Label titleLabel = new Label("Peak Times Report");
        titleLabel.setFont(new Font("Arial", 20));
        titleLabel.setStyle("-fx-font-weight: bold; -fx-padding: 10;");

        // Style the table and layout
        table.setStyle("-fx-font-size: 14px; -fx-border-color: silver; -fx-border-width: 1; -fx-padding: 10;");

        VBox vbox = new VBox(10);  // VBox with spacing of 10
        vbox.setStyle("-fx-padding: 20;");  // Padding around VBox
        vbox.getChildren().addAll(titleLabel, table);

        Scene scene = new Scene(vbox, 600, 400);
        primaryStage.setTitle("Report - Peak Times");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
