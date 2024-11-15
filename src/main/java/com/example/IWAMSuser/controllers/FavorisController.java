package com.example.IWAMSuser.controllers;

import com.example.IWAMSuser.services.FavorisService;
import com.example.IWAMSuser.models.FavorisModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/favoris")
public class FavorisController {

    private final FavorisService favorisService;

    @Autowired
    public FavorisController(FavorisService favorisService) {
        this.favorisService = favorisService;
    }

    // Endpoint pour créer un favori
    @PostMapping
    public ResponseEntity<FavorisModel> createFavorite(@RequestBody FavorisModel favorite) {
        FavorisModel createdFavorite = favorisService.createFavorite(favorite);
        return ResponseEntity.ok(createdFavorite);
    }

    // Endpoint pour récupérer tous les favoris
    @GetMapping
    public ResponseEntity<List<FavorisModel>> getAllFavorites() {
        List<FavorisModel> favorites = favorisService.getAllFavorites();
        return ResponseEntity.ok(favorites);
    }

    // Endpoint pour récupérer les favoris d'un utilisateur spécifique
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FavorisModel>> getFavoritesByUserId(@PathVariable Integer userId) {
        List<FavorisModel> favorites = favorisService.getFavoritesByUserId(userId);
        return ResponseEntity.ok(favorites);
    }

    // Endpoint pour supprimer un favori par ID
    @DeleteMapping("/{favoriteId}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable Integer favoriteId) {
        favorisService.deleteFavorite(favoriteId);
        return ResponseEntity.noContent().build();
    }
}
