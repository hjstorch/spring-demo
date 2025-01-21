package de.sopracss.demo.greeting;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GreetingServiceTests {

    private GreetingService sut;

    @BeforeEach
    void setUp() {
        sut = new GreetingService();
    }

    @Test
    void testGreeting() {
        assertThat(sut.getGreeting("John")).isEqualTo("Hello John");
    }

    @Test
    void testGreetingThrowsWhenNameIsNull() {
        assertThrows(IllegalArgumentException.class,() -> sut.getGreeting(null));
    }
    @Test
    void testGreetingThrowsWhenNameIsEmpty() {
        assertThrows(IllegalArgumentException.class,() -> sut.getGreeting(""));
    }
    @Test
    void testGreetingThrowsWhenNameIsWhitespace() {
        assertThrows(IllegalArgumentException.class,() -> sut.getGreeting(" "));
    }
}
