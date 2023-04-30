package inventorymanagement;

/**
 *
 * @author Rocco + Beedrix
 */

package InvenMan;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserService {

    Scanner scan = new Scanner(System.in);

    public UserService() {
    }

    public void startProgram(UserService userServ, Inventory inventory, CreateOrder createAnOrder) {
        /*Starts program by calling all the needed methods before user interacts
        with the CUI. Then runs a loop on the menu till user exits*/

        //productList.generateProducts();
        while (true) { // loops menu till user quits the program
            userServ.userMenu(inventory, createAnOrder); // starts off the program (would come after user login once implemented)
        }
    }

    public static void printUserMenu() {
        // Prints the user menu
        System.out.printf("+-------------------------------------------------+\n");
        System.out.printf("|    INVENTORY MANAGER.                           |\n");
        System.out.printf("+-------------------------------------------------+\n");
        System.out.printf("| %-2s. %-42s  |\n", "1", "View inventory");
        System.out.printf("| %-2s. %-42s  |\n", "2", "Update inventory");
        System.out.printf("| %-2s. %-42s  |\n", "3", "Create an order");
        System.out.printf("| %-2s. %-42s  |\n", "4", "Exit program");
        System.out.printf("+-------------------------------------------------+\n");
    }

    public void userMenu(Inventory inventory, CreateOrder createAnOrder) {
        // Gets the user input for the update inventory menu

        boolean validOptionSelected = false;
        while (!validOptionSelected) {
            printUserMenu();
            System.out.println("Select an option: ");
            int selectedMenuOption;
            try {
                selectedMenuOption = scan.nextInt();
            } catch (InputMismatchException e) {
                // Handle non-integer input
                System.out.println("> Invalid input. Please enter a number.");
                scan.nextLine(); // Consume the invalid input
                continue;
            }

            switch (selectedMenuOption) {
                case 1:
                    System.out.println(inventory.toString());
                    validOptionSelected = true;
                    break;
                case 2:
                    updateInventoryMenu(inventory, createAnOrder);
                    validOptionSelected = true;
                    break;
                case 3:
                    createAnOrder.orderMenu();
                    validOptionSelected = true;
                    break;
                case 4:
                    System.out.println("> Exiting Inventory Manager...");
                    System.exit(0);
                    break;

                default:
                    System.out.println("> Invalid choice. Please try again.");
            }
        }
    }

    public void printUpdateInventory() {
        // Prints the update inventory menu
        System.out.printf("+-------------------------------------------------+\n");
        System.out.printf("|    UPDATE INVENTORY.                            |\n");
        System.out.printf("+-------------------------------------------------+\n");
        System.out.printf("| %-2s. %-43s |\n", "1", "Add product to inventory");
        System.out.printf("| %-2s. %-43s |\n", "2", "Remove product from inventory");
        System.out.printf("| %-2s. %-43s |\n", "3", "Update quantity of a product");
        System.out.printf("| %-2s. %-43s |\n", "4", "Go back");
        System.out.printf("+-------------------------------------------------+\n");
    }

    public void printProductOptions() { // ADD OPTION TO GO BACK

        System.out.printf("+-------------------------------------------------+\n");
        System.out.printf("|    ADD PRODUCT.                                 |\n");
        System.out.printf("+-------------------------------------------------+\n");
        System.out.printf("| %-2s. %-43s |\n", "1", "Add Car Tires");
        System.out.printf("| %-2s. %-43s |\n", "2", "Add Car Side Mirror");
        System.out.printf("| %-2s. %-43s |\n", "3", "Add Car Engine");
        System.out.printf("| %-2s. %-43s |\n", "4", "Add Car Head Lights");
        System.out.printf("| %-2s. %-43s |\n", "5", "Go back to menu");
        System.out.printf("+-------------------------------------------------+\n");

    }

    public void updateInventoryMenu(Inventory inventory, CreateOrder createAnOrder) {
        // Gets the user input for the update inventory menu 
        boolean validOptionSelected = false;
        while (!validOptionSelected) {
            printUpdateInventory();

            System.out.println("Select an option: ");
            int updateInventoryOption;
            try {
                updateInventoryOption = scan.nextInt();
            } catch (InputMismatchException e) {
                // Handle non-integer input
                System.out.println("> Invalid input. Please enter a number.");
                scan.nextLine(); // Consume the invalid input
                continue;
            }

            switch (updateInventoryOption) {
                case 1:
                    printProductOptions();
                    inventory.addProduct();
                    validOptionSelected = true;
                    break;
                case 2:
                    inventory.removeProduct();
                    validOptionSelected = true;
                    break;
                case 3:
                    inventory.adjustQuantity();
                    validOptionSelected = true;
                    break;
                case 4:
                    System.out.println("> Back to menu.\n");
                    userMenu(inventory, createAnOrder);
                    validOptionSelected = true;
                    break;
                default:
                    System.out.println("> Invalid choice. Please try again.");
            }
        }
    }

    public static void main(String[] args) {

        LogIn logIn = new LogIn();

        logIn.logInInterface();
        String currentUser = logIn.getCurrentUser();

        Inventory inventory = new Inventory(currentUser); // calls default constructor to acces methods
        CreateOrder createAnOrder = new CreateOrder(currentUser);

        UserService userServ = new UserService(); // calls default constructor to acces methods
        userServ.startProgram(userServ, inventory, createAnOrder);
    }
}
