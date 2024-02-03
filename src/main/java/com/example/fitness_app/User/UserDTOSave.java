package com.example.fitness_app.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
public class UserDTOSave {

    private String firstname;

    private String lastname;

    private String email;

    private String username;

    private String password;
}
