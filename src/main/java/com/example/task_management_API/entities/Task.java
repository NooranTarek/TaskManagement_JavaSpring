package com.example.task_management_API.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private LocalDate dueDate;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Periority periority;
    public enum Status{
        to_do,
        in_progress,
        completed
    }
    public enum Periority{
        low,
        medium,
        high
    }

}
