package com.example.task_management_API.models;

import jakarta.persistence.*;

import java.time.LocalDate;


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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Periority getPeriority() {
        return periority;
    }

    public void setPeriority(Periority periority) {
        this.periority = periority;
    }

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
