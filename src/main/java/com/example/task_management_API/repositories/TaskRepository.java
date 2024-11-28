package com.example.task_management_API.repositories;

import com.example.task_management_API.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Task t where t.id=:id")
    int deleteTaskById(Integer id);
    List<Task> findByUserId (Integer id);
    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.user")
    List<Task> findAllTasks();


}
