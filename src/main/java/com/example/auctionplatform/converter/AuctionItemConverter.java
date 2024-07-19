package com.example.auctionplatform.converter;

import com.example.auctionplatform.dao.AuctionItem;
import com.example.auctionplatform.dto.AuctionItemDTO;

import java.util.ArrayList;
import java.util.List;

public class AuctionItemConverter {
    public static AuctionItem convertAuctionItemDTO(AuctionItemDTO auctionItemDTO) {
        AuctionItem auctionItem = new AuctionItem();
        auctionItem.setId(auctionItemDTO.getId());
        auctionItem.setUserId(auctionItemDTO.getUserId());
        auctionItem.setDescription(auctionItemDTO.getDescription());
        auctionItem.setImage(auctionItemDTO.getImage());
        auctionItem.setInitialPrice(auctionItemDTO.getInitialPrice());
        auctionItem.setCurrPrice(auctionItemDTO.getCurrPrice());
        auctionItem.setState(auctionItemDTO.getState());
        auctionItem.setUploadTime(auctionItemDTO.getUploadTime());
        auctionItem.setAuctionTime(auctionItemDTO.getAuctionTime());
        auctionItem.setFavoriteSum(auctionItemDTO.getFavoriteSum());
        return auctionItem;
    }
    public static AuctionItemDTO convertAuctionItem(AuctionItem auctionItem) {
        AuctionItemDTO auctionItemDTO = new AuctionItemDTO();
        auctionItemDTO.setId(auctionItem.getId());
        auctionItemDTO.setUserId(auctionItem.getUserId());
        auctionItemDTO.setDescription(auctionItem.getDescription());
        auctionItemDTO.setImage(auctionItem.getImage());
        auctionItemDTO.setInitialPrice(auctionItem.getInitialPrice());
        auctionItemDTO.setCurrPrice(auctionItem.getCurrPrice());
        auctionItemDTO.setState(auctionItem.getState());
        auctionItemDTO.setUploadTime(auctionItem.getUploadTime());
        auctionItemDTO.setAuctionTime(auctionItem.getAuctionTime());
        auctionItemDTO.setFavoriteSum(auctionItem.getFavoriteSum());
        return auctionItemDTO;
    }
    public static List<AuctionItem> convertAuctionItemDTOs(List<AuctionItemDTO> AuctionItemDTOS) {
        List<AuctionItem> AuctionItemList = new ArrayList<>();
        for (AuctionItemDTO AuctionItemDTO : AuctionItemDTOS) {
            AuctionItemList.add(convertAuctionItemDTO(AuctionItemDTO));
        }
        return AuctionItemList;
    }
    public static List<AuctionItemDTO> convertAuctionItems(List<AuctionItem> AuctionItemS) {
        List<AuctionItemDTO> AuctionItemDTOList = new ArrayList<>();
        for (AuctionItem AuctionItem : AuctionItemS) {
            AuctionItemDTOList.add(convertAuctionItem(AuctionItem));
        }
        return AuctionItemDTOList;
    }
}
