package com.example.auctionplatform.controller;

import com.example.auctionplatform.dao.AuctionItem;
import com.example.auctionplatform.dto.AuctionItemDTO;
import com.example.auctionplatform.service.AuctionItemService;
import com.example.auctionplatform.service.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auction")
public class AuctionController {
    @Autowired
    public AuctionController(AuctionItemService auctionItemService) {
        this.auctionItemService = auctionItemService;
    }
    private final AuctionItemService auctionItemService;
    @PostMapping("/OfferingPrice")
    public Response<Void> OfferingPrice(@RequestBody AuctionItemDTO auctionItemDTO){
        AuctionItemDTO offeringPriceDTO  = new AuctionItemDTO();
         return Response.newError("Not implemented yet");
    }
}
