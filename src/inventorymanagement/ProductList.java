package inventorymanagement;

/**
 *
 * @author Rocco + Beedrix
 */

import java.util.ArrayList;

public class ProductList {
    private ArrayList<Product> products;

    public ProductList() {
        this.products = new ArrayList<Product>();
    }
    
    public void generateProducts() {
        // Adds products to product list for user to then later add to inventory
        products.add(new Product(1, "Apples", 0.60));
        products.add(new Product(2, "Banana", 0.65));
        products.add(new Product(3, "Orange", 0.70));
        products.add(new Product(4, "Avocado", 1.10));
        products.add(new Product(5, "Kiwifruit", 0.85));
        products.add(new Product(6, "Feijoa", 0.55));
        products.add(new Product(7, "Pineapple", 4.20));
        products.add(new Product(8, "Tomato", 0.40));
        products.add(new Product(9, "Coconut", 4.50));
        products.add(new Product(10, "Pear", 0.75));
    }

    public Product getProductById(int productId) {
    // To search for a product object with just the ID (to add to inventory)
        for (Product prod : products) {
            if (prod.getProductID() == productId) {
                return prod;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String output = "╔══════════════════════════════╗";
        output += "\n║                  PRODUCT LIST:                 ║";
        output += ("\n╚══════════════════════════════╝\n");
        
        for (Product product : products) {
            output += "ID: " + product.getProductID() + "       PRODUCT: " + product.getProductName() + "       PRICE: " + product.getPrice() + "\n";
        }

        return output;
    }
}
