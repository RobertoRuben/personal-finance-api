package com.betooo.personalfinanceapi.service.interfaces;

import com.betooo.personalfinanceapi.dto.TenantDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TenantService {

    TenantDTO createTenant(TenantDTO tenantDTO);
    List<TenantDTO> getAllTenants();
    TenantDTO updateTenant(Long id, TenantDTO tenantDTO);
    void deleteTenant(Long id);
    Page<TenantDTO> getTenantsPage(Pageable pageable);
    TenantDTO getTenantById(Long id);
    TenantDTO changeTenantActiveStatus(Long id, boolean active);

}
