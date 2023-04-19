package inventorymanagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class LogIn {

    private String userName;
    private String password;
    private String fileName;

    public LogIn() {
        this.fileName = "src\\inventorymanagement\\UserDataBase.txt";
    }

    public void logInInterface() {
        Scanner scan = new Scanner(System.in);

        System.out.println("1) Login\n2) Create new User\n3) Exit program");

        String loginOption = scan.nextLine();

        if (loginOption.equals("1")) {

            System.out.println("\nEnter Username: ");

            String username = scan.nextLine();

            System.out.println("Enter Password: ");
            String password = scan.nextLine();

            if (checkCredentials(username, password)) { // Check if username and password are valid
                System.out.println("> Login successful!\n");
                // Do something else here, like open the main menu
            } else {
                System.out.println("> Incorrect username or password.\n");
                logInInterface(); // Loop back to the beginning
            }

        } else if (loginOption.equals("2")) {
            // Code for creating a new user
            CreateNewUser userCreation = new CreateNewUser();
            userCreation.createUser();
            logInInterface(); // Loop back to the beginning
        } else if (loginOption.equals("3")) {
            System.exit(0);
            System.out.println("> Exiting Inventory Manager...")
        } else {
            System.out.println("> Invalid option.");
            logInInterface(); // Loop back to the beginning of LogIn
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
            System.out.println("> Error reading user database file.");
        }
        return false; // Username and/or password is invalid
    }
}
