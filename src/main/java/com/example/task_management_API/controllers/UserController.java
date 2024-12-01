package com.example.task_management_API.controllers;

import com.example.task_management_API.DTO.TaskDto;
import com.example.task_management_API.DTO.UserDto;
import com.example.task_management_API.Helpers.ApiResponse;
import com.example.task_management_API.entities.Task;
import com.example.task_management_API.entities.User;
import com.example.task_management_API.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
            ApiResponse<UserDto> userExistResponse=new ApiResponse<>("you account is exist",userDto, HttpStatus.FOUND);
            return ResponseEntity.status(HttpStatus.FOUND).body(userExistResponse);
        }catch(RuntimeException e){
            ApiResponse<UserDto> userNotExistResponse=new ApiResponse<>("user not found", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userNotExistResponse);

        }


    }
    @GetMapping("")
    public ResponseEntity<ApiResponse<String>> getUserName() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = user.getUsername();
            ApiResponse<String> nameResponse = new ApiResponse<>("username found",userName, HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK).body(nameResponse);
    }
    //delete user
    //show all tasks

}
