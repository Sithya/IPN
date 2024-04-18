

import java.io.*;
import java.util.*;

public class InventoryManager {
    private static final String INVENTORY_FILE = "inventory.txt";
    private final Map<String, Map<String, Integer>> categories = new HashMap<>();

    public InventoryManager() throws IOException {
        File file = new File(INVENTORY_FILE);
        if (!file.exists()) {
            // Attempt to create the file if it doesn't exist
            boolean isFileCreated = file.createNewFile();
            System.out.println("Creating inventory file: " + isFileCreated);
        }
        loadItems();
    }

    private void loadItems() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(INVENTORY_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String category = parts[0].trim();
                    String productName = parts[1].trim();
                    int quantity = Integer.parseInt(parts[2].trim());
                    categories.putIfAbsent(category, new HashMap<>());
                    categories.get(category).put(productName, quantity);
                }
            }
            System.out.println("Loaded items: " + categories);
        } catch (FileNotFoundException e) {
            System.err.println("Inventory file not found.");
        } catch (IOException e) {
            System.err.println("Error reading inventory file.");
            throw e;
        }
    }

    public void addProduct(String category, String product, int quantity) throws IOException {
        System.out.println("Adding product: " + product + ", Category: " + category + ", Quantity: " + quantity);
        categories.putIfAbsent(category, new HashMap<>());
        Map<String, Integer> products = categories.get(category);
        products.put(product, products.getOrDefault(product, 0) + quantity);
        saveItems();
    }

    public void saveItems() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(INVENTORY_FILE))) {
            for (Map.Entry<String, Map<String, Integer>> entry : categories.entrySet()) {
                for (Map.Entry<String, Integer> productEntry : entry.getValue().entrySet()) {
                    bw.write(entry.getKey() + "," + productEntry.getKey() + "," + productEntry.getValue());
                    bw.newLine();
                }
            }
            System.out.println("Inventory saved.");
        } catch (IOException e) {
            System.err.println("Error writing inventory file.");
            throw e;
        }
    }

    public String getInventoryData() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Map<String, Integer>> entry : categories.entrySet()) {
            sb.append("Category: ").append(entry.getKey()).append("\n");
            for (Map.Entry<String, Integer> productEntry : entry.getValue().entrySet()) {
                sb.append(" - ").append(productEntry.getKey()).append(": ").append(productEntry.getValue()).append("\n");
            }
        }
        return sb.toString();
    }
}
