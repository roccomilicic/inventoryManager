package inventorymanagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

//LogIn class responsible for handling user LogIn interface. The first prompt 
//of the program. Prompts include: Login, Create new User, Exit. 
public class LogIn {

    private String userName;
    private String password;
    private String fileName;

    //LogIn constructor which initializes the fileName field with
    //the name of the path to the txt file that stores all the 
    //username-passwrods in a comma seperated value format.
    public LogIn() {
        this.fileName = "resources/UserDataBase.txt";
    }

    //the logInInterface() method is responsible for printing out
    //the prompts "Login", "Create new User" and "Exit" and also 
    //handling user input.
    public void logInInterface() {

        Scanner scan = new Scanner(System.in);

        System.out.println("1) Login\n2) Create new User\n3) Exit program");

        String loginOption = scan.nextLine();
        
        //if the userinput is more than one character it is considered invalid input
        //this block of code checks for that e.g. 23 is invalid
        if (loginOption.length() != 1) {
            System.out.println("> Invalid option. Please enter a valid option.");
            logInInterface();
            return;
        }
        
        //makes sure the userinput is one character
        char option = loginOption.charAt(0);

        //block of if-else statements that run the program according
        //to user input.
        if (option == '1') {
            //if the login option is 1 then run the "Login" process

            System.out.println("\nEnter Username: ");

            String username = scan.nextLine(); //retrieve user input for userName
            this.userName = username; //initializes this.userName with user input
            System.out.println("Enter Password: ");
            String password = scan.nextLine();

            if (checkCredentials(username, password)) { // Check if username and password are valid
                System.out.println("> Login successful!\n");
                //Method ends when user input is valid and returns to the caller in this case the main method
            } else {
                System.out.println("> Incorrect username or password.\n");
                logInInterface(); //loop back to the beginning
            }

        } else if (option == '2') {
            //if the login option is 2 the run the "Create new User" process

            CreateNewUser userCreation = new CreateNewUser();
            userCreation.createUser();
            logInInterface(); //loop back to the beginning
        } else if (option == '3') {
            //if the option is 3 the user chose "Exit" and the program terminates

            System.exit(0);//program exits
            System.out.println("> Exiting Inventory Manager...");
        } else {
            //if the user input does not correspond with any of the options
            //prints out message and loops to beginning

            System.out.println("> Invalid option.");
            logInInterface(); //loop back to the beginning of LogIn
        }
    }

    //getCurrentUser() method returns the current users username for the program
    //to identify which user is using the program and access the correct files
    public String getCurrentUser() {
        return this.userName;
    }

    //checkCredentials() method reads from the UserDataBase txt file, and
    //returns true if the username and password entered by the user match
    //any of the records we have on file in the UserDataBase txt file
    private boolean checkCredentials(String username, String password) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            String line = reader.readLine();//String line stores the first line in the UserDataBase txt file
            //while the line is not empty meaning while the UserDataBase txt file is not empty and while the reader
            //has not reached the end of the file (where the newLine is null)

            while (line != null) {
                String[] userData = line.split(","); //Usernames and Passwords are stored as Comma Seperated Values 
                //so they are split here and stored as two Strings in the userData String Array
                if (userData.length == 2 && userData[0].equals(username) && userData[1].equals(password)) {
                    //if the userData string array has two Strings stored and username and password match 
                    //these Strings then this method returns true 
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
