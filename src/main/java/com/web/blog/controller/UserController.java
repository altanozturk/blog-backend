package com.web.blog.controller;

import com.web.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Frontend'in çalıştığı port
public class UserController {

    @Autowired
    private UserService userService;

    
    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password) {
        userService.registerUser(username, email, password);
        return "redirect:login";
    }

    @GetMapping("/deneme")
    String getMethodName() {
        return "test";
    }
}