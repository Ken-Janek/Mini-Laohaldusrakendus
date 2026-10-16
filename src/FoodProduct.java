// Pärilus: toidutoode saab Product-klassi väljad ja ühised meetodid.
public class FoodProduct extends Product {
    public FoodProduct(String name, double price, int quantity) {
        // Kutsume vanemklassi konstruktorit andmete kontrollimiseks ja salvestamiseks.
        super(name, price, quantity);
    }

    // Anname abstraktsele meetodile toidukauba jaoks sobiva teostuse.
    @Override
    public String getExtraInfo() {
        return "Toidukaup: kontrolli säilivusaega";
    }
}
