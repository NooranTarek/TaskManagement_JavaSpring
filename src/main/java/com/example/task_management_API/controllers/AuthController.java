package com.example.task_management_API.controllers;

import com.example.task_management_API.Helpers.ApiResponse;
import com.example.task_management_API.entities.User;
import com.example.task_management_API.services.UserService;
import com.example.task_management_API.utilities.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/register")
    public ApiResponse<User> registerUser (@RequestBody User user){
        Optional<User> existUser=userService.findByUserName(user.getUsername());
        if(existUser.isPresent()){
            return new ApiResponse<>("user already exist with this name");
        }
        else {
            User registeredUser=userService.registerUser(user);
            return new ApiResponse<>("user registered successfully",registeredUser);
        }

    }
    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody User loginData){
        Optional<User> userExist=userService.findByUserName(loginData.getUsername());
        if (userExist.isPresent()){
            User user =userExist.get();
            if(passwordEncoder.matches(loginData.getPassword(),user.getPassword())){
                String token=jwtUtil.generateToken(user.getUsername(),user.getRole().name());
                return new ApiResponse<>("login successfully",token);
            }

        }
        return new ApiResponse<>("failed to login");

    }
}
