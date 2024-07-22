package com.example.auctionplatform.service;


import com.example.auctionplatform.dto.AuctionItemDTO;


import java.util.List;

public interface AuctionItemService {
    /**
     *方法和AddressService都一样
     */
    Response<AuctionItemDTO> getAuctionItem(int id);

    /**
     * 返回所有订单信息
     */
    Response<List<AuctionItemDTO>> getAllAuctionItems();

    /**
     *添加新的商品项目进去
     */
    Response<Void> addAuctionItem(AuctionItemDTO newAuctionItemDTO);

    /**
     *  根据id删除
     */
    Response<Void> deleteAuctionItemById(int id);

    /**
     * 更新数据
     * -1,0,1在short state里面是有意义的值，所以更改了short的默认值，在control层返回-2
     */
    Response<Void> updateAuctionItem(AuctionItemDTO newAuctionItemDTO);
    /**
     * 根据id显示某人所有当前出价最高商品
     */
    Response<List<AuctionItemDTO>> getAuctionItemsByUserId(int userId);

    /**
     * 根据id显示商家所有商品
     */
    Response<List<AuctionItemDTO>> getAuctionItemsByUploaderId(int UploadId);
    /**
     *根据 name，返回 字段 AuctionItem.name中包含 name字串的所有AuctionItem的 ID,name,Image
     */
    Response<List<AuctionItemDTO>> getAuctionItemsByName(String name);

    /**
     *根据 ID 找到 对应商品，然后对比auctionItemDTO中
     */
    Response<Void> OfferingPrice(AuctionItemDTO auctionItemDTO);

}
