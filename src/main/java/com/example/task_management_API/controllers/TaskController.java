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

import java.time.LocalDate;
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
        if(task.getDueDate().isBefore((LocalDate.now()))){
            ApiResponse<TaskDto> invalidDateRes=new ApiResponse<>("due date have to be equal to or greater than today",HttpStatus.BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(invalidDateRes);
        }
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println("user in task: " + user);
            task.setUser(user);
            Task savedTask = taskService.createTask(task);
            TaskDto taskDto = new TaskDto(savedTask);
            ApiResponse<TaskDto> successResponse = new ApiResponse<>("task added successfully", taskDto, HttpStatus.CREATED);
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);

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

//_______________________________user tesks (admin,user)_______________________________
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
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<ApiResponse<Void>> updateTaskStatus(@PathVariable Integer id,
                                                              @RequestParam (value = "status") String status  )  {
        boolean statusIsUpdated= taskService.updateStatus(id,status);
        if(statusIsUpdated){
            ApiResponse<Void> successResponse = new ApiResponse<>("Task status is updated successfully", null, HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK).body(successResponse);
        }
        ApiResponse<Void> successResponse = new ApiResponse<>("Task status is not updated", null, HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);

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




    }
