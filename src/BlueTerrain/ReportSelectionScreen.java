package BlueTerrain;

import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
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

        BorderPane root = new BorderPane();
        root.setTop(Functions.welcomePane());

        Button activeCustomersButton = Functions.createStyledButton("Active Customers");
        Button peakTimeButton = Functions.createStyledButton("Peak Time");
        Button startWorkButton = Functions.createStyledButton("Start Work");
        Button popularItemsButton = Functions.createStyledButton("Popular Items");

        activeCustomersButton.setOnAction(e -> {
            showActiveCustomersReport(primaryStage);
            menuPopup.close();
        });
        peakTimeButton.setOnAction(e -> {
            showPeakTimeReport(primaryStage);
            menuPopup.close();
        });
        startWorkButton.setOnAction(e -> {
            showStartWorkReport(primaryStage);
            menuPopup.close();
        });
        popularItemsButton.setOnAction(e -> {
            showPopularItemsReport(primaryStage);
            menuPopup.close();
        });

        root.setLeft(activeCustomersButton);
        root.setRight(peakTimeButton);
        root.setBottom(startWorkButton);
        root.setCenter(popularItemsButton);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> menuPopup.close());
        root.setTop(closeButton);

        Scene scene = new Scene(root, 800, 600);
        menuPopup.setScene(scene);
        menuPopup.showAndWait();
    }

    private void showActiveCustomersReport(Stage primaryStage) {
        List<Customer> customers = SampleReportData.getActiveCustomers();
        ActiveCustomer report = new ActiveCustomer(customers);
        report.generateReport();
        report.showReport(primaryStage);
    }

    private void showPeakTimeReport(Stage primaryStage) {
        List<Order> orders = SampleReportData.getSampleOrders();
        PeakTime report = new PeakTime(orders);
        report.generateReport();
        report.showReport(primaryStage);
    }

    private void showStartWorkReport(Stage primaryStage) {
        List<StaffWorkRecord> records = SampleReportData.getStaffWorkRecords();
        StaffWork report = new StaffWork(records);
        report.generateReport();
        report.showReport(primaryStage);
    }

    private void showPopularItemsReport(Stage primaryStage) {
        List<Item> items = SampleReportData.getPopularItems();
        PopularItem report = new PopularItem(items);
        report.generateReport();
        report.showReport(primaryStage);
    }

}
