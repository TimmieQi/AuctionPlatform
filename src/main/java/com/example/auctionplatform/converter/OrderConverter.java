package com.example.auctionplatform.converter;

import com.example.auctionplatform.dao.Order;
import com.example.auctionplatform.dto.OrderDTO;

public class OrderConverter {
    public static Order convertOrderDTO(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setSaleId(orderDTO.getSaleId());
        order.setBuyId(orderDTO.getBuyId());
        order.setItemId(orderDTO.getItemId());
        order.setPrice(orderDTO.getPrice());
        order.setSaleAdd(orderDTO.getSaleAdd());
        order.setBuyAdd(orderDTO.getBuyAdd());
        order.setReceived(orderDTO.isReceived());
        order.setMessage(orderDTO.getMessage());
        return order;
    }
    public static OrderDTO convertOrder(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setSaleId(order.getSaleId());
        orderDTO.setBuyId(order.getBuyId());
        orderDTO.setItemId(order.getItemId());
        orderDTO.setPrice(order.getPrice());
        orderDTO.setSaleAdd(order.getSaleAdd());
        orderDTO.setBuyAdd(order.getBuyAdd());
        orderDTO.setReceived(order.isReceived());
        orderDTO.setMessage(order.getMessage());
        return orderDTO;
    }
}