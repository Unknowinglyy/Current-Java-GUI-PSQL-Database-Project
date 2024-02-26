import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class managerView {
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
    
    }
    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> new managerView());
    }
}
