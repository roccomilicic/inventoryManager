package inventorymanagement;

/**
 *
 * @author Rocco + Beedrix
 */

import java.util.Scanner;

public class UserService {

    Scanner scan = new Scanner(System.in);
    private ProductList productList;
    //private Inventory inventory;
    //private UserService userServ;

    public UserService() {
        this.productList = new ProductList(); // to call productList methods
    }

    public void startProgram(UserService userServ, Inventory inventory) {
        /*Starts program by calling all the needed methods before user interacts
        with the CUI. Then runs a loop on the menu till user exits*/

        productList.generateProducts();
        while (true) { // loops menu till user quits the program
            userServ.userMenu(inventory); // starts off the program (would come after user login once implemented)
        }
    }

    public static void printUserMenu() {
        // Prints the user menu
        System.out.printf("+-------------------------------------------------+\n");
        System.out.printf("|                  INVENTORY MANAGER              |\n");
        System.out.printf("+-------------------------------------------------+\n");
        System.out.printf("| %-2s. %-42s  |\n", "1", "View inventory");
        System.out.printf("| %-2s. %-42s  |\n", "2", "Update inventory");
        System.out.printf("| %-2s. %-42s  |\n", "3", "Exit program");
        System.out.printf("+-------------------------------------------------+\n");
    }

    public void userMenu(Inventory inventory) {
        // Gets the user input for the update inventory menu

        printUserMenu();
        System.out.println("Select an option: ");
        int selectedMenuOption = scan.nextInt();

        switch (selectedMenuOption) {
            case 1:
                System.out.println(inventory.toString());
                break;
            case 2:
                updateInventoryMenu(inventory);
                break;
            case 3:
                System.out.println("> Exiting Inventory Manager...");
                System.exit(0);
                break;

            default:
                System.out.println("> Invalid choice. Please try again.");
        }
    }

    public void printUpdateInventory() {
        // Prints the update inventory menu
        System.out.printf("+-------------------------------------------------+\n");
        System.out.printf("|                  UPDATE INVENTORY               |\n");
        System.out.printf("+-------------------------------------------------+\n");
        System.out.printf("| %-2s. %-43s |\n", "1", "Add product to inventory");
        System.out.printf("| %-2s. %-43s |\n", "2", "Remove product from inventory");
        System.out.printf("| %-2s. %-43s |\n", "3", "Update quantity of a product");
        System.out.printf("| %-2s. %-43s |\n", "4", "Go back");
        System.out.printf("+-------------------------------------------------+\n");
    }

    public void updateInventoryMenu(Inventory inventory) {
        // Gets the user input for the update inventory menu 
        printUpdateInventory();

        System.out.println("Select an option: ");
        int updateInventoryOption = scan.nextInt();

        switch (updateInventoryOption) {
            case 1:
                System.out.println(productList.toString()); // displays products available to add to inventory
                inventory.addProduct(productList);
                break;
            case 2:
                inventory.removeProduct(productList);
                break;
            case 3:
                inventory.adjustQuantity(productList);
                break;
            case 4:
                System.out.println("> Back to menu.\n");
                userMenu(inventory);
                break;
            default:
                System.out.println("> Invalid choice. Please try again.");
        }
    }

    public static void main(String[] args) {

        LogIn logIn = new LogIn();

        logIn.logInInterface();
        String currentUser = logIn.getCurrentUser();

        Inventory inventory = new Inventory(currentUser); // calls default constructor to acces methods
        //UserProfile currentUserProfile = new UserProfile(currentUser, inventory);

        UserService userServ = new UserService(); // calls default constructor to acces methods
        userServ.startProgram(userServ, inventory);
    }
}

