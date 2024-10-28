package Baru;

import java.util.ArrayList;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

class MenuItem {
    private String name;
    private double price;
    private String category;

    public MenuItem(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
}

class Order {
    private ArrayList<MenuItem> items;
    private ArrayList<Integer> quantities;
    private String customerName;
    private String tableNumber;

    public Order(String customerName, String tableNumber) {
        this.customerName = customerName;
        this.tableNumber = tableNumber;
        this.items = new ArrayList<>();
        this.quantities = new ArrayList<>();
    }

    public void addItem(MenuItem item, int quantity) {
        items.add(item);
        quantities.add(quantity);
    }

    public double calculateTotal() {
        double total = 0;
        for (int i = 0; i < items.size(); i++) {
            total += items.get(i).getPrice() * quantities.get(i);
        }
        return total;
    }

    public double calculateDiscount() {
        double total = calculateTotal();
        if (total > 500000) return 0.15;
        if (total > 300000) return 0.10;
        if (total > 150000) return 0.05;
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
            MenuItem item = items.get(i);
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
    private static ArrayList<MenuItem> menu = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void initializeMenu() {
        menu.add(new MenuItem("California Roll", 45000, "Sushi Roll"));
        menu.add(new MenuItem("Spicy Tuna Roll", 52000, "Sushi Roll"));
        menu.add(new MenuItem("Dragon Roll", 65000, "Sushi Roll"));
        menu.add(new MenuItem("Salmon Nigiri", 35000, "Nigiri"));
        menu.add(new MenuItem("Tuna Nigiri", 32000, "Nigiri"));
        menu.add(new MenuItem("Unagi Nigiri", 48000, "Nigiri"));
        menu.add(new MenuItem("Green Tea", 20000, "Minuman"));
        menu.add(new MenuItem("Ocha", 15000, "Minuman"));
    }

    public static void displayMenu() {
        System.out.println("\n=== MENU SUSHI SAKURA ===");
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.get(i);
            System.out.printf("%d. %s - Rp%.2f (%s)\n",
                    i + 1,
                    item.getName(),
                    item.getPrice(),
                    item.getCategory());
        }
    }

    public static int getValidMenuChoice() {
        int choice = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.println("\nPilih menu (0 untuk selesai):");
                String input = scanner.nextLine().trim();

                // Cek jika input kosong
                if (input.isEmpty()) {
                    System.out.println("Input tidak boleh kosong! Silakan masukkan nomor menu.");
                    continue;
                }

                // Cek jika input mengandung huruf
                if (!input.matches("\\d+")) {
                    System.out.println("Input harus berupa angka! Silakan masukkan nomor menu.");
                    continue;
                }

                choice = Integer.parseInt(input);

                // Validasi range menu
                if (choice < 0 || choice > menu.size()) {
                    System.out.println("Menu tidak valid! Silakan pilih nomor menu 1-" + menu.size() + " atau 0 untuk selesai.");
                    continue;
                }

                validInput = true;

            } catch (Exception e) {
                System.out.println("Terjadi kesalahan! Silakan coba lagi.");
                scanner.nextLine(); // Clear scanner buffer
            }
        }
        return choice;
    }

    public static int getValidQuantity() {
        int quantity = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Jumlah pesanan: ");
                String input = scanner.nextLine().trim();

                // Cek jika input kosong
                if (input.isEmpty()) {
                    System.out.println("Input tidak boleh kosong! Silakan masukkan jumlah pesanan.");
                    continue;
                }

                // Cek jika input mengandung huruf
                if (!input.matches("\\d+")) {
                    System.out.println("Input harus berupa angka! Silakan masukkan jumlah pesanan.");
                    continue;
                }

                quantity = Integer.parseInt(input);

                // Validasi jumlah pesanan
                if (quantity <= 0) {
                    System.out.println("Jumlah pesanan harus lebih dari 0! Silakan masukkan jumlah yang valid.");
                    continue;
                }

                validInput = true;

            } catch (Exception e) {
                System.out.println("Terjadi kesalahan! Silakan coba lagi.");
                scanner.nextLine(); // Clear scanner buffer
            }
        }
        return quantity;
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
            int choice = getValidMenuChoice();

            if (choice == 0) break;

            int quantity = getValidQuantity();
            order.addItem(menu.get(choice - 1), quantity);
            System.out.println("Pesanan ditambahkan!");
        }

        order.printReceipt();
    }
}

