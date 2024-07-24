package com.example.auctionplatform.service;

import com.example.auctionplatform.converter.AuctionItemConverter;
import com.example.auctionplatform.dao.AuctionItem;
import com.example.auctionplatform.dao.AuctionItemRepository;

import com.example.auctionplatform.dto.AuctionItemDTO;

import com.example.auctionplatform.dto.OrderDTO;
import com.example.auctionplatform.logger.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;



import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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
    private class dateWithId{
        int id;
        Date uploadTime;
        Date AuctionTime;

        public Date getAuctionTime() {
            return AuctionTime;
        }

        public void setAuctionTime(Date auctionTime) {
            AuctionTime = auctionTime;
        }

        public Date getUploadTime() {
            return uploadTime;
        }

        public void setUploadTime(Date uploadTime) {
            this.uploadTime = uploadTime;
        }

        public dateWithId(int id, Date uploadTime, Date AuctionTime) {
            this.id = id;
            this.uploadTime = uploadTime;
            this.AuctionTime = AuctionTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }



    private final Map<Integer, List<AuctionItemDTO>> auctionItemQueueMap = new ConcurrentHashMap<>();
    private static final List<dateWithId> notStartedAuctions = new ArrayList<>();
    private static final List<dateWithId> notFinishedAuctions = new ArrayList<>();
    public void insert2notStartedAuction(int id, Date uploadTime, Date AuctionTime) {
        notStartedAuctions.add(new dateWithId(id, uploadTime, AuctionTime));
    }
    public void insert2notFinishedAuction(int id, Date uploadTime, Date AuctionTime) {
        notFinishedAuctions.add(new dateWithId(id, uploadTime, AuctionTime));
    }
    @Autowired
    public AuctionItemServiceImpl(AuctionItemRepository auctionItemRepository,OrderService orderService) {
        this.auctionItemRepository = auctionItemRepository;
        this.orderService = orderService;
    }

    private final AuctionItemRepository auctionItemRepository;
    private final OrderService orderService;
    private void changeAuctionItemState(int auctionItemId, short state) {
        Optional<AuctionItem> auctionItem = auctionItemRepository.findById(auctionItemId);
        if (auctionItem.isPresent()) {
            auctionItem.get().setState(state);
            auctionItemRepository.save(auctionItem.get());
        }
    }
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
                message += "AuctionTime set to" + newAuctionItemDTO.getAuctionTime() + "\n";
            }
            if (newAuctionItemDTO.getState() != null) {
                auctionItem.get().setState(newAuctionItemDTO.getState());
                message += "State set to" + newAuctionItemDTO.getState() + "\n";
            }
            if (newAuctionItemDTO.getInitialPrice() > 0.01f) {//最低不能低于1分钱
                auctionItem.get().setInitialPrice(newAuctionItemDTO.getInitialPrice());
                message += "Price set to" + newAuctionItemDTO.getInitialPrice() + "\n";
            }
            auctionItemRepository.save(auctionItem.get());
            return Response.newSuccess(null, message);
        } catch (Exception e) {
            e.fillInStackTrace();
            LogManager.LogOtherError(e.getMessage() + "An error occurred while updating auction item.\n");
            return Response.newError("An error occurred while updating auction item.\n");
        }
    }

    @Override
    public Response<AuctionItemDTO> getAuctionItem(int id) {
        try {
            Optional<AuctionItem> optionalAuctionItem = auctionItemRepository.findById(id);
            return optionalAuctionItem.map(auctionItem ->
                            Response.newSuccess(AuctionItemConverter.convertAuctionItem(auctionItem), "Auction item found.\n"))
                    .orElseGet(() -> Response.newErrorWithEmptyReturn("Auction item does not exist.\n"));
        } catch (Exception e) {
            e.fillInStackTrace();
            LogManager.LogOtherError(e.getMessage() + "An error occurred while getting auction item.\n");
            return Response.newErrorWithEmptyReturn("An error occurred while getting auction item.\n");
        }
    }

    @Override
    public Response<List<AuctionItemDTO>> getAuctionItemsByUserId(int userId) {
        try {
            List<AuctionItem> auctionItems = auctionItemRepository.findByUserId(userId);
            if (auctionItems.isEmpty()) {
                return Response.newErrorWithEmptyReturn("No Auction items found.\n");
            }
            return Response.newSuccess(AuctionItemConverter.convertAuctionItems(auctionItems), "Auction items found.\n");
        } catch (Exception e) {
            e.fillInStackTrace();
            LogManager.LogOtherError(e.getMessage() + "An error occurred while getting auction items.\n");
            return Response.newErrorWithEmptyReturn("An error occurred while getting auction items.\n");
        }
    }

    @Override
    public Response<List<AuctionItemDTO>> getAuctionItemsByUploaderId(int UploadId) {
        try {
            List<AuctionItem> auctionItems = auctionItemRepository.findByUploaderId(UploadId);
            if (auctionItems.isEmpty()) {
                return Response.newErrorWithEmptyReturn("No Auction items found.\n");
            }
            return Response.newSuccess(AuctionItemConverter.convertAuctionItems(auctionItems), "Auction items found.\n");
        } catch (Exception e) {
            e.fillInStackTrace();
            LogManager.LogOtherError(e.getMessage() + "An error occurred while getting auction items.\n");
            return Response.newErrorWithEmptyReturn("An error occurred while getting auction items.\n");
        }
    }

    @Override
    public Response<List<AuctionItemDTO>> getAuctionItemsByName(String name) {
        try {
            List<AuctionItem> auctionItems = auctionItemRepository.findByNameContains(name);
            if (auctionItems.isEmpty()) {
                return Response.newErrorWithEmptyReturn("No Auction items found.\n");
            }
            List<AuctionItemDTO> auctionItemDTOs = AuctionItemConverter.convertAuctionItems(auctionItems);
            return Response.newSuccess(auctionItemDTOs, "Auction items found.\n");
        } catch (Exception e) {
            e.fillInStackTrace();
            LogManager.LogOtherError(e.getMessage() + "An error occurred while getting auction items.\n");
            return Response.newErrorWithEmptyReturn("An error occurred while getting auction items.\n");
        }
    }

    public void addOfferingPrice(AuctionItemDTO auctionItemDTO) {
        auctionItemQueueMap.computeIfAbsent(auctionItemDTO.getId(), k -> new ArrayList<>()).add(auctionItemDTO);
    }

    @Override
    public Response<Void> OfferingPrice(AuctionItemDTO auctionItemDTO) {
        try {
            Optional<AuctionItem> auctionItem = auctionItemRepository.findById(auctionItemDTO.getId());
            if (auctionItem.isEmpty()) {
                return Response.newError("No auction item found\n");
            }
            this.addOfferingPrice(auctionItemDTO);//把请求添加到哈希表里
            return Response.newSuccess(null, "new offering added.\n");
        } catch (Exception e) {
            e.fillInStackTrace();
            LogManager.LogOtherError(e.getMessage() + "An error occurred while Offering price.\n");
            return Response.newError("An error occurred while Offering price.\n");
        }
    }

    @Override
    public Response<Void> addFavourite(int auctionItemId) {
        try{
            Optional<AuctionItem> auctionItem = auctionItemRepository.findById(auctionItemId);
            if (auctionItem.isEmpty()) {
                return Response.newError("No auction item found.\n");
            }
            auctionItem.get().setFavoriteSum(auctionItem.get().getFavoriteSum() + 1);
            auctionItemRepository.save(auctionItem.get());
            return Response.newSuccess(null, "favouriteSum added.\n");
        }catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogOtherError(e.getMessage() + "An error occurred while addFavourite.\n");
            return Response.newError("An error occurred while addFavourite.\n");
        }
    }

    @Override
    public Response<Void> decreaseFavourite(int auctionItemId) {
        try{
            Optional<AuctionItem> auctionItem = auctionItemRepository.findById(auctionItemId);
            if (auctionItem.isEmpty()) {
                return Response.newError("No auction item found.\n");
            }
            if(auctionItem.get().getFavoriteSum() <= 1)
            {
                auctionItem.get().setFavoriteSum(0);
            }
            else {
                auctionItem.get().setFavoriteSum(auctionItem.get().getFavoriteSum() - 1);
            }
            auctionItemRepository.save(auctionItem.get());
            return Response.newSuccess(null, "favouriteSum decreased.\n");
        }catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogOtherError(e.getMessage() + "An error occurred while addFavourite.\n");
            return Response.newError("An error occurred while addFavourite.\n");
        }
    }
