package inventorymanagement;

/**
 *
 * @author Rocco + Beedrix
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedReader;

public class CreateOrder {
    
    private String currentUser;
    private LocalDate todaysDate;
    private LocalDate etaDate;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private String etaDateString;
    private String todaysDateString;
    private Scanner scan;
    private Inventory inv;
    private Product product;

    public CreateOrder(String currentUser) { 
        this.currentUser = currentUser;
        this.inv = new Inventory(currentUser);
        this.product = new Product();

        this.todaysDate = LocalDate.now();
        this.etaDate = todaysDate.plusWeeks(2);
        this.todaysDateString = todaysDate.format(formatter);
        this.etaDateString = etaDate.format(formatter);
        this.scan = new Scanner(System.in);
    }
    
    public void orderMenu() {
        // Order menu to let user select their choice
        printOrderMenu();
        
        System.out.println("Select an option: ");
        int selectedMenuOption = scan.nextInt();

        switch (selectedMenuOption) {
            case 1:
                createNewOrder();
                break;
            case 2:
                orderHistory();
                break;
            case 3:
                break;
            default:
                System.out.println("> Invalid choice. Please try again.");
        }
    }

    public void printOrderMenu() {
        // Prints the order menu
        System.out.printf("+-------------------------------------------------+\n");
        System.out.printf("|    ORDERS.                                      |\n");
        System.out.printf("+-------------------------------------------------+\n");
        System.out.printf("| %-2s. %-42s  |\n", "1", "Create new order");
        System.out.printf("| %-2s. %-42s  |\n", "2", "Order history");
        System.out.printf("| %-2s. %-42s  |\n", "3", "Go back to menu");
        System.out.printf("+-------------------------------------------------+\n");
    }

    public void createNewOrder() {
        // Allows the order to create a new order of their current inventory
        System.out.println("Name your order: ");
        String orderName = scan.next();

        System.out.println("> Order '" + orderName + "' has been placed and will arrive by " + etaDateString);
        boolean valid = false;

        do {
            System.out.print("\nSave the receipt? (y/n) ");
            char createReceipt = Character.toLowerCase(scan.next().charAt(0));
            
            switch (createReceipt) {
                case 'n':
                    orderMenu();
                    valid = true;
                    break;
                case 'y':
                    createReceipt(orderName);
                    valid = true;
                    break;
                default:
                    System.out.println("> Invalid choice. Please try again.");
                    break;
            }
        } while (!valid);
    }

    public void createReceipt(String orderName) {
        // Prints a reciept of the order user had just placed   
        HashMap<Integer, Product> currentInventory = inv.getInventory();
        final LocalDate dateOfOrder = todaysDate;

        try {
            File orderFile = new File("UserProfiles/" + this.currentUser + "/Orders/" + orderName + ".txt");
            PrintWriter pw = new PrintWriter(new FileOutputStream(orderFile));

            String output = "+--------------------------------------------------------------+\n";
            output += String.format("| %-44s  %-14s |\n", "Order Name", orderName);
            output += String.format("| %-44s  %-14s |\n", "Date of order", dateOfOrder);
            output += String.format("| %-44s  %-14s |\n", "ETA Date", etaDate);
            output += "+--------------------------------------------------------------+\n";
            output += String.format("| %-8s | %-20s | %-11s | %-12s |\n", "ID", "Product Name", "Price", "Quantity");
            output += "+--------------------------------------------------------------+\n";

            double totalPrice = 0;

            for (HashMap.Entry<Integer, Product> entry : currentInventory.entrySet()) {
                Product product = entry.getValue();
                double productTotalPrice = product.getPrice() * product.getQuantity();
                totalPrice += productTotalPrice;
                output += String.format("| %-8s | %-20s | $%-10.2f | %-12s |\n", product.getProductID(), 
                        product.getProductName(), product.getPrice(), product.getQuantity());   
            }

            output += "+--------------------------------------------------------------+\n";
            output += String.format("| %-50s  $%-7.2f |\n", "Total Cost", totalPrice);
            output += "+--------------------------------------------------------------+\n";

            System.out.println(output);

            pw.println(output); // Appends the inventory toString to the txt file
            System.out.println("> '" + orderName + "' reciept has been saved");
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void orderHistory() {
        // Allows the user to select an order to view
        printAllOrders();
        System.out.println("Select the order (by name, e.g. 'myOrder') that you would like to view: ");
        String orderToView = scan.next();

        File orderFileToPrint = new File("UserProfiles/" + this.currentUser + "/Orders/" + orderToView + ".txt");

        if (orderFileToPrint.exists() && orderFileToPrint.length() > 0) {
            System.out.println("> Printing order '" + orderToView + "'\n");
            try (BufferedReader br = new BufferedReader(new FileReader(orderFileToPrint))) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                System.err.println("Failed to read file: " + e.getMessage());
            }
        } else {
            System.err.println("Order file not found or empty");
        }

        orderMenu();
    }

    public void printAllOrders() {
        // Prints a list of all previous orders made by the user
        File directory = new File("UserProfiles/" + this.currentUser + "/Orders/");
        
        //Get all the files from the directory
        File[] files = directory.listFiles();

        //Loop through all the files and print the name of the text files
        int orderID = 1;
        String output = "+-------------------------------------------------+\n";
        output += "|    YOUR ORDERS.                                 |\n";
        output += "|-------------------------------------------------|\n";

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                String fileName = file.getName();
                String orderDetails = orderID + ") " + fileName;
                output += String.format("| %-47s |\n", orderDetails);
                ++orderID;
            }
        }

        output += "+--------------------------------------------------+\n";
        System.out.println(output); 
    }
}