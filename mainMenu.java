import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class mainMenu {
    Menu currentMenu = new Menu();
    JPanel currentOrdersList;
    GridBagConstraints gbc = new GridBagConstraints();
    JFrame f = new JFrame();
    JPanel panel = new JPanel();
    JPanel foodCatagories = new JPanel(new GridLayout(3, 5, 10, 10));
    

    mainMenu() {
        panel.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(30, 10, 0, 10);
        gbc.weightx = 0;
        gbc.weighty = 0;

        JLabel currentOrder = new JLabel("Current Order");
        gbc.insets = new Insets(30, 10, 0, 10);
        panel.add(currentOrder, gbc);

        currentOrdersList = new JPanel();
        currentOrdersList.setLayout(new BoxLayout(currentOrdersList, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(currentOrdersList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(300, 600));

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(30, 10, 0, 10);
        panel.add(scrollPane, gbc);

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

        JLabel drinksAndCondiments = new JLabel("Drinks & Condiments");
        gbc.gridy = 2;
        gbc.insets = new Insets(30, 200, 0, 10);
        panel.add(drinksAndCondiments, gbc);

        JButton b16 = createButton("Fountain Drink");
        JButton b17 = createButton("Milkshake");
        JButton b18 = createButton("Condiment 1");
        JButton b19 = createButton("Condiment 2");
        JButton b20 = createButton("Condiment 3");

        JPanel dandcItems = new JPanel(new GridLayout(1, 5, 10, 10));
        gbc.gridy = 3;
        gbc.insets = new Insets(30, 200, 30, 10);
        dandcItems.add(b16);
        dandcItems.add(b17);
        dandcItems.add(b18);
        dandcItems.add(b19);
        dandcItems.add(b20);

        gbc.gridy++;
        panel.add(dandcItems, gbc);

        JButton managerView = new JButton("Manager View");
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

    private void CreateFoodPanel(String Catagory){
        Vector<String> Foods = currentMenu.GetFoodFromFoodType(Catagory);
        foodCatagories.removeAll();
        //foodCatagories = new JPanel(new GridLayout(3, 5, 10, 10));
        gbc.gridy = 1;
        gbc.insets = new Insets(30, 200, 50, 10);
        for(int i = 0; i < Foods.size();i++){
            JButton b1 = createFoodButton(Foods.get(i));
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
        for(int i = 0; i < Recipe.size();i++){
            JButton b1 = createIngredientButton(Recipe.get(i));
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
                // JLabel orderLabel = new JLabel(itemName);
                // currentOrdersList.add(orderLabel);
                // currentOrdersList.revalidate();
                // currentOrdersList.repaint();
            }
        });
        return button;
    }

    private JButton createFoodButton(String itemName){
        JButton button = new JButton(itemName);
        Dimension buttonSize = new Dimension(175, 175);
        button.setPreferredSize(buttonSize);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateIngredientsPanel(itemName);
                // JLabel orderLabel = new JLabel(itemName);
                // currentOrdersList.add(orderLabel);
                // currentOrdersList.revalidate();
                // currentOrdersList.repaint();
            }
        });
        return button;
    }

    private JButton createIngredientButton(String itemName){
        JButton button = new JButton(itemName);
        Dimension buttonSize = new Dimension(175, 175);
        button.setPreferredSize(buttonSize);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // JLabel orderLabel = new JLabel(itemName);
                // currentOrdersList.add(orderLabel);
                // currentOrdersList.revalidate();
                // currentOrdersList.repaint();
            }
        });
        return button;
    }

    private JButton AddToOrderButton(){
        JButton button = new JButton("Add To Order");
        Dimension buttonSize = new Dimension(175, 175);
        button.setPreferredSize(buttonSize);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateFoodCatagoriesPanel();
                // JLabel orderLabel = new JLabel(itemName);
                // currentOrdersList.add(orderLabel);
                // currentOrdersList.revalidate();
                // currentOrdersList.repaint();
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
                currentOrdersList.add(orderLabel);
                currentOrdersList.revalidate();
                currentOrdersList.repaint();
            }
        });
        return button;
    }

    public static void main(String a[]) {
        SwingUtilities.invokeLater(() -> new mainMenu());
    }
}

