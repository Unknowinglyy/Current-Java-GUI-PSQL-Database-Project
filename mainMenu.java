import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


class mainMenu {
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

    mainMenu() {
        panel.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(30, 10, 0, 10);
        gbc.weightx = 0;
        gbc.weighty = 0;
        orderLabelList = new ArrayList<>();
        String order = "";
        CreateCurrentOrderPanel(order);
        
        // JLabel currentOrder = new JLabel("Current Order");
        // gbc.insets = new Insets(30, 10, 0, 10);
        // panel.add(currentOrder, gbc);

        // currentOrdersList = new JPanel();
        // currentOrdersList.setLayout(new BoxLayout(currentOrdersList, BoxLayout.Y_AXIS));
        

        // JScrollPane scrollPane = new JScrollPane(currentOrdersList);
        // scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        // scrollPane.setPreferredSize(new Dimension(300, 600));

        // gbc.gridy = 1;
        // gbc.fill = GridBagConstraints.BOTH;
        // gbc.insets = new Insets(30, 10, 0, 10);
        // panel.add(scrollPane, gbc);

        //makes the menu to add and subtract items
        JLabel selectFood = new JLabel("Select Food");
        gbc.gridx++;
        gbc.gridy = 0;
        gbc.insets = new Insets(30, 200, 0, 10);
        panel.add(selectFood, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(30, 200, 50, 10);
        panel.add(foodCatagories, gbc);
        currentMenu.GenerateBasicMenu();
        CreateFoodCatagoriesPanel();

        // JButton b1 = createButton("Hamburger");
        // JButton b2 = createButton("Rev Burger");
        // JButton b3 = createButton("Bacon Burger");
        // JButton b4 = createButton("Cheeseburger");
        // JButton b5 = createButton("Deluxe Burger");
        // JButton b6 = createButton("Chicken Sandwich");
        // JButton b7 = createButton("Grilled Chicken Sandwich");
        // JButton b8 = createButton("Spicy Chicken Sandwich");
        // JButton b9 = createButton("Texas Toast Patty Melt");
        // JButton b10 = createButton("5 Chicken Tenders Box");
        // JButton b11 = createButton("3 Chicken Tenders Box");
        // JButton b12 = createButton("3 Chicken Tenders Box");
        // JButton b13 = createButton("3 Chicken Tenders Box");
        // JButton b14 = createButton("3 Chicken Tenders Box");
        // JButton b15 = createButton("3 Chicken Tenders Box");


        // JPanel foodItems = new JPanel(new GridLayout(3, 5, 10, 10));
        // foodItems.add(b1);
        // foodItems.add(b2);
        // foodItems.add(b3);
        // foodItems.add(b4);
        // foodItems.add(b5);
        // foodItems.add(b6);
        // foodItems.add(b7);
        // foodItems.add(b8);
        // foodItems.add(b9);
        // foodItems.add(b10);
        // foodItems.add(b11);
        // foodItems.add(b12);
        // foodItems.add(b13);
        // foodItems.add(b14);
        // foodItems.add(b15);

        // gbc.gridy = 1;
        // gbc.insets = new Insets(30, 200, 50, 10);
        // panel.add(foodItems, gbc);

        JLabel drinksAndCondiments = new JLabel("Option");
        gbc.gridy = 2;
        gbc.insets = new Insets(30, 200, 0, 10);
        panel.add(drinksAndCondiments, gbc);

        JButton backButton = backButton("Go Back");
        JPanel dandcItems = new JPanel(new GridLayout(1, 1, 10, 10));
        gbc.gridy = 3;
        gbc.insets = new Insets(30, 200, 30, 10);
        dandcItems.add(backButton);
        gbc.gridy++;
        panel.add(dandcItems, gbc);

        JButton managerView = createManagerButton("Manager View");
        managerView.setPreferredSize(new Dimension(150, 40));
        gbc.gridx++;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(10, 50, 0, 10);
        panel.add(managerView, gbc);

        f.add(panel);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    private Boolean foodCategoriesPanelOpen = true;
    private Boolean foodPanelOpen = false;
    private Boolean ingredientsPanelOpen = false;
    private String foodType;

    private void CreateFoodCatagoriesPanel(){
        foodCatagories.removeAll();
        //foodCatagories = new JPanel(new GridLayout(3, 5, 10, 10));
        gbc.gridy = 1;
        gbc.insets = new Insets(30, 200, 50, 10);
        for(int i = 0; i < currentMenu.FoodTypes.size();i++){
            JButton b1 = createFoodTypesButton(currentMenu.FoodTypes.get(i));
            foodCatagories.add(b1,gbc);
        }
        panel.revalidate();
        panel.repaint();
    }


    private void CreateFoodPanel(String Category){
        Vector<String> Foods = currentMenu.GetFoodFromFoodType(Category);
        foodType = Category;
        foodCatagories.removeAll();
        //foodCatagories = new JPanel(new GridLayout(3, 5, 10, 10));
        gbc.gridy = 1;
        gbc.insets = new Insets(30, 200, 50, 10);
        for(int i = 0; i < Foods.size();i++){
            JButton b1 = createFoodButton(Foods.get(i), currentMenu.GetPrice(Foods.get(i)));
            foodCatagories.add(b1,gbc);
        }
        panel.revalidate();
        panel.repaint();
    }

    private void CreateIngredientsPanel(String Food){
        Vector<String> Recipe = currentMenu.GetRecipe(Food);
        foodCatagories.removeAll();
        //foodCatagories = new JPanel(new GridLayout(3, 5, 10, 10));
        gbc.gridy = 1;
        gbc.insets = new Insets(30, 200, 50, 10);
        for(int i = 1; i < Recipe.size();i++){
            JToggleButton b1 = createIngredientButton(Recipe.get(i));
            foodCatagories.add(b1,gbc);
        }
        JButton b2 = AddToOrderButton();
        foodCatagories.add(b2,gbc);
        panel.revalidate();
        panel.repaint();
    }

    private JButton createFoodTypesButton(String itemName){
        JButton button = new JButton(itemName);
        Dimension buttonSize = new Dimension(175, 175);
        button.setPreferredSize(buttonSize);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateFoodPanel(itemName);
                foodCategoriesPanelOpen = false;
                foodPanelOpen = true;
                ingredientsPanelOpen = false;
            }
        });
        return button;
    }

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
                CreateIngredientsPanel(itemName);
                foodCategoriesPanelOpen = false;
                foodPanelOpen = false;
                ingredientsPanelOpen = true;
            }
        });
        return button;
    }

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
                    
                    Tbutton.setBackground(Color.RED); // this actually just turns it gray
                    // // list ingredients to be removed from order
                    currItemLabel = "  - No: " + itemName + " in item: #" + itemNum;
                    // // add to list
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

    private JButton AddToOrderButton(){
        JButton button = new JButton("Add To Order");
        Dimension buttonSize = new Dimension(175, 175);
        button.setPreferredSize(buttonSize);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // only update order side bar when the add order button is pressed
                UpdateOrderLabels();
                CreateFoodCatagoriesPanel();
            }
        });
        return button;
    }

    private JButton createButton(String itemName) {
        JButton button = new JButton(itemName);
        Dimension buttonSize = new Dimension(175, 175);
        button.setPreferredSize(buttonSize);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JLabel orderLabel = new JLabel(itemName);
                // currentOrdersList.add(orderLabel);
                currentOrdersList.revalidate();
                currentOrdersList.repaint();
            }
        });
        return button;
    }

    private JButton createManagerButton(String itemName) {
        JButton button = new JButton(itemName);
        Dimension buttonSize = new Dimension(175, 175);
        button.setPreferredSize(buttonSize);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerView currentManager = new managerView();
                //currentManager.main(null);
                // currentOrdersList.add(orderLabel);
                // currentOrdersList.revalidate();
                // currentOrdersList.repaint();
            }
        });
        return button;
    }

    private JButton backButton(String itemName) {
        JButton button = new JButton(itemName);
        Dimension buttonSize = new Dimension(175, 175);
        button.setPreferredSize(buttonSize);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (foodPanelOpen)
                {
                    CreateFoodCatagoriesPanel();
                    foodCategoriesPanelOpen = true;
                    foodPanelOpen = false;
                    ingredientsPanelOpen = false;
                }
                else if (ingredientsPanelOpen)
                {
                    CreateFoodPanel(foodType);
                    foodCategoriesPanelOpen = false;
                    foodPanelOpen = true;
                    ingredientsPanelOpen = false;

                }
            }
        });
        return button;
    }

    // made into a function for better readability, creates the order display panel
    private JPanel CreateCurrentOrderPanel(String theOrder) {
        JLabel currentOrder = new JLabel("Current Order");
        gbc.insets = new Insets(30, 10, 0, 10);
        panel.add(currentOrder, gbc);

        currentOrdersList = new JPanel();
        currentOrdersList.setLayout(new BoxLayout(currentOrdersList, BoxLayout.Y_AXIS));
        

        JScrollPane scrollPane = new JScrollPane(currentOrdersList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(400, 600));

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(30, 10, 0, 10);
        panel.add(scrollPane, gbc);
        UpdateOrderLabels();
        return panel;
    }

    // this function updates all of the orders in the side panel based on the order list
    // any entry starting with a '+' is the full food item, anything right after until the next '+' will be ingredients to remove from that food item
    private void UpdateOrderLabels() {
        
        // // Clear existing labels
        currentOrdersList.removeAll(); 

        // // loop to fill list
        // for (String text : orderLabelList) {
        //     JLabel label = new JLabel(text);
        //     currentOrdersList.add(label);
        // }

        // currentOrdersList.revalidate();
        // currentOrdersList.repaint();

        for (String text : new ArrayList<>(orderLabelList)) { // Use a copy to avoid concurrent modification
            
            JPanel labelWithButtonPanel = new JPanel();
            labelWithButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
            
            if (text.charAt(0) == '#') {
           
                JLabel label = new JLabel(text);
                JButton removeButton = new JButton("X");
                removeButton.setForeground(Color.WHITE);
                removeButton.setBackground(Color.RED);
                removeButton.addActionListener((ActionEvent e) -> {
                    int index = orderLabelList.indexOf(label.getText());
                    orderLabelList.remove(index);
                    // remove all from start
                    while ((orderLabelList.size() > index) && (orderLabelList.get(index).charAt(0) != '#')) {
                        orderLabelList.remove(orderLabelList.get(index));
                    }

                    UpdateOrderLabels();
                });
                labelWithButtonPanel.add(label);
                labelWithButtonPanel.add(removeButton);
            }
            else {
                
                JLabel label = new JLabel(text);
                labelWithButtonPanel.add(label);
            }
            currentOrdersList.add(labelWithButtonPanel);
        }

        currentOrdersList.revalidate();
        currentOrdersList.repaint();
    }

    public void confirmOrderWithPayment() {
        Double theCost = GetTotalPrice();
        String paymentMethod = "yes";
        String sqlStatements = "INSERT INTO ticket(\"ticketID\", \"timeOrdered\", \"totalCost\", payment) VALUES(" + currTicketID + ", date (LOCALTIMESTAMP), " + Double.toString(theCost) + ", " + paymentMethod + ");";
        
        java.util.List<Integer> itemPositions = new ArrayList<>();
        // get the item indexes
        for (int index=0; index < orderLabelList.size(); index++) {
            if (orderLabelList.get(index).charAt(0) == '#') {
                itemPositions.add(index);
            }
        }
        for (int index=0; index < itemPositions.size(); index++) {
            if (((index+1) != itemPositions.size())) {
                int diffPos = itemPositions.get(index+1) - itemPositions.get(index);
                if (diffPos == 0) {
                    sqlStatements += "";
                }
            }
        }
    }

    // calculate the total price for the order
    public Double GetTotalPrice() {
        Double theTotalCost = 0.0;
        for (String iText : orderLabelList) {
            if (iText.get(0) == '#') {
                String foodItem = iText.split("\\s+")[1];
                System.out.println(foodItem + "\n");
                theTotalCost += currentMenu.GetPrice(foodItem);
            }
        }
        System.out.println(theTotalCost+"\n");
        return theTotalCost;
    }

    public static void main(String a[]) {
        SwingUtilities.invokeLater(() -> new mainMenu());
    }
}

