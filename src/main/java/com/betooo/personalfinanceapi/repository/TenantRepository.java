package com.betooo.personalfinanceapi.repository;

import com.betooo.personalfinanceapi.model.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, Long> {

    boolean existsByTenantName(String tenantName);
    boolean existsBySlug(String slug);

}
