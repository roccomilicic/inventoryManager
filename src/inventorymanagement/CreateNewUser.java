package inventorymanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CreateNewUser {

    private String fileName;

    public CreateNewUser() {
        this.fileName = "resources/UserDataBase.txt";
    }

    public void createUser() {
        // Get user input for user details
        Scanner scan = new Scanner(System.in);
        System.out.println("\nEnter Username: ");
        String username = scan.nextLine();

        if (checkUserAvailability(username)) {
            // Write username and password to the UserDataBase.txt file
            try {
                createUserFolders(username);

                System.out.println("Enter Password: ");
                String password = scan.nextLine();

                FileWriter writer = new FileWriter(fileName, true);
                writer.write(username + "," + password + "\n");
                writer.close();
                System.out.println("> User '" + username + "' created successfully!\n");
            } catch (IOException e) {
                System.out.println("> Error writing to user database file.\n");
            }
        } else {
            createUser();
        }
    }

    private boolean checkUserAvailability(String username) {
        // Checks is username input is available
        boolean userAvailable = true;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                String[] userData = line.split(",");
                if (userData[0].equals(username)) {
                    userAvailable = false; // Username already exists
                    System.out.println("> This user already exists. You must choose a different username.");
                    break;
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("> Error verifying user details.");
        }
        return userAvailable; // Username does not exist so it can be created
    }
    
    public void createUserFolders(String username) {
        // Create user inventory folder in UserProfile
        File userFolder = new File("UserProfiles/" + username);
        userFolder.mkdir();
        // Create users order folder in Orders
        File orderFolder = new File("UserProfiles/" + username + "/Orders/");
        orderFolder.mkdir();
    }
}
