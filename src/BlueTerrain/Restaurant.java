package BlueTerrain;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Restaurant {
    
    public void start(Stage primaryStage) {
        VBox root = Functions.createRootVBox();
        root.setAlignment(Pos.TOP_CENTER);

        Color lightBlue = Color.rgb(173, 216, 230);
        StackPane lightBluePadding = new StackPane();
        lightBluePadding.setBackground(new Background(new BackgroundFill(lightBlue, CornerRadii.EMPTY, Insets.EMPTY)));
        lightBluePadding.setPadding(new Insets(10));
        Label welcomeLabel = new Label("BlueTerrain");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        welcomeLabel.setStyle("-fx-text-fill: darkblue;");
        lightBluePadding.getChildren().add(welcomeLabel);

        MenuButton menuButton = new MenuButton("MENU");
        menuButton.setFont(new Font(15));
        menuButton.setTextFill(Color.DARKBLUE);

        MenuItem item1 = new MenuItem("Item 1");
        MenuItem item2 = new MenuItem("Item 2");
        MenuItem item3 = new MenuItem("Item 3");
        MenuItem item4 = new MenuItem("Item 4");

        ContextMenu chipMenu1 = createChipMenu("Chip 1", "Chip 2", "Chip 3");
        ContextMenu chipMenu2 = createChipMenu("Chip A", "Chip B", "Chip C");
        ContextMenu chipMenu3 = createChipMenu("Chip X", "Chip Y", "Chip Z");
        ContextMenu chipMenu4 = createChipMenu("Chip Alpha", "Chip Beta", "Chip Gamma");

        item1.setOnAction(e -> chipMenu1.show(menuButton, Side.BOTTOM, 0, 0));
        item2.setOnAction(e -> chipMenu2.show(menuButton, Side.BOTTOM, 0, 0));
        item3.setOnAction(e -> chipMenu3.show(menuButton, Side.BOTTOM, 0, 0));
        item4.setOnAction(e -> chipMenu4.show(menuButton, Side.BOTTOM, 0, 0));

        menuButton.getItems().addAll(item1, item2, item3, item4);

        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        header.getChildren().addAll(menuButton);

        root.getChildren().addAll(lightBluePadding, header);
        Functions.setupAndShowScene(primaryStage, root, 800, 600);
    }

    private ContextMenu createChipMenu(String... chipItems) {
        ContextMenu contextMenu = new ContextMenu();
        for (String chipItem : chipItems) {
            MenuItem menuItem = new MenuItem(chipItem);
            contextMenu.getItems().add(menuItem);
        }
        return contextMenu;
    }
}
