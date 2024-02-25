import java.awt.*;
 
class mainMenu {
    mainMenu()
    {
        Frame f = new Frame();
 
        Label headerLabel = new Label("Select Food:");
        headerLabel.setBounds(100, 20, 400, 20); 
        f.add(headerLabel);
 
        Button b1 = new Button("Burger");
        b1.setBounds(100, 50, 200, 200);
        f.add(b1);
 
    
        Button b2 = new Button("Cheeseburger");
        b2.setBounds(200, 50, 200, 200);
        f.add(b2);
       
       
        Button b3 = new Button("Tender");
        b3.setBounds(300, 50, 200, 200);
        f.add(b3);
 
        f.setSize(2000, 2000);
        f.setLayout(null);
        f.setVisible(true);
    }
 
    public static void main(String a[]) { new mainMenu(); }
}
