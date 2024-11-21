package com.example.task_management_API.repos;

import com.example.task_management_API.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Integer> {
}
