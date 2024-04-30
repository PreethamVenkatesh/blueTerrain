package BlueTerrain;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PopularItem extends Report {

    private final List<MenuItem> items;

    public PopularItem(List<MenuItem> items) {
        this.items = items;
    }

    @Override
    public void generateReport() {
        System.out.println("Most Popular Item: Seafood Pasta");
    }

    @Override
    public void showReport(Stage primaryStage) {
        TableView<MenuItem> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(items));
// SELECT MenuId, ItemName, ItemValue, ItemType, purchaseCount
        TableColumn<MenuItem, Integer> itemIdColumn = new TableColumn<>("Item ID");
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("MenuId"));

        TableColumn<MenuItem, String> nameColumn = new TableColumn<>("Item Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("ItemName"));

        TableColumn<MenuItem, String> typeColumn = new TableColumn<>("Menu Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("ItemType"));

        TableColumn<MenuItem, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("ItemValue"));

        TableColumn<MenuItem, Integer> orderCountColumn = new TableColumn<>("Order Count");
        orderCountColumn.setCellValueFactory(new PropertyValueFactory<>("purchaseCount"));

        table.getColumns().addAll(itemIdColumn, nameColumn, typeColumn, priceColumn, orderCountColumn);

        Label titleLabel = new Label("Popular Items Report");
        titleLabel.setFont(new Font("Arial", 20));
        titleLabel.setStyle("-fx-font-weight: bold; -fx-padding: 10; -fx-text-fill: #ffffff;");

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
