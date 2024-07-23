package com.example.auctionplatform.service;


import com.example.auctionplatform.dao.AuctionItem;
import com.example.auctionplatform.dao.AuctionItemRepository;

import jakarta.servlet.ServletContextEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能：
 * 作者：万礼阳
 * 日期：2024/7/23 下午8:45
 */
@Component
public class InitListListener implements ApplicationRunner {
    @Autowired
    public AuctionItemServiceImpl auctionItemServiceImpl;
    @Autowired
    public AuctionItemRepository auctionItemRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("contextInitialized");
        List<AuctionItem> auctionItemList = auctionItemRepository.findAll();
        for(var auctionItem : auctionItemList) {
            switch (auctionItem.getState()){
                case 0:
                    auctionItemServiceImpl.insert2notStartedAuction(auctionItem.getId(),
                            auctionItem.getUploadTime(),auctionItem.getAuctionTime());
                    break;
                case 1:
                    auctionItemServiceImpl.insert2notFinishedAuction(auctionItem.getId(),
                            auctionItem.getUploadTime(),auctionItem.getAuctionTime());
                    break;
                case -1:
            }
        }
    }
}