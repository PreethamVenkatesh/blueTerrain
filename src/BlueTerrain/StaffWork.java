package BlueTerrain;

import java.time.LocalDate;
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

        TableColumn<StaffWorkRecord, Integer> staffIdColumn = new TableColumn<>("Staff ID");
        staffIdColumn.setCellValueFactory(new PropertyValueFactory<>("staffId"));

        TableColumn<StaffWorkRecord, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<StaffWorkRecord, LocalDate> dateOfWorkColumn = new TableColumn<>("Date of Work");
        dateOfWorkColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfWork"));

        TableColumn<StaffWorkRecord, String> hoursColumn = new TableColumn<>("Hours Worked");
        hoursColumn.setCellValueFactory(new PropertyValueFactory<>("hoursWorked"));

        TableColumn<StaffWorkRecord, String> notesColumn = new TableColumn<>("Notes");
        notesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));

        table.getColumns().addAll(staffIdColumn, nameColumn, dateOfWorkColumn, hoursColumn, notesColumn);
        table.setItems(data);

        Label titleLabel = new Label("Staff Work Records Report");
        titleLabel.setFont(new Font("Arial", 20));
        titleLabel.setStyle("-fx-font-weight: bold; -fx-padding: 10;");

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(titleLabel, table);

        Scene scene = new Scene(vbox, 800, 450);
        primaryStage.setTitle("Report - Staff Work Records");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
