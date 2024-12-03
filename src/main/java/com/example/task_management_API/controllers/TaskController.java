package com.example.task_management_API.controllers;

import com.example.task_management_API.DTO.TaskDto;
import com.example.task_management_API.Helpers.ApiResponse;
import com.example.task_management_API.entities.Task;
import com.example.task_management_API.entities.User;
import com.example.task_management_API.services.TaskService;
import com.example.task_management_API.services.UserService;
import com.example.task_management_API.utilities.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@Slf4j
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
            System.out.println("user in task: " + user);
            task.setUser(user);
            Task savedTask = taskService.createTask(task);
            TaskDto taskDto = new TaskDto(savedTask);
            ApiResponse<TaskDto> successResponse = new ApiResponse<>("task added successfully", taskDto, HttpStatus.CREATED);
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
        } catch (RuntimeException e) {
            System.out.println("error");
            ApiResponse<TaskDto> errorResponse = new ApiResponse<>(e.getMessage(), HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }


    @GetMapping("")
    public ApiResponse<Page<TaskDto>> getAllTasks(
            @RequestParam (value = "page" ,defaultValue = "0") int page,
            @RequestParam (value = "size" , defaultValue = "3") int size,
            @RequestParam (value = "field" , defaultValue = "dueDate") String field
            ) {
//        log.info("from get all");
        Pageable pageable=PageRequest.of(page,size, Sort.by(field).ascending());
        Page<TaskDto> allTasks = taskService.getAllTasks(pageable);
        return new ApiResponse<>("your tasks showed successfully", allTasks);

    }
//    @GetMapping("")
//    public ResponseEntity<ApiResponse<List<TaskDto>>> getAllTasks(){
//        List <TaskDto> allTasks=taskService.getAllTasks();
//        ApiResponse<List<TaskDto>> successResponse=new ApiResponse<>("users showed successfully",allTasks,HttpStatus.OK);
//        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
//    }

    @GetMapping("/userTasks")
    public ResponseEntity<ApiResponse<Page<TaskDto>>> getUserTasks(
            @RequestParam (value = "page" ,defaultValue = "0") int page,
            @RequestParam (value = "size" , defaultValue = "3") int size) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = user.getId();
        Pageable pageable= PageRequest.of(page,size);
        Page<TaskDto> paginatedTasks = taskService.getUserTasks(userId,pageable);
        ApiResponse<Page<TaskDto>> response = new ApiResponse<>("your tasks showed successfully", paginatedTasks, HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskDto>> updateSpesificTask(@PathVariable Integer id, @RequestBody Task task) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Task updatedTask = taskService.updateTask(id, task, user);
        if (updatedTask != null) {
            TaskDto taskDto = new TaskDto(updatedTask);
            ApiResponse<TaskDto> updateResponse = new ApiResponse<>("task updated successfully", taskDto, HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK).body(updateResponse);
        } else {
            ApiResponse<TaskDto> updateResponse = new ApiResponse<>("task not found to be updated", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(updateResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTask(@PathVariable Integer id) {
        boolean deletedTask = taskService.deleteTask(id);
        if (deletedTask) {
            ApiResponse<Void> successResponse = new ApiResponse<>("Task deleted successfully", null, HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK).body(successResponse);
        } else {
            ApiResponse<Void> errorResponse = new ApiResponse<>("Task not found", null, HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }}


//    @GetMapping("/{id}")
//    public ResponseEntity<ApiResponse<TaskDto>> getSpesificTask (@PathVariable Integer id){
//        try {
//                Task foundTask = taskService.findTaskById(id);
//                TaskDto taskDto = new TaskDto(foundTask);
//                ApiResponse<TaskDto> taskExistResponse = new ApiResponse<>("task is found", taskDto, HttpStatus.OK);
//                return ResponseEntity.status(HttpStatus.OK).body(taskExistResponse);
//        } catch (RuntimeException e) {
//                ApiResponse<TaskDto> taskNotExistResponse = new ApiResponse<>("task not found", HttpStatus.NOT_FOUND);
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(taskNotExistResponse);
//
//            }
//        }


    }
