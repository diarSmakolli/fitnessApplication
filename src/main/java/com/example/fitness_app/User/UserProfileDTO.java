package com.example.fitness_app.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class UserProfileDTO {

    private String firstname;

    private String lastname;

    private String email;

    private String username;

    @Nullable
    private String profilePicture;

}
