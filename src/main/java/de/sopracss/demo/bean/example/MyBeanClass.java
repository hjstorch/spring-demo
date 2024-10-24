package de.sopracss.demo.bean.example;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyBeanClass {

    private final String parameter;

    public MyBeanClass(String parameter) {
        this.parameter = parameter;
    }
    private void initMethod() {
        log.debug("Initialized with parameter: {}", parameter);
    }

    private void destroyMethod() {
        log.debug("Destroyed with parameter: {}", parameter);
    }
}
