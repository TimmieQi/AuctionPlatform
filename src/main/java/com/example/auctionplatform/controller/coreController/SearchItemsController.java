package com.example.auctionplatform.controller.coreController;


import com.example.auctionplatform.dto.AuctionItemDTO;
import com.example.auctionplatform.service.AuctionItemService;
import com.example.auctionplatform.service.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SearchItemsController {
    @Autowired
    public SearchItemsController(AuctionItemService auctionItemService) {
        this.auctionItemService = auctionItemService;
    }
    private final AuctionItemService auctionItemService;
    @GetMapping("/search/name/{name}")
    public Response<List<AuctionItemDTO>> searchItemsByName(@PathVariable(name = "name") String name) {
        return auctionItemService.getAuctionItemsByName(name);
    }
}
