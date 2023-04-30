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
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;

public class CreateOrder {
    /* This class works as a menu that allows the user to:
        1. Create a new order of the users current inventory
        2. Allow user to view all previously saved orders
        3. Go back to the inventory menu*/

    private Scanner scan;
    private Inventory inv;
    private String currentUser;
    private CarProduct product;
    private LocalDate todaysDate;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public CreateOrder(String currentUser) { 
        this.currentUser = currentUser;
        this.todaysDate = LocalDate.now();
        this.scan = new Scanner(System.in);
    }
    
    public void orderMenu() {
        // Order menu lets user select how they want to interact with the orders
        printOrderMenu();

        try {
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
                    break; // this will send user back to the previous menu
                default:
                    System.out.println("> Invalid choice. Please try again (1-3).");
                    orderMenu(); // Recursively call orderMenu() to keep looping
            }
        } catch (InputMismatchException e) {
            System.out.println("> Invalid input. Please pick a valid integer.");
            scan.nextLine(); // clear scanner buffer
            orderMenu(); // Recursively call orderMenu() to keep looping
        } catch (Exception e) {
            System.out.println("> Error: " + e.getMessage());
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
        // Allows the order to create a new order of their current inventory and save the reciept
        final LocalDate dateOfOrder = todaysDate; // Fetching date and ETA date
        final LocalDate etaDate = dateOfOrder.plusWeeks(2);

        System.out.println("Name your order: ");
        String orderName = scan.next();
        System.out.println("> Order '" + orderName + "' has been placed and will arrive by " + etaDate);
        
        // Asks user if they would like to save their order reciept
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
                    createReceipt(orderName, dateOfOrder, etaDate);
                    valid = true;
                    break;
                default:
                    System.out.println("> Invalid choice. Please try again.");
            }
        } while (!valid);
    }

    public void createReceipt(String orderName, LocalDate dateOfOrder, LocalDate etaDate) {
        // Prints a reciept of the order user had just placed   
        this.inv = new Inventory(currentUser);
        HashMap<Integer, CarProduct> currentInventory = inv.getInventory();

        while (true) {
            try {
                File orderFile = new File("UserProfiles/" + this.currentUser + "/Orders/" + orderName + ".txt");
                PrintWriter pw = new PrintWriter(new FileOutputStream(orderFile));

                String output = "+----------------------------------------------------------------------------------+\n";
                output += String.format("| %-81s|\n", "Order name: " + orderName);
                output += String.format("| %-81s|\n", "Date ordered: " + dateOfOrder);
                output += String.format("| %-81s|\n", "ETA Date: " + etaDate);
                output += "+--------+----------------+---------------+------------+--------------+------------+\n";
                output += String.format("| %-6s | %-14s | %-13s | %-10s | %-12s | %-10s |\n", "ID", "PRODUCT NAME", "BRAND", "PRICE", "TYPE", "QUANTITY");
                output += "+--------+----------------+---------------+------------+--------------+------------+\n";

                double totalPrice = 0;

                for (HashMap.Entry<Integer, CarProduct> entry : currentInventory.entrySet()) {
                    CarProduct product = entry.getValue();
                    double productTotalPrice = product.getPrice() * product.getQuantity(); // calculates total order cost
                    totalPrice += productTotalPrice;
                    output += String.format("| %-6s | %-14s | %-13s | $%-9.2f | %-12s | %-10s |\n", product.getProductId(), 
                            product.getProductModel(), product.getProductBrand(), product.getPrice(), product.getProductType(), product.getQuantity());   
                }

                output += "+----------------------------------------------------------------------------------+\n";
                output += String.format("| Total Cost %69s |\n", "$" + totalPrice);
                output += "+----------------------------------------------------------------------------------+\n";

                System.out.println(output); // Printing reciept to console
                pw.println(output); // Appends the reciept to newly created reciept txt file
                System.out.println("> '" + orderName + "' reciept has been saved");
                pw.close();
                break; // exit loop when no exceptions are thrown
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
                System.out.println("Please enter a valid order name:");
                orderName = scan.nextLine();
            }
        }
    }

    public void orderHistory() {
        // Allows the user to select an order to view
        printAllOrders();
        String orderToView = "";
        File orderFile = null; // declare orderFile before the while loop

        while (true) {
            System.out.println("Select the order (by name, e.g. 'myOrder') that you would like to view: ");
            orderToView = scan.next();
            orderFile = new File("UserProfiles/" + this.currentUser + "/Orders/" + orderToView + ".txt");

            if (orderFile.exists() && orderFile.length() > 0) { // if file exists and isnt empty
                break; // exit loop when no exceptions are thrown
            } else {
                System.err.println("> This order is either empty or doesn't exist");
            }
        }

        System.out.println("> Printing order '" + orderToView + "'...\n");
        try (BufferedReader br = new BufferedReader(new FileReader(orderFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printAllOrders() {
        // Prints a list of all previous orders made by the user
        File directory = new File("UserProfiles/" + this.currentUser + "/Orders/");
        if (!directory.exists()) {
            System.out.println("Error: Directory does not exist.");
            return;
        }
        File[] files = directory.listFiles(); // get all the files from the directory
        if (files == null) {
            System.out.println("Error: No files found in directory.");
            return;
        }

        int orderID = 1;
        String output = "+-------------------------------------------------+\n";
        output += "|    YOUR ORDERS.                                 |\n";
        output += "|-------------------------------------------------|\n";

        for (File file : files) { // loop through all the files and print the name of the text files
            if (file.isFile() && file.getName().endsWith(".txt")) {
                String fileName = file.getName();
                String orderDetails = orderID + ") " + fileName;
                output += String.format("| %-47s |\n", orderDetails);
                ++orderID; // increment so that each order has unique ID
            }
        }

        output += "+--------------------------------------------------+\n";
        System.out.println(output); 
    }
}
