
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainFrame extends JFrame {
    private InventoryManager inventoryManager;
    private JTextArea inventoryArea;
    private JTextField productNameField;
    private JTextField productQuantityField;

    public MainFrame() throws IOException {
        inventoryManager = new InventoryManager();
        setTitle("Inventory Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        inventoryArea = new JTextArea();
        inventoryArea.setEditable(false);
        refreshInventoryArea();

        productNameField = new JTextField(20);
        productQuantityField = new JTextField(5);
        JButton addButton = new JButton("Add Product");
        addButton.setBackground(Color.cyan);
        addButton.addActionListener(e -> addProduct());
        JButton deleteButton = new JButton("Delete Product");
        deleteButton.addActionListener(e -> deleteProduct());

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Product Name:"));
        inputPanel.add(productNameField);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(productQuantityField);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);

        add(new JScrollPane(inventoryArea), BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void addProduct() {
        try {
            String name = productNameField.getText();
            int quantity = Integer.parseInt(productQuantityField.getText());
            inventoryManager.addProduct("Default", name, quantity); // Assuming a single category for simplicity
            productNameField.setText("");
            productQuantityField.setText("");
            refreshInventoryArea();
        } catch (NumberFormatException | IOException ex) {
            JOptionPane.showMessageDialog(this, "Error adding product: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteProduct() {
        String name = JOptionPane.showInputDialog(this, "Enter the name of the product to delete:");
        // Implement the logic to delete a product from InventoryManager
        // This requires modifying InventoryManager to support product deletion
        // For now, we just print the name to standard output
        System.out.println("Deleting product: " + name);
        // After deleting, refresh the inventory display
        // refreshInventoryArea();
    }

    private void refreshInventoryArea() {
        inventoryArea.setText(inventoryManager.getInventoryData());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new MainFrame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
