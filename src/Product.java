public abstract class Product {
    private final String name;
    private final double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nimi ei tohi olla tühi.");
        }
        if (!Double.isFinite(price) || price < 0 || quantity < 0) {
            throw new IllegalArgumentException("Hind ja kogus peavad olema mittenegatiivsed.");
        }
        this.name = name.trim();
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public void changeQuantity(int change) {
        long newQuantity = (long) quantity + change;
        if (newQuantity < 0 || newQuantity > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Uus kogus peab jääma vahemikku 0 kuni " + Integer.MAX_VALUE);
        }
        quantity = (int) newQuantity;
    }

    public double getValue() { return price * quantity; }

    public abstract String getExtraInfo();

    @Override
    public String toString() {
        return String.format(java.util.Locale.ROOT,
                "%s | %.2f EUR | kogus: %d | %s", name, price, quantity, getExtraInfo());
    }
}
