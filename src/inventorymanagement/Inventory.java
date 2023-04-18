package inventorymanagement;

/**
 *
 * @author Rocco + Beedrix
 */

import java.util.HashMap;
import java.util.Scanner;
import java.io.File;  
import java.io.PrintWriter;  
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

public class Inventory {

    private String inventoryTxt;
    private Scanner scan = new Scanner(System.in);
    private Product product;
    ProductList productList;
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

    public void inventoryToTxt() {
        // Gets the current inventory and writes to a newly made txt file  
        try {
            System.out.println("What would you like to save the txt file as: ");
            inventoryTxt = scan.next();
            File inventoryFile = new File(inventoryTxt + ".txt");

            if (inventoryTxt != null) { // makes sure user actually entered a file name
                PrintWriter pw = new PrintWriter(new FileOutputStream(inventoryFile));
                pw.println(toString()); // Appends the inventory toString to the txt file
                System.out.println("> Inventory added to file '" + inventoryFile);
                pw.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        String output = "+-------------------------------------------------------------+\n";
        output += "|                        INVENTORY:                           |\n";
        output += "+-------------------------------------------------------------+\n";

        for (HashMap.Entry<Integer, Product> entry : inventory.entrySet()) {
            Product product = entry.getValue();
            output += String.format("| ID: %-2d | Name: %-10s | Price: $%-5.2f | Quantity: %-4d  |\n",
                    product.getProductID(), product.getProductName(), product.getPrice(), product.getQuantity());
        }

        output += "+-------------------------------------------------------------+\n";
        return output;
    }

}

