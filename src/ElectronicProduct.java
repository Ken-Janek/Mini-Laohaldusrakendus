public class ElectronicProduct extends Product {
    public ElectronicProduct(String name, double price, int quantity) {
        super(name, price, quantity);
    }

    @Override
    public String getExtraInfo() {
        return "Elektroonika: hoia kuivas";
    }
}
