package com.betooo.personalfinanceapi.mapper;

import com.betooo.personalfinanceapi.dto.RoleDTO;
import com.betooo.personalfinanceapi.model.entity.Role;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleMapper {

    private final ModelMapper modelMapper;

    public RoleDTO toDTO(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }

    public Role toEntity(RoleDTO roleDTO) {
        return modelMapper.map(roleDTO, Role.class);
    }

}