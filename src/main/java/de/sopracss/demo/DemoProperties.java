package de.sopracss.demo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "demo")
@Data
public class DemoProperties {

    private String greeting;

    @NestedConfigurationProperty
    private UserProperties user = new UserProperties();

    @Data
    private static class UserProperties {

        private String file;
    }
}
