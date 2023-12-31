package com.kmini.springsecurity.repository;

import com.kmini.springsecurity.domain.entity.RoleHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleHierarchyRepository extends JpaRepository<RoleHierarchy, Long> {

    Optional<RoleHierarchy> findByChildName(String roleName);
}
