package com.maw.crudsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.maw.crudsecurity.Service.UserService;
import com.maw.crudsecurity.entity.User;

public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UserService userService;
    // private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByName(username);
        if(user == null) {
            throw new UsernameNotFoundException("could not find user");
        }
        return new CustomUserDetails(user);
    }
    
}
