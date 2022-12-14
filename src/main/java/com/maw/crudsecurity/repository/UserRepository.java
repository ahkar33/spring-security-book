package com.maw.crudsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maw.crudsecurity.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
   
     User findByName(String name); 

     User findByEmail(String email);

     Boolean existsByName(String name);
     
     Boolean existsByEmail(String email);

     User findByVerificationCode(String code);

}
