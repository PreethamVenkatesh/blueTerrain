package BlueTerrain;

public class Item {
    private int slNo;
    private String itemName;
    private double itemPrice;

    public Item(int slNo, String itemName, double itemPrice, boolean b) {
        this.slNo = slNo;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    public int getSlNo() {
        return slNo;
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

    public boolean isSelected() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isSelected'");
    }
}
