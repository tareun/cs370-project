package com.example.demo.mysql;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RecommendedArtistRepository extends JpaRepository<RecommendedArtist, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM RecommendedArtist")
    void clearTable();

    @Transactional
    @Modifying
    @Query(value = "ALTER TABLE recommended_artist AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();

    boolean existsByArtistName(String artistName);

}
