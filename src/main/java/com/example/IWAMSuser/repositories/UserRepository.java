package com.example.IWAMSuser.repositories;

import com.example.IWAMSuser.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByEmail(String email);
}


