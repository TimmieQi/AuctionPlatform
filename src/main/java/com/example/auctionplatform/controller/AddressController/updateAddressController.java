package com.example.auctionplatform.controller.AddressController;

import com.example.auctionplatform.dto.AddressDTO;
import com.example.auctionplatform.service.AddressService;
import com.example.auctionplatform.service.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：更新地址信息
 * 作者：万礼阳
 * 日期：2024/7/20 下午4:09
 */
@RestController
@RequestMapping("/api/address")
public class updateAddressController {

    @Autowired
    public updateAddressController(AddressService addressService) {
        this.addressService = addressService;
    }
    private final AddressService addressService;

    @PostMapping("/update/address")
    public Response<Void> updateAddressById(@RequestBody AddressDTO address) {
        if(address.getUserId()!=0 && address.getAddressId()!=0){
        return addressService.updateAddressById(address);
        }
        return null;
    }
}