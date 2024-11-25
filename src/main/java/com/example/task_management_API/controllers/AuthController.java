package com.example.task_management_API.controllers;

import com.example.task_management_API.Helpers.ApiResponse;
import com.example.task_management_API.entities.User;
import com.example.task_management_API.services.UserService;
import com.example.task_management_API.utilities.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            ApiResponse<User> response = new ApiResponse<>("User registered successfully", registeredUser);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (DataIntegrityViolationException e) {
            ApiResponse<User> errorResponse = new ApiResponse<>("Username or Email already exists", HttpStatus.BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody User loginData) {
        Optional<User> userExist = userService.findByUserName(loginData.getUsername());
        if (userExist.isPresent()) {
            User user = userExist.get();
            if (passwordEncoder.matches(loginData.getPassword(), user.getPassword())) {
                String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
                ApiResponse<String> successResponse = new ApiResponse<>("Login successful", token);
                return ResponseEntity.status(HttpStatus.OK).body(successResponse);
            }
        }
        ApiResponse<String> errorResponse = new ApiResponse<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

}
