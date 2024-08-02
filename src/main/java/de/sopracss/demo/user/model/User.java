package de.sopracss.demo.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @NotBlank
    private String username;
    private String firstname;
    private String lastname;
    @Email
    private String email;

}
