package ru.eltex.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.eltex.phonebook.PhoneBook;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(App.class, args);
        PhoneBook.INSTANCE.enterMenu();
        SpringApplication.exit(context);
    }
}