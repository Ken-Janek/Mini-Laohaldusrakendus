import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private final ArrayList<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Toode puudub.");
        }
        if (findByName(product.getName()) != null) {
            throw new IllegalArgumentException("Sama nimega toode on juba olemas.");
        }
        products.add(product);
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public Product findByName(String name) {
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(name.trim())) {
                return product;
            }
        }
        return null;
    }

    public double getTotalValue() {
        double total = 0;
        for (Product product : products) {
            total += product.getValue();
        }
        return total;
    }
}
