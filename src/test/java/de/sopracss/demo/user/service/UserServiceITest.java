package de.sopracss.demo.user.service;

import de.sopracss.demo.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceITest {

    @Autowired
    UserService userService;

    @Test
    void testAddExistingUser() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.addUser(new User("jdoe","John", "Doe", "john@doe.org"));
        });
    }

    @Test
    void testListUser(){
        List<User> users = userService.listUsers();
        assertFalse(users.isEmpty());
    }

    @Test
    void testAddUser() {
        userService.addUser(new User("jndoe","Jane", "Doe", "jane@doe.org"));
        assertFalse(userService.listUsers().isEmpty());
    }
}
