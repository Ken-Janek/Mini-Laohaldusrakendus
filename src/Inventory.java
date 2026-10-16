// Faili eesmärk: hoida toodete loendit, lisada ja otsida tooteid ning arvutada lao koguväärtust.
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    // Kasvav loend mahutab mõlemat liiki tooteid ühise Product-tüübi kaudu.
    // ArrayList kasvab lisamisel: toodete arvu ei pea ette teadma.
    // Väikese lao jaoks on see lihtne valik, sest lisamine ja tsükliga
    // läbimine on arusaadavad ning järjestikune otsing on piisavalt kiire.
    private final ArrayList<Product> products = new ArrayList<>();

    // Ühine lisamiskoht tagab, et sama nime kontroll kehtib kõigi tooteliikide jaoks.
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
        // Kutsuja ei saa koopia kaudu lao algsest loendist tooteid eemaldada.
        // Toote kogust saab endiselt muuta selle objekti changeQuantity() kaudu.
        return new ArrayList<>(products);
    }

    public Product findByName(String name) {
        // Otsing on O(n): halvimal juhul vaadatakse läbi kõik n toodet.
        // Tagastame tooteobjekti, et sama otsing sobiks nii kuvamiseks kui muutmiseks.
        // Vaatame tooted järjest läbi, kuni leiame sobiva nime.
        for (Product product : products) {
            // Võrdlus ei erista suur- ja väiketähti.
            // Näiteks "Piim" ja "piim" annavad vaste; osalist nime ei otsita.
            if (product.getName().equalsIgnoreCase(name.trim())) {
                return product;
            }
        }
        return null; // Ühegi toote nimi ei sobinud.
    }

    // Iga toode arvutab oma väärtuse ja ladu liidab need kokku.
    // Näiteks 10 toodet hinnaga 1.50 annavad summale juurde 15.00 eurot.
    public double getTotalValue() {
        double total = 0; // Alustame summat nullist.
        for (Product product : products) {
            total += product.getValue(); // Liidame iga toote laoväärtuse summale.
        }
        return total;
    }
}
