package de.sopracss.demo.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.sopracss.demo.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private final String userJsonExisting = """
            [{"username":"jdoe","firstname":"John","lastname":"Doe","email":"joun@doe.us"}]
            """;
    private final ObjectMapper mockMapper = spy(new ObjectMapper());

    private final Resource mockResource = mock(Resource.class);

    private UserService sut;

    @BeforeEach
    public void setUp() throws IOException {
        when(mockResource.getFilename()).thenReturn("users.json");
        when(mockResource.getFile()).thenReturn(new File("users.json"));
        when(mockResource.getContentAsByteArray()).thenReturn(userJsonExisting.getBytes());
        sut = new UserService(mockResource, mockMapper);
        sut.loadUsers(); // is done by SpringBoot in real application
    }

    @Test
    void testAddUser() throws IOException {
        User user = new User("jndoe", "Jane", "Doe", "jane@doe.us");
        sut.addUser(user);
        verify(mockMapper, times(1)).writeValue(any(File.class), any(List.class));
        List<User> users = sut.listUsers();
        assertEquals(2, users.size());
        assertTrue(users.contains(user));
    }

    @Test
    void testAddUserExisting() throws IOException {
        User user = new User("jdoe", "John", "Doe", "john@doe.us");
        assertThrows(IllegalArgumentException.class, () -> sut.addUser(user));
        verify(mockMapper, never()).writeValue(any(File.class), any(List.class));
        List<User> users = sut.listUsers();
        assertEquals(1, users.size());
        assertFalse(users.contains(user));
    }

    @Test
    void testListUser() {
        List<User> users = sut.listUsers();
        assertEquals(1, users.size());
    }

}
