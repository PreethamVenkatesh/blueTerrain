package BlueTerrain;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Reports {
    public static void showReportsPopup(Stage primaryStage) {
        VBox root = Functions.commonHeader("/BlueTerrain/Images/BT_Common.jpeg");

        Button mostPopularItemButton = Functions.createButtonMenu("Most \nPopular \nItem", Color.LAVENDER);
        Button busiestPeriodButton = Functions.createButtonMenu("Busiest \nPeriod", Color.LAVENDER);
        Button mostActiveCustomerButton = Functions.createButtonMenu("Most \nActive \nCustomer", Color.LAVENDER);
        Button highestHoursWorkedButton = Functions.createButtonMenu("Highest \nHours \nWorked \nby Staff", Color.LAVENDER);

        mostPopularItemButton.setOnAction(e -> showPopup("Most Popular Item", 
                                                     new String[]{"Sl. No.", "Item Name"}));

        busiestPeriodButton.setOnAction(e -> showPopup("Busiest Period", 
                                                          new String[]{"Sl. No.", "Date", "Time"}));

        mostActiveCustomerButton.setOnAction(e -> showPopup("Most Active Customer", 
                                                       new String[]{"Sl. No.","Customer Name"}));

        highestHoursWorkedButton.setOnAction(e -> showPopup("Highest Hours Worked by Staff", 
                                                       new String[]{"Sl. No.","Name", "Total Worked"}));

        HBox buttonsBox = new HBox(40);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(mostPopularItemButton, busiestPeriodButton, mostActiveCustomerButton, highestHoursWorkedButton);

        Functions.setMarginForNode(root, buttonsBox, new Insets(20, 20, 20, 0));

        Button closeButton = Functions.closeButton(primaryStage);

        root.getChildren().addAll(Functions.welcomePane(), buttonsBox, closeButton);
        Functions.setupAndShowScene(primaryStage, root);

    }

    @SuppressWarnings({ "deprecation", "unchecked" })
    private static void showPopup(String title, String[] columnTitles) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(title);

        TableView<List<String>> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<List<String>> itemList = FXCollections.observableArrayList();

        for (int i = 0; i < columnTitles.length; i++) {
            final int index = i;
            TableColumn<List<String>, String> column = new TableColumn<>(columnTitles[i]);
            column.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-alignment: CENTER;");
            column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(index)));
            tableView.getColumns().add(column);
        }

        tableView.setItems(itemList);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> popupStage.close());

        VBox popupRoot = new VBox(10);
        popupRoot.setAlignment(Pos.CENTER);
        popupRoot.setPadding(new Insets(20));
        popupRoot.getChildren().addAll(tableView, closeButton);

        Scene popupScene = new Scene(popupRoot, 800, 400);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
        
    }
}
