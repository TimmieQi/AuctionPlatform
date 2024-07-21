package com.example.auctionplatform.controller.AddressController;

import com.example.auctionplatform.dto.AddressDTO;
import com.example.auctionplatform.service.AddressService;
import com.example.auctionplatform.service.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：增加地址
 * 作者：万礼阳
 * 日期：2024/7/21 下午3:17
 */
@RestController
@RequestMapping("api/address")
public class addAddressController {
    @Autowired
    public addAddressController(AddressService addressService) {
        this.addressService = addressService;
    }
    private final AddressService addressService;
    @DeleteMapping("/delete")
    public Response<Void> delete(@RequestBody AddressDTO addressDTO){
        return addressService.addNewAddress(addressDTO);
    }
}