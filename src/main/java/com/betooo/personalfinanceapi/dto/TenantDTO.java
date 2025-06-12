package com.betooo.personalfinanceapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TenantDTO {

    @Schema(description = "Unique identifier for the tenant", example = "1")
    private Long id;

    @Schema(description = "Name of the tenant", example = "Example Tenant")
    @NotBlank(message = "Tenant name cannot be blank")
    @Size(min = 2, max = 255, message = "Tenant name must be between 2 and 255 characters")
    private String tenantName;

    @Schema(description = "Unique slug for the tenant", example = "example-tenant")
    @NotBlank(message = "Slug cannot be blank")
    @Size(min = 2, max = 255, message = "Slug must be between 2 and 255 characters")
    private String slug;

    @Schema(description = "Indicates if the tenant is active", example = "true")
    private Boolean isActive;

    @Schema(description = "Timestamp when the tenant was created", example = "2023-10-01T12:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp when the tenant was last updated", example = "2023-10-01T12:00:00")
    private LocalDateTime updatedAt;
}
