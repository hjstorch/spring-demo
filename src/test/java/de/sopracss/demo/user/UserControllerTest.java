package de.sopracss.demo.user;

import de.sopracss.demo.exception.WebExceptionHandler;
import de.sopracss.demo.quote.QuoteService;
import de.sopracss.demo.security.WebSecurityConfig;
import de.sopracss.demo.user.model.User;
import de.sopracss.demo.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import({WebSecurityConfig.class, WebExceptionHandler.class})
@AutoConfigureMockMvc
@ActiveProfiles("unittest")
public class UserControllerTest {

    private final String userJson = """
            {"username":"zwylde","firstname":"Zak","lastname":"Wylde","email":"zak@bls.com"}
            """;

    private final String userJsonInvalid = """
            {"username":"zwylde","firstname":"Zak","lastname":"Wylde","email":"abc"}
            """;

    private final String userJsonInvalid2 = """
            {"username":"","firstname":"Zak","lastname":"Wylde","email":"zak@bls.com"}
            """;

    private final String userJsonExisting = """
            {"username":"jndoe","firstname":"Zak","lastname":"Wylde","email":"zak@bls.com"}
            """;

    @Autowired
    MockMvc client;

    @MockitoBean
    UserService userService;

    // this should not be necessary as it is not used in the UserController‚
    // there is maybe a bug in spring test also loading the GreetingController
    // even this test is restricted to UserController.class
    @MockitoBean
    QuoteService quoteService;

    @BeforeEach
    void setUp() {
        when(userService.listUsers())
                .thenReturn(List.of(new User("jndoe","Jane", "Doe", "jane@doe.xy")));
        // here we use doThrow.when instead of when.thenThrow because the Function is void
        doThrow(new IllegalArgumentException("User already exists")).when(userService).addUser(argThat((User u) -> "jndoe".equals(u.getUsername())));
    }

    @Test
    @WithMockUser()
    public void testUserList() throws Exception {
        client.perform(get("/user"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"}) // needs both roles to satisfy the @PreAuthorize("hasRole('ADMIN')") and Matcher in WebSecurityConfig
    public void testUserAdd() throws Exception {
        client.perform(put("/user").contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    public void testInvalidUserAdd() throws Exception {
        client.perform(put("/user").contentType(MediaType.APPLICATION_JSON).content(userJsonInvalid))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    public void testNoUsernameUserAdd() throws Exception {
        client.perform(put("/user").contentType(MediaType.APPLICATION_JSON).content(userJsonInvalid2))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    public void testExistingUserAdd() throws Exception {
        client.perform(put("/user").contentType(MediaType.APPLICATION_JSON).content(userJsonExisting))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser()
    public void testUserAddNoRole() throws Exception {
        client.perform(put("/user").contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isForbidden());
    }

}
