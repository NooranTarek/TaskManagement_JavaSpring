package com.example.task_management_API.services;

import com.example.task_management_API.DTO.TaskDto;
import com.example.task_management_API.Helpers.TaskMapper;
import com.example.task_management_API.entities.Task;
import com.example.task_management_API.entities.User;
import com.example.task_management_API.repositories.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskMapper taskMapper;
    //CRUD
    public Task findTaskById(Integer id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + id));
    }
    public Task createTask (Task task){

        return taskRepository.save(task);
    }
    public Page<TaskDto> getAllTasks (Pageable pageable){
        Page<Task> tasks=taskRepository.findAll(pageable);
        List<TaskDto> taskDtos = taskMapper.tasksToTaskDtos(tasks.getContent());
        return new PageImpl<>(taskDtos, pageable, tasks.getTotalElements());
    }
    public Task updateTask(Integer id, Task task, User user){
        if (taskRepository.existsById(id)) {
            task.setId(id);
            task.setUser(user);
            return taskRepository.save(task);
        }
        return null;
    }
        public boolean deleteTask(Integer id) {
        Integer deletedTasks= taskRepository.deleteTaskById(id);
        return  deletedTasks>0;
        }


    public Page<TaskDto> getUserTasks(Integer id, Pageable pageable) {
        Page<Task> tasksPage = taskRepository.findByUserId(id, pageable);
        return tasksPage.map(task -> new TaskDto(task));
    }
    }


