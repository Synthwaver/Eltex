package ru.eltex.phonebook;

import java.util.List;

public interface PhoneBookStorage {
    List<User> getAllUsers();
    void insertNewUser(String name, String phoneNumber);
    void removeUserById(int id);
}
