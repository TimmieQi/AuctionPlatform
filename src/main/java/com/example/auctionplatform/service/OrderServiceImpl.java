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
            Order newOrder = new Order();
            if (order.getSaleId() != null) {
                newOrder.setSaleId(order.getSaleId());
            }
            else{
                return Response.newError("SaleId cannot be empty\n");
            }
            if (order.getBuyId() != null) {
                newOrder.setBuyId(order.getBuyId());
            }
            else {
                return Response.newError("BuyId cannot be empty\n");
            }
            if(order.getItemId() != null) {
                newOrder.setItemId(order.getItemId());
            }else{
                return Response.newError("ItemId cannot be empty\n");
            }
            if(order.getPrice() != null) {
                newOrder.setPrice(order.getPrice());
            }else{
                return Response.newError("Price cannot be empty\n");
            }
            if(order.getSaleAdd() != null){
                newOrder.setSaleAdd(order.getSaleAdd());
            }else{
                return Response.newError("SaleAdd cannot be empty\n");
            }
            if(order.getBuyAdd() != null){
                newOrder.setBuyAdd(order.getBuyAdd());
            }else{
                return Response.newError("BuyAdd cannot be empty\n");
            }
            if(order.getMessage() != null){
                newOrder.setMessage(order.getMessage());
            }else{
                return Response.newError("Message cannot be empty\n");
            }
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

    @Override
    public Response<Void> changeOrderPayStateById(int id, boolean state) {
        try {
            Optional<Order> optionalOrder = orderRepository.findById(id);
            if (optionalOrder.isEmpty()) {
                return Response.newError("Order not found\n");
            }
            Order order = optionalOrder.get();
            order.setPayed(state);
            orderRepository.save(order);
            return  Response.newSuccess(null, "Order updated successfully isPayed now :"+state);
        }catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogOrder(LogLevel.ERROR, e.getMessage());
            return Response.newErrorWithEmptyReturn("An error occurred while changing an order's state\n");
        }
    }

    @Override
    public Response<Void> changeOrderReceivedStateById(int id, boolean state) {
        try {
            Optional<Order> optionalOrder = orderRepository.findById(id);
            if (optionalOrder.isEmpty()) {
                return Response.newError("Order not found\n");
            }
            Order order = optionalOrder.get();
            order.setReceived(state);
            orderRepository.save(order);
            return  Response.newSuccess(null, "Order updated successfully isReceived now :"+state);
        }catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogOrder(LogLevel.ERROR, e.getMessage());
            return Response.newErrorWithEmptyReturn("An error occurred while changing an order's state\n");
        }
    }
}
