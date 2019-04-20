package ru.eltex.test;

import org.junit.*;
import ru.eltex.phonebook.DBStorage;
import ru.eltex.phonebook.User;

import java.util.List;

public class DBStorageTests {
    private static DBStorage db;

    @BeforeClass
    public static void initDB() {
        db = new DBStorage("users_test");
    }

    @After
    public void clearDB() {
        List<User> users = db.getAllUsers();
        for (User user : users) {
            db.removeUserById(user.getId());
        }
    }

    @Test
    public void testInsertNewUser() {
        String expectedName = "Name";
        String expectedPhoneNumber = "12345678910";

        db.insertNewUser(expectedName, expectedPhoneNumber);
        User user = db.getAllUsers().get(0);

        Assert.assertEquals(expectedName, user.getName());
        Assert.assertEquals(expectedPhoneNumber, user.getPhoneNumber());
    }

    @Test
    public void testUserRemoving() {
        db.insertNewUser("User0", "12345678910");
        db.insertNewUser("User1", "12345678910");

        User userToBeRemoved = db.getAllUsers().get(0);
        db.removeUserById(userToBeRemoved.getId());
        List<User> usersAfterRemoving = db.getAllUsers();

        if (usersAfterRemoving.size() > 1 || !usersAfterRemoving.get(0).getName().equals("User1")) {
            Assert.fail();
        }
    }
}
