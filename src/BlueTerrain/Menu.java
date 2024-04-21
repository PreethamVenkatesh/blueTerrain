package Bluetrain;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Menu {

    private static String STARTER = "1.Crispy and Salted Belly Pork Squares with Bourbon Glaze and Dates Puree\t£8.95\r\n" + //
            "2.Soup of the Day , Served with Truffle Oil Ⓥ\t\t\t\t\t\t\t\t\t£7.25\r\n" + //
            "3.Patatas Bravas with Homemade Spicy Brava Sauce & Aioli Ⓥ\t\t\t\t\t£7.50\r\n" + //
            "4.Two Grilled Fresh Spanish Chorizo, on a Bed of Caramelised Onions\t\t\t£9.25\r\n" + //
            "5.Canarian Salty Glazed Potatoes with a Duo of Canarian Mojo Sauces Ⓥ\t\t£6.95\r\n" + //
            "6.Meatballs in a Homemade Tomato Sauce\t\t\t\t\t\t\t\t\t\t£8.50\r\n" + //
            "7.Trio of dips , Aubergine Pate, Piquillo Peppers Dip & Olive Pate Ⓥ\t\t\t\t£7.50\r\n" + //
            "8.Traditional Croquettes Served with Homemade Alioli Sauce\t\t\t\t\t£8.75\r\n" + //
            "9.Duck & Pork Liver Pate with Orange & Cognac\t\t\t\t\t\t\t\t£8.25\r\n" + //
            "10.Fried Spanish Padron Peppers, Alioli, Maldon Salt & Extra Virgin Olive Oil Ⓥ\t£7.95\r\n";

    private static String FISH_MENU = "1.Fresh Whitstable Oysters\t\t\t\t£2.95\r\n" + //
            "2.Mussels Al Marinara\t\t\t\t\t£8.50\r\n" + //
            "3.Rock Lobster Tail\t\t\t\t\t\t£21.95\r\n" + //
            "4.Deep Fried Calamares\t\t\t\t£7.95\r\n" + //
            "5.Deep Fried Cod Goujons\t\t\t\t£8.25\r\n" + //
            "6.Tripled Cooked Octopus\t\t\t\t£13.95\r\n" + //
            "7.Gambas Al Ajillo\t\t\t\t\t\t£11.95\r\n" + //
            "8.Grilled Sardines\t\t\t\t\t\t£7.95\r\n" + //
            "9.Pan Fried Scallops\t\t\t\t\t£13.95\r\n"; 
    
    private static String GRILL_MEAT = "1.8oz Grilled Fillet Steak Medallions (Peppercorn Sauce)\t\t\t\t\t\t\t\t£29.95\r\n" + //
            "2.7 oz Fillet Steak Served with a Spanish Pisto & Roasted Tomato\t\t\t\t\t\t£29.95\r\n" + //
            "3.10 oz Ribeye Steak Served with a Spanish Pisto & Roasted Tomato\t\t\t\t\t£27.95\r\n" + //
            "4.16 oz T-Bone Steak Served with a Spanish Pisto & Roasted Tomato\t\t\t\t\t£33.95\r\n" + //
            "5.Slow Roasted Lamb Shank Served with Red Rioja Wine & Rosemary Sauce\t\t\t£19.95\r\n" + //
            "6.Chicken Breast Stuffed with Spinach, Spanish Tetilla Cheese & Sweet Date Paste\t£17.95\r\n" + //
            "7.Slow Cooked Pork Belly, Served with Spanish Cider, Apples & Pear Sauce\t\t\t£18.95\r\n" + //
            "8.Tagliatelle A La Marinera, Seafood and Served in a Seafood Tomato Sauce\t\t\t£18.95\r\n";

    private static String VEGAN = "1.Vegan Ravioli stuffed with peas and mint on a Bed of piquillo pepper sauce\t£17.95\r\n" + //
            "2.Beetroot, roasted red Onion on a bed of Rich Tomato and mushrooms Sauce\t£17.95\r\n";        

    private static String FRIED_MEAT = "1.Jerk Chicken Half\t\t\t\t\t£15.20\r\n" + //
            "2.Jerk Chicken Breast\t\t\t\t£14.00\r\n" + //
            "3.Jerk Lamb\t\t\t\t\t\t£19.00\r\n" + //
            "4.Jerk Pork Belly\t\t\t\t\t£16.50\r\n" + //
            "5.Rum BBQ Ribs\t\t\t\t\t£15.50\r\n" + //
            "6.Mo'Bay Chicken\t\t\t\t\t£14.40\r\n" + //
            "7.Jerk Salmon\t\t\t\t\t\t£14.80\r\n" + //
            "8.Vegan Jerk\"Chicken\"\t\t\t\t£14.00\r\n";

    public static void showMenuPopup(Stage primaryStage) {
        Stage menuPopup = new Stage();
        menuPopup.initOwner(primaryStage);
        menuPopup.initStyle(StageStyle.UTILITY);
        menuPopup.initModality(Modality.APPLICATION_MODAL);

        VBox root = Functions.createRootVBox();
        root.setAlignment(Pos.TOP_CENTER);

        StackPane lightBluePadding = Functions.welcomePane();

        Button startersButton = Functions.createButton("STARTERS");
        Button fishMenuButton = Functions.createButton("FISH MENU");
        Button grillMeatButton = Functions.createButton("GRILL & MEAT");
        Button veganButton = Functions.createButton("VEGAN");
        Button pitButton = Functions.createButton("FRIED MEAT");
        Button dessertsButton = Functions.createButton("CHEF'S SPECIAL");

        Functions.setTooltip(startersButton, STARTER);
        Functions.setTooltip(fishMenuButton, FISH_MENU);
        Functions.setTooltip(grillMeatButton, GRILL_MEAT);
        Functions.setTooltip(veganButton, VEGAN);
        Functions.setTooltip(pitButton, FRIED_MEAT);
        Functions.setTooltip(dessertsButton, "Chef Special");

        VBox leftBox = Functions.createButtonVBox(startersButton, fishMenuButton);
        VBox centreBox = Functions.createButtonVBox(grillMeatButton, pitButton);
        VBox rightBox = Functions.createButtonVBox(veganButton, dessertsButton);

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

