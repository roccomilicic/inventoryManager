package inventorymanagement;

/**
 *
 * @author Rocco + Beedrix
 */

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Inventory {

    private String inventoryTxt;
    private Scanner scan = new Scanner(System.in);
    private Product product;
    ProductList productList;
    private int productQuantity;
    private HashMap<Integer, Product> inventory;
    private String currentUser;

    public Inventory(String currentUser) { // default constructor
        this.inventory = new HashMap<Integer, Product>();
        this.product = new Product();
        this.productList = new ProductList(); // to call product methods
        this.currentUser = currentUser;

        setProductListArray();
    }

    public void setProductListArray() {

        File file = new File("UserProfiles/" + this.currentUser + '/' + this.currentUser + "Inventory.txt");

        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {

                if (file.length() != 0) {
                    String line;

                    while ((line = br.readLine()) != null) {
                        // skip empty or whitespace-only lines
                        if (line.trim().isEmpty()) {
                            continue;
                        }
                        String[] parts = line.split(",");

                        // parse values
                        int id = Integer.parseInt(parts[0]);
                        String name = parts[1];
                        double price = Double.parseDouble(parts[2]);
                        int quantity = Integer.parseInt(parts[3]);

                        Product setProduct = new Product(id, name, price, quantity);
                        this.inventory.put(id, setProduct);
                    }
                } else {
                    this.inventory = new HashMap<Integer, Product>();
                }

            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }else{
            inventoryToTxt();
            System.out.println("> New User text file created.");
        }

    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public void addProduct(ProductList productList) {
        // Adds the users choice of product to the current inventory

        System.out.println("Add an item based on ID: ");
        int productToAdd = scan.nextInt();
        product = productList.getProductById(productToAdd); // selects product to add based on ID from user

        if (product != null) { // if != empty product
            System.out.println("How many would you like to add: ");
            int quantityToAdd = scan.nextInt();
            product.addQuantity(quantityToAdd);

            inventory.put(product.getProductID(), product); // add product to inventory hashmap
            System.out.println("> Added " + product.getProductName() + " to inventory");
            inventoryToTxt();

        } else {
            System.out.println("> Item not found!");
        }
    }

    public void removeProduct(ProductList productList) {
        // Removes the users choice of product to the current inventory
        if (inventory.isEmpty()) { // if no items inventory
            System.out.println("> You need at least 1 item in your inventory in order to remove an item.\n");
        } else { // if items in inventory
            System.out.println(toString());
            System.out.println("\nRemove an item based on ID: ");
            int productToRemove = scan.nextInt();
            product = productList.getProductById(productToRemove); // selects product to add based on ID from user

            if (product != null && inventory.containsKey(productToRemove)) { // if != empty product and if product is in inventory
                inventory.remove(productToRemove); // remove product from inventory hashmap
                product.setQuantity(0); // reset quanity incase added back in the future
                System.out.println("> Removed " + product.getProductName() + " from inventory");
                inventoryToTxt();
            } else {
                System.out.println("> Item not found!");
            }
        }
    }

    public void adjustQuantity(ProductList productList) {
        // Updates the quantity of chosen product in the current inventory     
        if (inventory.isEmpty()) { // if no items inventory
            System.out.println("> You need at least 1 item in your inventory in order to adjust the quantity of item.\n");
        } else { // if items in inventory
            System.out.println(toString());
            System.out.println("\nSelect an item based on ID: ");
            int productToUpdate = scan.nextInt();
            product = productList.getProductById(productToUpdate); // selects product to add based on ID from user

            if (product != null && inventory.containsKey(productToUpdate)) { // if != empty product and if product is in inventory
                System.out.println("Enter the quantity of " + product.getProductName().toUpperCase());
                int newQuantity = scan.nextInt();
                product.setQuantity(newQuantity); // overwrite the quanity based on user input
                System.out.println("> Updated " + product.getProductName() + " to a quantity of " + product.getQuantity());
                inventoryToTxt();
            } else {
                System.out.println("> Item not found!");
            }
        }
    }

    public void inventoryToTxt() {
        // Gets the current inventory and writes to a newly made txt file  
        //if (inventory.isEmpty()) {
        // System.out.println("> You need at least 1 item in your inventory in order to save it as a txt file.\n");
        //} else {

        try {

            // makes sure user actually entered a file name
            File newUserInven = new File("UserProfiles/" + this.currentUser + "/" + this.currentUser + "Inventory.txt");
            PrintWriter pw = new PrintWriter(new FileOutputStream(newUserInven));

            String output = "";
            for (HashMap.Entry<Integer, Product> entry : inventory.entrySet()) {
                Product product = entry.getValue();
                output += String.format(product.getProductID() + "," + product.getProductName() + ","
                        + product.getPrice() + "," + product.getQuantity() + "\n");
            }

            pw.println(output); // Appends the inventory toString to the txt file
            System.out.println("> Inventory added to file '" + newUserInven + "'");
            pw.close();

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        //}
    }

    public String getInventoryTxt() {
        return this.inventoryTxt;
    }

    @Override
    public String toString() {

        String output = "";

        //if (inventory.isEmpty()) {
        //     output = "> You currently have no items in your inventory.\n";
        // } else {
        output += "+-------------------------------------------------------------+\n";
        output += "|                        INVENTORY:                           |\n";
        output += "|-------------------------------------------------------------|\n";
        output += "| ID     | Name              | Price         | Quantity       |\n";
        output += "+-------------------------------------------------------------+\n";

        for (HashMap.Entry<Integer, Product> entry : inventory.entrySet()) {
            Product product = entry.getValue();
            output += String.format("| %-3d    | %-10s        | $%-6.2f       | %-6d         |\n",
                    product.getProductID(), product.getProductName(), product.getPrice(), product.getQuantity());
        }

        output += "+-------------------------------------------------------------+\n";
        //}

        return output;
    }
}


