package com.maw.crudsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/adminPage")
    public String showAdminPage() {
        return "/users-home/admin-page";
    }

    @GetMapping("/editorPage")
    public String showEditorPage() {
        return "/users-home/editor-page";
    }

    @GetMapping("/userPage")
    public String showUserPage() {
        return "/users-home/user-page";
    }

}
