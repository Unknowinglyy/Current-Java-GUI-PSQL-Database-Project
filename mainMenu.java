import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class mainMenu {

    JPanel currentOrdersList;

    mainMenu() {
        JFrame f = new JFrame();

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
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
        scrollPane.setPreferredSize(new Dimension(300, 500));

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(30, 10, 0, 10);
        panel.add(scrollPane, gbc);

        JLabel selectFood = new JLabel("Select Food");
        gbc.gridx++;
        gbc.gridy = 0;
        gbc.insets = new Insets(30, 300, 0, 10);
        panel.add(selectFood, gbc);

        JButton b1 = createButton("Hamburger", "Hamburger");
        JButton b2 = createButton("Rev Burger", "Hamburger");
        JButton b3 = createButton("Bacon Burger", "Hamburger");
        JButton b4 = createButton("Cheeseburger", "Hamburger");
        JButton b5 = createButton("Deluxe Burger", "Hamburger");
        JButton b6 = createButton("Chicken Sandwich", "Hamburger");
        JButton b7 = createButton("Grilled Chicken Sandwich", "Hamburger");
        JButton b8 = createButton("Spicy Chicken Sandwich", "Hamburger");
        JButton b9 = createButton("Texas Toast Patty Melt", "Hamburger");
        JButton b10 = createButton("5 Chicken Tenders Box", "Hamburger");
        JButton b11 = createButton("3 Chicken Tenders Box", "Hamburger");
        JButton b12 = createButton("3 Chicken Tenders Box", "Hamburger");
        JButton b13 = createButton("3 Chicken Tenders Box", "Hamburger");
        JButton b14 = createButton("3 Chicken Tenders Box", "Hamburger");
        JButton b15 = createButton("3 Chicken Tenders Box", "Hamburger");


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
        gbc.insets = new Insets(30, 300, 50, 10);
        panel.add(foodItems, gbc);

        JLabel drinksAndCondiments = new JLabel("Drinks & Condiments");
        gbc.gridy = 2;
        gbc.insets = new Insets(30, 300, 0, 10);
        panel.add(drinksAndCondiments, gbc);

        JButton b16 = createButton("Fountain Drink", "Hamburger");
        JButton b17 = createButton("Milkshake", "Hamburger");
        JButton b18 = createButton("Condiment 1", "Hamburger");
        JButton b19 = createButton("Condiment 2", "Hamburger");
        JButton b20 = createButton("Condiment 3", "Hamburger");

        JPanel dandcItems = new JPanel(new GridLayout(1, 5, 10, 10));
        gbc.gridy = 3;
        gbc.insets = new Insets(30, 300, 30, 10);
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
        gbc.insets = new Insets(10, 10, 0, 10);
        panel.add(managerView, gbc);

        f.add(panel);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    private JButton createButton(String itemName, String orderName) {
        JButton button = new JButton(itemName);
        Dimension buttonSize = new Dimension(175, 175);
        button.setPreferredSize(buttonSize);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JLabel orderLabel = new JLabel(orderName);
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

