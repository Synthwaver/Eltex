package ru.eltex.phonebook;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBStorage extends PhoneBookStorage {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/phonebook?serverTimezone=UTC";
    private static final String LOGIN = "admin";
    private static final String PASSWORD = "qwerty";
    private static final String TABLE_NAME = "users";

    public DBStorage(PhoneBook phoneBook) {
        super(phoneBook);
    }

    @Override
    public List<User> getAllUsers() {
        final String selectQuery = "SELECT * FROM " + TABLE_NAME;
        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, LOGIN, PASSWORD);
             Statement statement = connection.createStatement()) {

            List<User> users = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String phoneNumber = resultSet.getString("phone");
                users.add(new User(id, name, phoneNumber));
            }
            return users;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void insertNewUser(String name, String phoneNumber) {
        final String insertQuery = "INSERT INTO " + TABLE_NAME + " (name, phone) VALUE (?, ?)";

        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, LOGIN, PASSWORD);
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
        final String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE id = " + id;
        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, LOGIN, PASSWORD);
                Statement statement = connection.createStatement()) {
            statement.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}