package com.example.task_management_API.DTO;

import com.example.task_management_API.entities.User;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private User.Role role;
    private List<TaskDto> tasks;

    public UserDto(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.tasks = user.getTasks().stream().map(TaskDto::new).collect(Collectors.toList());
    }
}
