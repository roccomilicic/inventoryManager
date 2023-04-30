package inventorymanagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CarSideMirror extends CarProduct {

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
        try {
            prodList = setSideMirrorArrayList();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static ArrayList<CarProduct> setSideMirrorArrayList() throws IOException {
        ArrayList<CarProduct> sideMirrorList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("CarProducts/CarSideMirror.txt"));
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
            CarSideMirror sideMirror = new CarSideMirror(productId, model, brand, type, price);
            sideMirrorList.add(sideMirror);
        }
        reader.close();
        return sideMirrorList;
    }

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

    public CarProduct getSideMirrorProductByID(int sideMirrorID) {

        CarProduct selectedSideMirror = new CarSideMirror();

        for (CarProduct sideMirror : prodList) {
            if (sideMirror.productID == sideMirrorID) {
                selectedSideMirror = sideMirror;
            }
        }

        return selectedSideMirror;
    }
}

