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
    private HashMap<Integer, CarProduct> inventory2;
    private String currentUser;
    private CarProduct product;

    public Inventory(String currentUser) { // default constructor

        this.inventory2 = new HashMap<Integer, CarProduct>();
        this.currentUser = currentUser;
        setInventoryHashMap();
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public void setInventoryHashMap() {

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
                        String model = parts[1];
                        String brand = parts[2];
                        String type = parts[3];
                        double price = Double.parseDouble(parts[4]);
                        int quantity = Integer.parseInt(parts[5]);

                        CarProduct setProduct = new CarTire(id, model, brand, type, price, quantity);
                        this.inventory2.put(id, setProduct);
                    }
                } else {
                    this.inventory2 = new HashMap<Integer, CarProduct>();
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
        boolean hasProd = false;
        for (HashMap.Entry<Integer, CarProduct> entry : inventory2.entrySet()) {
            CarProduct product = entry.getValue();
            if (product.productID == prod.productID) {
                hasProd = true;
            }

        }

        return hasProd;
    }

    public void addProduct() {


        System.out.println("Select the product type you wish to add: ");
        int productToAdd = scan.nextInt();

        addProduct(productToAdd);
    }

    public void addProduct(int prodOption) {

        String x = "";

        switch (prodOption) {
            case 1:
                this.product = new CarTire();
                x = "Tire";
                break;
            case 2:
                this.product = new CarSideMirror();
                x = "Side Mirror";
                break;
            case 3:
                this.product = new CarEngine();
                x = "Engine";
                break;
            case 4:
                this.product = new CarHeadLights();
                x = "Head Lights";
                break;

            default:
                System.out.println("> Invalid choice. Please try again.");

        }

        System.out.println(product.toString());
        System.out.println("Select the " + x + " you want to add to inventory based on ID: ");
        int productToAdd = scan.nextInt();

        CarProduct tempProd = product.getProductByID(productToAdd);

        if (tempProd != null) {
            if (hasProduct(tempProd)) {
                System.out.println("How many would you like to add: ");
                int quantityToAdd = scan.nextInt();
                inventory2.get(productToAdd).addQuantity(quantityToAdd);
            } else {
                System.out.println("How many would you like to add: ");
                int quantityToAdd = scan.nextInt();
                tempProd.addQuantity(quantityToAdd);

                inventory2.put(tempProd.productID, tempProd); 
                System.out.println("> Added " + tempProd.productID + " to inventory");

            }
            inventoryToTxt();

        } else {
            System.out.println("> Item not found!");
        }
    }

    public void removeProduct() {

        if (inventory2.isEmpty()) { 
            System.out.println("> You need at least 1 item in your inventory in order to remove an item.\n");
        } else { 
            System.out.println(toString());
            System.out.println("\nRemove an item based on ID: ");
            int productToRemove = scan.nextInt();
            CarProduct tempProd = inventory2.get(productToRemove);
            
            if (tempProd != null) { 
                inventory2.remove(productToRemove); 
                System.out.println("> Removed " + tempProd.productModel + " " + tempProd.productBrand + " from inventory");
                inventoryToTxt();
            } else {
                System.out.println("> Item not found!");
            }
        }
    }

    public void adjustQuantity() {
 
        if (inventory2.isEmpty()) { 
            System.out.println("> You need at least 1 item in your inventory in order to adjust the quantity of item.\n");
        } else {
            System.out.println(toString());
            System.out.println("\nSelect an item based on ID: ");
            int productToUpdate = scan.nextInt();
            
            CarProduct tempProd = inventory2.get(productToUpdate);
            
            if (tempProd != null) { 
                System.out.println("Enter the quantity of " + tempProd.productModel + " " + tempProd.productBrand);
                int newQuantity = scan.nextInt();
                inventory2.get(productToUpdate).quantity = newQuantity; // overwrite the quanity based on user input
                System.out.println("> Updated " + tempProd.productModel + " " + tempProd.productBrand + " to a quantity of " + inventory2.get(productToUpdate).quantity);
                inventoryToTxt();
            } else {
                System.out.println("> Item not found!");
            }
        }
    }

    public void inventoryToTxt() {
        try {

            // makes sure user actually entered a file name
            File newUserInven = new File("UserProfiles/" + this.currentUser + "/" + this.currentUser + "Inventory.txt");
            PrintWriter pw = new PrintWriter(new FileOutputStream(newUserInven));

            String output = "";
            for (HashMap.Entry<Integer, CarProduct> entry : inventory2.entrySet()) {
                CarProduct product = entry.getValue();
                output += String.format(product.productID + "," + product.productModel + ","
                        + product.productBrand + "," + product.productType + "," + product.price + "," + product.quantity + "\n");
            }

            pw.println(output);
            System.out.println("> Inventory added to file '" + newUserInven + "'");
            pw.close();

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    public String getInventoryTxt() {
        return this.inventoryTxt;
    }

    @Override
    public String toString() {
        String output = "+----------------------------------------------------------------------------------+\n";
        output += "|                                     INVENTORY:                                   |\n";
        output += "+--------+----------------+---------------+------------+--------------+------------+\n";
        output += "| ID     | PRODUCT NAME   |     BRAND     |    PRICE   |      TYPE    |  QUANTITY  |\n";
        output += "+--------+----------------+---------------+------------+--------------+------------+\n";

        for (HashMap.Entry<Integer, CarProduct> entry : inventory2.entrySet()) {
            CarProduct product = entry.getValue();
            output += String.format("| %-6d | %-14s | %-13s | $%9.2f | %-12s | %-10s |\n",
                    product.productID, product.productModel, product.productBrand, product.price, product.productType, product.quantity);
        }

        output += "+----------------------------------------------------------------------------------+\n";
        return output;
    }

}
