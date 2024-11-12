// ReservationModel.java
package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManageAccommodationModel {
    private final String url = "jdbc:mysql://localhost:3306/hotelmanagementsystem";
    private final String user = "root";
    private final String password = "";

    public List<Object[]> loadReservations() throws SQLException {
        List<Object[]> reservations = new ArrayList<>();
        String query = "SELECT customer_id, name, email, room_type, adults, children, check_in, check_out, total_bill FROM reservations";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                reservations.add(new Object[]{
                    resultSet.getString("customer_id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("room_type"),
                    resultSet.getInt("adults"),
                    resultSet.getInt("children"),
                    resultSet.getDate("check_in"),
                    resultSet.getDate("check_out"),
                    resultSet.getDouble("total_bill")
                });
            }
        }
        return reservations;
    }

    public boolean updateReservation(String customerId, String name, String email, String roomType, int adults, int children, double totalBill) throws SQLException {
        String query = "UPDATE reservations SET name=?, email=?, room_type=?, adults=?, children=?, total_bill=? WHERE customer_id=?";
        
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, roomType);
            statement.setInt(4, adults);
            statement.setInt(5, children);
            statement.setDouble(6, totalBill);
            statement.setString(7, customerId);

            return statement.executeUpdate() > 0;
        }
    }

    public boolean deleteReservation(String customerId) throws SQLException {
        String query = "DELETE FROM reservations WHERE customer_id=?";
        
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, customerId);
            return statement.executeUpdate() > 0;
        }
    }
}
