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
import java.util.InputMismatchException;

public class Inventory {

    private String inventoryTxt;
    private Scanner scan;
    private HashMap<Integer, CarProduct> inventory;
    private String currentUser;
    private CarProduct carProduct;

    public Inventory(String currentUser) {
        this.inventory = new HashMap<Integer, CarProduct>();
        this.currentUser = currentUser;
        this.scan = new Scanner(System.in);
        setInventoryHashMap();
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public HashMap<Integer, CarProduct> getInventory() {
        return this.inventory;
    }

    public String getInventoryTxt() {
        return this.inventoryTxt;
    }

    public void setInventoryHashMap() {
    // Loads in all inventory information from current users txt file into a hashmap
        File file = new File("UserProfiles/" + this.currentUser + '/' + this.currentUser + "Inventory.txt");

        if (file.exists()) { // if user has an inventory txt file
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                if (file.length() != 0) { // checks if file is empty
                    String line;
                    while ((line = br.readLine()) != null) {
                        // skip empty or whitespace-only lines
                        if (line.trim().isEmpty()) {
                            continue;
                        }

                        String[] parts = line.split(","); // get inventory info of products into an array
                        try {
                            // Parse values from array
                            int id = Integer.parseInt(parts[0]);
                            String model = parts[1];
                            String brand = parts[2];
                            String type = parts[3];
                            double price = Double.parseDouble(parts[4]);
                            int quantity = Integer.parseInt(parts[5]);

                            CarProduct setProduct = new CarTire(id, model, brand, type, price, quantity); // Sets product based on values extracted from txt file
                            this.inventory.put(id, setProduct); // Adds product into inventory hashmap with corresponding id
                        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                            System.err.println("Error parsing inventory file: " + e.getMessage());
                        }
                    }
                } else {
                    this.inventory = new HashMap<Integer, CarProduct>();
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } else {
            inventoryToTxt();
            System.out.println("> New User text file created.");
        }
    }

    public boolean hasProduct(CarProduct prod) {
        // Checks if product already exists in users inventory
        boolean hasProd = false;
        for (HashMap.Entry<Integer, CarProduct> entry : inventory.entrySet()) {
            CarProduct product = entry.getValue();
            if (product.productID == prod.productID) {
                hasProd = true;
            }
        }
        return hasProd;
    }

    public void chooseProductType() {
    // Lets user pick what type of product they would like to add to their inventory
        int productType = 0;
        boolean validInput = false;
        
        while (!validInput) { // Loop until a valid input is entered
            try {
                System.out.println("Select the product type you wish to add: ");
                productType = scan.nextInt();
                if (productType >= 1 && productType <= 5) { // Makes sure input is a valid option on the menu
                    validInput = true;
                } else {
                    System.out.println("> Choose a valid option from 1-5");
                }
            } catch (InputMismatchException e) {
                System.out.println("> Invalid input. Please enter a valid integer.");
                scan.next(); // clear the invalid input from the scanner
            }
        }

        CarProduct carProduct = null;
        String product = "";

        switch (productType) {
            case 1:
                carProduct = new CarTire();
                product = "Tire";
                break;
            case 2:
                carProduct = new CarSideMirror();
                product = "Side Mirror";
                break;
            case 3:
                carProduct = new CarEngine();
                product = "Engine";
                break;
            case 4:
                carProduct = new CarHeadLights();
                product = "Head Lights";
                break;
            default:
                System.out.println("> Invalid choice. Please try again.");
                return; // exit the method if an invalid choice was made
        }

        System.out.println(carProduct.toString()); // Prints out list of available products to add of chosen product type
        System.out.println("Select the " + product + " you want to add to inventory based on ID: ");
    }

    public void addProduct() {
        // Gets the user's input for type of product they would like to add to inventory
        chooseProductType();

        boolean validInput = false;
        int productToAdd = 0;
        int quantityToAdd = 0;
        CarProduct tempProd = null;

        while (!validInput) { // Loop until a valid input is entered
            try {
                productToAdd = scan.nextInt(); 
                tempProd = carProduct.getProductByID(productToAdd); // gets product based on users input for ID
                if (tempProd != null) { // checks if selected product is null
                    validInput = true; // exits loop as product exists
                } else {
                    System.out.println("> Choose a valid product from the list");
                }
            } catch (InputMismatchException e) {
                System.out.println("> Invalid input. Please enter a valid integer.");
                scan.next(); // clear the invalid input from the scanner
            }
        }
        if (hasProduct(tempProd)) { // if product already exists in user inventory
            System.out.println("This item already exists in your inventory. How many mmore would you like to add: ");
            validInput = false;

            while (!validInput) { // Loop until a valid input is entered
                try {
                    quantityToAdd = scan.nextInt();
                    validInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("> Invalid input. Please enter a valid integer.");
                    scan.next(); // clear the invalid input from the scanner
                }
            }
            inventory.get(productToAdd).setQuantity(quantityToAdd);
        }
        tempProd.setQuantity(quantityToAdd);
        inventory.put(tempProd.productID, tempProd);
        System.out.println("> Added " + tempProd.productID + " to inventory");
    }

    public void removeProduct() {
        // Allows user to remove a product from their inventory
        if (inventory.isEmpty()) { 
            System.out.println("> You need at least 1 item in your inventory in order to remove an item.\n");
        } else { 
            System.out.println(toString());
            System.out.println("Remove an item based on ID: ");
            boolean productRemoved = false;
            while (!productRemoved) {
                try {
                    int productToRemove = scan.nextInt();
                    CarProduct tempProd = inventory.get(productToRemove); // gets product to remove from inventory

                    if (tempProd != null) { 
                        inventory.remove(productToRemove); 
                        System.out.println("> Removed " + tempProd.productModel + " " + tempProd.productBrand + " from inventory");
                        inventoryToTxt();
                        productRemoved = true;
                    } else {
                        System.out.println("> Item not found! Please enter a valid ID.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("> Invalid input! Please enter a number.");
                    scan.next();
                }
            }
        }
    }

    public void adjustQuantity() {
        // Allows the user to adjust the quantity of a product within their inventory
        if (inventory.isEmpty()) { 
            System.out.println("> You need at least 1 item in your inventory in order to adjust the quantity of item.\n");
        } else {
            // Display inventory and prompt user to select an item
            System.out.println(toString());
            System.out.println("\nSelect an item based on ID: ");
            
            boolean validID = false;
            boolean validQuantity = false;
            int productToUpdate = 0;
            CarProduct tempProd = null;

            while(!validID){
                try {
                    productToUpdate = scan.nextInt();
                    tempProd = inventory.get(productToUpdate); // get product to update from inventory
                    if (tempProd != null) { 
                        // Prompt user to enter new quantity and update the quantity of the selected product in the inventory   
                        validID = true; // break loop if input is valid
                    } else {
                        System.out.println("> Invalid ID: item not found!");
                    }
                } catch (InputMismatchException e) { // Handle input mismatch exception
                    System.out.println("> Invalid input: please enter a number.");
                    scan.next(); // Clear invalid input from scanner
                }
            }
            //CarProduct tempProd = inventory.get(productToUpdate); // get product to update from inventory
            while(!validQuantity){
                try {
                    // Prompt user to enter new quantity and update the quantity of the selected product in the inventory
                    System.out.println("Enter the quantity of " + tempProd.productModel + " " + tempProd.productBrand + ": ");
                    int newQuantity = scan.nextInt();
                    inventory.get(productToUpdate).quantity = newQuantity;
                    System.out.println("> Updated " + tempProd.productModel + " " + tempProd.productBrand + " to a quantity of " + inventory.get(productToUpdate).quantity);
                    inventoryToTxt(); // Update the inventory text file
                    validQuantity = true; // break loop if input is valid
                } catch (InputMismatchException e) { // Handle input mismatch exception
                    System.out.println("> Invalid input! Please enter a number.");
                    scan.next(); // Clear invalid input from scanner
                }
            }
        }
    }

    public void inventoryToTxt() {
        try {

            // makes sure user actually entered a file name
            File newUserInven = new File("UserProfiles/" + this.currentUser + "/" + this.currentUser + "Inventory.txt");
            PrintWriter pw = new PrintWriter(new FileOutputStream(newUserInven));

            String output = "";
            for (HashMap.Entry<Integer, CarProduct> entry : inventory.entrySet()) {
                CarProduct product = entry.getValue();
                output += String.format(product.productID + "," + product.productModel + ","
                        + product.productBrand + "," + product.productType + "," + product.price + "," + product.quantity + "\n");
            }

            pw.println(output);
            //System.out.println("> Inventory added to file '" + newUserInven + "'");
            pw.close();

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        String output = "+----------------------------------------------------------------------------------+\n";
        output += "|    YOUR INVENTORY.                                                               |\n";
        output += "+--------+----------------+---------------+------------+--------------+------------+\n";
        output += "| ID     | PRODUCT NAME   |     BRAND     |    PRICE   |      TYPE    |  QUANTITY  |\n";
        output += "+--------+----------------+---------------+------------+--------------+------------+\n";

        for (HashMap.Entry<Integer, CarProduct> entry : inventory.entrySet()) {
            CarProduct product = entry.getValue();
            output += String.format("| %-6d | %-14s | %-13s | $%9.2f | %-12s | %-10s |\n",
                    product.productID, product.productModel, product.productBrand, product.price, product.productType, product.quantity);
        }

        output += "+----------------------------------------------------------------------------------+\n";
        return output;
    }
}
