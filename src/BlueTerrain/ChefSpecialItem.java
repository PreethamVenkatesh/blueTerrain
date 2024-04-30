package BlueTerrain;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ChefSpecialItem {
    private final IntegerProperty slNo;
    private final StringProperty itemName;
    private final DoubleProperty itemPrice;
    private final StringProperty itemType;

    public ChefSpecialItem(int slNo, String itemName, double itemPrice, String itemType) {
        this.slNo = new SimpleIntegerProperty(slNo);
        this.itemName = new SimpleStringProperty(itemName);
        this.itemPrice = new SimpleDoubleProperty(itemPrice);
        this.itemType = new SimpleStringProperty(itemType);
    }

    public int getSlNo() {
        return slNo.get();
    }

    public IntegerProperty slNoProperty() {
        return slNo;
    }

    public void setSlNo(int slNo) {
        this.slNo.set(slNo);
    }

    public String getItemName() {
        return itemName.get();
    }

    public StringProperty itemNameProperty() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName.set(itemName);
    }

    public double getItemPrice() {
        return itemPrice.get();
    }

    public DoubleProperty itemPriceProperty() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice.set(itemPrice);
    }

    public String getItemType() {
        return itemType.get();
    }

    public StringProperty itemTypeProperty() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType.set(itemType);
    }
}
