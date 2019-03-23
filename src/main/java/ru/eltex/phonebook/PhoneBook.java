package ru.eltex.phonebook;

import java.util.List;
import java.util.Scanner;

public class PhoneBook {
    private final PhoneBookStorage storage;

    private static PhoneBook instance;
    public static PhoneBook getInstance() {
        if (instance == null) {
            instance = new PhoneBook();
        }
        return instance;
    }

    private PhoneBook() {
        storage = new DBStorage("users");
    }

    public List<User> getUsers() {
        return storage.getAllUsers();
    }

    public void enterMenu() {
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Menu:");
                System.out.println("  1. Print all users\n  2. Add user\n  3. Remove user\n  0. Exit");
                System.out.print("Enter option: ");

                int option = scanner.nextInt();
                switch (option) {
                    case 1: printUsers(); break;
                    case 2: addUser(); break;
                    case 3: removeUser(); break;
                    case 0: System.exit(0);
                }
            }
        }).start();
    }

    private void printUsers() {
        List<User> users = storage.getAllUsers();
        if (users.size() == 0) {
            System.out.println("No users\n");
            return;
        }

        System.out.printf("%3s %30s %20s\n", "ID", "Name", "Phone number");
        for (User user : users) {
            System.out.printf("%3d %30s %20s\n", user.getId(), user.getName(), user.getPhoneNumber());
        }
        System.out.println();
    }
    
    private void addUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Phone number: ");
        String phoneNumber = scanner.nextLine();

        storage.insertNewUser(name, phoneNumber);
    }
    
    private void removeUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("ID: ");
        int id = scanner.nextInt();

        storage.removeUserById(id);
    }
}
