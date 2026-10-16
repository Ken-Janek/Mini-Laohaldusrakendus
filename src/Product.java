// Faili eesmärk: kirjeldada kõigi toodete ühiseid andmeid ja toiminguid.
// Abstraktsioon: Product koondab kõigile toodetele ühised andmed ja toimingud.
// abstract tähendab, et new Product(...) pole lubatud: luua tuleb konkreetse
// alamklassi objekt, näiteks FoodProduct. Nii saab ladu kasutada ühiseid
// meetodeid, ilma et peaks teadma iga tooteliigi eripära.
public abstract class Product {
    // Kapseldamine: private keelab teistel klassidel välju otse lugeda ja muuta.
    // Lugemiseks on getterid ja koguse muutmiseks kontrolliga changeQuantity().
    // Nii ei saa muu kood määrata koguseks näiteks -5 ja kontrollist mööda minna.
    // final tähendab, et nimi ja hind pärast loomist ei muutu.
    private final String name; // String hoiab teksti, näiteks "Piim".
    // double võimaldab komakohaga hinda, näiteks 1.50.
    // Täpseks rahaarvestuseks sobiks paremini BigDecimal või täisarvulised sendid:
    // double võib arvutamisel anda väikeseid ümardusvigu.
    private final double price;
    private int quantity; // int sobib tervete ühikute arvuks, näiteks 10.

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
    // Näiteks kogus 4 ja muutus -5 annavad vea; kogus jääb endiselt neljaks.
    // Kontroll toimub siin, et kõik selle meetodi kasutajad järgiksid sama reeglit.
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

    // Abstraktne meetod määrab nõude: igal tooteliigil peab olema lisainfo.
    // Siin puudub meetodi keha, sest täpse teksti määrab iga alamklass ise.
    public abstract String getExtraInfo();

    // Asendame Object-klassi toString-meetodi loetava tootekirjeldusega.
    @Override
    public String toString() {
        // %s on tekst, %.2f kahe komakohaga arv ja %d täisarv.
        // Polümorfism: sama getExtraInfo() kutse töötab eri tooteliikidega.
        // Java valib tegeliku objekti järgi FoodProduct või ElectronicProduct
        // teostuse. Seega pole siin vaja tooteliiki kontrollivaid if-tingimusi.
        return String.format(java.util.Locale.ROOT,
                "%s | %.2f EUR | kogus: %d | %s", name, price, quantity, getExtraInfo());
    }
}
