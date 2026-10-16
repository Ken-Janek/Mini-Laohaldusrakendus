// Faili eesmärk: kirjeldada kõigi toodete ühiseid andmeid ja toiminguid.
// Abstraktsioon: ühine tooteklass, millest otse objekti luua ei saa.
public abstract class Product {
    // Kapseldamine: private välju kasutatakse selle klassi meetodite kaudu.
    // final tähendab, et nimi ja hind pärast loomist ei muutu.
    private final String name;
    private final double price;
    private int quantity;

    // Konstruktor käivitatakse uue toote loomisel.
    public Product(String name, double price, int quantity) {
        // null tähendab puuduvat väärtust; trim eemaldab servadest tühikud.
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nimi ei tohi olla tühi.");
        }
        // Keelame ka NaN-i ja lõpmatuse, mis ei sobi hinnaks.
        if (!Double.isFinite(price) || price < 0 || quantity < 0) {
            throw new IllegalArgumentException("Hind ja kogus peavad olema mittenegatiivsed.");
        }
        // this viitab loodava objekti väljale, parem pool parameetrile.
        this.name = name.trim();
        this.price = price;
        this.quantity = quantity;
    }

    // Getterid lubavad väljade väärtusi lugeda.
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    // Positiivne muutus lisab kaupa, negatiivne võtab seda vähemaks.
    public void changeQuantity(int change) {
        // long väldib int-vahetulemuse ületäitumist enne kontrolli.
        long newQuantity = (long) quantity + change;
        if (newQuantity < 0 || newQuantity > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Uus kogus peab jääma vahemikku 0 kuni " + Integer.MAX_VALUE);
        }
        // Pärast kontrolli mahub tulemus int-tüüpi.
        quantity = (int) newQuantity;
    }

    // Selle toote kogu laoväärtus: ühe ühiku hind korda kogus.
    public double getValue() { return price * quantity; }

    // Alamklass peab sellele meetodile oma teostuse andma.
    public abstract String getExtraInfo();

    // Asendame Object-klassi toString-meetodi loetava tootekirjeldusega.
    @Override
    public String toString() {
        // %s on tekst, %.2f kahe komakohaga arv ja %d täisarv.
        // Polümorfism: getExtraInfo teostus valitakse tegeliku tooteliigi järgi.
        return String.format(java.util.Locale.ROOT,
                "%s | %.2f EUR | kogus: %d | %s", name, price, quantity, getExtraInfo());
    }
}
