package ru.eltex.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.eltex.phonebook.PhoneBook;
import ru.eltex.phonebook.User;

import java.util.List;

@RestController
public class UsersController {
    @GetMapping("/phonebook")
    public List<User> getUsers() {
        return PhoneBook.getInstance().getUsers();
    }
}