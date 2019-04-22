package ru.eltex.phonebook;

import java.util.List;
import java.util.Scanner;

/**
 * Singleton class for working with the phone book
 */
public class PhoneBook {

    public static final PhoneBook INSTANCE = new PhoneBook();

    private final PhoneBookStorage storage;

    /**
     * Allocates a new phone book object
     */
    private PhoneBook() {
        storage = new DBStorage("users");
    }

    /**
     * Get all {@link User}s from the phone book
     * @return list of {@link User}s stored in the phone book
     */
    public List<User> getUsers() {
        return storage.getAllUsers();
    }

    /**
     * Open console menu for interaction with the phone book and wait for inputs.
     * <p>The options are:
     * <p>1. List all the users
     * <p>2. Create new user
     * <p>3. Remove existing user
     * <p>0. Exit
     */
    public void enterMenu() {
        Scanner scanner = createStdinScanner();
        while (true) {
            System.out.println("Menu:");
            System.out.println("  1. Print all users\n  2. Add user\n  3. Remove user\n  0. Exit");
            System.out.print("Enter option: ");

            int option = scanner.nextInt();
            switch (option) {
                case 1: printUsers(); break;
                case 2: addUser(); break;
                case 3: removeUser(); break;
                case 0: return;
            }
        }
    }

    /**
     * Print all users stored in the phone book
     */
    private void printUsers() {
        List<User> users = storage.getAllUsers();
        if (users.size() == 0) {
            System.out.println("No users\n");
            return;
        }

        System.out.printf("%3s %30s %20s%n", "ID", "Name", "Phone number");
        for (User user : users) {
            System.out.printf("%3d %30s %20s%n", user.getId(), user.getName(), user.getPhoneNumber());
        }
        System.out.println();
    }

    /**
     * Start dialog for user creation
     */
    private void addUser() {
        Scanner scanner = createStdinScanner();
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Phone number: ");
        String phoneNumber = scanner.nextLine();

        storage.insertNewUser(name, phoneNumber);
    }

    /**
     * Start dialog for user deletion
     */
    private void removeUser() {
        Scanner scanner = createStdinScanner();
        System.out.println("ID: ");
        int id = scanner.nextInt();

        storage.removeUserById(id);
    }


    private static Scanner createStdinScanner() {
        return new Scanner(System.in, "UTF-8");
    }
}
