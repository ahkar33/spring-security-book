package com.maw.crudsecurity.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.maw.crudsecurity.Service.RoleService;
import com.maw.crudsecurity.Service.UserService;
import com.maw.crudsecurity.entity.Role;
import com.maw.crudsecurity.entity.User;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;    

    @Autowired
    private RoleService roleService;

    @GetMapping("/registration")
    public String setupUserRegistration(ModelMap model) {
        model.addAttribute("user", new User());
        return "user-registration";
    }    

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("user") User user, ModelMap model) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        List<Role> roleList = roleService.findAllRoles(); 
        for(Role role : roleList) {
            if(role.getName().equals("USER")) {
                user.addUserRoles(role);
            }
        }
        userService.addUser(user);
        model.addAttribute("msg", "successfully registered");
        return "user-registration";
    }

}
