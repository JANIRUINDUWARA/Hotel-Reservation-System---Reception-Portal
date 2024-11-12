package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class ReserveAccommodationPage extends JFrame {

    private JTextField customerID, name, email, totalBill;
    private JComboBox<String> comboBox;
    private JSpinner spinner1, spinner2, dateSpinner1, dateSpinner2;
    private boolean isBillCalculated = false; // Flag to track if the bill is calculated

    public ReserveAccommodationPage() {
        setTitle("Reseve Accommodations");
        setSize(1300, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        BackgroundPanel backgroundPanel = new BackgroundPanel("C:\\Users\\janir\\OneDrive\\Desktop\\Academics\\11-Enterprise Application Development-1\\Eclipse\\Hotel_Management_System\\Images\\Reserve Accommodation Page.png");
        setContentPane(backgroundPanel);

        customerID = new JTextField();
        customerID.setBounds(350, 145, 510, 35);
        customerID.setEditable(false); // Make ID field read-only
        backgroundPanel.add(customerID);

        name = new JTextField();
        name.setBounds(350, 205, 510, 35);
        backgroundPanel.add(name);

        email = new JTextField();
        email.setBounds(350, 265, 510, 35);
        backgroundPanel.add(email);

        String[] roomtype = { "De Luxe Room", "De Luxe Sea View", "The Wellhall Family Suite" };
        comboBox = new JComboBox<>(roomtype);
        comboBox.setBounds(350, 325, 510, 35);
        backgroundPanel.add(comboBox);

        SpinnerNumberModel numAdult = new SpinnerNumberModel(1, 1, 4, 1);
        spinner1 = new JSpinner(numAdult);
        spinner1.setBounds(350, 385, 100, 35);
        backgroundPanel.add(spinner1);

        SpinnerNumberModel numChild = new SpinnerNumberModel(0, 0, 2, 1);
        spinner2 = new JSpinner(numChild);
        spinner2.setBounds(350, 445, 100, 35);
        backgroundPanel.add(spinner2);

        SpinnerDateModel checkin = new SpinnerDateModel();
        dateSpinner1 = new JSpinner(checkin);
        JSpinner.DateEditor dateEditor1 = new JSpinner.DateEditor(dateSpinner1, "dd-MM-yyyy");
        dateSpinner1.setEditor(dateEditor1);
        dateSpinner1.setBounds(350, 505, 300, 35);
        backgroundPanel.add(dateSpinner1);

        SpinnerDateModel checkout = new SpinnerDateModel();
        dateSpinner2 = new JSpinner(checkout);
        JSpinner.DateEditor dateEditor2 = new JSpinner.DateEditor(dateSpinner2, "dd-MM-yyyy");
        dateSpinner2.setEditor(dateEditor2);
        dateSpinner2.setBounds(350, 565, 300, 35);
        backgroundPanel.add(dateSpinner2);

        totalBill = new JTextField(); 
        totalBill.setBounds(350, 625, 510, 35);
        totalBill.setEditable(false); // Make Total Bill read-only
        backgroundPanel.add(totalBill);

        JButton calculateBillButton = new JButton("Calculate Total Bill"); // New Calculate Total Bill button
        calculateBillButton.setBounds(880, 625, 200, 40);
        calculateBillButton.setBackground(new Color(0x9C8F85));
        calculateBillButton.setForeground(Color.WHITE);
        calculateBillButton.setFont(new Font("Arial", Font.BOLD, 16));
        backgroundPanel.add(calculateBillButton);

        JButton backButton = new JButton("< BACK");
        backButton.setBounds(450, 700, 150, 40);
        backButton.setBackground(new Color(0x9C8F85));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backgroundPanel.add(backButton);

        JButton submitButton = new JButton("Reserve");
        submitButton.setBounds(700, 700, 150, 40);
        submitButton.setBackground(new Color(0x9C8F85));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        backgroundPanel.add(submitButton);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MainPage();
            }
        });

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!isBillCalculated) { // Check if the bill is calculated before submitting
                    JOptionPane.showMessageDialog(null, "Please calculate the total bill before submitting.");
                } else {
                    reserveAccommodation();
                }
            }
        });

        calculateBillButton.addActionListener(new ActionListener() { // Action for Calculate Total Bill
            public void actionPerformed(ActionEvent e) {
                calculateTotalBill();
            }
        });

        backgroundPanel.setLayout(null);
        setLocationRelativeTo(null);

        generateCustomerID(); // Auto-generate customer ID on form load
        setVisible(true);
    }

    private void generateCustomerID() {
        String url = "jdbc:mysql://localhost:3306/hotelmanagementsystem";
        String user = "root";
        String password = "";

        String query = "SELECT customer_id FROM reservations ORDER BY customer_id DESC LIMIT 1";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                String lastID = resultSet.getString("customer_id");
                int numericID = Integer.parseInt(lastID.substring(1)) + 1;
                customerID.setText("C" + String.format("%03d", numericID));
            } else {
                customerID.setText("C001");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error generating customer ID.");
        }
    }

    private void calculateTotalBill() {
        String selectedRoomType = (String) comboBox.getSelectedItem();
        java.util.Date checkInDate = (java.util.Date) dateSpinner1.getValue();
        java.util.Date checkOutDate = (java.util.Date) dateSpinner2.getValue();

        long daysOfStay = TimeUnit.DAYS.convert(checkOutDate.getTime() - checkInDate.getTime(), TimeUnit.MILLISECONDS);
        daysOfStay = Math.max(daysOfStay, 1); // Minimum stay is 1 day

        int roomRate;
        switch (selectedRoomType) {
            case "De Luxe Room":
                roomRate = 5000;
                break;
            case "De Luxe Sea View":
                roomRate = 10000;
                break;
            case "The Wellhall Family Suite":
                roomRate = 15000;
                break;
            default:
                roomRate = 0;
                break;
        }

        int totalCost = (int) (daysOfStay * roomRate);
        totalBill.setText(String.valueOf(totalCost));
        isBillCalculated = true; // Set flag to true after calculating the bill
    }

    private void reserveAccommodation() {
        String customerId = customerID.getText();
        String customerName = name.getText();
        String customerEmail = email.getText();
        String selectedRoomType = (String) comboBox.getSelectedItem();
        int adults = (Integer) spinner1.getValue();
        int children = (Integer) spinner2.getValue();
        java.util.Date checkInDate = (java.util.Date) dateSpinner1.getValue();
        java.util.Date checkOutDate = (java.util.Date) dateSpinner2.getValue();
        String billAmount = totalBill.getText();

        String url = "jdbc:mysql://localhost:3306/hotelmanagementsystem";
        String user = "root";
        String password = "";

        String query = "INSERT INTO reservations (customer_id, name, email, room_type, adults, children, check_in, check_out, total_bill) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, customerId);
            statement.setString(2, customerName);
            statement.setString(3, customerEmail);
            statement.setString(4, selectedRoomType);
            statement.setInt(5, adults);
            statement.setInt(6, children);
            statement.setDate(7, new java.sql.Date(checkInDate.getTime()));
            statement.setDate(8, new java.sql.Date(checkOutDate.getTime()));
            statement.setDouble(9, Double.parseDouble(billAmount)); 

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Reservation successful!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error in reservation. Please try again.");
        }
    }

    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = new ImageIcon(imagePath).getImage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ReserveAccommodationPage::new);
    }
}