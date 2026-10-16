import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    // Ühine sisendilugeja loeb terminalist UTF-8 teksti.
    private static final Scanner input = new Scanner(System.in, StandardCharsets.UTF_8);

    // Java alustab programmi täitmist main-meetodist.
    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        // Algandmed: kaks toidutoodet ja üks elektroonikatoode.
        inventory.addProduct(new FoodProduct("Piim", 1.50, 10));
        inventory.addProduct(new FoodProduct("Leib", 2.00, 5));
        inventory.addProduct(new ElectronicProduct("Hiir", 15.00, 2));

        boolean running = true; // true hoiab menüü töös.
        while (running) { // Kordame menüüd kuni väljumiseni.
            System.out.println("\n1 - Kõik tooted\n2 - Lisa toode\n3 - Muuda kogust"
                    + "\n4 - Otsi nime järgi\n5 - Lao koguväärtus\n0 - Välju");
            if (!input.hasNextLine()) break; // Lõpetame, kui sisend on otsas.
            try {
                int choice = readInt("Valik: ");
                switch (choice) { // Valime kasutaja numbrile vastava tegevuse.
                    case 1: // Käime kõik tooted läbi ja kuvame nende kirjelduse.
                        for (Product product : inventory.getProducts()) {
                            System.out.println(product);
                        }
                        break;
                    case 2: // Küsime uue toote andmed eraldi meetodis.
                        addProduct(inventory);
                        break;
                    case 3: // Muudame kogust ainult siis, kui toode leiti.
                        Product product = findProduct(inventory);
                        if (product != null) {
                            product.changeQuantity(readInt("Koguse muutus (nt 3 või -2): "));
                            System.out.println(product);
                        }
                        break;
                    case 4: // Otsime ja kuvame ühe toote.
                        Product found = findProduct(inventory);
                        if (found != null) System.out.println(found);
                        break;
                    case 5: // Kuvame koguväärtuse kahe komakohaga.
                        System.out.printf(Locale.ROOT, "Lao koguväärtus: %.2f EUR%n", inventory.getTotalValue());
                        break;
                    case 0: // false lõpetab järgmise menüükorduse.
                        running = false;
                        break;
                    default: // Seda haru kasutatakse tundmatu menüünumbri korral.
                        System.out.println("Tundmatu valik.");
                }
            // Vigane väärtus annab teate; seejärel saab menüüs jätkata.
            } catch (IllegalArgumentException exception) {
                System.out.println("Viga: " + exception.getMessage());
            // Sisend võib lõppeda ka poole toimingu pealt.
            } catch (java.util.NoSuchElementException exception) {
                break;
            }
        }
        System.out.println("Head aega!");
    }

    // Loeme uue toote andmed ja lisame sobiva alamklassi objekti lattu.
    private static void addProduct(Inventory inventory) {
        int type = readInt("Liik (1 - toit, 2 - elektroonika): ");
        if (type != 1 && type != 2) { // && nõuab mõlema tingimuse kehtimist.
            throw new IllegalArgumentException("Liik peab olema 1 või 2.");
        }
        String name = readLine("Nimi: ");
        double price;
        try {
            // Asendame koma punktiga ja teisendame teksti arvuks.
            price = Double.parseDouble(readLine("Hind: ").replace(',', '.'));
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Hind peab olema arv.");
        }
        int quantity = readInt("Kogus: ");
        // Tingimusavaldis: kui liik on 1, loome toidu, muidu elektroonika.
        Product product = type == 1
                ? new FoodProduct(name, price, quantity)
                : new ElectronicProduct(name, price, quantity);
        inventory.addProduct(product);
        System.out.println("Lisatud: " + product);
    }

    // Ühine otsingumeetod koguse muutmise ja nimeotsingu jaoks.
    private static Product findProduct(Inventory inventory) {
        Product product = inventory.findByName(readLine("Toote nimi: "));
        if (product == null) System.out.println("Toodet ei leitud.");
        return product;
    }

    // Näitame küsimust ja tagastame terve sisestatud rea ilma servatühikuteta.
    private static String readLine(String prompt) {
        System.out.print(prompt);
        return input.nextLine().trim();
    }

    // Teisendame sisestatud teksti täisarvuks või anname arusaadava veateate.
    private static int readInt(String prompt) {
        try {
            return Integer.parseInt(readLine(prompt));
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Sisesta täisarv.");
        }
    }
}
