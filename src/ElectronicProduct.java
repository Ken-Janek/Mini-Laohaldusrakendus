// Faili eesmärk: kirjeldada elektroonikatoodet ja anda sellele hoiustamise lisainfo.

// Pärilus: extends Product teeb ElectronicProductist Producti alamklassi.
// Ühised meetodid, näiteks getName() ja changeQuantity(), tulevad Productist.
// Private välju kasutame nende meetodite kaudu, mitte otse.
// Nii jääb ühine loogika ühte kohta ja siin kirjeldame elektroonika eripära.
public class ElectronicProduct extends Product {
    public ElectronicProduct(String name, double price, int quantity) {
        // Nime, hinna ja koguse salvestab vanemklassi konstruktor.
        super(name, price, quantity);
    }

    // Sama meetodinimi kui toidukaubal, kuid erinev tulemus.
    // @Override näitab, et teostame Productis kirjeldatud abstraktse meetodi.
    // Polümorfism: Product.toString() saab seda kutsuda ühisel viisil,
    // kuid elektroonikatoote puhul kuvatakse just kuivas hoidmise soovitus.
    @Override
    public String getExtraInfo() {
        return "Elektroonika: hoia kuivas";
    }
}
