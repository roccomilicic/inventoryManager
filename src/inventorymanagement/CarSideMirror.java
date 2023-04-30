package inventorymanagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//CarSideMirror class is a subclass of CarProduct and is primarily responsible for reading from the CarSideMirrortxt file 
//to store the contents of that text file in a HashMap array to be used for toString method and other general methods
public class CarSideMirror extends CarProduct {

    //two constructors were created one for when no quantity is required usually when printing to menus 
    //and the other constructor requires quantity when a user is adding to inventory
    public CarSideMirror(int productId, String model, String brand, String type, double price) {
        this.productID = productId;
        this.productModel = model;
        this.productBrand = brand;
        this.productType = type;
        this.price = price;

    }

    public CarSideMirror(int productId, String model, String brand, String type, double price, int quantity) {
        this.productID = productId;
        this.productModel = model;
        this.productBrand = brand;
        this.productType = type;
        this.price = price;
        this.quantity = quantity;

    }

    public CarSideMirror() {
        try { //calls the setSideMirrorArrayList() method to initialize 
            //the prodList with all sideMirror products stored on file.
            prodList = setSideMirrorArrayList();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //setSideMirrorArrayList is a static method that initializes the prodList ArrayList which is used in the toString 
    //method to print out the menus options for the Car SideMirror products
    public static ArrayList<CarProduct> setSideMirrorArrayList() throws IOException {
        ArrayList<CarProduct> sideMirrorList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("CarProducts/CarSideMirror.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            if (fields[0].equals("Product ID")) { //skips the header row in the CarSideMirror txt file
                continue;
            }
            int productId = Integer.parseInt(fields[0]);
            String model = fields[1];
            String brand = fields[2];
            String type = fields[3];
            double price = Double.parseDouble(fields[4].substring(1)); // remove the $ symbol
            CarSideMirror sideMirror = new CarSideMirror(productId, model, brand, type, price);
            sideMirrorList.add(sideMirror);
        }
        reader.close();
        return sideMirrorList;
    }

    //toString method that prints out the contents of the prodList hashMap array in a formatted menu style
    @Override
    public String toString() {
        String output = "+-----------------------------------------------------------------+\n";
        output += "|    CAR SIDE MIRROR LIST.                                        |\n";
        output += "+--------+----------------+--------------+---------+--------------+\n";
        output += "| ID     | PRODUCT NAME   |    BRAND     |  PRICE  |      TYPE    |\n";
        output += "+--------+----------------+--------------+---------+--------------+\n";

        for (CarProduct product : prodList) {
            if (product instanceof CarSideMirror) {
                CarSideMirror sideMirror = (CarSideMirror) product;

                output += String.format("| %-6d | %-14s | %-12s | $%6.2f | %-12s |\n",
                        sideMirror.productID, sideMirror.productModel, sideMirror.productBrand, sideMirror.price, sideMirror.productType);
            }
        }

        output += "+-----------------------------------------------------------------+\n";
        return output;
    }

    //getSideMirrorProductByID method returns a Car SideMirror Product based on its ID using the prodList Hashmap
    @Override
    public CarProduct getProductByID(int sideMirrorID) {

        CarProduct selectedSideMirror = new CarSideMirror();

        for (CarProduct sideMirror : prodList) {
              //compares sideMirror.productID with the Input parameter sideMirrorID 
            if (sideMirror.productID == sideMirrorID) {
                selectedSideMirror = sideMirror; //assigns the sideMirror product if the ID's match
            }
        }

        return selectedSideMirror;
    }
}

