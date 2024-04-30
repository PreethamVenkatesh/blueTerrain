package BlueTerrain;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
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
        TableColumn<Customer, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        idColumn.setMinWidth(100);

        TableColumn<Customer, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameColumn.setMinWidth(200);

        TableColumn<Customer, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameColumn.setMinWidth(200);

        TableColumn<Customer, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setMinWidth(200);

        TableColumn<Customer, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressColumn.setMinWidth(200);

        TableColumn<Customer, String> noOfVisitsColumn = new TableColumn<>("No of Visits");
        noOfVisitsColumn.setCellValueFactory(new PropertyValueFactory<>("noOfVisits"));
        noOfVisitsColumn.setMinWidth(200);

        table.getColumns().addAll(idColumn, firstNameColumn, lastNameColumn, emailColumn, addressColumn, noOfVisitsColumn);
        table.setItems(data);

        // Create a title label
        Label titleLabel = new Label("Active Customers Report");
        titleLabel.setFont(new Font("Arial", 20));
        titleLabel.setStyle("-fx-font-weight: bold; -fx-padding: 10; -fx-text-fill: #ffffff;");

        // Style the table
        table.setStyle("-fx-font-size: 14px; -fx-border-color: silver; -fx-border-width: 1; -fx-padding: 10;");

        VBox root = Functions.commonHeader("/BlueTerrain/Images/BT_Common.jpeg");

        // Layout
        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20;");
        vbox.getChildren().addAll(titleLabel, table);
        Functions.setMarginForNode(root, vbox, new Insets(20, 20, 20, 0));

        Button closeButton = Functions.closeButton(primaryStage);

        root.getChildren().addAll(Functions.welcomePane(), vbox, closeButton);
        Functions.setupAndShowScene(primaryStage, root);
    }

}
