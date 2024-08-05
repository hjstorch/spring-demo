package de.sopracss.demo.quote;

import de.sopracss.demo.webclient.WebClientConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("unittest")
class QuoteServiceITest {

    @Autowired
    QuoteService quoteService;

    @Test
    public void testGetRandomQuote() {
        String quote = quoteService.getQuote();
        assertNotNull(quote);
    }

}
