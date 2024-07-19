package com.example.auctionplatform.service;

import com.example.auctionplatform.dao.AuctionItem;
import com.example.auctionplatform.dto.AuctionItemDTO;
import com.example.auctionplatform.dto.FavoriteDTO;

import java.util.List;

public interface AuctionItemService {
    /**
     *方法和AddressService都一样
     */
    AuctionItem getAuctionItem(int id);

    /**
     * 返回所有订单信息
     * @return
     */
    List<AuctionItem> getAllAuctionItems();

    /**
     *更新数据
     */
    AuctionItem updateAuctionItem(AuctionItem auctionItem);

    /**
     *添加新的商品项目进去
     */
    String addAuctionItem(AuctionItemDTO newAuctionItemDTO);

    /**
     *  根据id删除
     */
    String deleteAuctionItemById(int id);

    /**
     * -1,0,1在short state里面是有意义的值，所以更改了short的默认值，在control层返回-2
     */
    public String updateAuctionItem(AuctionItemDTO newAuctionItemDTO);
    /**
     * 根据id显示商家所有商品
     */
    public List<AuctionItemDTO> getAuctionItemsByUserId(int userId);
}
