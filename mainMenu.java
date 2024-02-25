import javax.swing.*;
import java.awt.*; //module for getting user display size

class mainMenu {
    mainMenu() {
        JFrame f = new JFrame();

        JLabel currentOrder = new JLabel("Current Order");
        currentOrder.setBounds(100, 50, 400, 20);
        f.add(currentOrder);

        JLabel foodItems = new JLabel("Select Food");
        foodItems.setBounds(1000, 50, 400, 20);
        f.add(foodItems);

        JButton b1 = new JButton("Hamburger");
        b1.setBounds(1000, 100, 200, 200);
        f.add(b1);

        JButton b2 = new JButton("Rev Burger");
        b2.setBounds(1200, 100, 200, 200);
        f.add(b2);

        JButton b3 = new JButton("Bacon Burger");
        b3.setBounds(1400, 100, 200, 200);
        f.add(b3);

        JButton b4 = new JButton("Cheeseburger");
        b4.setBounds(1600, 100, 200, 200);
        f.add(b4);

        JButton b5 = new JButton("Deluxe Burger");
        b5.setBounds(1800, 100, 200, 200);
        f.add(b5);

        JButton b6 = new JButton("Chicken Sandwich");
        b6.setBounds(2000, 100, 200, 200);
        f.add(b6);

        JButton b7 = new JButton("Grilled Chicken Sandwich");
        b7.setBounds(2200, 100, 200, 200);
        f.add(b7);

        JButton b8 = new JButton("Spicy Chicken Sandwich");
        b8.setBounds(1000, 300, 200, 200);
        f.add(b8);

        JButton b9 = new JButton("Texas Toast Patty Melt");
        b9.setBounds(1200, 300, 200, 200);
        f.add(b9);

        JButton b10 = new JButton("5 Chicken Tenders Box");
        b10.setBounds(1400, 300, 200, 200);
        f.add(b10);

        JLabel drinksAndCondiments = new JLabel("Drinks & Condiments");
        drinksAndCondiments.setBounds(1000, 1000, 400, 20);
        f.add(drinksAndCondiments);

        JButton b11 = new JButton("Drink 1");
        b11.setBounds(1000, 1050, 200, 200);
        f.add(b11);

        JButton b12 = new JButton("Drink 2");
        b12.setBounds(1200, 1050, 200, 200);
        f.add(b12);

        JButton b13 = new JButton("Drink 3");
        b13.setBounds(1400, 1050, 200, 200);
        f.add(b13);

        //get user display size and makes that the frame display size
        Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)displaySize.getWidth();
        int height = (int)displaySize.getHeight();

        f.setSize(width, height);
        f.setLayout(null);
        f.setVisible(true);
    }

    public static void main(String a[]) {
        new mainMenu();
    }
}
