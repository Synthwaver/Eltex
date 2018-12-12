package ru.eltex.phonebook;

import java.util.ArrayList;
import java.util.Scanner;

public class PhoneBook {
    private static ArrayList<User> users = new ArrayList<>();
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Menu:");
        while (true) {
            System.out.println("  1. Print all users\n  2. Add user\n  3. Remove user\n  0. Exit");
            System.out.println("Enter option:");

            int option = scanner.nextInt();
            switch (option) {
                case 1: printAllUsers(); break;
                case 2: addUser(); break;
                case 3: removeUser(); break;
                case 0: return;
            }
        }
    }
    
    private static void printAllUsers() {
        if (users.size() == 0) {
            System.out.println("No users\n");
            return;
        }

        System.out.printf("%3s %25s %20s\n", "ID", "Name", "Phone number");
        for (User user : users) {
            System.out.printf("%3d %25s %20s\n", user.getId(), user.getName(), user.getPhoneNumber());
        }
        System.out.println();
    }
    
    private static void addUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.println("Phone number: ");
        String phoneNumber = scanner.nextLine();

        User user = new User(name, phoneNumber);
        users.add(user);
        
        System.out.printf("User added with ID = %d\n", user.getId());
    }
    
    private static void removeUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("ID: ");
        int id = scanner.nextInt();
        
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                users.remove(i);
                System.out.println("User removed\n");
                return;
            }
        }
        System.out.printf("User not found\n");
    }
}
