package com.example.IWAMSuser.services;

import com.example.IWAMSuser.models.FavorisModel;
import com.example.IWAMSuser.repositories.FavorisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class FavorisService {

    @Autowired
    private final FavorisRepository favorisRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public FavorisService(FavorisRepository favorisRepository, RestTemplate restTemplate) {
        this.favorisRepository = favorisRepository;
        this.restTemplate = restTemplate;
    }

    // Créer un nouveau favori et envoyer une notification
    public FavorisModel createFavorite(FavorisModel favorite) {
        // Enregistrer le favori dans la base de données
        FavorisModel savedFavorite = favorisRepository.save(favorite);

        // Appeler le service de notifications pour informer le propriétaire du lieu
        createFavoriteNotification(savedFavorite);

        return savedFavorite;
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

    // Appeler le service de notifications pour informer le propriétaire du lieu
    private void createFavoriteNotification(FavorisModel favoris) {

        // Construire le corps de la requête
        Map<String, Long> requestBody = Map.of(
                "locationId", favoris.getLocationId()
        );

        // URL du service de notifications
        String notificationUrl = "http://host.docker.internal:8085/notifications/create/favorite";

        try {
            // Appel à la route POST du ms_notification
            restTemplate.postForEntity(notificationUrl, requestBody, Void.class);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'appel au service de notifications : " + e.getMessage());
        }
    }
}
