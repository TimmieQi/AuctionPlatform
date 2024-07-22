package com.example.auctionplatform.controller.AddressController;

import com.example.auctionplatform.dto.AddressDTO;
import com.example.auctionplatform.service.AddressService;
import com.example.auctionplatform.service.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public GetAddressByUserIdController(AddressService addressService) {
        this.addressService = addressService;
    }
    private final AddressService addressService;
    @GetMapping("/fromId/userId/{userId}")
    public Response<List<AddressDTO>> getAddressByUserIdControl(@PathVariable("userId") int userId) {
        return addressService.getAddressesByUserId(userId);
    }
}
