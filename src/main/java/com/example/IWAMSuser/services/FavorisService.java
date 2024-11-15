package com.example.IWAMSuser.services;

import com.example.IWAMSuser.models.FavorisModel;
import com.example.IWAMSuser.repositories.FavorisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavorisService {

    private final FavorisRepository favorisRepository;

    @Autowired
    public FavorisService(FavorisRepository favorisRepository) {
        this.favorisRepository = favorisRepository;
    }

    // Créer un nouveau favori
    public FavorisModel createFavorite(FavorisModel favorite) {
        return favorisRepository.save(favorite);
    }

    // Récupérer tous les favoris
    public List<FavorisModel> getAllFavorites() {
        return favorisRepository.findAll();
    }

    // Récupérer les favoris d'un utilisateur spécifique
    public List<FavorisModel> getFavoritesByUserId(Integer userId) {
        return favorisRepository.findByUserId(userId);
    }

    // Supprimer un favori par ID
    public void deleteFavorite(Integer favoriteId) {
        favorisRepository.deleteById(favoriteId);
    }

    // Vérifier si un favori existe par ID
    public Optional<FavorisModel> getFavoriteById(Integer favoriteId) {
        return favorisRepository.findById(favoriteId);
    }
}
