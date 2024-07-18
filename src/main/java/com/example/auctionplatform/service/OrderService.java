package com.example.auctionplatform.service;

import com.example.auctionplatform.dto.OrderDTO;

public interface OrderService {
    public String addNewOrder(OrderDTO order);
    public String deleteOrderById(int id);
    public String getOrderById(int id);
    public String getAllOrders();
}
