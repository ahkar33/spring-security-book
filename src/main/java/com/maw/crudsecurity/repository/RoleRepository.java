package com.maw.crudsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maw.crudsecurity.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
    
}
