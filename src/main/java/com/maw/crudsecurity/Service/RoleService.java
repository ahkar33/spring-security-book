package com.maw.crudsecurity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maw.crudsecurity.entity.Role;
import com.maw.crudsecurity.repository.RoleRepository;

@Service
public class RoleService {
   
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }   
    
    public Role findRoleByRoleName(String roleName) {
        return roleRepository.findByName(roleName);
    }

}
