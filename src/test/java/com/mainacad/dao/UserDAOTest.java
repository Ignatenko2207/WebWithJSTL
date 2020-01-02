package com.mainacad.dao;

import com.mainacad.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    private static List<User> users;

    @BeforeAll
    static void setPreConditions() {
        users = new ArrayList<>();
    }

    @AfterAll
    static void deleteTestData() {
        users.forEach(it -> UserDAO.delete(it.getId()));
    }


@Test
    void save() {
        User user = new User("testLogin", "testPass", "testName", "testLastName", "testEmail", "123456789");
        UserDAO.save(user);
        users.add(user);
        assertNotNull(user.getId());
    }

    @Test
    void update() {
        User user = new User("testLogin", "testPass", "testName", "testLastName", "testEmail", "123456789");
        User savedUser = UserDAO.save(user);
        users.add(savedUser);
        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertEquals("testPass", savedUser.getPassword());

        user.setPassword("newPass");

        User updatedUser = UserDAO.update(user);
        assertNotNull(updatedUser);
        assertEquals("newPass", updatedUser.getPassword());
    }

    @Test
    void getAndDelete() {
        User user = new User("testLogin", "testPass", "testName", "testLastName", "testEmail", "123456789");
        UserDAO.save(user);

        assertNotNull(user.getId());

        User targetUser = UserDAO.getById(user.getId());
        assertNotNull(targetUser);
        UserDAO.delete(targetUser.getId());
        targetUser = UserDAO.getById(user.getId());
        assertNull(targetUser);
    }

    @Test
    void getAll() {
        User user1 = new User("testLogin22", "testPass", "testName", "testLastName", "testEmail", "123456789");
        User user2 = new User("testLogin23", "testPass", "testName", "testLastName", "testEmail", "123456789");
        UserDAO.save(user1);
        UserDAO.save(user2);
        users.add(user1);
        users.add(user2);
        assertNotNull(user1.getId());
        assertNotNull(user2.getId());

        List<User> targetUsers = UserDAO.getAll();
        assertTrue(targetUsers.size() >= 2);

        int count = 0;
        for (User each:targetUsers){
            if ((user1.getId()).equals(each.getId())) {count++;}
            if ((user2.getId()).equals(each.getId())) {count++;}
        }
        assertEquals(2,count);
    }

    @Test
    void getById() {
        User user = new User("testLogin", "testPass", "testName", "testLastName", "testEmail", "123456789");
        UserDAO.save(user);
        users.add(user);
        assertNotNull(user.getId());

        User targetUser = UserDAO.getById(user.getId());
        assertNotNull(targetUser);
        assertNotNull(targetUser.getId());
    }

    @Test
    void getByLoginAndPassword() {
        User user = new User("testLogin100", "testPass100", "testName", "testLastName", "testEmail", "123456789");
        UserDAO.save(user);
        users.add(user);
        assertNotNull(user.getId());

        User targetUser = UserDAO.getByLoginAndPassword(user.getLogin(), user.getPassword());
        assertNotNull(targetUser);
        assertNotNull(targetUser.getId());
        assertEquals(targetUser.getLogin(),user.getLogin());
    }
}