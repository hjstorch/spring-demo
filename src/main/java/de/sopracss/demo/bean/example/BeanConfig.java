package de.sopracss.demo.bean.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean(name = "myBean", initMethod = "initMethod", destroyMethod = "destroyMethod")
    public MyBeanClass myBean(@Value("${myApp.parameter}") String parameter) {
        return new MyBeanClass(parameter);
    }

}
