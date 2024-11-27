package com.example.task_management_API.controllers;

import com.example.task_management_API.DTO.TaskDto;
import com.example.task_management_API.Helpers.ApiResponse;
import com.example.task_management_API.entities.Task;
import com.example.task_management_API.entities.User;
import com.example.task_management_API.services.TaskService;
import com.example.task_management_API.services.UserService;
import com.example.task_management_API.utilities.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpHeaders;
import java.util.List;


@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;
@Autowired
private UserService userService;
@Autowired
private JwtUtil jwtUtil;


    @PostMapping("")
    public ResponseEntity<ApiResponse<TaskDto>> createTask(@RequestBody Task task) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            task.setUser(user);
            Task savedTask = taskService.createTask(task);
            TaskDto taskDto = new TaskDto(savedTask);
            ApiResponse<TaskDto> successResponse = new ApiResponse<>("task added successfully", taskDto, HttpStatus.CREATED);
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
        } catch (RuntimeException e) {
            ApiResponse<TaskDto> errorResponse = new ApiResponse<>(e.getMessage(), HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

//    @PostMapping("")
//    public ApiResponse<Task> createTask(
//            @RequestBody Task task,
//            @RequestHeader("Authorization") String token) {
//        System.out.println("Received token: " + token);
//
//        try {
//            token = token.replace("Bearer ", "");
//            System.out.println("Processed token: " + token);
//
//            Integer userId = jwtUtil.extractUserId(token);
//            System.out.println("Extracted userId: " + userId);
//
//            User userOfId = userService.findUserById(userId);
//            task.setUser(userOfId);
//
//            Task savedTask = taskService.createTask(task);
//            return new ApiResponse<>("Tasks added successfully", savedTask);
//        } catch (Exception e) {
//            throw new RuntimeException("Error processing token or task creation",e);
//        }
//    }

    @GetMapping("")
    public ApiResponse<List<Task>> getAllTasks() {
        List<Task> allTasks=taskService.getAllTasks();
        return new ApiResponse<>("your tasks showed successfully",allTasks);

    }
    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<List<TaskDto>>> getUserTasks(@PathVariable Integer id) {
        List<TaskDto> allTasks=taskService.getUserTasks(id);
        ApiResponse<List<TaskDto>> response= new ApiResponse<>("your tasks showed successfully",allTasks,HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ApiResponse<Task> updateSpesificTask(@PathVariable Integer id , @RequestBody Task task){
        Task updatedTask=taskService.updateTask(id,task);
        return updatedTask!=null?new ApiResponse<>("task updated successfully",updatedTask):new ApiResponse<>("task  can not be updated",null);
    }
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteTask(@PathVariable Integer id){
        boolean deletedTask=taskService.deleteTask(id);
        if(deletedTask){
            return new ApiResponse<>("task deleted successfully",null);

        }
        else {
            return new ApiResponse<>("task not exist to be deleted",null);

        }
    }

}
