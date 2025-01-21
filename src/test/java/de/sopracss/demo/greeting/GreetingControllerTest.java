package de.sopracss.demo.greeting;

import de.sopracss.demo.filter.BadNameFilter;
import de.sopracss.demo.quote.QuoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(value = GreetingController.class,
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = BadNameFilter.class)
)
@Import({BadNameFilter.class, QuoteService.class})
@AutoConfigureMockMvc
class GreetingControllerTest {

    @Autowired
    MockMvc client;

    @MockitoBean
    QuoteService quoteService;

    @MockitoBean
    GreetingService greetingService;

    @BeforeEach
    public void setUp() {
        when(quoteService.getQuote()).thenReturn("Test Quote");
        when(greetingService.getGreeting("John")).thenReturn("Guude, John");
        when(greetingService.getGreeting(null)).thenThrow(new IllegalArgumentException("Name must not be empty"));
    }


    @Test
    void testControllerFunctionNoName() throws Exception {
        client.perform(get("/greeting/"))
                .andExpect(status().isBadRequest());
    }
    @Test
    void testControllerFunctionPath() throws Exception {
        client.perform(get("/greeting/John"))
                .andExpect(status().isOk())
                .andExpect(content().string("Guude, John Quote of the Day: Test Quote"));
    }

    @Test
    void testControllerFunction() throws Exception {
        client.perform(get("/greeting").param("myname", "John"))
                .andExpect(status().isOk())
                .andExpect(content().string("Guude, John Quote of the Day: Test Quote"));
    }
}
