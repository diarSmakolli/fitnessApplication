package com.example.fitness_app.User;

public interface UserService {
    void createUser(UserDTOSave userDTOSave);
    String loginUser(UserLoginDTO userLoginDTO);
}
