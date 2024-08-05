package de.sopracss.demo.quote;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class QuoteServiceTest {

    RestTemplate restTemplateMock = Mockito.mock(RestTemplate.class);

    QuoteService quoteService;

    @BeforeEach
    public void init() {
        when(restTemplateMock.getForObject("", Quote[].class))
                .thenReturn(new Quote[]{ new Quote("Test Quote","anonymous")});
        this.quoteService = new QuoteService(restTemplateMock, "", ""  );
    }

    @Test
    public void testGetRandomQuote() {
        String quote = quoteService.getQuote();
        assertEquals("Test Quote", quote);
    }

}
