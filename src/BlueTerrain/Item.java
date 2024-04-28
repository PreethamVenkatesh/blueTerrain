package BlueTerrain;

public class Item {
    private int slNo;
    private String itemName;
    private double itemPrice;
    private boolean selected;

    public Item(int slNo, String itemName, double itemPrice, boolean b) {
        this.slNo = slNo;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    public int getSlNo() {
        return slNo;
    }
//boolean method to get menu selection
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }


    public void setSlNo(int slNo) {
        this.slNo = slNo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    
}
