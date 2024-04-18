

import java.io.*;
import java.util.*;

public class SalesManager {
    private static final String SALES_FILE = "sales.txt";
    private final List<String> sales = new ArrayList<>();

    public SalesManager() throws IOException {
        loadSales();
    }

    private void loadSales() throws IOException {
        File file = new File(SALES_FILE);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    sales.add(line);
                }
            }
        }
    }

    public void recordSale(String product, int quantity) throws IOException {
        sales.add("Sold " + quantity + " of " + product);
        saveSales();
    }

    private void saveSales() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(SALES_FILE))) {
            for (String sale : sales) {
                bw.write(sale);
                bw.newLine();
            }
        }
    }

    public String getSalesData() {
        return String.join("\n", sales);
    }
}
