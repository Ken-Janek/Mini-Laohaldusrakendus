// Faili eesmärk: hoida toodete loendit, lisada ja otsida tooteid ning arvutada lao koguväärtust.
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    // Kasvav loend mahutab mõlemat liiki tooteid ühise Product-tüübi kaudu.
    private final ArrayList<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        // Puuduvat toodet ei saa lattu lisada.
        if (product == null) {
            throw new IllegalArgumentException("Toode puudub.");
        }
        // Otsingu vaste tähendab, et sama nimi on juba kasutusel.
        if (findByName(product.getName()) != null) {
            throw new IllegalArgumentException("Sama nimega toode on juba olemas.");
        }
        products.add(product); // Lisame kontrollitud toote loendi lõppu.
    }

    public List<Product> getProducts() {
        // Tagastame loendi koopia; selles on viited samadele tooteobjektidele.
        return new ArrayList<>(products);
    }

    public Product findByName(String name) {
        // Vaatame tooted järjest läbi, kuni leiame sobiva nime.
        for (Product product : products) {
            // Võrdlus ei erista suur- ja väiketähti.
            if (product.getName().equalsIgnoreCase(name.trim())) {
                return product;
            }
        }
        return null; // Ühegi toote nimi ei sobinud.
    }

    public double getTotalValue() {
        double total = 0; // Alustame summat nullist.
        for (Product product : products) {
            total += product.getValue(); // Liidame iga toote laoväärtuse summale.
        }
        return total;
    }
}
