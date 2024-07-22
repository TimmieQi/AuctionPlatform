package com.example.auctionplatform.controller.AddressController;

import com.example.auctionplatform.dto.AddressDTO;
import com.example.auctionplatform.dto.AuctionItemDTO;
import com.example.auctionplatform.service.AddressService;
import com.example.auctionplatform.service.JWTService;
import com.example.auctionplatform.service.Response;
import com.example.auctionplatform.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 功能：增加地址
 * 作者：万礼阳
 * 日期：2024/7/21 下午3:17
 */
@RestController
@RequestMapping("/api")
public class AddAddressController {
    @Autowired
    public AddAddressController(AddressService addressService, UserService userService) {
        this.addressService = addressService;
        this.userService =userService;
    }
    private final AddressService addressService;
    private final UserService userService;
    @PostMapping("/address/add")
    public Response<Void> getDefaultAuctionItem(@RequestHeader(name ="Authorization") String token,AddressDTO addressDTO,
                                                                HttpServletResponse httpServletResponse) {
        try{
            JWTService.parseToken(token,userService.getSecret());
            return addressService.addNewAddress(addressDTO);
        }catch (Exception e) {
            httpServletResponse.setStatus(401);
            return Response.newErrorWithEmptyReturn("添加失败");
        }
    }
}