package ru.eltex.phonebook;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class PhoneBook {
    private ArrayList<User> users = new ArrayList<>();
    private int idCounter;
    private final String filename;
    
    public static void main(String[] args) {
        PhoneBook phonebook = new PhoneBook("phonebook.csv");
        phonebook.enterMenu();
        phonebook.save();
    }

    public PhoneBook(String filename) {
        this.filename = filename;

        try (FileReader reader = new FileReader(filename); Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                User user = new User(line);
                users.add(user);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File '" + filename + "' was not found. A new one will be created on exit.");
        }
        catch (IOException e) {
            System.err.println("Couldn't open file '" + filename + "'");
        }
        initIdCounter();
    }

    private void enterMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Menu:");
            System.out.println("  1. Print all users\n  2. Add user\n  3. Remove user\n  0. Exit");
            System.out.print("Enter option: ");

            int option = scanner.nextInt();
            switch (option) {
                case 1: printAllUsers(); break;
                case 2: addUser(); break;
                case 3: removeUser(); break;
                case 0: return;
            }
        }
    }

    private void save() {
        try (FileWriter writer = new FileWriter(filename)) {
            for (User user : users) {
                writer.write(user.toCSV() + "\n");
            }
        }
        catch (IOException e) {
            System.err.println("Failed to save to file '" + filename + "'");
        }
    }

    private void initIdCounter() {
        int maxId = 0;
        for (User user : users) {
            if (user.getId() > maxId) {
                maxId = user.getId();
            }
        }
        idCounter = maxId;
    }

    private void printAllUsers() {
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

        User user = new User(++idCounter, name, phoneNumber);
        users.add(user);
        
        System.out.printf("User added with ID = %d\n\n", user.getId());
    }
    
    private void removeUser() {
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
        System.out.print("User not found\n");
    }

    public byte[] getUsersHtmlTable() {
        if (users.size() == 0) {
            return "No users".getBytes();
        }

        StringBuilder str = new StringBuilder();
        str.append("<html><center><table>");

        str.append("<style type=\"text/css\">" +
                "table { border-collapse: collapse; }" +
                "td, th { padding: 10px; border: 1px solid black; }" +
                "</style>");

        str.append("<tr><td>ID</td><td>Name</td><td>Phone number</td></tr>");

        for (User user : users) {
            str.append("<tr><td>");
            str.append(user.getId());
            str.append("</td><td>");
            str.append(user.getName());
            str.append("</td><td>");
            str.append(user.getPhoneNumber());
            str.append("</td></tr>");
        }
        str.append("</table></center></html>");

        return str.toString().getBytes();
    }
}
