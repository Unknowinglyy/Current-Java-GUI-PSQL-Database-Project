package gui.database;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


class mainMenu {
    // Define global objects and constants
    Menu currentMenu = new Menu();
    JPanel currentOrdersList;
    GridBagConstraints gbc = new GridBagConstraints();
    JFrame f = new JFrame();
    JPanel panel = new JPanel();
    JPanel foodCatagories = new JPanel(new GridLayout(3, 5, 10, 10));
    java.util.List<String> orderLabelList;
    String currItemLabel;
    int currOrderIndex;
    int itemNum = 0;
    int currTicketID;
    Connection conn = null;
    String database_name = "csce331_902_01_db";
    String database_user = "csce331_902_01_user";
    String database_password = "EPICCSCEPROJECT";
    String database_url = String.format("jdbc:postgresql://csce-315-db.engr.tamu.edu/%s", database_name);
    private Boolean foodCategoriesPanelOpen = true;
    private Boolean foodPanelOpen = false;
    private Boolean ingredientsPanelOpen = false;
    private String foodType;

    // Define UI constructor
    mainMenu() {
        // set constraints for initial layout
        panel.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(30, 10, 0, 10);
        gbc.weightx = 0;
        gbc.weighty = 0;
        orderLabelList = new ArrayList<>();
        String order = "";

        // update the current ticket id to display with order panel
        updateCurrID();
        // create the order panel for displaying items in order
        createCurrentOrderPanel(order);

        // creating the food selecting section
        JLabel selectFood = new JLabel("Select Food");
        gbc.gridx++;
        gbc.gridy = 0;
        gbc.insets = new Insets(30, 200, 0, 10);
        panel.add(selectFood, gbc);
        gbc.gridy = 1;
        gbc.insets = new Insets(30, 200, 50, 10);
        panel.add(foodCatagories, gbc);
        
        createFoodCatagoriesPanel();

        // add payment / back button section
        JLabel paymentSection = new JLabel("Payment Option");
        JButton paymentView = createPaymentButton("Payment View");
        JPanel dandcItems = new JPanel(new GridLayout(1, 1, 1, 10));
        gbc.gridy = 3;
        gbc.insets = new Insets(30, 200, 30, 10);
        dandcItems.add(paymentView);
        gbc.gridy++;
        panel.add(paymentView, gbc);


        // add manager view button
        JButton managerView = createManagerButton("Manager View", f);
        managerView.setPreferredSize(new Dimension(150, 40));
        gbc.gridx++;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(10, 50, 0, 10);
        panel.add(managerView, gbc);

        // display UI
        f.add(panel);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    // this function creates the panel of food categories for selecting
    private void createFoodCatagoriesPanel(){

        foodCatagories.removeAll();
        gbc.gridy = 1;
        gbc.insets = new Insets(30, 200, 50, 10);
        Vector<String> currentTypes = currentMenu.getFoodTypes();

        for(int i = 0; i < currentTypes.size();i++){

            JButton b1 = createFoodTypesButton(currentTypes.get(i));
            foodCatagories.add(b1,gbc);
            
        }


        // update panel
        panel.revalidate();
        panel.repaint();
    }

    // this function creates the food selecting panel when the cashier has cicked the food type
    private void createFoodPanel(String Category){

        Vector<String> Foods = currentMenu.getInStockFoodFromFoodType(Category);
        foodType = Category;
        foodCatagories.removeAll();
        gbc.gridy = 1;
        gbc.insets = new Insets(30, 200, 50, 10);

        for(int i = 0; i < Foods.size();i++){

            JButton b1 = createFoodButton(Foods.get(i), currentMenu.getPrice(Foods.get(i)));
            foodCatagories.add(b1,gbc);

        }
        JButton back = backButton();
        foodCatagories.add(back,gbc);

        // update panel
        panel.revalidate();
        panel.repaint();
    }

    // this function creates the ingredients panel when cashier is modifying a food
    private void createIngredientsPanel(String Food){

        Vector<String> Recipe = currentMenu.getRecipe(Food);
        foodCatagories.removeAll();
        gbc.gridy = 1;
        gbc.insets = new Insets(30, 200, 50, 10);

        for(int i = 1; i < Recipe.size();i++){

            JToggleButton b1 = createIngredientButton(Recipe.get(i));
            foodCatagories.add(b1,gbc);

        }
        JButton back = backButton();
        foodCatagories.add(back,gbc);

        // update frame with panel
        JButton b2 = addToOrderButton();
        foodCatagories.add(b2,gbc);
        panel.revalidate();
        panel.repaint();
    }

    // this function creates the buttons in the categories panel
    private JButton createFoodTypesButton(String itemName){
        
        JButton button = new JButton(itemName);
        Dimension buttonSize = new Dimension(175, 175);
        button.setPreferredSize(buttonSize);
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                createFoodPanel(itemName);
                foodCategoriesPanelOpen = false;
                foodPanelOpen = true;
                ingredientsPanelOpen = false;

            }
        });

        return button;
    }

    // this function creates the buttons in the food panel
    private JButton createFoodButton(String itemName , Double price){
        
        JButton button = new JButton(itemName);
        Dimension buttonSize = new Dimension(175, 175);
        button.setPreferredSize(buttonSize);
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                // make new label
                itemNum++;
                currItemLabel = "#" + itemNum + ": " + itemName + ", $" + Double.toString(price);
                
                // add to list
                orderLabelList.add(currItemLabel);
                createIngredientsPanel(itemName);
                foodCategoriesPanelOpen = false;
                foodPanelOpen = false;
                ingredientsPanelOpen = true;
            }
        });

        return button;
    }

    // this function creates the buttons in the item modifying panel
    private JToggleButton createIngredientButton(String itemName){

        JToggleButton Tbutton = new JToggleButton(itemName);
        Tbutton.setBackground(Color.GREEN);
        Tbutton.setOpaque(true);
        Tbutton.setBorderPainted(false);
        Tbutton.setFocusPainted(false);
        Dimension buttonSize = new Dimension(175, 175);
        Tbutton.setPreferredSize(buttonSize);
        
        Tbutton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                
                if (e.getStateChange() == ItemEvent.SELECTED) {

                    // this actually just turns it gray
                    Tbutton.setBackground(Color.RED); 

                    // list ingredients to be removed from order
                    currItemLabel = "  - No: " + itemName + " in item: #" + itemNum;

                    // add to list
                    orderLabelList.add(currItemLabel);

                }
                if (e.getStateChange() == ItemEvent.DESELECTED) {

                    currItemLabel = "  - No: " + itemName + " in item: #" + itemNum;
                    Tbutton.setBackground(Color.GREEN);
                    orderLabelList.remove(currItemLabel);

                }
            }
        });

        return Tbutton;
    }

    // creates the button for confirming item modifications and adding it to the order
    private JButton addToOrderButton(){
        JButton button = new JButton("Add To Order");
        Dimension buttonSize = new Dimension(175, 175);
        button.setPreferredSize(buttonSize);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // only update order side bar when the add order button is pressed
                updateOrderLabels();
                createFoodCatagoriesPanel();

            }
        });
        return button;
    }

    // creates the button in the payment options panel
    private JButton createPaymentButton(String itemName) {
        JButton button = new JButton(itemName);
        Dimension buttonSize = new Dimension(175, 175);
        button.setPreferredSize(buttonSize);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                double price = getTotalPrice();
                paymentMenu newPay = new paymentMenu(price);
                newPay.setVisible(true);
                confirmOrderWithPayment(newPay.outPay);

            }
        });
        return button;
    }

    // creates the button to navigate to managerview
    private JButton createManagerButton(String itemName, JFrame f) {
        JButton button = new JButton(itemName);
        Dimension buttonSize = new Dimension(175, 175);
        button.setPreferredSize(buttonSize);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                login loginAttempt = new login(f);
                loginAttempt.setVisible(true);
                if (loginAttempt.isSuccessful()) 
                {
                    managerView currentManager = new managerView();
                }
            }
        });
        return button;
    }

    // adds the back button for the payment/back panel
    private JButton backButton() {
        JButton button = new JButton("Back Button");
        Dimension buttonSize = new Dimension(175, 175);
        button.setPreferredSize(buttonSize);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (foodPanelOpen)
                {
                    createFoodCatagoriesPanel();
                    foodCategoriesPanelOpen = true;
                    foodPanelOpen = false;
                    ingredientsPanelOpen = false;
                }
                else if (ingredientsPanelOpen)
                {
                    createFoodPanel(foodType);
                    foodCategoriesPanelOpen = false;
                    foodPanelOpen = true;
                    ingredientsPanelOpen = false;

                }
            }
        });
        return button;
    }

    // creates the order display panel
    private JPanel createCurrentOrderPanel(String theOrder) {

        // create label
        JLabel currentOrder = new JLabel("Current Order");
        gbc.insets = new Insets(30, 10, 0, 10);
        panel.add(currentOrder, gbc);

        // turn list for storing order item labels
        currentOrdersList = new JPanel();
        currentOrdersList.setLayout(new BoxLayout(currentOrdersList, BoxLayout.Y_AXIS));
    
        // create the scrolling panel from the labels list
        JScrollPane scrollPane = new JScrollPane(currentOrdersList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(400, 600));

        // format constraints
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(30, 10, 0, 10);
        panel.add(scrollPane, gbc);

        // update the currentorderslist panel
        updateOrderLabels();
        return panel;
    }

    // this function updates all of the orders in the side panel based on the order list
    // any entry starting with a '#' is the full food item, anything right after until the next '#' will be ingredients to remove from that food item
    private void updateOrderLabels() {
        
        // // Clear existing labels
        currentOrdersList.removeAll(); 

        // update the panel based on the current order list
        for (String text : new ArrayList<>(orderLabelList)) {

            // create panel to held the label and an x button to remove
            JPanel labelWithButtonPanel = new JPanel();
            labelWithButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
            
            // if the item in list is a food, add an x button to delete it
            if (text.charAt(0) == '#') {

                // create label
                JLabel label = new JLabel(text);

                // make x button
                JButton removeButton = new JButton("X");
                removeButton.setForeground(Color.WHITE);
                removeButton.setBackground(Color.RED);
                removeButton.addActionListener((ActionEvent e) -> {
                    int index = orderLabelList.indexOf(label.getText());
                    orderLabelList.remove(index);

                    // remove all modifiers from start of food
                    while ((orderLabelList.size() > index) && (orderLabelList.get(index).charAt(0) != '#')) {
                        orderLabelList.remove(orderLabelList.get(index));
                    }

                    // give the button functionality to rebuild the panel
                    updateOrderLabels();
                });

                // add in label and its button to panel
                labelWithButtonPanel.add(label);
                labelWithButtonPanel.add(removeButton);
            }
            else {

                // if element is an ingredient modifier, do not add x button
                JLabel label = new JLabel(text);
                labelWithButtonPanel.add(label);
            }

            // add new panel to main scroll panel
            currentOrdersList.add(labelWithButtonPanel);
        }

        // remake the scroll panel in UI
        currentOrdersList.revalidate();
        currentOrdersList.repaint();
    }

    // this is the function to complete each order and allow for the next
    // order to be started
    public void confirmOrderWithPayment(String paymentMethod) {
        updateCurrID();
        Double theCost = getTotalPrice();
        try {
            conn = DriverManager.getConnection(database_url, database_user, database_password);
            String sqlStatements = "INSERT INTO ticket (\"ticketID\", \"timeOrdered\", \"totalCost\", payment) VALUES (" + currTicketID + ", date (LOCALTIMESTAMP), " + Double.toString(theCost) + ", '" + paymentMethod + "');";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sqlStatements);
            java.util.List<Integer> itemPositions = new ArrayList<>();
            
            // get the item indexes
            for (int index=0; index < orderLabelList.size(); index++) {
                if (orderLabelList.get(index).charAt(0) == '#') {
                    itemPositions.add(index);
                }
            }
            for (int index=0; index < itemPositions.size(); index++) {
                
                // get current food item
                String foodName = orderLabelList.get(index).split(",")[0].substring(orderLabelList.get(index).indexOf(':') + 2);
                int foodID = currentMenu.getFoodID(foodName);
                Vector<String> Recipe = new Vector<>(currentMenu.getRecipe(foodName));

                if (((index+1) != itemPositions.size())) {

                    int diffPos = itemPositions.get(index+1) - itemPositions.get(index);
                    
                    // for unmodified food
                    if (diffPos == 0) {
                        
                        sqlStatements = "INSERT INTO foodticket(amount, \"ticketID\", \"foodID\") VALUES(1, " + currTicketID + ", " + foodID + ");";

                    }

                    // for modified
                    else {

                        // step 3 remove recipe items set by modifiers
                        for (int jIndex = (itemPositions.get(index)+1); jIndex < itemPositions.get(index+1); jIndex++) {
                            String remIng = orderLabelList.get(jIndex).split(" in")[0].substring(8);
                            Recipe.removeElement(remIng);
                        }

                        // step 4 add to food table with new id
                        currentMenu.addFood(foodName, currentMenu.getFoodCatagory(foodName), currentMenu.getPrice(foodName), Recipe);
                        int newFoodID = getNewFoodID();

                        // step 5 add the now modified food id to foodticket
                        sqlStatements = "INSERT INTO foodticket(amount, \"ticketID\", \"foodID\") VALUES(1, " + currTicketID + ", " + newFoodID + ");";

                    }

                // if this is the last item in the list and has no modifiers
                } else if ((index+1) == orderLabelList.size()) {
                    
                    sqlStatements = "INSERT INTO foodticket(amount, \"ticketID\", \"foodID\") VALUES(1, " + currTicketID + ", " + foodID + ");";

                }

                // update foodticket with new food item
                Statement stmt2 = conn.createStatement();
                stmt2.executeUpdate(sqlStatements);

                // update stock
                for (String ing : Recipe) {
                    //checking if the stock is 0. If it is, throw an exception.
                    String checkQuery = "SELECT stock from ingredient where \"ingredientID\" = ?";
                    PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
                    checkStmt.setInt(1, currentMenu.getIngredientID((ing)));
                    ResultSet rs = checkStmt.executeQuery();
                    int stock = 0;

                    if (rs.next()) {
                        stock = rs.getInt("stock");
                    }
                    rs.close();
                    checkStmt.close();

                    if(stock > 0) {
                        String updateQuery = "UPDATE ingredient SET stock = stock - ? WHERE \"ingredientID\" = ?";
                        PreparedStatement stmt5 = conn.prepareStatement(updateQuery);
                        stmt5.setInt(1, 1);
                        stmt5.setInt(2, currentMenu.getIngredientID((ing)));
                        stmt5.executeUpdate();
                        stmt5.close();
                    }
                }
            }
        } catch (Exception e) {

            e.printStackTrace();

        }

        // update order dependencies to begin new order
        orderLabelList.clear();
        updateOrderLabels();
        updateCurrID();
    }

    // calculate the total price for the order
    public Double getTotalPrice() {
        Double theTotalCost = 0.0;
        for (String iText : orderLabelList) {
            if (iText.charAt(0) == '#') {
                String foodItem = iText.split(",")[0].substring(iText.indexOf(':') + 2);
                // String foodItem = iText.split(",")[0];
                theTotalCost += currentMenu.getPrice(foodItem);
            }
        }
        return theTotalCost;
    }

    // this function grabs the highest ticket id in the database
    public int getPrevTicketID() {
        
        // get connection to db
        try {
        conn = DriverManager.getConnection(database_url, database_user, database_password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            Statement stmt = conn.createStatement();
            String sqlStatement = "SELECT max(\"ticketID\") FROM ticket;";
            // String sqlStatement = "SELECT * FROM ingredient;";
            ResultSet result = stmt.executeQuery(sqlStatement);
            int number = 0;
            while (result.next()) {
                number = result.getInt("max");
                
            }

            return number;
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error accessing Database.");

            return -1;
        }
        
    }
    
    // this function finds the next id to insert a new food with
    public int getNewFoodID() {

        // get connection to db
        try {

            conn = DriverManager.getConnection(database_url, database_user, database_password);

        } catch (Exception e) {

            e.printStackTrace();

        }

        // the try statements should be redundant
        try {

            Statement stmt = conn.createStatement();
            String sqlStatement = "SELECT max(\"foodID\") FROM food;";
            ResultSet result = stmt.executeQuery(sqlStatement);
            int number = 0;
            while (result.next()) {

                number = result.getInt("max");
                
            }

            return number;
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error accessing Database.");

            return -1;
        }
        
    }
    
    // this function updates the current ticket id
    public void updateCurrID() {

        currTicketID = getPrevTicketID() + 1;

    }

    // main function
    public static void main(String a[]) {
        SwingUtilities.invokeLater(() -> new mainMenu());
    }
}

