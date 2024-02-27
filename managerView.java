
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent; 
import java.util.ArrayList;

public class managerView {
    public class Ingredient {
        private String name;
        private int stock;
    
        // Constructor
        public Ingredient(String name, int stock) {
            this.name = name;
            this.stock = stock;
        }
    
        // Getters
        public String getName() {
            return name;
        }
    
        public int getStock() {
            return stock;
        }
    
        // Setters
        public void setName(String name) {
            this.name = name;
        }
    
        public void setStock(int stock) {
            this.stock = stock;
        }
        public void adjustStock(int adjustment){
            this.stock = this.stock + adjustment;
        }
    }
    private ActionListener createUpdateListener(Ingredient ingredient, int adjustment) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement the logic to update the ingredient stock based on 'ingredient' and 'adjustment'
                // Example:
                ingredient.adjustStock(adjustment); 
    
                // ... (Update the UI, if needed)
            }
        };
    }
    managerView(){
        Connection conn = null;
        String database_name = "csce331_902_01_db";
        String database_user = "csce331_902_01_user";
        String database_password = "EPICCSCEPROJECT";
        String database_url = String.format("jdbc:postgresql://csce-315-db.engr.tamu.edu/%s", database_name);
        try {
        conn = DriverManager.getConnection(database_url, database_user, database_password);
        } catch (Exception e) {
        e.printStackTrace();
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
        System.exit(0);
        }
         JOptionPane.showMessageDialog(null, "Opened database successfully");

        String ingredients = "";
        ArrayList<Ingredient> ingredientList = new ArrayList<Ingredient>(); // Create a list to hold ingredients

        try {
        // create a statement object
        Statement stmt = conn.createStatement();
        // create a SQL statement
        // TODO Step 2 (see line 8)
        String sqlStatement = "SELECT * FROM ingredient;";
        // send statement to DBMS
        ResultSet result = stmt.executeQuery(sqlStatement);
        while (result.next()) {
            ingredients += result.getString("name") + "   ";
            ingredients += result.getString("stock") + "\n";
            String name = result.getString("name");
            int stock = result.getInt("stock"); // Assuming stock is an integer in your database
            Ingredient ingredient = new Ingredient(name, stock); 
            ingredientList.add(ingredient);
        }
        } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error accessing Database.");
        }
        JTextArea textArea = new JTextArea(ingredients);
        textArea.setEditable(false);

        // Inventory Panel (remains mostly the same)
        JPanel inventoryPanel = new JPanel();
        inventoryPanel.add(textArea);
        inventoryPanel.setLayout(new BoxLayout(inventoryPanel, BoxLayout.Y_AXIS));
        JButton restockButton = new JButton("Restock");
        inventoryPanel.add(restockButton);
        // Set preferred height to limit size 
        inventoryPanel.setPreferredSize(new Dimension(300, 300)); 

        // Create a JScrollPane and add the inventoryPanel to it
        JScrollPane scrollPane = new JScrollPane(inventoryPanel); 
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Main Panel (Add the scrollPane)
        mainPanel.add(scrollPane, BorderLayout.EAST);  

        // Main Panel
        // Add placeholder for other potential content on the left
        mainPanel.add(new JLabel("(Placeholder)"), BorderLayout.CENTER);

        // Add Inventory Panel to the right
        mainPanel.add(inventoryPanel, BorderLayout.EAST);

        JFrame f = new JFrame();
        f.setSize(1920, 1080); 
        f.add(mainPanel); // Add the main panel to the frame
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    
    
    restockButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Create a new JDialog for ingredient adjustments
            JDialog restockDialog = new JDialog(f, "Restock Ingredients"); // Associate with main frame 'f'
            restockDialog.setModal(true); // Block interaction with the main window
    
            // Panel to hold the list of ingredients
            JPanel restockPanel = new JPanel();
            restockPanel.setLayout(new GridLayout(0, 3)); // Adjust rows as needed
    
            // Fetch ingredients and stock data (you likely already have this logic)
            // ...
    
            // Dynamically create labels and buttons
            for (int i = 0; i < ingredientList.size();i++) { // Assuming you have an Ingredient class
                restockPanel.add(new JLabel(ingredientList.get(i).getName()));
    
                JButton minusButton = new JButton("-");
                minusButton.addActionListener(createUpdateListener(ingredientList.get(i), -1));
                restockPanel.add(minusButton); 
    
                JButton plusButton = new JButton("+");
                plusButton.addActionListener(createUpdateListener(ingredientList.get(i), 1));
                restockPanel.add(plusButton);
            }
    
            restockDialog.add(restockPanel);
            restockDialog.pack();
            restockDialog.setLocationRelativeTo(f); // Center relative to the main frame
            restockDialog.setVisible(true);
        }
    });
}
    
    
    
    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> new managerView());
    }
}
