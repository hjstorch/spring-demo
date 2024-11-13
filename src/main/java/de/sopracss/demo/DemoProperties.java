package de.sopracss.demo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "demo")
@Data
public class DemoProperties {

    private String greeting;

    private UserProperties user = new UserProperties();

    @Data
    private class UserProperties {

        private String file;
    }
}
