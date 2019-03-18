package ru.eltex.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.eltex.phonebook.PhoneBook;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        PhoneBook phoneBook = PhoneBook.getInstance();
        phoneBook.enterMenu();
        SpringApplication.run(App.class, args);
    }
}