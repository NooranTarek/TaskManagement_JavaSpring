package com.example.task_management_API.controllers;

import com.example.task_management_API.models.Task;
import com.example.task_management_API.services.TaskService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("")
    public Task createTask (@RequestBody Task task){
        return taskService.createTask(task);
 }
    @GetMapping("")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

}
