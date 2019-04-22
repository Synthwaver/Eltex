package ru.eltex.phonebook;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class providing access to the phone book MySQL database
 */
public class DBStorage implements PhoneBookStorage {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/phonebook?serverTimezone=UTC";

    private static String getLogin() {
        return "admin";
    }

    private static String getPassword() {
        return "qwerty";
    }

    private static final Logger logger = LogManager.getLogger(DBStorage.class);

    private final String tableName;

    /**
     * Allocates {@link DBStorage} object for further work with database table
     * @param tableName the name of database table
     */
    public DBStorage(String tableName) {
        this.tableName = tableName;
        logger.debug("Created DBStorage instance with tableName = '" + tableName + "'");
    }

    /**
     * Get all users from the table
     * @return list of {@link User}s obtained from the table
     */
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
            logger.debug("getAllUsers() for table '" + tableName + "' returned " + users.size() + " user(s)");
            return users;
        } catch (SQLException e) {
            logger.warn("Failed to obtain users from the database", e);
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * Create new user in the database table
     * @param name the name of new user
     * @param phoneNumber the phone number of new user
     */
    @Override
    public void insertNewUser(String name, String phoneNumber) {
        final String insertQuery = "INSERT INTO " + tableName + " (name, phone) VALUE (?, ?)";

        logger.info("Inserting new user into table '" + tableName + "' with name=  '"
                + name + "', phoneNumber = '" + phoneNumber + "'...");
        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, getLogin(), getPassword());
                PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setString(1, name);
            statement.setString(2, phoneNumber);
            statement.executeUpdate();
            logger.info("User with name '" + name + "' inserted successfully");
        } catch (SQLException e) {
            logger.warn("Failed to create a new user with name = '" +
                    name + "', phone number = '" + phoneNumber + "'", e);
            System.err.println(e.getMessage());
        }
    }

    /**
     * Remove user from the database table by ID
     * @param id the id of user to remove
     */
    @Override
    public void removeUserById(int id) {
        final String deleteQuery = "DELETE FROM " + tableName + " WHERE id = " + id;
        logger.info("Removing user with ID " + id + "...");
        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, getLogin(), getPassword());
                Statement statement = connection.createStatement()) {
            boolean success = 1 == statement.executeUpdate(deleteQuery);
            logger.info("User with ID " + id + (success ? " removed successfully" : " was not found"));
        } catch (SQLException e) {
            logger.warn("Failed to remove user with ID " + id, e);
            System.err.println(e.getMessage());
        }
    }
}