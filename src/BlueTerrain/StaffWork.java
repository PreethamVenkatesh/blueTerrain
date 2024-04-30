package BlueTerrain;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class StaffWork extends Report {

    private final List<StaffWorkRecord> records;

    public StaffWork(List<StaffWorkRecord> records) {
        this.records = records;
    }

    @Override
    public void generateReport() {
        System.out.println("Staff with Most Hours: Lola Ayantoye");
    }

    @Override
    public void showReport(Stage primaryStage) {
        TableView<StaffWorkRecord> table = new TableView<>();
        ObservableList<StaffWorkRecord> data = FXCollections.observableArrayList(records);

        // SELECT staff_id, first_name, last_name, email, profile_type, isApproved, hours_worked
        TableColumn<StaffWorkRecord, Integer> staffIdColumn = new TableColumn<>("Staff ID");
        staffIdColumn.setCellValueFactory(new PropertyValueFactory<>("staffId"));

        TableColumn<StaffWorkRecord, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<StaffWorkRecord, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<StaffWorkRecord, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<StaffWorkRecord, String> profileColumn = new TableColumn<>("Profile Type");
        profileColumn.setCellValueFactory(new PropertyValueFactory<>("profileType"));

        TableColumn<StaffWorkRecord, Boolean> approvedColumn = new TableColumn<>("Approved");
        approvedColumn.setCellValueFactory(new PropertyValueFactory<>("approved"));

        TableColumn<StaffWorkRecord, String> hoursColumn = new TableColumn<>("Hours Worked");
        hoursColumn.setCellValueFactory(new PropertyValueFactory<>("hoursWorked"));

        table.getColumns().addAll(staffIdColumn, firstNameColumn, lastNameColumn, emailColumn, profileColumn,
                approvedColumn, hoursColumn);
        table.setItems(data);

        Label titleLabel = new Label("Staff Work Records Report");
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
