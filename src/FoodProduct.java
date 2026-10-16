// Faili eesmärk: kirjeldada toidutoodet ja anda sellele toidukauba lisainfo.

// Pärilus: extends Product tähendab, et FoodProduct on Producti alamklass.
// See saab kasutada ühiseid meetodeid, näiteks getName() ja changeQuantity().
// Producti private väljadele pääseb ligi nende meetodite kaudu, mitte otse.
// Nii ei pea nime, hinna ja koguse haldamist siin uuesti kirjutama.
public class FoodProduct extends Product {
    public FoodProduct(String name, double price, int quantity) {
        // Kutsume vanemklassi konstruktorit andmete kontrollimiseks ja salvestamiseks.
        super(name, price, quantity);
    }

    // Anname abstraktsele meetodile toidukauba jaoks sobiva teostuse.
    // @Override näitab, et teostame vanemklassis kirjeldatud meetodi.
    // Polümorfismi tõttu kasutatakse seda teksti ka siis, kui toidutootele
    // viitava muutuja tüüp on Product ja kuvamine toimub Product.toString() kaudu.
    @Override
    public String getExtraInfo() {
        return "Toidukaup: kontrolli säilivusaega";
    }
}
