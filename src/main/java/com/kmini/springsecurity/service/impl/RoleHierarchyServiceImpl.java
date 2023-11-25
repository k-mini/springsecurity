package com.kmini.springsecurity.service.impl;

import com.kmini.springsecurity.domain.entity.RoleHierarchy;
import com.kmini.springsecurity.repository.RoleHierarchyRepository;
import com.kmini.springsecurity.service.RoleHierarchyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleHierarchyServiceImpl implements RoleHierarchyService {

    private final RoleHierarchyRepository roleHierarchyRepository;

    @Transactional
    @Override
    public String findAllHierarchy() {
//        StringBuilder concatedRoles = new StringBuilder();
        return roleHierarchyRepository.findAll()
                .stream()
                .filter(roleHierarchy ->  Objects.nonNull(roleHierarchy.getParentName()))
                .map(roleHierarchy ->
                        roleHierarchy.getParentName().getChildName() + " > " + roleHierarchy.getChildName() + "\n"
                )
                .collect(Collectors.joining());
    }
}
