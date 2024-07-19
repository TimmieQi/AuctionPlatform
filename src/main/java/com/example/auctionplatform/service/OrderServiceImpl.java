package com.example.auctionplatform.service;

import com.example.auctionplatform.converter.OrderConverter;
import com.example.auctionplatform.dao.Order;
import com.example.auctionplatform.dao.OrderRepository;
import com.example.auctionplatform.dto.OrderDTO;
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
        List<Order> tempOrder = orderRepository.findByOrderByBuyId(order.getId());
        if (!tempOrder.isEmpty()) {
            for (Order o : tempOrder) {
                if(o.getItemId() == order.getItemId()) {
                    return "Order already exists";
                }
            }
        }
        Order newOrder = OrderConverter.convertOrderDTO(order);
        orderRepository.save(newOrder);
        return "Order "+newOrder.getId()+" successfully added";
    }

    @Override
    public String deleteOrderById(int id) {
        Optional<Order> tempOrder = orderRepository.findById(id);
        if (tempOrder.isPresent()) {
            orderRepository.delete(tempOrder.get());
            return "Order "+tempOrder.get().getId()+" successfully deleted";
        }
        return "Order "+id+" not found";
    }

    @Override
    public Order getOrderById(int id) {
        Optional<Order> tempOrder = orderRepository.findById(id);
        return tempOrder.orElse(null);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
