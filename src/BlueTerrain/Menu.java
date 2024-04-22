package BlueTerrain;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Menu {

    private static String MENU_QUERY = "SELECT CONCAT(ItemValue, ' ', ItemName) AS result_column FROM Menu WHERE ItemType = ?";

    public static void showMenuPopup(Stage primaryStage) {

        Stage menuPopup = Functions.createPopupWindow(primaryStage);

        VBox root = Functions.createRootVBox();
        root.setAlignment(Pos.TOP_CENTER);

        StackPane lightBluePadding = Functions.restuarantLabel();

        Button startersButton = Functions.createButtonMenu("STARTERS", Color.LAVENDER);
        Button fishMenuButton = Functions.createButtonMenu("FISH MENU", Color.LAVENDER);
        Button grillMeatButton = Functions.createButtonMenu("GRILL & MEAT", Color.LAVENDER);
        Button veganButton = Functions.createButtonMenu("VEGAN", Color.LAVENDER);
        Button pitButton = Functions.createButtonMenu("MEAT MAINS", Color.LAVENDER);
        Button dessertsButton = Functions.createButtonMenu("CHEF'S SPECIAL", Color.LAVENDER);

        Functions.setTooltip(startersButton, Functions.fetchDBdata("Starter", MENU_QUERY));
        Functions.setTooltip(fishMenuButton, Functions.fetchDBdata("Fish_Menu", MENU_QUERY));
        Functions.setTooltip(grillMeatButton, Functions.fetchDBdata("Grill_Meat", MENU_QUERY));
        Functions.setTooltip(veganButton, Functions.fetchDBdata("Vegan", MENU_QUERY));
        Functions.setTooltip(pitButton, Functions.fetchDBdata("Meat Main", MENU_QUERY));

        VBox leftBox = Functions.createButtonVBoxMenu(startersButton, fishMenuButton);
        VBox centreBox = Functions.createButtonVBoxMenu(grillMeatButton, pitButton);
        VBox rightBox = Functions.createButtonVBoxMenu(veganButton, dessertsButton);

        HBox buttonsBox = new HBox(10);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(leftBox, centreBox, rightBox);

        Button closeButton = new Button("Close");
        closeButton.setAlignment(Pos.BOTTOM_RIGHT);
        closeButton.setOnAction(e -> {
                menuPopup.close();
                primaryStage.show();
        });

        root.getChildren().addAll(lightBluePadding, buttonsBox, closeButton);

        Scene scene = new Scene(root, 750, 550);
        menuPopup.setScene(scene);
        menuPopup.showAndWait();
    }
}



