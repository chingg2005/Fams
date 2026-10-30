package com.example.fams.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterPageController {
    @GetMapping("/register")
    public String registerPage() {
        return "user/register";
    }
}
