package com.betooo.personalfinanceapi.service.implementations;

import com.betooo.personalfinanceapi.dto.RoleDTO;
import com.betooo.personalfinanceapi.exception.ConflictException;
import com.betooo.personalfinanceapi.exception.ResourceNotFoundException;
import com.betooo.personalfinanceapi.mapper.RoleMapper;
import com.betooo.personalfinanceapi.model.entity.Role;
import com.betooo.personalfinanceapi.repository.RoleRepository;
import com.betooo.personalfinanceapi.service.interfaces.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Transactional
    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        if(roleRepository.existsByRoleName(roleDTO.getRoleName())){
            throw new ConflictException("Role already exists");
        }
        Role role = roleMapper.toEntity(roleDTO);
        role = roleRepository.save(role);
        return roleMapper.toDTO(role);
    }

    @Transactional(readOnly = true)
    @Override
    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::toDTO).toList();

    }

    @Transactional
    @Override
    public RoleDTO updateRole(Long id, RoleDTO roleDTO) {
        Role roleToUpdate = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        if (roleRepository.existsByRoleName(roleDTO.getRoleName()) &&
            !roleToUpdate.getRoleName().equals(roleDTO.getRoleName())) {
            throw new ConflictException("Role with this name already exists");
        }

        roleToUpdate.setRoleName(roleDTO.getRoleName());

        return roleMapper.toDTO(roleRepository.save(roleToUpdate));
    }

    @Transactional
    @Override
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        roleRepository.delete(role);

    }

    @Transactional(readOnly = true)
    @Override
    public Page<RoleDTO> getPageableRoles(Pageable pageable) {
        Page<Role> roles = roleRepository.findAll(pageable);
        return roles.map(roleMapper::toDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public RoleDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        return roleMapper.toDTO(role);
    }
}
