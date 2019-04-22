package ru.eltex.phonebook;

import java.util.List;

/**
 * Interface representing a means of interaction with the phone book storage
 */
public interface PhoneBookStorage {
    /**
     * Get list of all users stored in the storage
     * @return list of {@link User}s stored in the storage
     */
    List<User> getAllUsers();

    /**
     * Create new user and put it in the storage
     * @param name the name of new user
     * @param phoneNumber the phone number of new user
     */
    void insertNewUser(String name, String phoneNumber);

    /**
     * Remove an existing user by ID from the storage
     * @param id The ID of the user to remove
     */
    void removeUserById(int id);
}
