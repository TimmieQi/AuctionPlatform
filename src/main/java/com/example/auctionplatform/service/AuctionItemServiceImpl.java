package com.example.auctionplatform.service;

import com.example.auctionplatform.converter.AuctionItemConverter;
import com.example.auctionplatform.dao.AuctionItem;
import com.example.auctionplatform.dao.AuctionItemRepository;
import com.example.auctionplatform.dto.AddressDTO;
import com.example.auctionplatform.dto.AuctionItemDTO;

import com.example.auctionplatform.logger.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
/*
 已重构
 YCX
 */

/**
 * 功能：
 * 作者：万礼阳
 * 日期：2024/7/18 下午11:51
 */
@Service
public class AuctionItemServiceImpl implements AuctionItemService {

    @Autowired
    public AuctionItemServiceImpl(AuctionItemRepository auctionItemRepository) {
        this.auctionItemRepository = auctionItemRepository;
    }
    private final AuctionItemRepository auctionItemRepository;
    @Override
    public Response<Void> updateAuctionItem(AuctionItemDTO newAuctionItemDTO) {
        try {
            Optional<AuctionItem> auctionItem = auctionItemRepository.findById(newAuctionItemDTO.getId());
            if (auctionItem.isEmpty()) {
                return Response.newError("Auction item does not exist.\n");
            }
            String message = "";
            if (newAuctionItemDTO.getDescription() != null) {
                auctionItem.get().setDescription(newAuctionItemDTO.getDescription());
                message += "Address set to" + newAuctionItemDTO.getDescription() + "\n";
            }
            if (newAuctionItemDTO.getImage() != null) {
                auctionItem.get().setImage(newAuctionItemDTO.getImage());
                message += "Image set to" + newAuctionItemDTO.getImage() + "\n";
            }
            if (newAuctionItemDTO.getAuctionTime() != null) {
                auctionItem.get().setAuctionTime(newAuctionItemDTO.getAuctionTime());
                message += "Image set to" + newAuctionItemDTO.getAuctionTime() + "\n";
            }
            if (newAuctionItemDTO.getState() != AddressDTO.AUCTION_ITEM_STATE_NOT_CHANGE) {
                auctionItem.get().setState(newAuctionItemDTO.getState());
                message += "Image set to" + newAuctionItemDTO.getState() + "\n";
            }
            if (newAuctionItemDTO.getInitialPrice() > 0.01f) {//最低不能低于1分钱
                auctionItem.get().setInitialPrice(newAuctionItemDTO.getInitialPrice());
                message += "Price set to" + newAuctionItemDTO.getInitialPrice() + "\n";
            }
            auctionItemRepository.save(auctionItem.get());
            return Response.newSuccess(null,message);
        }
        catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogOtherError(e.getMessage() + "An error occurred while updating auction item.\n");
            return Response.newError("An error occurred while updating auction item.\n");
        }
    }
    @Override
    public Response<AuctionItemDTO> getAuctionItem(int id){
        try {
            Optional<AuctionItem> optionalAuctionItem = auctionItemRepository.findById(id);
            return optionalAuctionItem.map(auctionItem ->
                    Response.newSuccess(AuctionItemConverter.convertAuctionItem(auctionItem), "Auction item found.\n"))
                    .orElseGet(() -> Response.newErrorWithEmptyReturn("Auction item does not exist.\n"));
        }catch (Exception e)
        {
            e.fillInStackTrace();
            LogManager.LogOtherError(e.getMessage() + "An error occurred while getting auction item.\n");
            return Response.newErrorWithEmptyReturn("An error occurred while getting auction item.\n");
        }
    }
    @Override
    public Response<List<AuctionItemDTO>> getAuctionItemsByUserId(int userId) {
        try{
            List<AuctionItem> auctionItems = auctionItemRepository.findByUserId(userId);
            if(auctionItems.isEmpty()){
                return Response.newErrorWithEmptyReturn("No Auction items found.\n");
            }
            return Response.newSuccess(AuctionItemConverter.convertAuctionItems(auctionItems),"Auction items found.\n");
        }catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogOtherError(e.getMessage()+"An error occurred while getting auction items.\n");
            return Response.newErrorWithEmptyReturn("An error occurred while getting auction items.\n");
        }
    }
    @Override
    public Response<List<AuctionItemDTO>> getAllAuctionItems(){
        try{
            List<AuctionItem> auctionItems = auctionItemRepository.findAll();
            if(auctionItems.isEmpty()){
                return Response.newErrorWithEmptyReturn("No Auction items found.\n");
            }
            return Response.newSuccess(AuctionItemConverter.convertAuctionItems(auctionItems),"Auction items found.\n");
        }catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogOtherError(e.getMessage()+"An error occurred while getting auction items.\n");
            return Response.newErrorWithEmptyReturn("An error occurred while getting auction items.\n");
        }
    }
    @Override
    public Response<Void> addAuctionItem(AuctionItemDTO newAuctionItemDTO){
        try{
            AuctionItem AuctionItem = AuctionItemConverter.convertAuctionItemDTO(newAuctionItemDTO);
            auctionItemRepository.save(AuctionItem);
            return Response.newSuccess(null,"Auction item added.\n");
        }catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogOtherError(e.getMessage()+"An error occurred while adding item.\n");
            return Response.newErrorWithEmptyReturn("An error occurred while adding item.\n");
        }
    }
    @Override
    public Response<Void> deleteAuctionItemById(int id){
        try{
            Optional<AuctionItem> optionalAuctionItem=auctionItemRepository.findById(id);
            if(optionalAuctionItem.isPresent()) {
                auctionItemRepository.delete(optionalAuctionItem.get());
                return Response.newSuccess(null,"Auction item deleted.\n");
            }
            return Response.newError("Auction item does not exist.\n");
        }catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogOtherError(e.getMessage()+"An error occurred while deleting item.\n");
            return Response.newError("An error occurred while deleting item.\n");
        }
    }
}