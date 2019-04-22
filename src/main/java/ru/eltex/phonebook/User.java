package ru.eltex.phonebook;

/**
 * Class representing a user stored in the phone book
 */
public class User {
    private int id;
    private String name;
    private String phoneNumber;

    /**
     * Allocates a new user object and initializes its fields with passed params
     * @param id the ID of new user
     * @param name the name of the new user
     * @param phoneNumber the phone number of the new user
     */
    public User(int id, String name, String phoneNumber) {
        setId(id);
        setName(name);
        setPhoneNumber(phoneNumber);
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
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
