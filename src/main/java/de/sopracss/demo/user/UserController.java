package de.sopracss.demo.user;

import de.sopracss.demo.user.model.User;
import de.sopracss.demo.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')||hasRole('ADMIN')")
    public List<User> userList() {
        return userService.listUsers();
    }

    @PutMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public void addUser(@RequestBody @Valid User user) {
        userService.addUser(user);
    }
}
