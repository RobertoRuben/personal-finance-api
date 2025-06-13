package com.betooo.personalfinanceapi.controller;

import com.betooo.personalfinanceapi.dto.TenantDTO;
import com.betooo.personalfinanceapi.service.interfaces.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tenants")
@RequiredArgsConstructor
@Tag(name = "Tenants", description = "Endpoints for managing tenants")
public class TenantController {

    private final TenantService tenantService;

    @GetMapping
    @Operation(summary = "Get all tenants", description = "Retrieves a list of all tenants in the system.")
    public ResponseEntity<List<TenantDTO>> getAllTenants(){
        List<TenantDTO> tenants = tenantService.getAllTenants();
        return ResponseEntity.ok(tenants);
    }

    @GetMapping("/page")
    @Operation(summary = "Get paginated tenants", description = "Retrieves a paginated list of tenants.")
    public ResponseEntity<Page<TenantDTO>> getTenantsPage(@PageableDefault(size = 10) Pageable pageable) {
        Page<TenantDTO> tenantsPage = tenantService.getTenantsPage(pageable);
        return new ResponseEntity<>(tenantsPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get tenant by ID", description = "Retrieves a tenant by its ID.")
    public ResponseEntity<TenantDTO> getTenantById(@PathVariable Long id) {
        TenantDTO tenant = tenantService.getTenantById(id);
        return new ResponseEntity<>(tenant, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create a new tenant", description = "Creates a new tenant in the system.")
    public ResponseEntity<TenantDTO> createTenant(@Valid @RequestBody TenantDTO tenantDTO){
        TenantDTO createdTenant = tenantService.createTenant(tenantDTO);
        return new ResponseEntity<>(createdTenant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing tenant", description = "Updates the details of an existing tenant by its ID.")
    public ResponseEntity<TenantDTO> updateTenant(@PathVariable Long id, @Valid @RequestBody TenantDTO tenantDTO) {
        TenantDTO updatedTenant = tenantService.updateTenant(id, tenantDTO);
        return new ResponseEntity<>(updatedTenant, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a tenant", description = "Deletes a tenant from the system.")
    public ResponseEntity<Void> deleteTenant(@PathVariable Long id) {
        tenantService.deleteTenant(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change tenant active status", description = "Updates the tenant active status to the specified value.")
    public ResponseEntity<TenantDTO> changeTenantActiveStatus(@PathVariable Long id, @RequestParam boolean active) {
        TenantDTO updatedTenant = tenantService.changeTenantActiveStatus(id, active);
        return ResponseEntity.ok(updatedTenant);
    }
}
