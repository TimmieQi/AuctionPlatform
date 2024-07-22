package com.example.auctionplatform.controller.coreController;

import com.example.auctionplatform.dto.OrderDTO;

import com.example.auctionplatform.service.JWTService;
import com.example.auctionplatform.service.OrderService;
import com.example.auctionplatform.service.Response;
import com.example.auctionplatform.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 查看用户所有拍下的商品
 */
@RestController
@RequestMapping("/api")
public class GetAllOrderByTokenController {
    @Autowired
    public GetAllOrderByTokenController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }
    private final OrderService orderService;
    private final UserService userService;
    @GetMapping("/Get/AllOrders")
    public Response<List<OrderDTO>> getAllOrderByToken(@RequestHeader(name ="Authorization") String token,
                                                       HttpServletResponse httpServletResponse) {
        try{
            Map<String,Object> map = JWTService.parseToken(token,userService.getSecret());
            return orderService.getOrdersByUserId((int)map.get("userId"));
        }catch (Exception e) {
            httpServletResponse.setStatus(401);
            return Response.newErrorWithEmptyReturn("Not Logged In");
        }

    }
}
