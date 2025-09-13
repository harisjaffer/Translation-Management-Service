package com.example.Translation.Management.Service.Controller;


import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.Translation.Management.Service.DTO.AuthResponse;
import com.example.Translation.Management.Service.DTO.LoginDto;
import com.example.Translation.Management.Service.DTO.RegisterDto;

import com.example.Translation.Management.Service.Servc.AuthService;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto dto) {
        try {
            String message = authService.register(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto) {
        try {
            AuthResponse response = authService.login(dto);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
