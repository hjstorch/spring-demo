package de.sopracss.demo.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @NotBlank
    @Schema(example = "jdoe")
    private String username;
    @Schema(example = "John")
    private String firstname;
    @Schema(example = "Doe")
    private String lastname;
    @Email
    @Schema(example = "john@doe.ex")
    private String email;

}
