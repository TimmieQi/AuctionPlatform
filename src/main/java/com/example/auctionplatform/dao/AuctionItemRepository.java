package com.example.auctionplatform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionItemRepository extends JpaRepository<AuctionItem, Integer> {
    List<AuctionItem> findByUserId(int userId);
    List<AuctionItem> findByName(String name);
    List<AuctionItem> findByUploaderId(int uploaderId);
}
