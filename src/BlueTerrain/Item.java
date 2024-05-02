package BlueTerrain;

/**
 * The Item class represents an item in a menu or order.
 * @author Preetham
 */
public class Item {
    private int slNo;
    private String itemName;
    private double itemPrice;
    private boolean selected;

    /**
     * Constructs an Item object with the specified attributes.
     * 
     * @param slNo      The serial number of the item.
     * @param itemName  The name of the item.
     * @param itemPrice The price of the item.
     * @param selected  A boolean indicating whether the item is selected.
     */
    public Item(int slNo, String itemName, double itemPrice, boolean b) {
        this.slNo = slNo;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    /**
     * Gets the serial number of the item.
     * 
     * @return The serial number of the item.
     */
    public int getSlNo() {
        return slNo;
    }

     /**
     * Sets whether the item is selected.
     * 
     * @param selected A boolean indicating whether the item is selected.
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Checks if the item is selected.
     * 
     * @return true if the item is selected, false otherwise.
     */
    public boolean isSelected() {
        return selected;
    }

     /**
     * Sets the serial number of the item.
     * 
     * @param slNo The serial number to set.
     */
    public void setSlNo(int slNo) {
        this.slNo = slNo;
    }

    /**
     * Gets the name of the item.
     * 
     * @return The name of the item.
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Sets the name of the item.
     * 
     * @param itemName The name to set.
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Gets the price of the item.
     * 
     * @return The price of the item.
     */
    public double getItemPrice() {
        return itemPrice;
    }

    /**
     * Sets the price of the item.
     * 
     * @param itemPrice The price to set.
     */
    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    
}
