package inventorymanagent;

/**
 *
 * @author rocco
 */
import java.util.HashMap;

public class Inventory {

    private Product prodToAdd;
    private int productQuantity;
    private HashMap<int, Product> inventory;

    public Inventory() {
        this.inventory = new HashMap<int, Product>();
    }

    public Inventory(Product product) {
        this.prodToAdd = product;
        this.productToDelete = product;
        this.productToUpdate = product;
        this.inventory = new HashMap<int, Product>();
    }

    public Product addProduct(Product product) {
        inventory.put(prodToAdd.getProductID(), prodToAdd);
    }

    public Product removeProduct(Product product) {
        inventory.remove(prodToAdd.getProductID(), prodToAdd);
    }

    public Product updateProduct(Product product) {

    }

}
