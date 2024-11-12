package main;

import view.FirstPage;
import view.ManageAccommodationView;
import model.ManageAccommodationModel;


import javax.swing.SwingUtilities;

import controller.ManageAccommodationController;

public class Main {
    public static void main(String[] args) {
        
        // Flag to control the opening of ManageAccommodationview
        boolean openManageAccommodationView = false;

        // Start the application with FirstPage
        SwingUtilities.invokeLater(() -> new FirstPage());       
       
        }
    }

