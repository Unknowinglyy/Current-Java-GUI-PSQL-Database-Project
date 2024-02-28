package gui.database;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class managerView {
    private ArrayList<Ingredient> ingredientList;
    public class Ingredient {
        private final int id;
        private String name;
        private int stock;

        // Constructor
        public Ingredient(String name, int stock, int id) {
            this.id = id;
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

        public int getId() {
            return this.id;
        }

        // Setters
        public void setName(String name) {
            this.name = name;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public void adjustStock(int adjustment) {
            Connection conn = null;
            String database_name = "csce331_902_01_db";
            String database_user = "csce331_902_01_user";
            String database_password = "EPICCSCEPROJECT";
            String database_url = String.format("jdbc:postgresql://csce-315-db.engr.tamu.edu/%s", database_name);
            try {
                conn = DriverManager.getConnection(database_url, database_user, database_password);
                String updateQuery = "UPDATE ingredient SET stock = stock + ? WHERE \"ingredientID\" = ?";
                // UPDATE ingredient SET stock = stock + 1 WHERE "ingredientID" = 1;
                PreparedStatement stmt = conn.prepareStatement(updateQuery);
                stmt.setInt(1, adjustment);
                stmt.setInt(2, this.getId());
                stmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }

            // Handle the exception appropriately (logging, error message, etc.)

            this.stock = this.stock + adjustment;
        }

    }

    private ActionListener createUpdateListener(Ingredient ingredient, int adjustment) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement the logic to update the ingredient stock based on 'ingredient' and
                // 'adjustment'
                // Example:
                ingredient.adjustStock(adjustment);
                fetchData();

                // ... (Update the UI, if needed)
            }
        };
    }
    private void fetchData(){
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
        ArrayList<Ingredient> ingredientListTemp = new ArrayList<Ingredient>();
        try{
            Statement stmt = conn.createStatement();
            // create a SQL statement
            // TODO Step 2 (see line 8)
            String sqlStatement = "SELECT * FROM ingredient ORDER BY \"ingredient\" ASC;";
            // send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);
            while (result.next()) {
                String name = result.getString("name");
                int stock = result.getInt("stock"); // Assuming stock is an integer in your database
                int id = result.getInt("ingredientID");
                Ingredient ingredient = new Ingredient(name, stock, id);
                ingredientListTemp.add(ingredient);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error accessing Database.");

        }
           
           ingredientList=ingredientListTemp;
            
        
    }
    // private JPanel updateInventory(){
    //         //todo
    // }
    managerView() {
        // Connection conn = null;
        // String database_name = "csce331_902_01_db";
        // String database_user = "csce331_902_01_user";
        // String database_password = "EPICCSCEPROJECT";
        // String database_url = String.format("jdbc:postgresql://csce-315-db.engr.tamu.edu/%s", database_name);
        // try {
        //     conn = DriverManager.getConnection(database_url, database_user, database_password);
        // } catch (Exception e) {
        //     e.printStackTrace();
        //     System.err.println(e.getClass().getName() + ": " + e.getMessage());
        //     System.exit(0);
        // }
        // JOptionPane.showMessageDialog(null, "Opened database successfully");

        
        // ArrayList<Ingredient> ingredientList = new ArrayList<Ingredient>(); // Create a list to hold ingredients
        fetchData();

        
        int lowStockThreshold = 15;
        int mediumStockThreshold = 40;
        JPanel inventoryPanel = new JPanel();

        for (Ingredient ingredient : ingredientList) {
            String displayLine = ingredient.getName() + " " + ingredient.getStock();
            JLabel displayLabel = new JLabel(displayLine); // Create a JLabel

            if (ingredient.getStock() <= lowStockThreshold) {
                displayLabel.setForeground(Color.RED); // Set text color to red
            } else if (ingredient.getStock() <= mediumStockThreshold) {
                displayLabel.setForeground(Color.ORANGE);
            }

            // Add the displayLabel to your textArea or restockPanel
            inventoryPanel.add(displayLabel); // Or: restockPanel.add(displayLabel);
        }
        // textArea.setEditable(false);

        // inventoryPanel.add(textArea);
        inventoryPanel.setLayout(new BoxLayout(inventoryPanel, BoxLayout.Y_AXIS));
        JButton restockButton = new JButton("Restock");
        inventoryPanel.add(restockButton);
        // Set preferred height to limit size
        inventoryPanel.setPreferredSize(new Dimension(300, 300));
        JButton navigationButton = new JButton("Back to Main Menu");
        // Create a JScrollPane and add the inventoryPanel to it
        JScrollPane scrollPane = new JScrollPane(inventoryPanel);
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Main Panel (Add the scrollPane)
        mainPanel.add(scrollPane, BorderLayout.EAST);

        // Main Panel
        // Add placeholder for other potential content on the left
        mainPanel.add(new JLabel("(Placeholder)"), BorderLayout.CENTER);
        mainPanel.add(navigationButton, BorderLayout.NORTH);
        // Add Inventory Panel to the right
        mainPanel.add(inventoryPanel, BorderLayout.EAST);

        JFrame f = new JFrame();
        f.setSize(1920, 1080);
        f.add(mainPanel); // Add the main panel to the frame
        // f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);

        navigationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.setVisible(false);
            }
        });
        restockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a new JDialog for ingredient adjustments
                JDialog restockDialog = new JDialog(f, "Restock Ingredients"); // Associate with main frame 'f'
                restockDialog.setModal(true); // Block interaction with the main window
                // Panel to hold the list of ingredients
                JPanel restockPanel = new JPanel();
                restockPanel.setLayout(new GridLayout(0, 4)); // Adjust rows as needed
                // Dynamically create labels and buttons
                for (int i = 0; i < ingredientList.size(); i++) { // Assuming you have an Ingredient class
                    JLabel restockLabel = new JLabel(ingredientList.get(i).getId() + " " + ingredientList.get(i).getName());
                    JButton minusButton = new JButton("-");
                    JButton plusButton = new JButton("+");
                    JLabel stockLabel = new JLabel(" " + ingredientList.get(i).getStock() + " ");
                    minusButton.addActionListener(createUpdateListener(ingredientList.get(i), -1));
                    plusButton.addActionListener(createUpdateListener(ingredientList.get(i), 1));

                    if (ingredientList.get(i).getStock() <= lowStockThreshold) {
                        stockLabel.setForeground(Color.RED); // Set text color to red
                        restockLabel.setForeground(Color.RED);
                    } else if (ingredientList.get(i).getStock() <= mediumStockThreshold) {
                        stockLabel.setForeground(Color.ORANGE);
                        restockLabel.setForeground(Color.ORANGE);
                    }
                    

                    restockPanel.add(restockLabel);
                    restockPanel.add(minusButton);
                    restockPanel.add(stockLabel);
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
