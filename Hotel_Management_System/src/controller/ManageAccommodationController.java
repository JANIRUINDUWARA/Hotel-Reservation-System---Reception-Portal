// ManageAccommodationController.java
package controller;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import model.ManageAccommodationModel;
import view.ManageAccommodationView;

public class ManageAccommodationController {
    private final ManageAccommodationModel model;
    private final ManageAccommodationView view;

    public ManageAccommodationController(ManageAccommodationModel model, ManageAccommodationView view) {
        this.model = model;
        this.view = view;
        loadReservations();
        
        view.getUpdateButton().addActionListener(e -> updateReservation());
        view.getDeleteButton().addActionListener(e -> deleteReservation());
        view.getBackButton().addActionListener(e -> view.dispose());
    }

    private void loadReservations() {
        try {
            List<Object[]> data = model.loadReservations();
            view.setTableData(data);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error loading reservations.");
        }
    }

    private void updateReservation() {
        int selectedRow = view.getReservationTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select a reservation to update.");
            return;
        }

        String customerId = (String) view.getReservationTable().getValueAt(selectedRow, 0);
        String name = (String) view.getReservationTable().getValueAt(selectedRow, 1);
        String email = (String) view.getReservationTable().getValueAt(selectedRow, 2);
        String roomType = (String) view.getReservationTable().getValueAt(selectedRow, 3);
        int adults = (Integer) view.getReservationTable().getValueAt(selectedRow, 4);
        int children = (Integer) view.getReservationTable().getValueAt(selectedRow, 5);
        double totalBill = (Double) view.getReservationTable().getValueAt(selectedRow, 8);

        JTextField nameField = new JTextField(name);
        JTextField emailField = new JTextField(email);
        JTextField roomTypeField = new JTextField(roomType);
        JSpinner adultsSpinner = new JSpinner(new SpinnerNumberModel(adults, 1, 4, 1));
        JSpinner childrenSpinner = new JSpinner(new SpinnerNumberModel(children, 0, 2, 1));
        JTextField totalBillField = new JTextField(String.valueOf(totalBill));

        Object[] fields = {
            "Name:", nameField,
            "Email:", emailField,
            "Room Type:", roomTypeField,
            "Adults:", adultsSpinner,
            "Children:", childrenSpinner,
            "Total Bill:", totalBillField
        };

        int result = JOptionPane.showConfirmDialog(view, fields, "Update Reservation", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                boolean success = model.updateReservation(customerId, nameField.getText(), emailField.getText(), roomTypeField.getText(),
                        (Integer) adultsSpinner.getValue(), (Integer) childrenSpinner.getValue(), Double.parseDouble(totalBillField.getText()));
                
                if (success) {
                    loadReservations();
                    JOptionPane.showMessageDialog(view, "Reservation updated successfully!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(view, "Error updating reservation.");
            }
        }
    }

    private void deleteReservation() {
        int selectedRow = view.getReservationTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select a reservation to delete.");
            return;
        }

        String customerId = (String) view.getReservationTable().getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this reservation?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (model.deleteReservation(customerId)) {
                    loadReservations();
                    JOptionPane.showMessageDialog(view, "Reservation deleted successfully!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(view, "Error deleting reservation.");
            }
        }
    }
}
