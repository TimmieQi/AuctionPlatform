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
@RequestMapping("/api")
public class AddAddressController {
    @Autowired
    public AddAddressController(AddressService addressService) {
        this.addressService = addressService;
    }
    private final AddressService addressService;
    @PostMapping("/address/add")
    public Response<Void> addAddress(@RequestBody AddressDTO addressDTO){
        return addressService.addNewAddress(addressDTO);
    }
}