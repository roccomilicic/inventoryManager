package InvenMan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class LogIn {

    private String userName;
    private String password;
    private String fileName;

    public LogIn() {

        this.fileName = "C:\\Users\\Gorilla Rig\\OneDrive - AUT University\\Documents\\NetBeansProjects\\InventoryManagementSystems\\resources\\UserDataBase.txt";

    }

    public void logInInterface() {
        Scanner scan = new Scanner(System.in);

        System.out.println("1) Login\n2) Create new User");

        String loginOption = scan.nextLine();

        if (loginOption.equals("1")) {

            System.out.println("Enter Username: ");

            String username = scan.nextLine();

            System.out.println("Enter Password: ");
            String password = scan.nextLine();

            // Check if username and password are valid
            if (checkCredentials(username, password)) {
                System.out.println("Login successful!");
                // Do something else here, like open the main menu
            } else {
                System.out.println("Incorrect username or password.");
                logInInterface(); // Loop back to the beginning
            }

        } else if (loginOption.equals("2")) {
            // Code for creating a new user
            CreateNewUser userCreation = new CreateNewUser();
            userCreation.createUser();
            logInInterface(); // Loop back to the beginning
            System.out.println("Creating a new user...");
        } else {
            System.out.println("Invalid option.");
            logInInterface(); // Loop back to the beginning
        }

    }

    private boolean checkCredentials(String username, String password) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                String[] userData = line.split(",");
                if (userData.length == 2 && userData[0].equals(username) && userData[1].equals(password)) {
                    reader.close();
                    return true; // Username and password are valid
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading user database file.");
        }
        return false; // Username and/or password is invalid
    }

}
