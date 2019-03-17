package ru.eltex.phonebook;

import java.util.List;

public abstract class PhoneBookStorage {
    protected final PhoneBook phoneBook;

    PhoneBookStorage(PhoneBook phoneBook) {
        this.phoneBook = phoneBook;
    }

    public abstract List<User> getAllUsers();
    public abstract void insertNewUser(String name, String phoneNumber);
    public abstract void removeUserById(int id);
}
