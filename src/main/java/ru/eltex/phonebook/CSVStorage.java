package ru.eltex.phonebook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVStorage implements PhoneBookStorage {
    private final String csvFileName;

    public CSVStorage(String csvFileName) throws IOException {
        this.csvFileName = csvFileName;

        File file = new File(csvFileName);
        if (!file.exists()){
            file.createNewFile();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (FileReader reader = new FileReader(csvFileName); Scanner scanner = new Scanner(reader)) {
            List<User> users = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String csvLine = scanner.nextLine();
                users.add(new User(csvLine));
            }
            return users;
        } catch (FileNotFoundException e) {
            System.err.println("File '" + csvFileName + "' not found");
            return null;
        } catch (IOException e) {
            System.err.println("Failed to read file '" + csvFileName + "'");
            return null;
        }
    }

    @Override
    public void insertNewUser(String name, String phoneNumber){
        List<User> users = getAllUsers();
        int lastId = users.get(users.size() - 1).getId();

        User newUser = new User(lastId + 1, name, phoneNumber);

        try (FileWriter writer = new FileWriter(csvFileName, true)) {
            writer.write(newUser.toCSV() + "\n");
        } catch (IOException e) {
            System.err.println("Failed to write to file '" + csvFileName + "'");
        }
    }

    @Override
    public void removeUserById(int id) {
        List<User> users = getAllUsers();
        users.removeIf(user -> user.getId() == id);
        writeToFile(users);
    }

    private void writeToFile(List<User> users) {
        try (FileWriter writer = new FileWriter(csvFileName)) {
            for (User user : users) {
                writer.write(user.toCSV() + "\n");
            }
        }
        catch(IOException e) {
            System.err.println("Failed to write to file '" + csvFileName + "'");
        }
    }
}
