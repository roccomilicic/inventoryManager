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
        //Inventory inventory = = new Inventory();
        this.productList = new ProductList(); // to call product methods
    }

    public void startProgram(UserService userServ, Inventory inventory) {
        /*Starts program by calling all the needed methods before user interacts
        with the CUI. Then runs a loop on the mneu till user exits*/

        productList.generateProducts();

         while (true) { // loops menu till user quits the program
            userServ.userMenu(inventory); // starts off the program (would come after user login once implemented)
        }
    }

    public void printUserMenu() {
        // Prints the user menu
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║               INVENTORY MANAGER                ║");
        System.out.println("╠══════════════════════════════╣");
        System.out.println("║ 1. View inventory                              ║");
        System.out.println("║ 2. Print inventory to TXT file                 ║");
        System.out.println("║ 3. Update inventory                            ║");
        System.out.println("║ 4. Exit program                                ║");
        System.out.println("╚══════════════════════════════╝");
    }

    public void userMenu(Inventory inventory) {
        // Gets the user input for the update inventory menu
        printUserMenu();
        System.out.println("SELECT: ");
        int selectedMenuOption = scan.nextInt();
       
        switch (selectedMenuOption) {
            case 1:
                System.out.println(inventory.toString());
                break;
            case 2:
                inventory.inventoryToTxt();
                break;
            case 3:
                updateInventoryMenu(inventory);
                break;
            case 4:
                System.out.println("> Exiting Inventory Manager...");
                System.exit(0);
                break;
            default:
                System.out.println("> Invalid choice. Please try again.");
        }
    }

    public void printUpdateInventory() {
        // Prints the update inventory menu
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║               UPDATE INVENTORY                 ║");
        System.out.println("╠══════════════════════════════╣");
        System.out.println("║ 1. Add product to inventory                    ║");
        System.out.println("║ 2. Remove product from inventory               ║");
        System.out.println("║ 3. Update quantity of a product                ║");
        System.out.println("║ 4. Go back                                     ║");
        System.out.println("╚══════════════════════════════╝");
    }

    public void updateInventoryMenu(Inventory inventory) {
        // Gets the user input for the update inventory menu 
        printUpdateInventory();

        System.out.println("SELECT: ");
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
                System.out.println("> Back to menu");
                userMenu(inventory);
                break;
            default:
                System.out.println("> Invalid choice. Please try again.");
        }
    }

    public static void main(String[] args) {
        Inventory inventory = new Inventory(); // calls default constructor to acces methods
        //Product product = new Product(); // calls default constructor to acces methods
        UserService userServ = new UserService(); // calls default constructor to acces methods

        userServ.startProgram(userServ, inventory);        
    }
}
