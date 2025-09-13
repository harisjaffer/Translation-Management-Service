package com.example.Translation.Management.Service.Servc;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Translation.Management.Service.DTO.AuthResponse;
import com.example.Translation.Management.Service.DTO.LoginDto;
import com.example.Translation.Management.Service.DTO.RegisterDto;
import com.example.Translation.Management.Service.Entity.AppUserEntity;
import com.example.Translation.Management.Service.Repository.AppUserRepository;
import com.example.Translation.Management.Service.Repository.TokenProvider;


@Service
public class AuthService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public AuthService(AppUserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public String register(RegisterDto dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        AppUserEntity user = new AppUserEntity();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);

        return "User registered successfully";
    }

    public AuthResponse login(LoginDto dto) {
        AppUserEntity user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        // Roles in DB: "USER,ADMIN"
        List<String> roles = Arrays.asList(user.getRole().split(","));

        String token = tokenProvider.generateToken(user.getUsername(), roles);
        return new AuthResponse(token);
    }


}