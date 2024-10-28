import java.util.ArrayList;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

class Menuitem {
    private String name;
    private double price;
    private String category;

    public Menuitem(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
}

class Order {
    private ArrayList<Menuitem> items;
    private ArrayList<Integer> quantities;
    private String customerName;
    private String tableNumber;

    public Order(String customerName, String tableNumber) {
        this.customerName = customerName;
        this.tableNumber = tableNumber;
        this.items = new ArrayList<>();
        this.quantities = new ArrayList<>();
    }

    public void addItem(Menuitem item, int quantity) {
        items.add(item);
        quantities.add(quantity);
    }

    // Custom Live Template untuk perhitungan total
    public double calculateTotal() {
        double total = 0;
        for (int i = 0; i < items.size(); i++) {
            total += items.get(i).getPrice() * quantities.get(i);
        }
        return total;
    }

    // Custom Live Template untuk perhitungan diskon
    public double calculateDiscount() {
        double total = calculateTotal();
        if (total > 500000) return 0.15; // 15% untuk pembelian di atas 500rb
        if (total > 300000) return 0.10; // 10% untuk pembelian di atas 300rb
        if (total > 150000) return 0.05; // 5% untuk pembelian di atas 150rb
        return 0;
    }

    public void printReceipt() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        System.out.println("\n======= SUSHI SAKURA =======");
        System.out.println("Tanggal: " + formatter.format(date));
        System.out.println("Nama Pelanggan: " + customerName);
        System.out.println("Nomor Meja: " + tableNumber);
        System.out.println("==========================");
        System.out.println("Pesanan:");

        for (int i = 0; i < items.size(); i++) {
            Menuitem item = items.get(i);
            int quantity = quantities.get(i);
            System.out.printf("%s x%d\n", item.getName(), quantity);
            System.out.printf("@Rp%.2f = Rp%.2f\n",
                    item.getPrice(),
                    item.getPrice() * quantity);
        }

        double total = calculateTotal();
        double discount = calculateDiscount() * total;
        double finalTotal = total - discount;

        System.out.println("==========================");
        System.out.printf("Total: Rp%.2f\n", total);
        System.out.printf("Diskon (%.0f%%): Rp%.2f\n",
                calculateDiscount() * 100,
                discount);
        System.out.printf("Total Akhir: Rp%.2f\n", finalTotal);
        System.out.println("==========================");
        System.out.println("Terima kasih atas kunjungan Anda!");
        System.out.println("============================");
    }
}

class SushiRestaurant {
    private static ArrayList<Menuitem> menu = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void initializeMenu() {
        // Sushi Rolls
        menu.add(new Menuitem("California Roll", 45000, "Sushi Roll"));
        menu.add(new Menuitem("Spicy Tuna Roll", 52000, "Sushi Roll"));
        menu.add(new Menuitem("Dragon Roll", 65000, "Sushi Roll"));

        // Nigiri
        menu.add(new Menuitem("Salmon Nigiri", 35000, "Nigiri"));
        menu.add(new Menuitem("Tuna Nigiri", 32000, "Nigiri"));
        menu.add(new Menuitem("Unagi Nigiri", 48000, "Nigiri"));

        // Minuman
        menu.add(new Menuitem("Green Tea", 20000, "Minuman"));
        menu.add(new Menuitem("Ocha", 15000, "Minuman"));
    }

    public static void displayMenu() {
        System.out.println("\n=== MENU SUSHI SAKURA ===");
        for (int i = 0; i < menu.size(); i++) {
            Menuitem item = menu.get(i);
            System.out.printf("%d. %s - Rp%.2f (%s)\n",
                    i + 1,
                    item.getName(),
                    item.getPrice(),
                    item.getCategory());
        }
    }

    public static void main(String[] args) {
        initializeMenu();

        System.out.println("Selamat datang di Sushi Sakura!");

        System.out.print("Masukkan nama pelanggan: ");
        String customerName = scanner.nextLine();

        System.out.print("Masukkan nomor meja: ");
        String tableNumber = scanner.nextLine();

        Order order = new Order(customerName, tableNumber);

        while (true) {
            displayMenu();
            System.out.println("\nPilih menu (Ketik 0 untuk selesai):");
            int choice = scanner.nextInt();

            if (choice == 0) break;
            if (choice < 1 || choice > menu.size()) {
                System.out.println("Menu tidak valid!");
                continue;
            }

            System.out.print("Jumlah pesanan: ");
            int quantity = scanner.nextInt();

            if (quantity <= 0) {
                System.out.println("Jumlah pesanan tidak valid!");
                continue;
            }

            order.addItem(menu.get(choice - 1), quantity);
            System.out.println("Pesanan ditambahkan!");
        }

        order.printReceipt();
    }
}
