package com.example.auctionplatform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    List<Favorite> findByUserId(int userId);
    @Query("SELECT f.itemId, COUNT(f) AS favoriteCount FROM Favorite f GROUP BY f.itemId ORDER BY favoriteCount DESC")
    List<Object[]> findFavoriteCounts();
}
