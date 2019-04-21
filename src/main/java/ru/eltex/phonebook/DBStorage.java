package ru.eltex.phonebook;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBStorage implements PhoneBookStorage {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/phonebook?serverTimezone=UTC";

    private static String getLogin() {
        return "admin";
    }

    private static String getPassword() {
        return "qwerty";
    }

    private final String tableName;

    public DBStorage(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public List<User> getAllUsers() {
        final String selectQuery = "SELECT * FROM " + tableName;
        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, getLogin(), getPassword());
             Statement statement = connection.createStatement()) {

            List<User> users = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery(selectQuery)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String phoneNumber = resultSet.getString("phone");
                    users.add(new User(id, name, phoneNumber));
                }
            }
            return users;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void insertNewUser(String name, String phoneNumber) {
        final String insertQuery = "INSERT INTO " + tableName + " (name, phone) VALUE (?, ?)";

        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, getLogin(), getPassword());
                PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setString(1, name);
            statement.setString(2, phoneNumber);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void removeUserById(int id) {
        final String deleteQuery = "DELETE FROM " + tableName + " WHERE id = " + id;
        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, getLogin(), getPassword());
                Statement statement = connection.createStatement()) {
            statement.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}