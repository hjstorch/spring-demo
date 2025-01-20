package de.sopracss.demo.greeting;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class GreetingService {

    public String getGreeting(String name) throws IllegalArgumentException {
        if(!StringUtils.hasText(name)){
            throw new IllegalArgumentException("Name must not be empty");
        }
        return "Hello %s".formatted(name);
    }
}
