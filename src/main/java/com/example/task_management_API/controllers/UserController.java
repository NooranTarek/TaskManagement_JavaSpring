package com.example.task_management_API.controllers;

import com.example.task_management_API.DTO.TaskDto;
import com.example.task_management_API.DTO.UserDto;
import com.example.task_management_API.Helpers.ApiResponse;
import com.example.task_management_API.entities.Task;
import com.example.task_management_API.entities.User;
import com.example.task_management_API.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/userName")
    public ResponseEntity<ApiResponse<String>> getUserName() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = user.getUsername();
        ApiResponse<String> nameResponse = new ApiResponse<>("username found",userName, HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.OK).body(nameResponse);
    }
    //delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Integer id){
        boolean deletedUser=userService.deleteUser(id);
        if(deletedUser){
            ApiResponse<Void> successResponse=new ApiResponse<>("user deleted successfully",HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK).body(successResponse);
        }
        ApiResponse<Void> successResponse=new ApiResponse<>("user not found",HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(successResponse);
    }
    //show all users
    @GetMapping("")
    public ResponseEntity<ApiResponse<Page<UserDto>>> getAllUsers(
            @RequestParam (value = "page" , defaultValue = "0") int page,
            @RequestParam (value = "size" , defaultValue = "3") int size)
    {
        Pageable pageable = PageRequest.of(page,size);
        Page <UserDto> allUsers=userService.getAllUsers(pageable);
        ApiResponse<Page<UserDto>> successResponse=new ApiResponse<>("users showed successfully",allUsers,HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
//    @GetMapping("")
//    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers(){
//        List <UserDto> allUsers=userService.getAllUsers();
//        ApiResponse<List<UserDto>> successResponse=new ApiResponse<>("users showed successfully",allUsers,HttpStatus.OK);
//        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
//    }

}