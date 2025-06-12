package com.betooo.personalfinanceapi.service.interfaces;

import com.betooo.personalfinanceapi.dto.RoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {

    RoleDTO createRole(RoleDTO roleDTO);
    List<RoleDTO> getAllRoles();
    RoleDTO updateRole(Long id, RoleDTO roleDTO);
    void deleteRole(Long id);
    Page<RoleDTO> getPageableRoles(Pageable pageable);
    RoleDTO getRoleById(Long id);

}
