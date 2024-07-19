package com.example.auctionplatform.service;

import com.example.auctionplatform.dao.AuctionItem;
import com.example.auctionplatform.dto.AuctionItemDTO;

import java.util.List;

public interface AuctionItemService {
    /**
     *方法和AddressService都一样
     */
    AuctionItem getAuctionItem(int id);

    /**
     * 返回一个可被遍历的List类型。
     * @return
     */
    List<AuctionItem> getAllAuctionItems();
    AuctionItem updateAuctionItem(AuctionItem auctionItem);
    String addAuctionItem(AuctionItemDTO newAuctionItemDTO);
    String deleteAuctionItemById(int id);
}
