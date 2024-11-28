package com.example.task_management_API.services;

import com.example.task_management_API.DTO.TaskDto;
import com.example.task_management_API.Helpers.TaskMapper;
import com.example.task_management_API.entities.Task;
import com.example.task_management_API.entities.User;
import com.example.task_management_API.repositories.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
//    public List<TaskDto> getAllTasks(){
//        List<Task> allTasks=taskRepository.findAllTasks();
//        return allTasks.stream().map(TaskDto::new).collect(Collectors.toList());
//    }
    public List<TaskDto> getAllTasks (){
        log.info("before getting the tasks");
        List<Task> tasks=taskRepository.findAll();
        if (tasks == null) {
            log.info("tasks list is null!");
        }
        if (tasks.contains(null)) {
           log.info("list contains null");
        }
        tasks.forEach(task -> {
            if (task.getUser() == null) {
                log.info("no user assigned.");
            }
        });
        return taskMapper.tasksToTaskDtos(tasks);
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

//    public boolean deleteTask(Integer id) {
//        if (taskRepository.existsById(id)) {
//            System.out.println("delete task with id " + id);
//            taskRepository.deleteById(id);
//            if (!taskRepository.existsById(id)) {
//                System.out.println("Task successfully deleted");
//                return true;
//            } else {
//                System.out.println("Failed to delete task");
//                return false;
//            }
//        }
//        return false;
//    }

    public List<TaskDto> getUserTasks(Integer id){
        List<Task> tasks=taskRepository.findByUserId(id);
        return tasks.stream().map(TaskDto::new).collect(Collectors.toList());

    }

}
