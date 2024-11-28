package com.example.task_management_API.DTO;

import com.example.task_management_API.entities.Task;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
public class TaskDto {
    private Integer id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Task.Status status;
    private Task.Periority periority;
    private Integer userId;

    public TaskDto(){

    }
    @JsonIgnore
    public TaskDto(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.dueDate = task.getDueDate();
        this.status = task.getStatus();
        this.periority = task.getPeriority();
        this.userId = task.getUser() != null ? task.getUser().getId() : null;
    }
}
