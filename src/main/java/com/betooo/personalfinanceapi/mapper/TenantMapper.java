package com.betooo.personalfinanceapi.mapper;

import com.betooo.personalfinanceapi.dto.TenantDTO;
import com.betooo.personalfinanceapi.model.entity.Tenant;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TenantMapper {

    private final ModelMapper modelMapper;

    public TenantDTO toDTO(Tenant tenant) {
        return modelMapper.map(tenant, TenantDTO.class);
    }

    public Tenant toEntity(TenantDTO tenantDTO) {
        return modelMapper.map(tenantDTO, Tenant.class);
    }

}
