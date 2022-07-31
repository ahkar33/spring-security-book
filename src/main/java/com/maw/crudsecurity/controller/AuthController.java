package com.maw.crudsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.maw.crudsecurity.Service.RoleService;
import com.maw.crudsecurity.Service.UserService;
import com.maw.crudsecurity.entity.Role;
import com.maw.crudsecurity.entity.User;

@Controller
public class AuthController {

    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }

    @GetMapping("/login")
    public String setupLogin(ModelMap model) {
        model.addAttribute("user", new User());
        return "login";
    }

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
        Role role = roleService.findRoleByRoleName("USER");
        user.addUserRoles(role);
        userService.addUser(user);
        model.addAttribute("msg", "successfully registered");
        return "user-registration";
    }

}
