package ru.eltex.test;

import org.junit.Assert;
import org.junit.Test;
import ru.eltex.phonebook.User;

public class UserTests {

    @Test
    public void testInitUser() {
        int expectedId = 1;
        String expectedName = "Name";
        String expectedPhoneNumber = "12345678910";

        User user = new User(expectedId, expectedName, expectedPhoneNumber);

        Assert.assertEquals(expectedId, user.getId());
        Assert.assertEquals(expectedName, user.getName());
        Assert.assertEquals(expectedPhoneNumber, user.getPhoneNumber());
    }

    @Test
    public void testInitUserWithCSV() {
        int expectedId = 1;
        String expectedName = "Name";
        String expectedPhoneNumber = "12345678910";

        String CSVLine = expectedId + ";" + expectedName + ";" + expectedPhoneNumber;
        User user = new User(CSVLine);

        Assert.assertEquals(expectedId, user.getId());
        Assert.assertEquals(expectedName, user.getName());
        Assert.assertEquals(expectedPhoneNumber, user.getPhoneNumber());
    }

    @Test
    public void testConvertUserToCSV() {
        int id = 1;
        String name = "Name";
        String phoneNumber = "12345678910";
        String expectedCSVLine = id + ";" + name + ";" + phoneNumber;

        User user = new User(id, name, phoneNumber);

        Assert.assertEquals(expectedCSVLine, user.toCSV());
    }
}
