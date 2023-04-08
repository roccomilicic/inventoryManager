package inventorymanagement;

/**
 *
 * @author Rocco + Beedrix
 */

import java.util.HashMap;

public class Inventory {

    Product product = new Product(); // to call product methods
    private int productQuantity;
    private HashMap<Integer, Product> inventory;

    public Inventory() { // default constructor
        this.inventory = new HashMap<Integer, Product>();
    }

    /*public Inventory(Product product) {
        this.prodToAdd = product;
        this.productToDelete = product;
        this.productToUpdate = product;
        this.inventory = new HashMap<Integer, Product>();
    }*/

    public void addProduct() {
        // Adds the users choice of product to the current inventory
        System.out.println(product.toString()); // at the moment its not printing the toString????? but it can print it from UserService main
        
        //inventory.put(prodToAdd.getProductID(), prodToAdd);
    }

    /*public Product removeProduct(Product product) {
        inventory.remove(prodToAdd.getProductID(), prodToAdd);
    }

    public Product updateProduct(Product product) {

    }*/

    @Override
    public String toString() {
        String output = "\n╔══════════════════════════════╗";
        output += "\n║                  INVENTORY:                    ║";
        output += ("\n╚══════════════════════════════╝");
        

        /*for (HashMap.Entry<Integer, Product> inventoryItem : inventory.entrySet()) {
            int id = inventoryItem.getKey();
            Product product = inventoryItem.getValue();

            output += "\nID: %d\n%s\n", id, product.toString()));
        }*/

        return output;
    }
}
