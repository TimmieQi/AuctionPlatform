package com.example.auctionplatform.service;

import com.example.auctionplatform.converter.AuctionItemConverter;
import com.example.auctionplatform.dao.AuctionItem;
import com.example.auctionplatform.dao.AuctionItemRepository;
import com.example.auctionplatform.dto.AddressDTO;
import com.example.auctionplatform.dto.AuctionItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

/**
 * 功能：
 * 作者：万礼阳
 * 日期：2024/7/18 下午11:51
 */
@Service
public class AuctionItemServiceImpl {

    @Autowired
    public AuctionItemServiceImpl(AuctionItemRepository auctionItemRepository) {
        this.auctionItemRepository = auctionItemRepository;
    }
    private final AuctionItemRepository auctionItemRepository;
    public String updateAuctionItem(AuctionItemDTO newAuctionItemDTO) {
        Optional<AuctionItem> auctionItem = auctionItemRepository.findById(newAuctionItemDTO.getId());
        String message="";
        if (auctionItem.isPresent()) {
            if(newAuctionItemDTO.getDescription()!=null) {
                message += "Address set to"+newAuctionItemDTO.getDescription()+"\"\n";
            }
            if(newAuctionItemDTO.getImage()!=null){
                message += "Image set to"+newAuctionItemDTO.getImage()+"\"\n";
            }
            if (newAuctionItemDTO.getAuctionTime()!=null) {
                message += "Image set to" + newAuctionItemDTO.getAuctionTime() + "\"\n";
            }
            if(newAuctionItemDTO.getState()!= AddressDTO.AUCTION_ITEM_STATE_NOT_CHANGE){
                message += "Image set to" + newAuctionItemDTO.getState() + "\"\n";
            }
            if (newAuctionItemDTO.getInitialPrice()!=0){
                message += "Price set to"+newAuctionItemDTO.getInitialPrice()+"\"\n";
            }
        }
        return message;
    }
    public AuctionItem getAuctionItem(int id){
        Optional<AuctionItem> optionalAuctionItem=auctionItemRepository.findById(id);//为啥要变成long啊
        return optionalAuctionItem.orElse(null);
    }
    public List<AuctionItemDTO> getAllAuctionItems(){
        return  AuctionItemConverter.convertAuctionItems(auctionItemRepository.findAll());
    }
    public String  addAuctionItem(AuctionItemDTO newAuctionItemDTO){
        AuctionItem AuctionItem = AuctionItemConverter.convertAuctionItemDTO(newAuctionItemDTO);
        auctionItemRepository.save(AuctionItem);
        return "Successfully added new address!";
    }
    public String deleteAuctionItemById(int id){
        Optional<AuctionItem> optionalAuctionItem=auctionItemRepository.findById(id);//为啥要变成long啊
        if(optionalAuctionItem.isPresent()) {
            auctionItemRepository.delete(optionalAuctionItem.get());
            return "Successfully deleted address!";
        }
        return "No such address";
    }
}