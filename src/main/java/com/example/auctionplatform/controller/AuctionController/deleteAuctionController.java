package com.example.auctionplatform.controller.AuctionController;

import com.example.auctionplatform.service.AuctionItemService;
import com.example.auctionplatform.service.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：
 * 作者：万礼阳
 * 日期：2024/7/21 上午10:56
 */
@RestController
@RequestMapping("/api/auction")
public class deleteAuctionController {
    private final AuctionItemService auctionItemService;
    @Autowired
    public deleteAuctionController(AuctionItemService auctionItemService) {
        this.auctionItemService = auctionItemService;
    }
    @DeleteMapping("/delete/auctionId/{auctionId}")
    public Response<Void> delete(@PathVariable("auctionId") int auctionId) {
        return auctionItemService.deleteAuctionItemById(auctionId);
    }
}