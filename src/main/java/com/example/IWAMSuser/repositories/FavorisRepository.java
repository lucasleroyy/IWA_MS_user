package com.example.IWAMSuser.repositories;

import com.example.IWAMSuser.models.FavorisModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FavorisRepository extends JpaRepository<FavorisModel, Integer> {
    // Méthode pour récupérer les favoris d'un utilisateur spécifique
    List<FavorisModel> findByUserId(Integer userId);

    boolean existsByUserIdAndLocationId(Integer userId, Integer locationId);
}
