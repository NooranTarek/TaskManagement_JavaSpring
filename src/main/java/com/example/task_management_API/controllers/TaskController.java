package com.example.task_management_API.controllers;

import com.example.task_management_API.Helpers.ApiResponse;
import com.example.task_management_API.models.Task;
import com.example.task_management_API.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("")
    public ApiResponse<Task> createTask (@RequestBody Task task){
        Task savedTask=taskService.createTask(task);
        return new ApiResponse<>("tasks added successfully",savedTask);
 }
    @GetMapping("")
    public ApiResponse<List<Task>> getAllTasks() {
        List<Task> allTasks=taskService.getAllTasks();
        return new ApiResponse<>("tasks showed successfully",allTasks);

    }

}
