package ru.eltex.phonebook;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class PhoneBook {
    private static ArrayList<User> users = new ArrayList<>();
    private final String filename;
    
    public static void main(String[] args) throws IOException {
        PhoneBook phonebook = new PhoneBook("phonebook.csv");
        phonebook.enterMenu();
        phonebook.save();
    }

    public PhoneBook(String filename) throws IOException {
        this.filename = filename;

        try (FileReader reader = new FileReader(filename); Scanner scanner = new Scanner(reader)) {
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                User user = new User();
                user.initWithCSV(line);
                users.add(user);
            }
        }
        catch(FileNotFoundException e) {
            System.out.println("File '" + filename + "' was not found. A new one will be created on exit.");
            return;
        }
        catch(IOException e) {
            System.err.println("Couldn't open file '" + filename + "'");
            throw e;
        }
    }

    private void enterMenu() {
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

    public void save() throws IOException {
        try(FileWriter writer = new FileWriter(filename)) {
            for(User user : users) {
                writer.write(user.toCSV() + "\n");
            }
        }
        catch(IOException e) {
            System.err.println("Failed to save to file '" + filename + "'");
            throw e;
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
        
        System.out.printf("User added with ID = %d\n\n", user.getId());
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
