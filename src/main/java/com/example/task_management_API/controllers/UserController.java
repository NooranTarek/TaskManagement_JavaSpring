package com.example.task_management_API.controllers;

import com.example.task_management_API.DTO.UserDto;
import com.example.task_management_API.Helpers.ApiResponse;
import com.example.task_management_API.entities.User;
import com.example.task_management_API.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getSpesificUser (@PathVariable Integer id){
        try{
            User userExist=userService.findUserById(id);
            UserDto userDto = new UserDto(userExist);
            ApiResponse<UserDto> userExistResponse=new ApiResponse<>("user found successfully",userDto, HttpStatus.FOUND);
            return ResponseEntity.status(HttpStatus.FOUND).body(userExistResponse);
        }catch(RuntimeException e){
            ApiResponse<UserDto> userNotExistResponse=new ApiResponse<>("user not found", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userNotExistResponse);

        }


    }
}
