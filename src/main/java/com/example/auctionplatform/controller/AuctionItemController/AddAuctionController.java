package com.example.auctionplatform.controller.AuctionItemController;

import com.example.auctionplatform.dto.AuctionItemDTO;
import com.example.auctionplatform.service.AuctionItemService;
import com.example.auctionplatform.service.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能：上传商品
 * 作者：万礼阳
 * 日期：2024/7/21 上午10:23
 */
@RestController
@RequestMapping("/api/auction")
public class AddAuctionController {
    @Autowired
    public AddAuctionController(AuctionItemService auctionItemService) {
        this.auctionItemService = auctionItemService;
    }
    private final AuctionItemService auctionItemService;
    @PostMapping("/add/Auction")
    public Response<Void> addAuctionItem(@RequestBody AuctionItemDTO auctionItemDTO) {
        return auctionItemService.addAuctionItem(auctionItemDTO);
    }
}