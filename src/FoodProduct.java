public class FoodProduct extends Product {
    public FoodProduct(String name, double price, int quantity) {
        super(name, price, quantity);
    }

    @Override
    public String getExtraInfo() {
        return "Toidukaup: kontrolli säilivusaega";
    }
}
