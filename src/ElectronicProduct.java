// Pärilus: elektroonikatoode kasutab Product-klassi ühist loogikat.
public class ElectronicProduct extends Product {
    public ElectronicProduct(String name, double price, int quantity) {
        // Nime, hinna ja koguse salvestab vanemklassi konstruktor.
        super(name, price, quantity);
    }

    // Sama meetodinimi kui toidukaubal, kuid erinev tulemus.
    @Override
    public String getExtraInfo() {
        return "Elektroonika: hoia kuivas";
    }
}
