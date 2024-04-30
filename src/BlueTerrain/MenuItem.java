package BlueTerrain;

public class MenuItem {
    private int menuId;
    private double itemValue;
    private String itemName;
    private String itemType;
    private int purchaseCount;

    // Constructor to initialize the MenuItem object
    public MenuItem(int menuId, double itemValue, String itemName, String itemType, int purchaseCount) {
        this.menuId = menuId;
        this.itemValue = itemValue;
        this.itemName = itemName;
        this.itemType = itemType;
        this.purchaseCount = purchaseCount;
    }

    // Getters and Setters
    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public double getItemValue() {
        return itemValue;
    }

    public void setItemValue(double itemValue) {
        this.itemValue = itemValue;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(int purchaseCount) {
        this.purchaseCount = purchaseCount;
    }
}
