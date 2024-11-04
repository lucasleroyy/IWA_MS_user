package com.example.IWAMSuser.repositories;

import com.example.IWAMSuser.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Vous pouvez ajouter ici des méthodes de recherche personnalisées si nécessaire, par exemple:
    // Optional<User> findByEmail(String email);
}
