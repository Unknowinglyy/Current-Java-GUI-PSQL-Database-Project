import java.awt.*;
 
class mainMenu {
    mainMenu()
    {
        Frame f = new Frame();
 
        Label headerLabel = new Label("Select Food:");
        headerLabel.setBounds(1000, 50, 400, 20); 
        f.add(headerLabel);

        Button b1 = new Button("Burger");
        b1.setBounds(1000, 90, 200, 200);
        f.add(b1);

        Button b2 = new Button("Cheeseburger");
        b2.setBounds(1200, 90, 200, 200);
        f.add(b2);
       
        Button b3 = new Button("Tender");
        b3.setBounds(1400, 90, 200, 200);
        f.add(b3);

        Button b4 = new Button("Sandwich"); // Corrected variable name
        b4.setBounds(1600, 90, 200, 200); // Corrected button position
        f.add(b4);

        f.setSize(3000, 3000);
        f.setLayout(null);
        f.setVisible(true);
    }
 
    public static void main(String a[]) { new mainMenu(); }
}
