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
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Page<TaskDto> getAllTasks(Pageable pageable) {
        Page<Task> tasks = taskRepository.findAll(pageable);
        return tasks.map(task -> new TaskDto(task));
//        List<TaskDto> taskDtos = taskMapper.tasksToTaskDtos(tasks.getContent());
//        return new PageImpl<>(taskDtos, pageable, tasks.getTotalElements());
    }


    //public List<TaskDto> getAllTasks() {
//    List<Task> tasks = taskRepository.findAll();
//    return tasks.stream()
//            .map(task -> new TaskDto(task))
//            .collect(Collectors.toList());
//}
    public Task updateTask(Integer id, Task task, User user) {
        if (taskRepository.existsById(id)) {
            task.setId(id);
            task.setUser(user);
            return taskRepository.save(task);
        }
        return null;
    }

    public boolean updateStatus(Integer id, String statusString) {
        Task.Status status = Task.Status.valueOf(statusString);
        int updatedTask = taskRepository.updateTaskStatus(id, status);
        if (updatedTask > 0) {
            return true;
        }
        return false;
    }

    public boolean deleteTask(Integer id) {
        Integer deletedTasks = taskRepository.deleteTaskById(id);
        return deletedTasks > 0;
    }


    public Page<TaskDto> getUserTasks(Integer id, Pageable pageable) {
        Page<Task> tasksPage = taskRepository.findByUserId(id, pageable);
        return tasksPage.map(task -> new TaskDto(task));
    }
}


