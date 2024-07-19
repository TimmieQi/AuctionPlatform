package com.example.auctionplatform.service;

import com.example.auctionplatform.dao.AuctionItem;
import com.example.auctionplatform.dto.AuctionItemDTO;

import java.util.List;

public interface AuctionItemService {
    /**
     *方法和AddressService都一样
     */
    public AuctionItem getAuctionItem(int id);
    public List<AuctionItem> getAllAuctionItems();
    public AuctionItem updateAuctionItem(AuctionItem auctionItem);
    public String addAuctionItem(AuctionItemDTO newAuctionItemDTO);
    public String deleteAuctionItemById(int id);
}
