package view;

import javax.swing.*;

import controller.ManageAccommodationController;
import model.ManageAccommodationModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends JFrame {

    public MainPage() {
        setTitle("Receptionist SignUp");
        setSize(1300, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        BackgroundPanel backgroundPanel = new BackgroundPanel("C:\\Users\\janir\\OneDrive\\Desktop\\Academics\\11-Enterprise Application Development-1\\Eclipse\\Hotel_Management_System\\Images\\MainView.png");
        setContentPane(backgroundPanel);        
        
        ImageIcon reserveAccommodationIcon = new ImageIcon("C:\\Users\\janir\\OneDrive\\Desktop\\Academics\\11-Enterprise Application Development-1\\Eclipse\\Hotel_Management_System\\Images\\Reserve Accommodation.png");
        ImageIcon aboutAccommodationIcon = new ImageIcon("C:\\Users\\janir\\OneDrive\\Desktop\\Academics\\11-Enterprise Application Development-1\\Eclipse\\Hotel_Management_System\\Images\\About Accommodation.png");
        ImageIcon manageAccommodationIcon = new ImageIcon("C:\\Users\\janir\\OneDrive\\Desktop\\Academics\\11-Enterprise Application Development-1\\Eclipse\\Hotel_Management_System\\Images\\Manage Accommodations.png");
        ImageIcon otherServicesIcon = new ImageIcon("C:\\Users\\janir\\OneDrive\\Desktop\\Academics\\11-Enterprise Application Development-1\\Eclipse\\Hotel_Management_System\\Images\\Other Services.png");
        ImageIcon logoIcon = new ImageIcon("C:\\Users\\janir\\OneDrive\\Desktop\\Academics\\11-Enterprise Application Development-1\\Eclipse\\Hotel_Management_System\\Images\\logo.png");

        JButton ReserveAccommodationButton = new JButton("Reserve Accommodation", reserveAccommodationIcon);
        ReserveAccommodationButton.setBounds(130, 240, 500, 240);
        ReserveAccommodationButton.setContentAreaFilled(false);
        ReserveAccommodationButton.setFocusPainted(false);
        ReserveAccommodationButton.setOpaque(false);
        backgroundPanel.add(ReserveAccommodationButton);

        JButton AboutAccommodationButton = new JButton("About Accommodation", aboutAccommodationIcon);
        AboutAccommodationButton.setBounds(650, 240, 500, 240);
        AboutAccommodationButton.setContentAreaFilled(false);
        AboutAccommodationButton.setFocusPainted(false);
        AboutAccommodationButton.setOpaque(false);
        backgroundPanel.add(AboutAccommodationButton);

        JButton ManageAccommodationButton = new JButton("Manage Accommodation", manageAccommodationIcon);
        ManageAccommodationButton.setBounds(130, 500, 500, 240);
        ManageAccommodationButton.setContentAreaFilled(false);
        ManageAccommodationButton.setFocusPainted(false);
        ManageAccommodationButton.setOpaque(false);
        backgroundPanel.add(ManageAccommodationButton);
        
        JButton OtherServicesButton = new JButton("Other Services", otherServicesIcon);
        OtherServicesButton.setBounds(650, 500, 500, 240);
        OtherServicesButton.setContentAreaFilled(false);
        OtherServicesButton.setFocusPainted(false);
        OtherServicesButton.setOpaque(false);
        backgroundPanel.add(OtherServicesButton);
        
        JButton logoButton = new JButton(logoIcon);
        logoButton.setBounds(30, 30, 100, 100);
        logoButton.setBorderPainted(false);
        logoButton.setContentAreaFilled(false);
        logoButton.setFocusPainted(false);
        logoButton.setOpaque(false);
        backgroundPanel.add(logoButton);

        backgroundPanel.setLayout(null);
        
        ReserveAccommodationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ReserveAccommodationPage();
            }
        });

        AboutAccommodationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AboutAccommodationPage();
            }
        });
        
        ManageAccommodationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                ManageAccommodationModel model = new ManageAccommodationModel();
                ManageAccommodationView view = new ManageAccommodationView();
                new ManageAccommodationController(model, view);
            }
        });
        
        OtherServicesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Site is Under Maintenance");
            }
        });
        
        logoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new FirstPage();
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
        
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
