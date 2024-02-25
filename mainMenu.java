import java.awt.*;
 
class mainMenu {
    mainMenu()
    {
        Frame f = new Frame();

        Label currentOrder = new Label("Current Order");
        currentOrder.setBounds(100, 50, 400, 20); 
        f.add(currentOrder);
 
        Label foodItems = new Label("Select Food");
        foodItems.setBounds(1000, 50, 400, 20); 
        f.add(foodItems);

        Button b1 = new Button("Hamburger");
        b1.setBounds(1000, 100, 200, 200);
        f.add(b1);

        Button b2 = new Button("Rev Burger");
        b2.setBounds(1200, 100, 200, 200);
        f.add(b2);
       
        Button b3 = new Button("Bacon Burger");
        b3.setBounds(1400, 100, 200, 200);
        f.add(b3);

        Button b4 = new Button("Cheeseburger");
        b4.setBounds(1600, 100, 200, 200); 
        f.add(b4);

        Button b5 = new Button("Deluxe Burger");
        b5.setBounds(1800, 100, 200, 200); 
        f.add(b5);

        Button b6 = new Button("Chicken Sandwich");
        b6.setBounds(2000, 100, 200, 200); 
        f.add(b6);

        Button b7 = new Button("Grilled Chicken Sandwich");
        b7.setBounds(2200, 100, 200, 200); 
        f.add(b7);

        Button b8 = new Button("Spicy Chicken Sandwich");
        b8.setBounds(1000, 300, 200, 200); 
        f.add(b8);

        Button b9 = new Button("Texas Toast Patty Melt");
        b9.setBounds(1200, 300, 200, 200); 
        f.add(b9);
        
        Button b10 = new Button("5 Chicken Tenders Box");
        b10.setBounds(1400, 300, 200, 200); 
        f.add(b10);

        Label drinksAndCondiments = new Label("Drinks & Condiments");
        drinksAndCondiments.setBounds(1000, 1000, 400, 20); 
        f.add(drinksAndCondiments);

        Button b11 = new Button("Drink 1");
        b11.setBounds(1000, 1050, 200, 200); 
        f.add(b11);

        Button b12 = new Button("Drink 2");
        b12.setBounds(1200, 1050, 200, 200); 
        f.add(b12);

        Button b13 = new Button("Drink 3");
        b13.setBounds(1400, 1050, 200, 200); 
        f.add(b13);


        f.setSize(3000, 3000);
        f.setLayout(null);
        f.setVisible(true);
    }
 
    public static void main(String a[]) { new mainMenu(); }
}
