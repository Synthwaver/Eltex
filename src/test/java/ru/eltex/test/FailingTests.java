package ru.eltex.test;

import org.junit.Assert;
import org.junit.Test;
import ru.eltex.phonebook.User;

public class FailingTests {

    @Test
    public void fail1() {
        Assert.fail();
    }

    @Test
    public void fail2() {
        User user = new User(228, "Right Name", "88005553535");
        Assert.assertEquals("Wrong Name", user.getName());
    }
}
