package com.example.auctionplatform.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionItemRepository extends CrudRepository<AuctionItem, Integer> {
}
