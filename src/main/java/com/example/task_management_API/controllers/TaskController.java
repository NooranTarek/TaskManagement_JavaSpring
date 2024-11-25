package com.example.task_management_API.controllers;

import com.example.task_management_API.Helpers.ApiResponse;
import com.example.task_management_API.entities.Task;
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
        return new ApiResponse<>("your tasks showed successfully",allTasks);

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
