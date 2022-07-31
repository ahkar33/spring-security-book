package com.maw.crudsecurity.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maw.crudsecurity.entity.User;
import com.maw.crudsecurity.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Boolean isNameExists(String name) {
        return userRepository.existsByName(name);
    }

    public User findUserByName(String name) {
        return userRepository.findByName(name);
    } 

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    } 

    public void addUser(User user) {
        userRepository.save(user);
    }

}
