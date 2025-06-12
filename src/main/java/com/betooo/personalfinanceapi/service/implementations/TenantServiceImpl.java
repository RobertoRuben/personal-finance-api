package com.betooo.personalfinanceapi.service.implementations;

import com.betooo.personalfinanceapi.dto.TenantDTO;
import com.betooo.personalfinanceapi.exception.ConflictException;
import com.betooo.personalfinanceapi.exception.ResourceNotFoundException;
import com.betooo.personalfinanceapi.mapper.TenantMapper;
import com.betooo.personalfinanceapi.model.entity.Tenant;
import com.betooo.personalfinanceapi.repository.TenantRepository;
import com.betooo.personalfinanceapi.service.interfaces.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;

    @Transactional
    @Override
    public TenantDTO createTenant(TenantDTO tenantDTO) {
        if(tenantRepository.existsByTenantName(tenantDTO.getTenantName())){
            throw new ConflictException("Tenant already exists");
        }
        if (tenantRepository.existsBySlug(tenantDTO.getSlug())) {
            throw new ConflictException("Tenant with this slug already exists");
        }

        Tenant tenant = tenantMapper.toEntity(tenantDTO);
        tenant = tenantRepository.save(tenant);

        return tenantMapper.toDTO(tenant);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TenantDTO> getAllTenants() {
        List<Tenant> tenants = tenantRepository.findAll();
        return tenants.stream().map(tenantMapper::toDTO).toList();
    }

    @Transactional
    @Override
    public TenantDTO updateTenant(Long id, TenantDTO tenantDTO) {
        Tenant tenantToUpdate = tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));

        if (tenantRepository.existsByTenantName(tenantDTO.getTenantName()) &&
            !tenantToUpdate.getTenantName().equals(tenantDTO.getTenantName())) {
            throw new ConflictException("Tenant with this name already exists");
        }

        if (tenantRepository.existsBySlug(tenantDTO.getSlug()) &&
            !tenantToUpdate.getSlug().equals(tenantDTO.getSlug())) {
            throw new ConflictException("Tenant with this slug already exists");
        }

        tenantToUpdate.setTenantName(tenantDTO.getTenantName());
        tenantToUpdate.setSlug(tenantDTO.getSlug());

        return tenantMapper.toDTO(tenantRepository.save(tenantToUpdate));
    }

    @Transactional
    @Override
    public void deleteTenant(Long id) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
        tenantRepository.delete(tenant);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TenantDTO> getTenants(Pageable pageable) {
        Page<Tenant> tenants = tenantRepository.findAll(pageable);
        return tenants.map(tenantMapper::toDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public TenantDTO getTenantById(Long id) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
        return tenantMapper.toDTO(tenant);
    }

    @Transactional
    @Override
    public void deactivateTenant(Long id) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
        tenant.setIsActive(false);
        tenantRepository.save(tenant);
    }
}
