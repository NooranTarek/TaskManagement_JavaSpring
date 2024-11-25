package com.example.task_management_API.Helpers;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiResponse <T>{
    private String message ;
    private T data;
    private HttpStatus status;

    public ApiResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
    public ApiResponse(String message) {
        this.message = message;
        this.status = HttpStatus.OK;
    }

    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
        this.status = HttpStatus.OK;
    }


    public ApiResponse(String message, T data, HttpStatus status) {
        this.message = message;
        this.data = data;
        this.status = status;
    }
}
