package ru.eltex.phonebook;

public class User implements CSV {
    private static int idCounter = 0;
    
    private int id;
    private String name;
    private String phoneNumber;

    public User() {}

    public User(String name, String phoneNumber) {
        id = ++idCounter;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    private void setId(int id) {
        this.id = id;
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

    @Override
    public String toCSV() {
        return String.format("%d;%s;%s", id, name, phoneNumber);
    }

    @Override
    public void initWithCSV(String csvLine) {
        String[] args = csvLine.split(";");
        if(args.length != 3) {
            throw new IllegalArgumentException(csvLine);
        }
        setId(Integer.parseInt(args[0]));
        setName(args[1]);
        setPhoneNumber(args[2]);
    }
}
