package inventorymanagement;

/**
 *
 * @author Rocco + Beedrix
 */

public class Product {
    private int productID;
    private String productName;
    private double price;
    private int quantity;

    public Product() {
    }

    public Product(int productID, String productName, double price) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public int getProductID() {
        return productID;
    }
    public void setProductID(int productID) {
      this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }
    public void addQuantity(int quantity) { // adds onto the quantity of a product in inventory
        this.quantity += quantity;
    }
    public void setQuantity(int quantity) { // overwrites the quantity of a product in inventory
        this.quantity = quantity;
    }
}

