package com.example.task_management_API.services;

import com.example.task_management_API.models.Task;
import com.example.task_management_API.repos.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    //CRUD
    public Task createTask (Task task){
        return taskRepository.save(task);
    }
    public List<Task> getAllTasks (){
        return taskRepository.findAll();
    }

}
