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
}
