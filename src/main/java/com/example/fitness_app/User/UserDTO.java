package com.example.fitness_app.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class UserDTO {

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    private String newPassword;

    private String username;

    @Nullable
    private String profilePicture;

}
