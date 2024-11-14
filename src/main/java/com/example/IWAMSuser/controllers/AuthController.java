package com.example.IWAMSuser.controllers;

import com.example.IWAMSuser.models.UserModel;
import com.example.IWAMSuser.repositories.UserRepository;
import com.example.IWAMSuser.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserModel user) {
        UserModel existingUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        if (passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            // Créer le token JWT
            String jwt = jwtUtil.generateToken(existingUser);

            // Retourner le token et l'ID de l'utilisateur
            return ResponseEntity.ok(new LoginResponse(jwt, existingUser.getUserId()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    // Classe de réponse pour encapsuler le token et l'ID de l'utilisateur
    public static class LoginResponse {
        private String token;
        private Long userId;

        public LoginResponse(String token, Long userId) {
            this.token = token;
            this.userId = userId;
        }

        // Getters et setters
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }
    }
}
