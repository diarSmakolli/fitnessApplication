package com.example.fitness_app.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Getter
@Setter
public class UserLoginDTO {
    @NotBlank(message = "")
    @NotEmpty(message = "")
    @NotNull(message = "")
    @Size(min = 2, max = 100)
    private String Username;
    @JsonProperty("Password")
    @NotBlank(message = "Password is required!")
    @NotEmpty(message = "Password is required!")
    @NotNull(message = "Password is required!")
    @Size(min = 8)
    private String Password;
}
