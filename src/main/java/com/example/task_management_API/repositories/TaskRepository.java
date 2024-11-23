package com.example.task_management_API.repositories;

import com.example.task_management_API.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Integer> {
}
