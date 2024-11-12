package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageAccommodationView extends JFrame {

    private JTable reservationTable;
    private DefaultTableModel tableModel;
    private JButton updateButton, deleteButton, backButton;

    public ManageAccommodationView() {
        setTitle("Manage Accommodations");
        setSize(1300, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        BackgroundPanel backgroundPanel = new BackgroundPanel("C:\\Users\\janir\\OneDrive\\Desktop\\Academics\\11-Enterprise Application Development-1\\Eclipse\\Hotel_Management_System\\Images\\Manage Accommodation Page.png");
        setContentPane(backgroundPanel);
        backgroundPanel.setLayout(null);

        String[] columnNames = { "Customer ID", "Name", "Email", "Room Type", "Adults", "Children", "Check In", "Check Out", "Total Bill" };
        tableModel = new DefaultTableModel(columnNames, 0);
        reservationTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(reservationTable);
        scrollPane.setBounds(50, 100, 1200, 500);
        backgroundPanel.add(scrollPane);

        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        backButton = new JButton("< BACK");

        updateButton.setBounds(400, 650, 150, 40);
        deleteButton.setBounds(600, 650, 150, 40);
        backButton.setBounds(800, 650, 150, 40);
        
        updateButton.setBackground(new Color(0x9C8F85));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFont(new Font("Arial", Font.BOLD, 16));
        
        deleteButton.setBackground(new Color(0x9C8F85));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 16));
        
        backButton.setBackground(new Color(0x9C8F85));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        
        backButton.addActionListener(e -> {
            dispose();
            new MainPage();
        });

        backgroundPanel.add(updateButton);
        backgroundPanel.add(deleteButton);
        backgroundPanel.add(backButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void setTableData(List<Object[]> data) {
        tableModel.setRowCount(0); // Clear existing data
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }

    public JTable getReservationTable() {
        return reservationTable;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            backgroundImage = new ImageIcon(imagePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
