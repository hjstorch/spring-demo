package de.sopracss.demo.user.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

//@ApplicationScope
//@Component // Commented out because this in alternative to @PostConstruct Method of UserService
public class UserLoader implements ApplicationRunner {

    private final UserService userService;

    public UserLoader(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.userService.loadUsers();
    }
}
