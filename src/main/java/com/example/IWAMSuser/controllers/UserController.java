package com.example.IWAMSuser.controllers;

import com.example.IWAMSuser.models.UserModel;
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
    public List<UserModel> list() {
        return userRepository.findAll();
    }

    // Récupère un utilisateur par ID
    @GetMapping("/{id}")
    public ResponseEntity<UserModel> get(@PathVariable Long id) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID " + id + " not found"));
        return ResponseEntity.ok(user);
    }

    @PostMapping
public ResponseEntity<UserModel> create(@RequestBody UserModel user) {
    System.out.println("Utilisateur reçu : " + user);
    // Attribuer un rôle par défaut
    if (user.getRole() == null || user.getRole().isEmpty()) {
        user.setRole("user");
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    UserModel createdUser = userRepository.saveAndFlush(user);

    return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
}

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
    public ResponseEntity<UserModel> update(@PathVariable Long id, @RequestBody UserModel user) {
        UserModel existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID " + id + " not found"));

        // Copie des propriétés, ignore `userId` pour ne pas écraser l'ID de l'utilisateur existant
        BeanUtils.copyProperties(user, existingUser, "userId");

        UserModel updatedUser = userRepository.saveAndFlush(existingUser);
        return ResponseEntity.ok(updatedUser);
    }
}
