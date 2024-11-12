package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FirstPage extends JFrame {

    public FirstPage() {
        setTitle("Home Page");
        setSize(1300, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        BackgroundPanel backgroundPanel = new BackgroundPanel("C:\\Users\\janir\\OneDrive\\Desktop\\Academics\\11-Enterprise Application Development-1\\Eclipse\\Hotel_Management_System\\Images\\Home.png");
        setContentPane(backgroundPanel);
        
        JButton loginButton = new JButton("LOGIN");
        loginButton.setBounds(1080, 20, 190, 40);
        loginButton.setBackground(new Color(0xF4F1ED));
        loginButton.setForeground(Color.BLACK);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        backgroundPanel.add(loginButton);
        
        JButton signupButton = new JButton("SIGN UP");
        signupButton.setBounds(1080, 80, 190, 40);
        signupButton.setBackground(new Color(0xF4F1ED));
        signupButton.setForeground(Color.BLACK);
        signupButton.setFont(new Font("Arial", Font.BOLD, 16));
        backgroundPanel.add(signupButton);
        
        backgroundPanel.setLayout(null);
        
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	new LoginPage();
            }
        });
        
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	new SignUpPage();
            }
        });
        
        setLocationRelativeTo(null);
        setVisible(true);
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