//Scheduled(cron = "0 0 0/1 * * ?")
    @Scheduled(fixedRate = 30000)
    public void processAuctionState(){
       // System.out.println("Processing auction state...");
        Date now = new Date();
        for(Iterator<dateWithId> stateIterator = notStartedAuctions.iterator(); stateIterator.hasNext();){
           dateWithId state = stateIterator.next();
            if(state.getUploadTime().before(now)){
                Optional<AuctionItem> auctionItemOptional = auctionItemRepository.findById(state.getId());
                if(auctionItemOptional.isPresent()) {
                    AuctionItem auctionItem = auctionItemOptional.get();
                    auctionItem.setState((short) 1);
                    auctionItemRepository.save(auctionItem);
                    notFinishedAuctions.add(state);
                }
                stateIterator.remove();
            }
        }
        for(Iterator<dateWithId> stateIterator = notFinishedAuctions.iterator(); stateIterator.hasNext();){
            dateWithId state = stateIterator.next();
            if(state.getAuctionTime().before(now)){
                Optional<AuctionItem> auctionItemOptional = auctionItemRepository.findById(state.getId());
                if(auctionItemOptional.isPresent()) {
                    AuctionItem auctionItem = auctionItemOptional.get();
                    auctionItem.setState((short) -1);
                    auctionItemRepository.save(auctionItem);
                    OrderDTO newOrder = new OrderDTO();
                    if(auctionItem.getUserId() != -1)
                    {
                        newOrder.setSaleId(auctionItem.getUploaderId());
                        newOrder.setBuyId(auctionItem.getUserId());
                        newOrder.setItemId(auctionItem.getId());
                        newOrder.setPrice(auctionItem.getCurrPrice());
                        newOrder.setSaleAdd("Not choose address yet");
                        newOrder.setBuyAdd("Not choose address yet");
                        newOrder.setMessage("The merchant has not yet imported the logistics information");
                        newOrder.setReceived(false);
                        newOrder.setPayed(false);
                        orderService.addNewOrder(newOrder);
                    }
                }
                stateIterator.remove();
            }
        }
    }

    @Scheduled(fixedRate = 5000) // 每隔5秒处理一次
    public void processAuctionItemQueue() {
       // System.out.println("Processing auction item queue...");
        auctionItemQueueMap.forEach((itemId, auctionItemQueue) -> {
            if (!auctionItemQueue.isEmpty()) {
                Optional<AuctionItem> optionalAuctionItem = auctionItemRepository.findById(itemId);
                if (optionalAuctionItem.isPresent()) {
                    AuctionItem auctionItem = optionalAuctionItem.get();
                    // 找到最高竞价
                    AuctionItemDTO highestBid = auctionItemQueue.stream()
                            .max(Comparator.comparingDouble(AuctionItemDTO::getCurrPrice))
                            .orElse(null);

                    if (highestBid != null && highestBid.getCurrPrice() > auctionItem.getCurrPrice()) {
                        auctionItem.setCurrPrice(highestBid.getCurrPrice());
                        auctionItem.setUserId(highestBid.getUserId());
                        auctionItemRepository.save(auctionItem);
                    }
                }
                // 清空队列
                auctionItemQueue.clear();
            }
        });
    }

    @Override
    public Response<List<AuctionItemDTO>> getAllAuctionItems() {
        try {
            List<AuctionItem> auctionItems = auctionItemRepository.findAll();
            if (auctionItems.isEmpty()) {
                return Response.newErrorWithEmptyReturn("No Auction items found.\n");
            }
            return Response.newSuccess(AuctionItemConverter.convertAuctionItems(auctionItems), "Auction items found.\n");
        } catch (Exception e) {
            e.fillInStackTrace();
            LogManager.LogOtherError(e.getMessage() + "An error occurred while getting auction items.\n");
            return Response.newErrorWithEmptyReturn("An error occurred while getting auction items.\n");
        }
    }

    @Override
    public Response<Void> addAuctionItem(AuctionItemDTO newAuctionItemDTO) {
        try {
            AuctionItem auctionItem = new AuctionItem();
            String description = newAuctionItemDTO.getDescription();
            if(!description.isEmpty())
            {
                auctionItem.setDescription(description);
            }

            double initialPrice = newAuctionItemDTO.getInitialPrice();
            if(initialPrice <= 0.01f){
                return Response.newErrorWithEmptyReturn("Initial price cannot be less than 0.01\n");
            }else {
                auctionItem.setInitialPrice(initialPrice);
            }
            Object image = newAuctionItemDTO.getImage();
            if(image!=null){
                auctionItem.setImage(image.toString());
            }
            auctionItem.setState((short) 0);
            auctionItem.setUploadTime(newAuctionItemDTO.getUploadTime());
            auctionItem.setAuctionTime(newAuctionItemDTO.getAuctionTime());
            auctionItem.setUploaderId(newAuctionItemDTO.getUploaderId());
            auctionItem.setName(newAuctionItemDTO.getName());

            AuctionItem newAuction = auctionItemRepository.save(auctionItem);
            notStartedAuctions.add(new dateWithId(newAuction.getId(),newAuction.getUploadTime(),newAuction.getAuctionTime()));
            return Response.newSuccess(null, "Auction item added.\n");
        } catch (Exception e) {
            e.fillInStackTrace();
            LogManager.LogOtherError(e.getMessage() + "An error occurred while adding item.\n");
            return Response.newErrorWithEmptyReturn("An error occurred while adding item.\n");
        }
    }

    @Override
    public Response<Void> deleteAuctionItemById(int id) {
        try {
            Optional<AuctionItem> optionalAuctionItem = auctionItemRepository.findById(id);
            if (optionalAuctionItem.isPresent()) {
                auctionItemRepository.delete(optionalAuctionItem.get());
                return Response.newSuccess(null, "Auction item deleted.\n");
            }
            return Response.newError("Auction item does not exist.\n");
        } catch (Exception e) {
            e.fillInStackTrace();
            LogManager.LogOtherError(e.getMessage() + "An error occurred while deleting item.\n");
            return Response.newError("An error occurred while deleting item.\n");
        }
    }
}