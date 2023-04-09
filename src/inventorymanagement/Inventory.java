package inventorymanagement;

/**
 *
 * @author Rocco + Beedrix
 */

import java.util.HashMap;
import java.util.Scanner;

public class Inventory {

    Product product;
    UserService userServ = new UserService();
    Scanner scan = new Scanner(System.in);
    private int productQuantity;
    private HashMap<Integer, Product> inventory;

    public Inventory() { // default constructor
        this.inventory = new HashMap<Integer, Product>();
        this.product  = new Product(); // to call product methods
    }

    /*public Inventory(Product product) {
        this.prodToAdd = product;
        this.productToDelete = product;
        this.productToUpdate = product;
        this.inventory = new HashMap<Integer, Product>();
    }*/

    public void addProduct() {
        // Adds the users choice of product to the current inventory
        product.generateProducts(); // generates products to pick from
        System.out.println(product.toString()); // 

        System.out.println("SELECT AN ITEM BASED ON ID: ");
        int productToAdd = scan.nextInt();
        product = product.getProductById(productToAdd); // selects product to add based on ID from user

        if (product != null) { // if != empty product
            inventory.put(product.getProductID(), product); // add product to inventory hashmap
            System.out.println("> Added " + product.getProductName() + " to inventory");
            
        } else {
            System.out.println("> Item not found!");
        }
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
        

        for (HashMap.Entry<Integer, Product> entry : inventory.entrySet()) {
            Product product = entry.getValue();
            output += "\nID: " + product.getProductID() + ", Name: " + product.getProductName() + ", Price: " + product.getPrice() + "\n";
        }

        return output;
    }
}
