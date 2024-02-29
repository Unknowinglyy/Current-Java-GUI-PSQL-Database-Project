package gui.database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class paymentMenu extends JFrame {
    private JLabel totalAmountLabel;
    private JComboBox<String> paymentMethodComboBox;
    private JButton submitButton;
    private JButton cancelButton;
    private double totalAmount; // This could be dynamically set based on the POS system
    String outPay = ""; //this variable will be used later in the call of payment menu

    public paymentMenu(double totalAmount) {
        this.totalAmount = totalAmount;
        //function that initalizes the different elements of the payment window such as title, total amount of price, etc.
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Payment Window");
        setSize(300, 200);
        setLayout(new BorderLayout());
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addTotalAmountPanel();
        addPaymentMethodPanel();
        addControlPanel();

        setLocationRelativeTo(null); // Center on screen
    }

    private void addTotalAmountPanel() {
        JPanel totalAmountPanel = new JPanel();

        totalAmountLabel = new JLabel("Total Amount: $" + String.format("%.2f", totalAmount));
        totalAmountPanel.add(totalAmountLabel);

        add(totalAmountPanel, BorderLayout.NORTH);
    }

    private void addPaymentMethodPanel() {
        JPanel paymentMethodPanel = new JPanel();

        //drop down menu that includes all the avaible payment options
        paymentMethodComboBox = new JComboBox<>(new String[] { "Cash", "Credit Card", "Debit Card", "Dining Dollars", "Retail Swipe" });
        paymentMethodPanel.add(paymentMethodComboBox);

        add(paymentMethodPanel, BorderLayout.CENTER);
    }

    private void addControlPanel() {
        JPanel controlPanel = new JPanel();

        submitButton = new JButton("Submit Payment");
        cancelButton = new JButton("Cancel");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement payment processing logic here
                // mainMenu.confirmOrderWithPayment(paymentMethodComboBox.getSelectedItem().toString());
                JOptionPane.showMessageDialog(paymentMenu.this, "Payment submitted successfully!");
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Closing the window
                paymentMenu.this.dispose(); 
            }
        });
        //setting up of the control panel with its needed buttons
        controlPanel.add(submitButton);
        controlPanel.add(cancelButton);

        add(controlPanel, BorderLayout.SOUTH);
        //defining a variable that will be used later in the call to payment menu in main menu class
        outPay = paymentMethodComboBox.getSelectedItem().toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //used for testing the payment menu view with a given price
                new paymentMenu(50.00).setVisible(true); // Example total amount
            }
        });
    }
}
