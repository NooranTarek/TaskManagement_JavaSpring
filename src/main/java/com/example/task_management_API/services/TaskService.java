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
    public Task updateTask(Integer id,Task task){
        if (taskRepository.existsById(id)) {
            task.setId(id);
            return taskRepository.save(task);
        }
        return null;
    }
    public boolean deleteTask(Integer id){
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
