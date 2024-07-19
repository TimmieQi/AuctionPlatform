package com.example.auctionplatform.service;


import com.example.auctionplatform.dto.OrderDTO;


import java.util.List;

public interface OrderService {
    /**
     * 新增一个order
     */
    Response<Void> addNewOrder(OrderDTO order);

    /**
     * 删除对应id的order
     */
    Response<Void> deleteOrderById(int id);

    /**
     * 获得对应id的order
     */
    Response<OrderDTO> getOrderById(int id);

    /**
     * 获得所有order
     */
    Response<List<OrderDTO>> getAllOrders();

    /**
     * 修改对应id的订单的字段，如果对应字段为空或与原来相同，则不修改,并将信息写入日志中
     */
    Response<Void> updateOrderById(OrderDTO order);

    Response<List<OrderDTO>> getOrdersByUserId(int userId);
}
