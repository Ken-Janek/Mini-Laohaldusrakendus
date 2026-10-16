import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static final Scanner input = new Scanner(System.in, StandardCharsets.UTF_8);

    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        inventory.addProduct(new FoodProduct("Piim", 1.50, 10));
        inventory.addProduct(new FoodProduct("Leib", 2.00, 5));
        inventory.addProduct(new ElectronicProduct("Hiir", 15.00, 2));

        boolean running = true;
        while (running) {
            System.out.println("\n1 - Kõik tooted\n2 - Lisa toode\n3 - Muuda kogust"
                    + "\n4 - Otsi nime järgi\n5 - Lao koguväärtus\n0 - Välju");
            if (!input.hasNextLine()) break;
            try {
                int choice = readInt("Valik: ");
                switch (choice) {
                    case 1:
                        for (Product product : inventory.getProducts()) {
                            System.out.println(product);
                        }
                        break;
                    case 2:
                        addProduct(inventory);
                        break;
                    case 3:
                        Product product = findProduct(inventory);
                        if (product != null) {
                            product.changeQuantity(readInt("Koguse muutus (nt 3 või -2): "));
                            System.out.println(product);
                        }
                        break;
                    case 4:
                        Product found = findProduct(inventory);
                        if (found != null) System.out.println(found);
                        break;
                    case 5:
                        System.out.printf(Locale.ROOT, "Lao koguväärtus: %.2f EUR%n", inventory.getTotalValue());
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Tundmatu valik.");
                }
            } catch (IllegalArgumentException exception) {
                System.out.println("Viga: " + exception.getMessage());
            } catch (java.util.NoSuchElementException exception) {
                break;
            }
        }
        System.out.println("Head aega!");
    }

    private static void addProduct(Inventory inventory) {
        int type = readInt("Liik (1 - toit, 2 - elektroonika): ");
        if (type != 1 && type != 2) {
            throw new IllegalArgumentException("Liik peab olema 1 või 2.");
        }
        String name = readLine("Nimi: ");
        double price;
        try {
            price = Double.parseDouble(readLine("Hind: ").replace(',', '.'));
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Hind peab olema arv.");
        }
        int quantity = readInt("Kogus: ");
        Product product = type == 1
                ? new FoodProduct(name, price, quantity)
                : new ElectronicProduct(name, price, quantity);
        inventory.addProduct(product);
        System.out.println("Lisatud: " + product);
    }

    private static Product findProduct(Inventory inventory) {
        Product product = inventory.findByName(readLine("Toote nimi: "));
        if (product == null) System.out.println("Toodet ei leitud.");
        return product;
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return input.nextLine().trim();
    }

    private static int readInt(String prompt) {
        try {
            return Integer.parseInt(readLine(prompt));
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Sisesta täisarv.");
        }
    }
}
