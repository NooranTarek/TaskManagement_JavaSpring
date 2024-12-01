package com.example.task_management_API.Helpers;

import com.example.task_management_API.DTO.TaskDto;
import com.example.task_management_API.entities.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "userName")
    TaskDto taskToTaskDto(Task task);

    List<TaskDto> tasksToTaskDtos(List<Task> tasks);
}
