package inventorymanagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CarHeadLights extends CarProduct {
    
     public CarHeadLights(int productId, String model, String brand, String type, double price) {
        this.productID = productId;
        this.productModel = model;
        this.productBrand = brand;
        this.productType = type;
        this.price = price;

    }

    public CarHeadLights(int productId, String model, String brand, String type, double price, int quantity) {
        this.productID = productId;
        this.productModel = model;
        this.productBrand = brand;
        this.productType = type;
        this.price = price;
        this.quantity = quantity;

    }

    public CarHeadLights() {
        try {
            prodList = setHeadLightsArrayList();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static ArrayList<CarProduct> setHeadLightsArrayList() throws IOException {
        ArrayList<CarProduct> headLightsList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("CarProducts/CarHeadLights.txt"));
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
            CarHeadLights headLights = new CarHeadLights(productId, model, brand, type, price);
            headLightsList.add(headLights);
        }
        reader.close();
        return headLightsList;
    }

    @Override
    public String toString() {
        String output = "+-----------------------------------------------------------------+\n";
        output += "|    CAR SIDE MIRROR LIST.                                        |\n";
        output += "+--------+----------------+--------------+---------+--------------+\n";
        output += "| ID     | PRODUCT NAME   |    BRAND     |  PRICE  |      TYPE    |\n";
        output += "+--------+----------------+--------------+---------+--------------+\n";

        for (CarProduct product : prodList) {
            if (product instanceof CarHeadLights) {
                CarHeadLights headLights = (CarHeadLights) product;

                output += String.format("| %-6d | %-14s | %-12s | $%6.2f | %-12s |\n",
                        headLights.productID, headLights.productModel, headLights.productBrand, headLights.price, headLights.productType);
            }
        }

        output += "+-----------------------------------------------------------------+\n";
        return output;
    }

    public CarProduct getHeadLightsProductByID(int headLightsID) {

        CarProduct selectedHeadLights = new CarHeadLights();

        for (CarProduct headLights : prodList) {
            if (headLights.productID == headLightsID) {
                selectedHeadLights = headLights;
            }
        }

        return selectedHeadLights;
    }
    
}
