package com.example.task_management_API.repositories;

import com.example.task_management_API.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Integer> {

    List<Task> findByUserId (Integer id);

}
