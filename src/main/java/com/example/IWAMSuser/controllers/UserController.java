package com.example.IWAMSuser.controllers;

import com.example.IWAMSuser.models.User;
import com.example.IWAMSuser.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Liste tous les utilisateurs
    @GetMapping
    public List<User> list() {
        return userRepository.findAll();
    }

    // Récupère un utilisateur par ID
    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID " + id + " not found"));
        return ResponseEntity.ok(user);
    }

    // Crée un nouvel utilisateur
    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        // Hacher le mot de passe avant de sauvegarder
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User createdUser = userRepository.saveAndFlush(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // Supprime un utilisateur par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID " + id + " not found");
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Met à jour un utilisateur existant
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID " + id + " not found"));

        // Copie des propriétés, ignore `userId` pour ne pas écraser l'ID de l'utilisateur existant
        BeanUtils.copyProperties(user, existingUser, "userId");

        User updatedUser = userRepository.saveAndFlush(existingUser);
        return ResponseEntity.ok(updatedUser);
    }
}
