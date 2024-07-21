package com.example.auctionplatform.controller;

import com.example.auctionplatform.dto.AuctionItemDTO;
import com.example.auctionplatform.service.AuctionItemService;
import com.example.auctionplatform.service.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api")
public class ViewDetailController {
    @Autowired
    public ViewDetailController(AuctionItemService auctionItemService) {
        this.auctionItemService = auctionItemService;
    }
    private final AuctionItemService auctionItemService;
    @GetMapping("/viewDetail/auctionId/{auctionId}")
    public Response<AuctionItemDTO> viewDetailById(@PathVariable("auctionId") int auctionId) {
        return auctionItemService.getAuctionItem(auctionId);
    }
}
