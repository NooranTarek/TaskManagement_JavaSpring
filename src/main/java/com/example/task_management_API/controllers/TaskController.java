package com.example.task_management_API.controllers;

import com.example.task_management_API.models.Task;
import com.example.task_management_API.services.TaskService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @ApiResponse(responseCode= "201",description="task added successfully")
    @PostMapping("")
    public Task createTask (@RequestBody Task task){
        return taskService.createTask(task);
 }
}
