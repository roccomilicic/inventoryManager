package inventorymanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

//The CreateNewUser class is responsible for the operations involved in 
//creating a new user. These operations include reading and writing to a file
public class CreateNewUser {

    private String fileName;

    //initializes the fileName field with a String representing the file path
    //to UserDataBase text file which stores all the username-password records
    //in a Comma Seperated Value format
    public CreateNewUser() {
        this.fileName = "resources/UserDataBase.txt";
    }

    //createUser Method prompts user for username and password and checks them
    //to see if they are valid inputs
    public void createUser() {
        //get user input for user details
        Scanner scan = new Scanner(System.in);
        System.out.println("\nEnter Username: ");
        String username = scan.nextLine();

        if (checkUserAvailability(username)) {
            //write username and password to the UserDataBase txt file
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

    //checkUserAvailability returns true if the username enterd by the user is available
    //as in there is no other matching username on file or returns false if the username
    //already exists
    private boolean checkUserAvailability(String username) {
        //checks if username input is available
        boolean userAvailable = true;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                String[] userData = line.split(","); //splits the line String up by commas e.g. "username,password" 
                                                          //becomes two Strings "username" and "password"
                                                          
                if (userData[0].equals(username)) {
                    userAvailable = false; //username already exists
                    System.out.println("> This user already exists. You must choose a different username.");   
                    break;
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("> Error verifying user details.");
        }
        return userAvailable; //returns true if username available false if username taken
    }

    //createUserFolders() method is responsible for creating default folders for the user once the user has been created
    //these folders are the Users Main folder and stored within it is their Orders Folder and in the future their
    //Inventory text files which stores the data of their current inventory
    public void createUserFolders(String username) {
        //creates users inventory folder in UserProfile folder
        File userFolder = new File("UserProfiles/" + username);
        userFolder.mkdir();
        //create users order folder in Orders folder
        File orderFolder = new File("UserProfiles/" + username + "/Orders/");
        orderFolder.mkdir();
    }
}
