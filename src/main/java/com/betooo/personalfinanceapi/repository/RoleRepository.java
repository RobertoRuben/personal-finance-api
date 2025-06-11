package com.betooo.personalfinanceapi.repository;

import com.betooo.personalfinanceapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByRoleName(Role role);

}
