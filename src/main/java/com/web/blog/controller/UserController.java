package com.web.blog.controller;

import com.web.blog.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.web.blog.model.LoginRequest;
import com.web.blog.model.RegisterRequest;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*") // Frontend'in çalıştığı port
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register") // bu endpointe istek atılacaksa parametreler json formatında body şeklinde
                              // gitmeli, query değil
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
        userService.registerUser(registerRequest.getUsername(), registerRequest.getEmail(),
                registerRequest.getPassword());
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        String token = userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
        System.out.println("token" + token);
        if (token != null) {
            return ResponseEntity.ok(token); // Token döndür
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @GetMapping("/deneme")
    String getMethodName() {
        return "test";
    }
}