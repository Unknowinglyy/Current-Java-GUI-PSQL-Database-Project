import javax.swing.*;
import java.awt.*;

class mainMenu {
    mainMenu() {
        JFrame f = new JFrame();

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(30, 10, 0, 10);

        JLabel currentOrder = new JLabel("Current Order");
        gbc.insets = new Insets(30, 10, 0, 10);
        panel.add(currentOrder, gbc);

        JLabel selectFood = new JLabel("Select Food");
        gbc.gridx++;
        gbc.insets = new Insets(30, 400, 0, 10);
        panel.add(selectFood, gbc);

        JButton b1 = createButton("Hamburger");
        JButton b2 = createButton("Rev Burger");
        JButton b3 = createButton("Bacon Burger");
        JButton b4 = createButton("Cheeseburger");
        JButton b5 = createButton("Deluxe Burger");
        JButton b6 = createButton("Chicken Sandwich");
        JButton b7 = createButton("Grilled Chicken Sandwich");
        JButton b8 = createButton("Spicy Chicken Sandwich");
        JButton b9 = createButton("Texas Toast Patty Melt");
        JButton b10 = createButton("5 Chicken Tenders Box");
        JButton b11 = createButton("3 Chicken Tenders Box");
        JButton b12 = createButton("3 Chicken Tenders Box");
        JButton b13 = createButton("3 Chicken Tenders Box");
        JButton b14 = createButton("3 Chicken Tenders Box");
        JButton b15 = createButton("3 Chicken Tenders Box");


        JPanel foodItems = new JPanel(new GridLayout(3, 5, 10, 10));
        foodItems.add(b1);
        foodItems.add(b2);
        foodItems.add(b3);
        foodItems.add(b4);
        foodItems.add(b5);
        foodItems.add(b6);
        foodItems.add(b7);
        foodItems.add(b8);
        foodItems.add(b9);
        foodItems.add(b10);
        foodItems.add(b11);
        foodItems.add(b12);
        foodItems.add(b13);
        foodItems.add(b14);
        foodItems.add(b15);

        gbc.gridy = 1;
        gbc.insets = new Insets(30, 400, 50, 10);
        panel.add(foodItems, gbc);

        JLabel drinksAndCondiments = new JLabel("Drinks & Condiments");
        gbc.gridy = 2;
        gbc.insets = new Insets(30, 400, 0, 10);
        panel.add(drinksAndCondiments, gbc);

        JButton b16 = createButton("Fountain Drink");
        JButton b17 = createButton("Milkshake");
        JButton b18 = createButton("Condiment 1");
        JButton b19 = createButton("Condiment 2");
        JButton b20 = createButton("Condiment 3");

        JPanel dandcItems = new JPanel(new GridLayout(1, 5, 10, 10));
        gbc.gridy = 3;
        gbc.insets = new Insets(30, 400, 30, 10);
        dandcItems.add(b16);
        dandcItems.add(b17);
        dandcItems.add(b18);
        dandcItems.add(b19);
        dandcItems.add(b20);

        gbc.gridy++;
        panel.add(dandcItems, gbc);

        f.add(panel);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setUndecorated(true); 
        f.setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        Dimension buttonSize = new Dimension(175, 175);
        button.setPreferredSize(buttonSize);
        return button;
    }

    public static void main(String a[]) {
        SwingUtilities.invokeLater(() -> new mainMenu());
    }
}
