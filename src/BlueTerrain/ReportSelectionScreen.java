package BlueTerrain;

import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ReportSelectionScreen extends Application {

    @Override
    public void start(Stage primaryStage) {
        Stage menuPopup = new Stage();
        menuPopup.initOwner(primaryStage);
        menuPopup.initStyle(StageStyle.UTILITY);
        menuPopup.initModality(Modality.APPLICATION_MODAL);

        VBox root = Functions.commonHeader("/BlueTerrain/Images/BT_Common.jpeg");
        //root.setTop(Functions.welcomePane());

        HBox buttonBox = new HBox(40);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setStyle("-fx-padding: 20;");

        Button activeCustomersButton = ReportUtility.createStyledButton("Active Customers");
        Button peakTimeButton = ReportUtility.createStyledButton("Peak Time");
        Button startWorkButton = ReportUtility.createStyledButton("Staff Work Record");
        Button popularItemsButton = ReportUtility.createStyledButton("Popular Items");

        // Set actions for buttons
        activeCustomersButton.setOnAction(e -> {
            showActiveCustomersReport(primaryStage);
        });
        peakTimeButton.setOnAction(e -> {
            showPeakTimeReport(primaryStage);
        });
        startWorkButton.setOnAction(e -> {
            showStartWorkReport(primaryStage);
        });
        popularItemsButton.setOnAction(e -> {
            showPopularItemsReport(primaryStage);
        });

        // Adding buttons to VBox
        buttonBox.getChildren().addAll(activeCustomersButton, peakTimeButton, startWorkButton, popularItemsButton);
        Functions.setMarginForNode(root, buttonBox, new Insets(20, 20, 20, 0));

        Button closeButton = Functions.closeButton(primaryStage);

        root.getChildren().addAll(Functions.welcomePane(), buttonBox, closeButton);
        Functions.setupAndShowScene(primaryStage, root);
    }

    private void showActiveCustomersReport(Stage primaryStage) {
        List<Customer> customers = ReportData.getActiveCustomers();
        ActiveCustomer report = new ActiveCustomer(customers);
        report.generateReport();
        report.showReport(primaryStage);
    }

    private void showPeakTimeReport(Stage primaryStage) {
        List<Order> orders = ReportData.getOrders();
        PeakTime report = new PeakTime(orders);
        report.generateReport();
        report.showReport(primaryStage);
    }

    private void showStartWorkReport(Stage primaryStage) {
        List<StaffWorkRecord> records = ReportData.getStaffWorkRecords();
        StaffWork report = new StaffWork(records);
        report.generateReport();
        report.showReport(primaryStage);
    }

    private void showPopularItemsReport(Stage primaryStage) {
        List<MenuItem> items = ReportData.getPopularItems();
        PopularItem report = new PopularItem(items);
        report.generateReport();
        report.showReport(primaryStage);
    }

}