package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

	public class AboutAccommodationPage extends JFrame {

	    public AboutAccommodationPage() {
	        setTitle("About Accommodations");
	        setSize(1300, 800);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        // Set the content pane to our custom panel with a background image
	        BackgroundPanel backgroundPanel = new BackgroundPanel("C:\\Users\\janir\\OneDrive\\Desktop\\Academics\\11-Enterprise Application Development-1\\Eclipse\\Hotel_Management_System\\Images\\About Accommodations.png");
	        setContentPane(backgroundPanel);
	       	        
	        JButton backButton = new JButton("< BACK");
	        backButton.setBounds(575, 650, 150, 40); // Position and size of the button
	        backButton.setBackground(new Color(0x9C8F85));
	        backButton.setForeground(Color.WHITE); // Set text color to white
	        backButton.setFont(new Font("Arial", Font.BOLD, 16));
	        backgroundPanel.add(backButton);
	        
	        backButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                dispose(); // Close the current SignUpPage window
	                new MainPage(); // Navigate back to the main window
	            }
	        });
	        	    
	        backgroundPanel.setLayout(null);
	        setLocationRelativeTo(null); // Center the frame
	        setVisible(true);
	    }

	    // Custom JPanel to display background image
	    static class BackgroundPanel extends JPanel {
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