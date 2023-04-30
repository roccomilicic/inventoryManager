package inventorymanagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CarEngine extends CarProduct {
    
     public CarEngine(int productId, String model, String brand, String type, double price) {
        this.productID = productId;
        this.productModel = model;
        this.productBrand = brand;
        this.productType = type;
        this.price = price;

    }

    public CarEngine(int productId, String model, String brand, String type, double price, int quantity) {
        this.productID = productId;
        this.productModel = model;
        this.productBrand = brand;
        this.productType = type;
        this.price = price;
        this.quantity = quantity;

    }

    public CarEngine() {
        try {
            prodList = setEngineArrayList();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static ArrayList<CarProduct> setEngineArrayList() throws IOException {
        ArrayList<CarProduct> carEngineList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("CarProducts/CarEngine.txt"));
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
            CarEngine carEngine = new CarEngine(productId, model, brand, type, price);
            carEngineList.add(carEngine);
        }
        reader.close();
        return carEngineList;
    }

    @Override
    public String toString() {
        String output = "+---------------------------------------------------------------------+\n";
        output += "|    CAR SIDE MIRROR LIST.                                            |\n";
        output += "+--------+----------------+---------------+------------+--------------+\n";
        output += "| ID     | PRODUCT NAME   |     BRAND     |    PRICE   |      TYPE    |\n";
        output += "+--------+----------------+---------------+------------+--------------+\n";

        for (CarProduct product : prodList) {
            if (product instanceof CarEngine) {
                CarEngine carEngine = (CarEngine) product;

                output += String.format("| %-6d | %-14s | %-13s | $%9.2f | %-12s |\n",
                        carEngine.productID, carEngine.productModel, carEngine.productBrand, carEngine.price, carEngine.productType);
            }
        }

        output += "+---------------------------------------------------------------------+\n";
        return output;
    }

    public CarProduct getEngineProductByID(int carEngineID) {

        CarProduct selectedEngine = new CarEngine();

        for (CarProduct carEngine : prodList) {
            if (carEngine.productID == carEngineID) {
                selectedEngine = carEngine;
            }
        }

        return selectedEngine;
    }
    
    /*public static void main(String[] args) {
        
        CarProduct c = new CarEngine();
        
        System.out.println(c.toString());
        
    }*/
}
