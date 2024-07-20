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
    public Response<Void> addNewOrder(OrderDTO order) {
        try {
            List<Order> tempOrder = orderRepository.findByBuyId(order.getId());
            if (!tempOrder.isEmpty()) {
                for (Order o : tempOrder) {
                    if (o.getItemId() == order.getItemId()) {
                        return Response.newError("Order already exists\n");
                    }
                }
            }
            Order newOrder = OrderConverter.convertOrderDTO(order);
            orderRepository.save(newOrder);
            String message = "Order " + newOrder.getId() + " successfully added\n";
            LogManager.LogOrder(LogLevel.INFO, message);
            return Response.newSuccess(null,message);
        }
        catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogOrder(LogLevel.ERROR, e.getMessage() + "An error occurred while adding an order\n");
            return Response.newError("An error occurred while adding an order\n");
        }
    }

    @Override
    public Response<Void> deleteOrderById(int id) {
        try {
            Optional<Order> tempOrder = orderRepository.findById(id);
            if (tempOrder.isEmpty()) {
                return Response.newError("Order not found\n");
            }
            orderRepository.delete(tempOrder.get());
            String message = "Order " + tempOrder.get().getId() + " successfully deleted\n";
            LogManager.LogOrder(LogLevel.INFO, message);
            return Response.newSuccess(null, message);
        }catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogOrder(LogLevel.ERROR, e.getMessage());
            return Response.newError("An error occurred while deleting an order\n");
        }
    }

    @Override
    public Response<OrderDTO> getOrderById(int id) {
        try {
            Optional<Order> tempOrder = orderRepository.findById(id);
            return tempOrder.map(order -> Response.newSuccess(OrderConverter.convertOrder(order), "Order found\n"))
                    .orElseGet(() -> Response.newErrorWithEmptyReturn("Order with id " + id + " not found\n"));
        }catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogOrder(LogLevel.ERROR, e.getMessage());
            return Response.newErrorWithEmptyReturn("An error occurred while getting an order\n");
        }
    }

    @Override
    public Response<List<OrderDTO>> getAllOrders() {
        try {
            List<Order> tempOrder = orderRepository.findAll();
            if(tempOrder.isEmpty()){
                return Response.newErrorWithEmptyReturn("No orders found\n");
            }
            return Response.newSuccess(OrderConverter.convertOrders(tempOrder), "Orders found\n");
        }
        catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogOrder(LogLevel.ERROR, e.getMessage()+"An error occurred while getting an orders\n");
            return Response.newErrorWithEmptyReturn("An error occurred while getting an order\n");
        }
    }

    @Override
    public Response<Void> updateOrderById(OrderDTO order) {
        try {
            Optional<Order> tempOrder = orderRepository.findById(order.getId());
            if (tempOrder.isEmpty()) {
                return Response.newError("Order not found\n");
            }
            Order newOrder = tempOrder.get();
            String message = "";
            if (!order.getSaleAdd().isEmpty()) {
                if (!order.getSaleAdd().equals(newOrder.getSaleAdd())) {
                    newOrder.setSaleAdd(order.getSaleAdd());
                    message += "Order " + newOrder.getId() + " successfully updated\n";
                }
                else {
                    message += "No change in seller's address\n";
                }
            }
            if (!order.getBuyAdd().isEmpty()) {
                if (!order.getBuyAdd().equals(newOrder.getBuyAdd())) {
                    newOrder.setBuyAdd(order.getBuyAdd());
                    message += "Order " + newOrder.getId() + " successfully updated\n";
                }
                else {
                    message += "No change in buyer's address\n";
                }
            }
            if (!order.getMessage().isEmpty()) {
                newOrder.setMessage(order.getMessage());
                message += "Successfully changed the logistics information message\n";
            }
            orderRepository.save(newOrder);
            LogManager.LogOrder(LogLevel.INFO, message);
            return Response.newSuccess(null, message);
        }
        catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogOrder(LogLevel.ERROR, e.getMessage());
            return Response.newError("An error occurred while updating an order\n");
        }

    }

    @Override
    public Response<List<OrderDTO>> getOrdersByUserId(int userId) {
        try {
            List<Order> orders = orderRepository.findByBuyId(userId);
            if(orders.isEmpty()){
                return Response.newErrorWithEmptyReturn("No orders found\n");
            }
            return Response.newSuccess(OrderConverter.convertOrders(orders), "Orders found\n");
        }catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogOrder(LogLevel.ERROR, e.getMessage());
            return Response.newErrorWithEmptyReturn("An error occurred while getting orders\n");
        }
    }
}
