package de.sopracss.demo.user.service;

import de.sopracss.demo.user.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Scope("singleton")
@Qualifier("contextQualifier")
@Component("userService")
public class UserService {

    private Map<String, User> users = new HashMap<>();

    private final Resource userfile;
    private final ObjectMapper mapper;

    public UserService(@Value("${demo.user.file}")Resource userfile, ObjectMapper mapper) {
        this.userfile = userfile;
        this.mapper = mapper;
    }

    @PostConstruct // alternative activate UserLoader
    public void loadUsers() throws IOException {
        System.out.println("Loading users from: " + userfile.getFilename());
        List<User> loadedUsers =  mapper.readValue(
                userfile.getContentAsByteArray(),
                new TypeReference<List<User>>() {}
        );
        users = loadedUsers.stream().collect(
                HashMap::new,
                (map, user) -> map.put(user.getUsername(), user),
                HashMap::putAll
        );
    }

    public void saveUsers() throws IOException {
        System.out.println("Saving users to: " + userfile.getFilename());
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(userfile.getFile(), List.copyOf(users.values()));
    }

    public List<User> listUsers() {
        return List.copyOf(users.values());
    }

    public void addUser(User user) throws IllegalArgumentException {
        if(users.containsKey(user.getUsername())) {
            throw new IllegalArgumentException("User already exists");
        }
        users.put(user.getUsername(),user);
    }

    public User getUser(String username) throws NoSuchElementException, IOException {
        if(users.isEmpty()){
            this.loadUsers();
        }
        return Optional.ofNullable(users.get(username)).orElseThrow();
    }
}
