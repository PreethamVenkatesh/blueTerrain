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

public class ActiveCustomer extends Report {

    private final List<Customer> customers;

    public ActiveCustomer(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    @Override
    public void generateReport() {
        // Implementation for generating a report on the most active customer
        // Customer activeCustomer = bookings.getMostActiveCustomer();
        System.out.println("Most Active Customer: James Bond");
    }

    @Override
    public void showReport(Stage primaryStage) {
        TableView<Customer> table = new TableView<>();
        ObservableList<Customer> data = FXCollections.observableArrayList(customers);

        // Configure columns
        TableColumn<Customer, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setMinWidth(200);

        TableColumn<Customer, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setMinWidth(200);
        
        TableColumn<Customer, String> phoneNumberColumn = new TableColumn<>("Phone Number");
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        phoneNumberColumn.setMinWidth(200);

        TableColumn<Customer, String> favoriteDishColumn = new TableColumn<>("Favorite Dish");
        favoriteDishColumn.setCellValueFactory(new PropertyValueFactory<>("favoriteDish"));
        favoriteDishColumn.setMinWidth(200);

        table.getColumns().addAll(nameColumn, emailColumn, favoriteDishColumn);
        table.setItems(data);

        // Create a title label
        Label titleLabel = new Label("Active Customers Report");
        titleLabel.setFont(new Font("Arial", 20));
        titleLabel.setStyle("-fx-font-weight: bold; -fx-padding: 10;");

        // Style the table
        table.setStyle("-fx-font-size: 14px; -fx-border-color: silver; -fx-border-width: 1; -fx-padding: 10;");

        // Layout
        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20;");
        vbox.getChildren().addAll(titleLabel, table);

        Scene scene = new Scene(vbox, 600, 400);
        primaryStage.setTitle("Report - Active Customers");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
