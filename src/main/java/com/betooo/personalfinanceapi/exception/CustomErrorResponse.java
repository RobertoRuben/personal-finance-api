package com.betooo.personalfinanceapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorResponse {
    private String type;
    private String title;
    private Integer status;
    private String detail;
    private String instance;

    private LocalDateTime timestamp;
    private String code;

    public CustomErrorResponse(LocalDateTime dateTime, String message, String details) {
        this.timestamp = dateTime;
        this.title = message;
        this.detail = details;
    }

    public CustomErrorResponse(LocalDateTime dateTime, String message, String details, String code) {
        this.timestamp = dateTime;
        this.title = message;
        this.detail = details;
        this.code = code;
    }
}
