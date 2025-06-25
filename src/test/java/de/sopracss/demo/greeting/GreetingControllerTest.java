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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(value = GreetingController.class,
        properties = {
            "demo.greeting=Guude,",
        },
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = BadNameFilter.class)
)
@Import({BadNameFilter.class, QuoteService.class})
@AutoConfigureMockMvc
class GreetingControllerTest {

    @Autowired
    MockMvc client;

    @MockitoBean
    QuoteService quoteService;

    @BeforeEach
    public void setUp() {
        when(quoteService.getQuote()).thenReturn("Test Quote");
    }

    @Test
    @WithMockUser
    void testControllerFunctionWithView() throws Exception {
        client.perform(get("/greeting/John"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("username", "John"))
                .andExpect(xpath("//H1").string(containsString("Guude, John")));
    }

    @Test
    @WithMockUser
    void testControllerFunctionNoName() throws Exception {
        client.perform(get("/greeting/"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void testControllerRestFunction() throws Exception {
        client.perform(get("/greetingRest").param("myname", "John"))
                .andExpect(status().isOk())
                .andExpect(content().string("Guude, John"));
    }

    @Test
    @WithMockUser
    void testControllerRestFunctionWithProperty() throws Exception {
        System.setProperty("demo.greeting", "Hola,");
        client.perform(get("/greetingRest").param("myname", "John"))
                .andExpect(status().isOk())
                .andExpect(content().string("Guude, John"));
    }

    @Test
    @WithMockUser
    void testControllerRestFunctionWithBadProperty() throws Exception {
        client.perform(get("/greetingRest").param("myname", "bad"))
                .andExpect(status().isOk())
                .andExpect(content().string("Guude, World"));
    }

    @Test
    @WithMockUser
    void testControllerRestFunctionWithNoProperty() throws Exception {
        client.perform(get("/greetingRest"))
                .andExpect(status().isNotFound());
    }
}
