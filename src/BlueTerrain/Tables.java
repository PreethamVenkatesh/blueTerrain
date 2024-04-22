package BlueTerrain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Tables {
    
    private static String BOOKING_STATUS_QUERY = "SELECT bookingStatus FROM tables WHERE tableNumber = ?";
    private static String TABLE = "TABLE ";
    private static String FAILURE_QUERY = "Failed to fetch booking status: ";

    public static void showTablesPopup(Stage primaryStage) {

        Stage tablePopup = Functions.createPopupWindow(primaryStage);

        VBox root = Functions.createRootVBox();
        root.setAlignment(Pos.TOP_CENTER);

        StackPane lightBluePadding = Functions.restuarantLabel();

        VBox leftBox = Functions.createButtonVBox(Color.LIGHTGREY, 
                TABLE + "1", TABLE + "2", TABLE + "3", TABLE + "4", TABLE + "5");
        VBox centreBox = Functions.createButtonVBox(Color.LIGHTGREY, 
                TABLE + "6", TABLE + "7", TABLE + "8", TABLE + "9", TABLE + "10");
        VBox rightBox = Functions.createButtonVBox(Color.LIGHTGREY, 
                TABLE + "11", TABLE + "12", TABLE + "13", TABLE + "14", TABLE + "15");

        setButtonColorsForTable(leftBox);
        setButtonColorsForTable(centreBox);
        setButtonColorsForTable(rightBox);

        HBox buttonsBox = new HBox(50); 
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(leftBox, centreBox, rightBox);

        Button closeButton = new Button("Close");
        closeButton.setAlignment(Pos.BOTTOM_RIGHT);
        closeButton.setOnAction(e -> {
            tablePopup.close();
            primaryStage.show();
        });

        root.getChildren().addAll(lightBluePadding, buttonsBox, closeButton);

        Scene scene = new Scene(root, 750, 550);
        tablePopup.setScene(scene);
        tablePopup.showAndWait();

    }

    private static void setButtonColorsForTable(VBox box) {
        for (int i = 0; i < box.getChildren().size(); i++) {
            Button button = (Button) box.getChildren().get(i);
            int tableNumber = Integer.parseInt(button.getText().split(" ")[1]);
            boolean bookingStatus = getBookingStatus(tableNumber);
            if (bookingStatus) {
                button.setStyle("-fx-background-color: greenyellow;");
            } else {
                button.setStyle("-fx-background-color: lightgrey;");
            }
        }
    }

    private static boolean getBookingStatus(int tableNumber) {
        try (Connection connection = Functions.getConnection();
             PreparedStatement statement = connection.prepareStatement(BOOKING_STATUS_QUERY)) {
            statement.setInt(1, tableNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean("bookingStatus");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(FAILURE_QUERY + e.getMessage());
        }
        return false;
    }

}
