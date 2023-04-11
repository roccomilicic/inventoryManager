package inventorymanagement;

/**
 *
 * @author Rocco + Beedrix
 */

import java.util.HashMap;
import java.util.Scanner;

public class Inventory {

    private Product product;
    //private UserService userServ = new UserService();
    ProductList productList;
    private Scanner scan = new Scanner(System.in);
    private int productQuantity;
    private HashMap<Integer, Product> inventory;

    public Inventory() { // default constructor
        this.inventory = new HashMap<Integer, Product>();
        this.product = new Product();
        this.productList = new ProductList(); // to call product methods
    }

    /*public Inventory(Product product) {
        this.prodToAdd = product;
        this.productToDelete = product;
        this.productToUpdate = product;
        this.inventory = new HashMap<Integer, Product>();
    }*/

    public void addProduct(ProductList productList) {
        // Adds the users choice of product to the current inventory
        System.out.println("ADD AN ITEM BASED ON ID: ");
        int productToAdd = scan.nextInt();
        product = productList.getProductById(productToAdd); // selects product to add based on ID from user

        if (product != null) { // if != empty product
            System.out.println("HOW MANY WOULD YOU LIKE TO ADD: ");
            int quantityToAdd = scan.nextInt();
            product.addQuantity(quantityToAdd);

            inventory.put(product.getProductID(), product); // add product to inventory hashmap
            System.out.println("> Added " + product.getProductName() + " to inventory");
            
        } else {
            System.out.println("> Item not found!");
        }
    }

    public void removeProduct(ProductList productList) {
        // Removes the users choice of product to the current inventory
        System.out.println(toString());
        System.out.println("\nREMOVE AN ITEM BASED ON ID: ");
        int productToRemove = scan.nextInt();
        product = productList.getProductById(productToRemove); // selects product to add based on ID from user

        if (product != null && inventory.containsKey(productToRemove)) { // if != empty product and if product is in inventory
            inventory.remove(productToRemove); // remove product from inventory hashmap
            System.out.println("> Removed " + product.getProductName() + " from inventory");    
        } else {
            System.out.println("> Item not found!");
        }
    }

    public void adjustQuantity(ProductList productList) {
        // Updates the quantity of chosen product in the current inventory
        System.out.println(toString());
        System.out.println("\nSELECT ITEM TO UPDATE BASED ON ID: ");
        int productToUpdate = scan.nextInt();
        product = productList.getProductById(productToUpdate); // selects product to add based on ID from user

        if (product != null && inventory.containsKey(productToUpdate)) { // if != empty product and if product is in inventory
            System.out.println("ENTER THE NEW QUANTITY OF " + product.getProductName().toUpperCase() + "'S: ");
            int newQuantity = scan.nextInt();
            product.setQuantity(newQuantity); // overwrite the quanity based on user input
            System.out.println("> Updated " + product.getProductName() + " to a quantity of " + product.getQuantity());  
        } else {
            System.out.println("> Item not found!");
        }
    }

    @Override
    public String toString() {
        String output = "\n╔══════════════════════════════╗";
        output += "\n║                  INVENTORY:                    ║";
        output += ("\n╚══════════════════════════════╝\n");
        

        for (HashMap.Entry<Integer, Product> entry : inventory.entrySet()) {
            Product product = entry.getValue();
            output += "ID: " + product.getProductID() + ", Name: " + product.getProductName() + ", Price: " + product.getPrice() + ", Quantity: " + product.getQuantity() + "\n";
        }

        return output;
    }
}
