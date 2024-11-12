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
import java.sql.Statement;

public class SignUpPage extends JFrame {

    private JTextField employeeIdField;
    private JTextField usernameField;
    private JPasswordField setpasswordField;
    private JPasswordField confirmpasswordField;
    private JRadioButton showPasswordButton1;
    private JRadioButton showPasswordButton2;

    public SignUpPage() {
        setTitle("Receptionist SignUp");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        BackgroundPanel backgroundPanel = new BackgroundPanel("C:\\Users\\janir\\OneDrive\\Desktop\\Academics\\11-Enterprise Application Development-1\\Eclipse\\Hotel_Management_System\\Images\\Sign Up Page.png");
        setContentPane(backgroundPanel);
       
        employeeIdField = new JTextField();
        employeeIdField.setBounds(339, 224, 320, 35);
        employeeIdField.setEditable(false);
        backgroundPanel.add(employeeIdField);

        String newEmployeeId = generateEmployeeId();
        employeeIdField.setText(newEmployeeId);
        
        usernameField = new JTextField();
        usernameField.setBounds(339, 325, 320, 35);
        backgroundPanel.add(usernameField);
        
        setpasswordField = new JPasswordField();
        setpasswordField.setBounds(339, 427, 320, 35);
        backgroundPanel.add(setpasswordField);
        
        confirmpasswordField = new JPasswordField();
        confirmpasswordField.setBounds(339, 528, 320, 35);
        backgroundPanel.add(confirmpasswordField);

        JRadioButton showPasswordButton1 = new JRadioButton("Show");
        showPasswordButton1.setBounds(680, 427, 65, 30);
        showPasswordButton1.setBackground(new Color(0x9C8F85));
        showPasswordButton1.setForeground(Color.WHITE);
        backgroundPanel.add(showPasswordButton1);

        showPasswordButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (showPasswordButton1.isSelected()) {
                    setpasswordField.setEchoChar((char) 0);
                } else {
                    setpasswordField.setEchoChar('*');
                }
            }
        });
        
        JRadioButton showPasswordButton2 = new JRadioButton("Show");
        showPasswordButton2.setBounds(680, 528, 65, 30);
        showPasswordButton2.setBackground(new Color(0x9C8F85));
        showPasswordButton2.setForeground(Color.WHITE);
        backgroundPanel.add(showPasswordButton2);

        showPasswordButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (showPasswordButton2.isSelected()) {
                    confirmpasswordField.setEchoChar((char) 0);
                } else {
                    confirmpasswordField.setEchoChar('*');
                }
            }
        });
        
        JButton backButton = new JButton("< BACK");
        backButton.setBounds(339, 630, 150, 40);
        backButton.setBackground(new Color(0x9C8F85));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backgroundPanel.add(backButton);
        
        JButton submitButton = new JButton("NEXT >");
        submitButton.setBounds(508, 630, 150, 40);
        submitButton.setBackground(new Color(0x9C8F85));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        backgroundPanel.add(submitButton);
        backgroundPanel.setLayout(null);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String employeeId = employeeIdField.getText();
                String username = usernameField.getText();
                String password = new String(setpasswordField.getPassword());
                String confirmPassword = new String(confirmpasswordField.getPassword());

                if (username.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Username cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Confirm Password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    saveDataToDatabase(employeeId, username, password);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new FirstPage();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private String generateEmployeeId() {
        String url = "jdbc:mysql://localhost:3306/hotelmanagementsystem";
        String user = "root";
        String password = "";

        String lastEmployeeId = null;
        String newEmployeeId = null;

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
             
            String query = "SELECT employee_id FROM employees ORDER BY id DESC LIMIT 1";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                lastEmployeeId = resultSet.getString("employee_id");
            }

            if (lastEmployeeId != null) {
                int idNumber = Integer.parseInt(lastEmployeeId.substring(1));
                idNumber++;
                newEmployeeId = "E" + String.format("%03d", idNumber);
            } else {
                newEmployeeId = "E001";
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching Employee ID!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return newEmployeeId;
    }

    private void saveDataToDatabase(String employeeId, String username, String password) {
        String url = "jdbc:mysql://localhost:3306/hotelmanagementsystem";
        String dbUser = "root";
        String dbPassword = "";

        String insertQuery = "INSERT INTO employees (employee_id, username, password) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
             
            preparedStatement.setString(1, employeeId);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Sign Up Successful!");
            
            dispose();
            new LoginPage();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: Could not save data to database!", "Error", JOptionPane.ERROR_MESSAGE);
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
}
