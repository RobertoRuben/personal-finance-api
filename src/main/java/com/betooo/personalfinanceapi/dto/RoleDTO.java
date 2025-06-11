package com.betooo.personalfinanceapi.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RoleDTO {

    private Long id;

    @NotBlank(message = "Role name cannot be blank")
    @Size(min = 2, max = 255, message = "Role name must be between 2 and 255 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Role name can only contain alphanumeric characters and underscores")
    private String roleName;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
}
