package com.example.auctionplatform.service;

import com.example.auctionplatform.converter.OrderConverter;
import com.example.auctionplatform.dao.Order;
import com.example.auctionplatform.dao.OrderRepository;
import com.example.auctionplatform.dto.OrderDTO;
import com.example.auctionplatform.logger.LogLevel;
import com.example.auctionplatform.logger.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    private final OrderRepository orderRepository;
    @Override
    public String addNewOrder(OrderDTO order) {
        List<Order> tempOrder = orderRepository.findByBuyId(order.getId());
        if (!tempOrder.isEmpty()) {
            for (Order o : tempOrder) {
                if(o.getItemId() == order.getItemId()) {
                    return "Order already exists\n";
                }
            }
        }
        Order newOrder = OrderConverter.convertOrderDTO(order);
        orderRepository.save(newOrder);
        String message = "Order added successfully\n";
        LogManager.LogOrder(LogLevel.INFO,message);
        return "Order "+newOrder.getId()+" successfully added\n";
    }

    @Override
    public String deleteOrderById(int id) {
        Optional<Order> tempOrder = orderRepository.findById(id);
        if (tempOrder.isEmpty()) {
            return "Order "+id+" not found\n";
        }
        orderRepository.delete(tempOrder.get());
        String message = "Order "+tempOrder.get().getId()+" successfully deleted\n";
        LogManager.LogOrder(LogLevel.INFO,message);
        return message;
    }

    @Override
    public OrderDTO getOrderById(int id) {
        Optional<Order> tempOrder = orderRepository.findById(id);
        return tempOrder.map(OrderConverter::convertOrder).orElse(null);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return OrderConverter.convertOrders(orderRepository.findAll());
    }

    @Override
    public String updateOrderById(OrderDTO order) {
        Optional<Order> tempOrder = orderRepository.findById(order.getId());
        if (tempOrder.isEmpty()) {
            return "Order not found\n";
        }
        Order newOrder = tempOrder.get();
        String message ="";
        if(!order.getSaleAdd().isEmpty()) {
            if(!order.getSaleAdd().equals(newOrder.getSaleAdd())) {
                newOrder.setSaleAdd(order.getSaleAdd());
            }
            message += "No change in seller's address\n";
        }
        if(!order.getBuyAdd().isEmpty()) {
            if(!order.getBuyAdd().equals(newOrder.getBuyAdd())) {
                newOrder.setBuyAdd(order.getBuyAdd());
            }
            message += "No change in buyer's address\n";
        }
        if(!order.getMessage().isEmpty()){
            newOrder.setMessage(order.getMessage());
            message += "Successfully changed the logistics information message\n";
        }
        orderRepository.save(newOrder);
        return message;
    }

    @Override
    public List<OrderDTO> getOrdersByUserId(int userId) {
        List<Order> orders = orderRepository.findByBuyId(userId);
        return OrderConverter.convertOrders(orders);
    }
}
