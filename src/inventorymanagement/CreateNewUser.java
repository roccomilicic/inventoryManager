package inventorymanagement;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CreateNewUser {

    private String fileName;

    public CreateNewUser() {

        this.fileName = "C:\\Users\\Gorilla Rig\\OneDrive - AUT University\\Documents\\NetBeansProjects\\InventoryManagementSystems\\resources\\UserDataBase.txt";

    }

    public void createUser() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter Username: ");
        String username = scan.nextLine();

        System.out.println("Enter Password: ");
        String password = scan.nextLine();

        // Write username and password to the UserDataBase.txt file
        try {
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(username + "," + password + "\n");
            writer.close();
            System.out.println("User " + username + " created successfully!");
        } catch (IOException e) {
            System.out.println("Error writing to user database file.");
        }
    }
}
