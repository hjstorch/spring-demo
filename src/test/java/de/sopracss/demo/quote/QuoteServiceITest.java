package de.sopracss.demo.quote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("unittest")
class QuoteServiceITest {

    @Autowired
    QuoteService quoteService;

    @Test
    void testGetRandomQuote() {
        String quote = quoteService.getQuote();
        assertNotNull(quote);
    }

}
