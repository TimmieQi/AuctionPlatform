package com.example.auctionplatform.service;


import com.example.auctionplatform.dto.OrderDTO;


import java.util.List;

public interface OrderService {
    /**
     * 新增一个order
     */
    String addNewOrder(OrderDTO order);

    /**
     * 删除对应id的order
     */
    String deleteOrderById(int id);

    /**
     * 获得对应id的order
     */
    OrderDTO getOrderById(int id);

    /**
     * 获得所有order
     */
    List<OrderDTO> getAllOrders();

    String updateOrderById(OrderDTO order);

    List<OrderDTO> getOrdersByUserId(int userId);
}
