package com.kmini.springsecurity.service;

import com.kmini.springsecurity.domain.entity.Role;

import java.util.List;

public interface RoleService {

    Role getRole(long id);

    List<Role> getRoles();

    void createRole(Role role);

    void deleteRole(Long id);
}
