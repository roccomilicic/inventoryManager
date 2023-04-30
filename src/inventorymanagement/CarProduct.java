package inventorymanagement;

import java.util.ArrayList;

public abstract class CarProduct {

    protected int productID;
    protected String productModel;
    protected String productBrand;
    protected String productType;
    protected double price;
    protected int quantity;

    public ArrayList<CarProduct> prodList;

    public CarProduct getProductByID(int prodID) {
        CarProduct selectedTire = new CarTire();

        for (CarProduct tire : prodList) {
            if (tire.productID == prodID) {
                selectedTire = tire;
            }
        }

        return selectedTire;
    }

    public void addQuantity(int quantity) { // adds onto the quantity of a product in inventory
        this.quantity += quantity;
    }
    public int getProductId() {
        return this.productID;
    }

    public void setProductId(int prodId) {
        this.productID = prodId;
    }

    public String getProductModel() {
        return this.productModel;
    }

    public void setProductModel(String prodModel) {
        this.productModel = prodModel;
    }

    public String getProductBrand() {
        return this.productBrand;
    }

    public void setProductBrand(String prodBrand) {
        this.productBrand = prodBrand;
    }
    
      public String getProductType() {
        return this.productType;
    }

    public void setProductType(String prodType) {
        this.productType = prodType;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quant) {
        this.quantity = quant;
    }
}
