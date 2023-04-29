package InvenMan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarTire extends CarProduct {


    public CarTire(int productId, String model, String brand, String type, double price) {
        this.productID = productId;
        this.productModel = model;
        this.productBrand = brand;
        this.productType = type;
        this.price = price;

    }
    
      public CarTire(int productId, String model, String brand, String type, double price, int quantity) {
        this.productID = productId;
        this.productModel = model;
        this.productBrand = brand;
        this.productType = type;
        this.price = price;
        this.quantity = quantity;

    }

    public CarTire() {
        try {
            prodList = setTireArrayList();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static ArrayList<CarProduct> setTireArrayList() throws IOException {
        ArrayList<CarProduct> tireList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("CarProducts/CarTire.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            if (fields[0].equals("Product ID")) { // skip the header row
                continue;
            }
            int productId = Integer.parseInt(fields[0]);
            String model = fields[1];
            String brand = fields[2];
            String type = fields[3];
            double price = Double.parseDouble(fields[4].substring(1)); // remove the $ symbol
            CarTire tire = new CarTire(productId, model, brand, type, price);
            tireList.add(tire);
        }
        reader.close();
        return tireList;
    }

    @Override
    public String toString() {
        String output = "+-----------------------------------------------------------------+\n";
        output += "|                          CAR TIRE LIST:                         |\n";
        output += "+--------+----------------+--------------+---------+--------------+\n";
        output += "| ID     | PRODUCT NAME   |    BRAND     |  PRICE  |      TYPE    |\n";
        output += "+--------+----------------+--------------+---------+--------------+\n";

        for (CarProduct product : prodList) {
            if (product instanceof CarTire) {
                CarTire tire = (CarTire) product;

                output += String.format("| %-6d | %-14s | %-12s | $%6.2f | %-12s |\n",
                        tire.productID, tire.productModel, tire.productBrand, tire.price, tire.productType);
            }
        }

        output += "+-----------------------------------------------------------------+\n";
        return output;
    }

    public CarProduct getTireProductByID(int tireID){
       
        CarProduct selectedTire = new CarTire();
           
        for(CarProduct tire : prodList){
            if(tire.productID == tireID){
                selectedTire = tire;
            }
        }
        
        return selectedTire;
    }
  
}
