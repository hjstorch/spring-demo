package de.sopracss.demo.user;

import de.sopracss.demo.user.model.User;
import de.sopracss.demo.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@Tag(name = "User", description = "Managing users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    @Operation(summary = "List users", description = "List all users in the system", security =  {
            @SecurityRequirement(name = "basicAuth")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content =
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class)))),
            @ApiResponse(responseCode = "401", description = "Not Authenticated", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "text/plain"))
    })
    public List<User> userList() {
        return userService.listUsers();
    }

    @PutMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Add a user", description = "Add a new user to the system", security =  {
            @SecurityRequirement(name = "basicAuth")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User added"),
            @ApiResponse(responseCode = "400", description = "Invalid user supplied", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "401", description = "Not Authenticated", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "409", description = "User already exists", content = @Content(mediaType = "text/plain"))
    })
    public void addUser(@RequestBody @Valid User user) {
        userService.addUser(user);
    }
}
