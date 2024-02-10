package com.example.fitness_app.User;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all tests")
    @ApiResponse(responseCode = "404", description = "No tests found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserDTOSave userSaveDTO){
        userService.createUser(userSaveDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created");
    }


    @PostMapping("/login")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all tests")
    @ApiResponse(responseCode = "404", description = "No tests found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "400", description = "Invalid request")
//    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
//        String tokenResponse = userService.loginUser(userLoginDTO);
//        return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
//    }
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        String tokenResponse = userService.loginUser(userLoginDTO);
        return ResponseEntity.status(HttpStatus.OK).body(tokenResponse);
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved progress by ID")
    @ApiResponse(responseCode = "404", description = "Progress not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<UserEntity> findUserById(@PathVariable String id) {

        UserEntity user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/update-profile")
    public ResponseEntity<UserEntity> updateProfile(@PathVariable String id, @RequestBody UserDTO userDTO) {
        UserEntity user = userService.updateProfile(id, userDTO);
        return ResponseEntity.ok(user);
    }

    
    



}
