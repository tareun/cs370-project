package com.example.demo.mysql;

import jakarta.persistence.*;

@Entity
public class RecommendedArtist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String artistName;

    // Constructors, getters, and setters

    public RecommendedArtist() {
    }

    public RecommendedArtist(String artistName) {
        this.artistName = artistName;
    }

    public Long getId() {
        return id;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
