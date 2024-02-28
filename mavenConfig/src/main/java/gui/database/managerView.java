package gui.database;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class managerView {
    private ArrayList<Ingredient> ingredientList;
    //lists of JLabels for ease of updating frontend
    private ArrayList<JLabel> displayLabels = new ArrayList<>();
    private ArrayList<JLabel> stockLabels = new ArrayList<>();
    private ArrayList<JLabel> restockLabels = new ArrayList<>();
    private final int lowStockThreshold = 20;
    private final int mediumStockThreshold = 50;

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
        //adjusts stock values based on a given adjustment value
        public void adjustStock(int adjustment) {
            int id = this.getId();
            Ingredient thisOne = this;
            SwingWorker worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                     
                     Connection conn = null;
                    String database_name = "csce331_902_01_db";
                    String database_user = "csce331_902_01_user";
                    String database_password = "EPICCSCEPROJECT";
                    String database_url = String.format("jdbc:postgresql://csce-315-db.engr.tamu.edu/%s", database_name);
                    try {
                        conn = DriverManager.getConnection(database_url, database_user, database_password);
                        String updateQuery = "UPDATE ingredient SET stock = stock + ? WHERE \"ingredientID\" = ?";
                        PreparedStatement stmt = conn.prepareStatement(updateQuery);
                        stmt.setInt(1, adjustment);
                        stmt.setInt(2, id);
                        stmt.executeUpdate();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.err.println(e.getClass().getName() + ": " + e.getMessage());
                        System.exit(0);
                    }
                    thisOne.setStock(thisOne.getStock() + adjustment);
                    return null;
                }
                @Override
                protected void done() {
                     // Update UI on the Swing EDT
                     SwingUtilities.invokeLater(() -> {
                         updateGUI(thisOne);
                     });
                }
            };
            worker.execute();
        }

    }
    //updates stock when + and - buttons are clicked
    private ActionListener createUpdateListener(Ingredient ingredient, int adjustment) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingredient.adjustStock(adjustment);
            }
        };
    }
    //fetches data from db when needed
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
    private void updateGUI(Ingredient modifiedIngredient){
        for(int i = 0; i < displayLabels.size(); i++){
            JLabel currDisplayLabel = displayLabels.get(i);
            JLabel currStockLabel = stockLabels.get(i);
            JLabel currRestocklabel = restockLabels.get(i);
            if(ingredientMatchesLabel(currDisplayLabel, modifiedIngredient)){
                updateJLabel(currDisplayLabel, modifiedIngredient);
                updateStockLabel(currStockLabel, modifiedIngredient);
                updateLabelColor(currRestocklabel, modifiedIngredient);
                break;
            }
        }
    }
    //checks if a given Jlabel corresponds with a given ingredient
    private boolean ingredientMatchesLabel(JLabel label, Ingredient ingredient) {
        String labelText = label.getText();
        String labelIngredientId = labelText.split("-")[0].trim();
        return labelIngredientId.equals(ingredient.getName().trim());
        
    }
    //updates colors based on thresholds
    private void updateLabelColor(JLabel label, Ingredient ingredient){
        if(ingredient.getStock()<=lowStockThreshold){
            label.setForeground(Color.RED);
        }
        else if(ingredient.getStock()<=mediumStockThreshold){
            label.setForeground(Color.ORANGE);
        }
        else{
            label.setForeground(Color.BLACK);
        }
    }
    //updates labels in the popup window
    private void updateStockLabel(JLabel label,Ingredient ingredient){
        label.setText(" " + ingredient.getStock() + " ");
        updateLabelColor(label, ingredient);
       
    }
    //updates labels in the managerView panel
    private void updateJLabel(JLabel label, Ingredient ingredient) {
        String displayLine = ingredient.getName() + "-" + ingredient.getStock();
        label.setText(displayLine);
        updateLabelColor(label, ingredient);
       
    }
    managerView() {
        fetchData();
        JPanel inventoryPanel = new JPanel();
        for (Ingredient ingredient : ingredientList) {
            String displayLine = ingredient.getName() + "-" + ingredient.getStock();
            JLabel displayLabel = new JLabel(displayLine); 

            if (ingredient.getStock() <= lowStockThreshold) {
                displayLabel.setForeground(Color.RED); 
            } else if (ingredient.getStock() <= mediumStockThreshold) {
                displayLabel.setForeground(Color.ORANGE);
            }
            displayLabels.add(displayLabel);
            inventoryPanel.add(displayLabel); 
        }

        inventoryPanel.setLayout(new BoxLayout(inventoryPanel, BoxLayout.Y_AXIS));
        JButton restockButton = new JButton("Restock");
        inventoryPanel.add(restockButton);

        // Set preferred height to limit size
        inventoryPanel.setPreferredSize(new Dimension(300, 300));
        JButton navigationButton = new JButton("Back to Main Menu");
        JScrollPane scrollPane = new JScrollPane(inventoryPanel);
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.EAST);
        // Add placeholder for other potential content on the left
        mainPanel.add(new JLabel("(Placeholder)"), BorderLayout.CENTER);
        mainPanel.add(navigationButton, BorderLayout.NORTH);
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
               
                JDialog restockDialog = new JDialog(f, "Restock Ingredients"); 
                restockDialog.setModal(true); 
                
                JPanel restockPanel = new JPanel();
               
                restockPanel.setLayout(new GridLayout(0, 4)); 
                // Dynamically create labels and buttons
                for (int i = 0; i < ingredientList.size(); i++) { 
                    JLabel restockLabel = new JLabel(ingredientList.get(i).getId() + " " + ingredientList.get(i).getName());
                    JButton minusButton = new JButton("-");
                    JButton plusButton = new JButton("+");
                    JLabel stockLabel = new JLabel(" " + ingredientList.get(i).getStock() + " ");
                    minusButton.addActionListener(createUpdateListener(ingredientList.get(i), -1));
                    plusButton.addActionListener(createUpdateListener(ingredientList.get(i), 1));

                    if (ingredientList.get(i).getStock() <= lowStockThreshold) {
                        stockLabel.setForeground(Color.RED); 
                        restockLabel.setForeground(Color.RED);
                    } else if (ingredientList.get(i).getStock() <= mediumStockThreshold) {
                        stockLabel.setForeground(Color.ORANGE);
                        restockLabel.setForeground(Color.ORANGE);
                    }
                    stockLabels.add(stockLabel);
                    //adding labels to parent panel
                    restockLabels.add(restockLabel);
                    restockPanel.add(restockLabel);
                    restockPanel.add(minusButton);
                    restockPanel.add(stockLabel);
                    restockPanel.add(plusButton);
                }
                restockPanel.setSize(800,800);
                restockDialog.add(restockPanel);
                restockDialog.setSize(800,1000);
                restockDialog.setLocationRelativeTo(f); 
                restockDialog.setVisible(true);
            }
        });
    }
    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> new managerView());
    }
}
