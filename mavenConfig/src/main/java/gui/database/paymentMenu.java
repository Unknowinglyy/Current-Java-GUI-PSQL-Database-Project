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

    public paymentMenu(double totalAmount) {
        this.totalAmount = totalAmount;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Payment Window");
        setSize(300, 200);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        paymentMethodComboBox = new JComboBox<>(new String[] { "Cash", "Card", "Meal Swipes", "Dining Dollars"});
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
                JOptionPane.showMessageDialog(paymentMenu.this, "Payment submitted successfully!");
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the window or reset fields
                paymentMenu.this.dispose(); // Close the window
            }
        });

        controlPanel.add(submitButton);
        controlPanel.add(cancelButton);
        add(controlPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new paymentMenu(50.00).setVisible(true); // Example total amount
            }
        });
    }
}
