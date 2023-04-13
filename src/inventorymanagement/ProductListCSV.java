package InvenMan;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductListCSV {

    private final String file;
    private List<String> fileContent;

    public ProductListCSV() {
        //file stores path to CSV file that stores the data for each product in a table
        this.file = "C:\\Users\\Gorilla Rig\\OneDrive - AUT University\\Documents\\NetBeansProjects\\InventoryManagementSystems\\resources\\ProductListFile.csv";

        //fileContent will store the products details 
        fileContent = new ArrayList<>();

        BufferedReader reader = null;
        String line = "";

        //this will initialize the fileContent List storing the data of products 
        try {
            //this boolean only has one purpose. It just skips the title line of the CSV file
            //Since only want to store the Product details
            boolean skipLine = false;
            reader = new BufferedReader(new FileReader(this.file));
            while ((line = reader.readLine()) != null) {

                String[] row = line.split(",");
                if (skipLine) {
                    for (String index : row) {
                        fileContent.add(index);
                    }
                }
                skipLine = true;

            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(ProductListCSV.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    //uses Buffered Reader to read each line 
//and stores in  to String array then prints each row out
    public void printInventory() {

        BufferedReader reader = null;
        //String line reads each line of the file
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(this.file));
            while ((line = reader.readLine()) != null) {

                String[] row = line.split(",");
                for (String s : row) {
                    //prints out each row in a specific format
                    System.out.printf("%-13s", s);
                }

                System.out.println();
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(ProductListCSV.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //MAIN: declare and defines new ProductListCSV class and uses printInventory() method
    //also prints out the contents of the fileContent ArrayList to show what is stored in it
    
    public static void main(String[] args) {

        ProductListCSV prodList = new ProductListCSV();

        prodList.printInventory();
        System.out.println(prodList.fileContent.toString());

    }

}
