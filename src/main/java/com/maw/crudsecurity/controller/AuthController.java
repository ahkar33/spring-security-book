package com.maw.crudsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

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


}
