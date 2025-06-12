package com.betooo.personalfinanceapi.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String PROBLEM_BASE_URL = "https://api.personalfinance.com/errors";

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomErrorResponse errorResponse = buildErrorResponse(
                PROBLEM_BASE_URL + "/not-found",
                "Recurso no encontrado",
                status.value(),
                ex.getMessage(),
                getRequestURI(request),
                "404.1",
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CustomErrorResponse> handleBadRequestException(BadRequestException ex, WebRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomErrorResponse errorResponse = buildErrorResponse(
                PROBLEM_BASE_URL + "/bad-request",
                "Solicitud incorrecta",
                status.value(),
                ex.getMessage(),
                getRequestURI(request),
                "400.1",
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<CustomErrorResponse> handleConflictException(ConflictException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        CustomErrorResponse errorResponse = buildErrorResponse(
                PROBLEM_BASE_URL + "/conflict",
                "Conflicto de recursos",
                status.value(),
                ex.getMessage(),
                getRequestURI(request),
                "409.1",
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<CustomErrorResponse> handleUnprocessableEntityException(UnprocessableEntityException ex, WebRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        CustomErrorResponse errorResponse = buildErrorResponse(
                PROBLEM_BASE_URL + "/unprocessable-entity",
                "Entidad no procesable",
                status.value(),
                ex.getMessage(),
                getRequestURI(request),
                "422.2",
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleAllException(Exception ex, WebRequest request){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        CustomErrorResponse errorResponse = buildErrorResponse(
                PROBLEM_BASE_URL + "/server-error",
                "Error interno del servidor",
                status.value(),
                ex.getMessage(),
                getRequestURI(request),
                "500.1",
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        String errorDetails = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField().concat(": ").concat(e.getDefaultMessage()))
                .collect(Collectors.joining(", "));

        HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        CustomErrorResponse errorResponse = buildErrorResponse(
                PROBLEM_BASE_URL + "/validation-error",
                "Error de validación",
                httpStatus.value(),
                errorDetails,
                getRequestURI(request),
                "422.1",
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    private CustomErrorResponse buildErrorResponse(
            String type,
            String title,
            Integer status,
            String detail,
            String instance,
            String code,
            LocalDateTime timestamp) {

        CustomErrorResponse errorResponse = new CustomErrorResponse();
        errorResponse.setType(type);
        errorResponse.setTitle(title);
        errorResponse.setStatus(status);
        errorResponse.setDetail(detail);
        errorResponse.setInstance(instance);
        errorResponse.setCode(code);
        errorResponse.setTimestamp(timestamp);

        return errorResponse;
    }

    private String getRequestURI(WebRequest request) {
        if (request instanceof ServletWebRequest) {
            HttpServletRequest servletRequest = ((ServletWebRequest) request).getRequest();
            return servletRequest.getRequestURI();
        }
        return "";
    }
}
