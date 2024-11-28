package com.example.task_management_API.services;

import com.example.task_management_API.DTO.TaskDto;
import com.example.task_management_API.entities.Task;
import com.example.task_management_API.repositories.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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
