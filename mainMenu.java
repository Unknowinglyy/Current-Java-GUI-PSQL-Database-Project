import java.awt.*;
 
class mainMenu {
    mainMenu()
    {
        Frame f = new Frame();
 
        Label headerLabel = new Label("Select Food:");
        headerLabel.setBounds(1000, 50, 400, 20); 
        f.add(headerLabel);

        Button b1 = new Button("Hamburger");
        b1.setBounds(1000, 90, 200, 200);
        f.add(b1);

        Button b2 = new Button("Rev Burger");
        b2.setBounds(1200, 90, 200, 200);
        f.add(b2);
       
        Button b3 = new Button("Bacon Burger");
        b3.setBounds(1400, 90, 200, 200);
        f.add(b3);

        Button b4 = new Button("Cheeseburger");
        b4.setBounds(1600, 90, 200, 200); 
        f.add(b4);

        Button b5 = new Button("Deluxe Burger");
        b5.setBounds(1800, 90, 200, 200); 
        f.add(b5);

        Button b6 = new Button("Chicken Sandwich");
        b6.setBounds(2000, 90, 200, 200); 
        f.add(b6);

        Button b7 = new Button("Grilled Chicken Sandwich");
        b7.setBounds(2200, 90, 200, 200); 
        f.add(b7);

        Button b8 = new Button("Spicy Chicken Sandwich");
        b8.setBounds(1000, 290, 200, 200); 
        f.add(b8);

        Button b9 = new Button("Texas Toast Patty Melt");
        b9.setBounds(1200, 290, 200, 200); 
        f.add(b9);
        
        Button b10 = new Button("5 Chicken Tenders Box");
        b10.setBounds(1400, 290, 200, 200); 
        f.add(b10);


        f.setSize(3000, 3000);
        f.setLayout(null);
        f.setVisible(true);
    }
 
    public static void main(String a[]) { new mainMenu(); }
}
