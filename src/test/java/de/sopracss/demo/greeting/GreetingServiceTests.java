package de.sopracss.demo.greeting;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GreetingServiceTests {

    private GreetingService sut;

    @BeforeEach
    public void setUp() {
        sut = new GreetingService();
    }

    @Test
    public void testGreeting() {
        assertThat(sut.getGreeting("John")).isEqualTo("Hello John");
    }

    @Test
    public void testGreetingThrowsWhenNameIsNull() {
        assertThrows(IllegalArgumentException.class,() -> sut.getGreeting(null));
    }
    @Test
    public void testGreetingThrowsWhenNameIsEmpty() {
        assertThrows(IllegalArgumentException.class,() -> sut.getGreeting(""));
    }
    @Test
    public void testGreetingThrowsWhenNameIsWhitespace() {
        assertThrows(IllegalArgumentException.class,() -> sut.getGreeting(" "));
    }
}
