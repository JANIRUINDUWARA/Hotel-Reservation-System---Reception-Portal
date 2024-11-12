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

public class LoginPage extends JFrame {

    public LoginPage() {
        setTitle("Receptionist Login");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        BackgroundPanel backgroundPanel = new BackgroundPanel("C:\\Users\\janir\\OneDrive\\Desktop\\Academics\\11-Enterprise Application Development-1\\Eclipse\\Hotel_Management_System\\Images\\Login Page.png");
        setContentPane(backgroundPanel);

        JTextField userField = new JTextField();
        userField.setBounds(339, 325, 320, 35);
        backgroundPanel.add(userField);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(339, 426, 320, 35);
        passField.setEchoChar('*'); 
        backgroundPanel.add(passField);

        JRadioButton showPasswordButton = new JRadioButton("Show");
        showPasswordButton.setBounds(680, 426, 65, 30); 
        showPasswordButton.setBackground(new Color(0x9C8F85));
        showPasswordButton.setForeground(Color.WHITE);
        backgroundPanel.add(showPasswordButton);
       
        showPasswordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (showPasswordButton.isSelected()) {
                    passField.setEchoChar((char) 0);
                } else {
                    passField.setEchoChar('*');
                }
            }
        });

        JButton backButton = new JButton("< BACK");
        backButton.setBounds(339, 530, 150, 40);
        backButton.setBackground(new Color(0x9C8F85));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backgroundPanel.add(backButton);

        JButton submitButton = new JButton("NEXT >");
        submitButton.setBounds(508, 530, 150, 40);
        submitButton.setBackground(new Color(0x9C8F85));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        backgroundPanel.add(submitButton);
        backgroundPanel.setLayout(null);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new FirstPage();
            }
        });

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());

                if (checkLogin(username, password)) {
                    JOptionPane.showMessageDialog(null, "Login Successful!");
                    dispose(); 
                    new MainPage(); 
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        JLabel forgotPasswordLabel = new JLabel("<html><u>Forgot Password?</u></html>");
        forgotPasswordLabel.setBounds(339, 580, 150, 30);
        forgotPasswordLabel.setForeground(Color.BLUE);
        forgotPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backgroundPanel.add(forgotPasswordLabel);
        
        JLabel signupLabel = new JLabel("<html>Don't have an receptionist account?<u>Sign up</u></html>"); 
        signupLabel.setBounds(339, 620, 300, 30);
        signupLabel.setForeground(Color.BLUE);
        signupLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backgroundPanel.add(signupLabel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean checkLogin(String username, String password) {
        String url = "jdbc:mysql://localhost:3306/hotelmanagementsystem";
        String dbUser = "root";
        String dbPassword = "";

        String query = "SELECT * FROM employees WHERE username = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection error", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return false;
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
