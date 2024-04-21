package Bluetrain;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PopularItem extends Report {

    private final List<Item> items;

    public PopularItem(List<Item> items) {
        this.items = items;
    }

    @Override
    public void generateReport() {
        System.out.println("Most Popular Item: Seafood Pasta");
    }

    @Override
    public void showReport(Stage primaryStage) {
        TableView<Item> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(items));

        TableColumn<Item, Integer> itemIdColumn = new TableColumn<>("Item ID");
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Item, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Item, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Item, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Item, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Item, Integer> orderCountColumn = new TableColumn<>("Order Count");
        orderCountColumn.setCellValueFactory(new PropertyValueFactory<>("orderCount"));

        table.getColumns().addAll(itemIdColumn, nameColumn, descriptionColumn, priceColumn, categoryColumn, orderCountColumn);

        Label titleLabel = new Label("Popular Items Report");
        titleLabel.setFont(new Font("Arial", 20));

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(titleLabel, table);

        Scene scene = new Scene(vbox, 800, 450);
        primaryStage.setTitle("Report - Popular Items");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
