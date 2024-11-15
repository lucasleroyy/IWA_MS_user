package com.example.IWAMSuser.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
@Table(name = "favoris")
public class FavorisModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer favorisId;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private Long locationId;

    // Getters and Setters
    public Integer getFavoriteId() {
        return favorisId;
    }

    public void setFavoriteId(Integer favoriteId) {
        this.favorisId = favoriteId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }
}
