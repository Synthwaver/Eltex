package ru.eltex.phonebook;

public class User {
    private static int idCounter = 0;
    
    private final int id;
    private String name;
    private String phoneNumber;
    
    public User(String name, String phoneNumber) {
        id = ++idCounter;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
