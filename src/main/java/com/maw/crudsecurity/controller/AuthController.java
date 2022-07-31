package com.maw.crudsecurity.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.maw.crudsecurity.entity.User;
import com.maw.crudsecurity.service.UserService;

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

    @GetMapping("/registration")
    public String setupUserRegistration(ModelMap model) {
        model.addAttribute("user", new User());
        return "user-registration";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("user") User user, ModelMap model, HttpServletRequest request)
            throws UnsupportedEncodingException, MessagingException {
        if (userService.isEmailExists(user.getEmail())) {
            model.addAttribute("msg", "email already exists");
            return "user-registration";
        }
        if(!user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("msg", "passwords do not match");
            return "user-registration";
        }
        userService.registerUser(user, getSiteURL(request));

        model.addAttribute("msg", "please verify your email address");
        return "user-registration";
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (userService.verify(code)) {
            return "verify-success";
        } else {
            return "verify-fail";
        }
    }

}
