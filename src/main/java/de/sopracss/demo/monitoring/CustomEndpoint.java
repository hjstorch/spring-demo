package de.sopracss.demo.monitoring;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "greet", enableByDefault = false)
public class CustomEndpoint {

    @ReadOperation //HTTP GET
    public String customRead() {
        return "Hello User";
    }

    @ReadOperation //HTTP GET
    public String customReadPath(@Selector() String name) {
        return "Hello " + name;
    }

    @WriteOperation //HTTP POST
    public String customWrite(String user) {
        return "Hello " + user;
    }
}
