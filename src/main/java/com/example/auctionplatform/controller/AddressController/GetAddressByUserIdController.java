package com.example.auctionplatform.controller.AddressController;

import com.example.auctionplatform.dto.AddressDTO;
import com.example.auctionplatform.service.AddressService;
import com.example.auctionplatform.service.JWTService;
import com.example.auctionplatform.service.Response;
import com.example.auctionplatform.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 功能：根据用户id显示该用户的所有地址
 * 作者：万礼阳
 * 日期：2024/7/21 下午3:37
 */
@RestController
@RequestMapping("/api/address")
public class GetAddressByUserIdController {
    @Autowired
    public GetAddressByUserIdController(AddressService addressService, UserService userService) {
        this.addressService = addressService;
        this.userService = userService;
    }
    private final AddressService addressService;
    private final UserService userService;
    @GetMapping("/fromId/userId/{userId}")
    public Response<List<AddressDTO>> getAddressByUserIdControl(@PathVariable("userId") int userId, @RequestHeader(name ="Authorization") String token,
                                                                HttpServletResponse httpServletResponse) {
        try{
            JWTService.parseToken(token,userService.getSecret());
            return addressService.getAddressesByUserId(userId);
        }catch (Exception e) {
            httpServletResponse.setStatus(401);
            return Response.newErrorWithEmptyReturn("获取用户地址失败");
        }

    }
}
