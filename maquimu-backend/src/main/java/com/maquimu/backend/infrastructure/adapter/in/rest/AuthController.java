package com.maquimu.backend.infrastructure.adapter.in.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser() {
        return ResponseEntity.ok("Login endpoint working");
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser() {
        return ResponseEntity.ok("Register endpoint working");
    }
}
