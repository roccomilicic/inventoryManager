/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package inventorymanagement;

/**
 *
 * @author rocco
 */
public class Product {
    private int productID;
    private String productName;
    private int price;
    private ArrayList<Product> products = new ArrayList<Product>();

    public Product(int productID, String productName, int price, int quantity) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
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

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
}
