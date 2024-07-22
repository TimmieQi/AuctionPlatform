package com.example.auctionplatform.controller.AddressController;

import com.example.auctionplatform.dao.Address;
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
 * 功能：根据地址编号查询地址内容
 * 作者：万礼阳
 * 日期：2024/7/21 下午3:20
 */
@RestController
@RequestMapping("/api/address")
public class getAddressByIdController {
    @Autowired
    public getAddressByIdController(AddressService addressService) {
        this.addressService = addressService;
    }
    private final AddressService addressService;
    @GetMapping("/fromId")
    public Response<Address> getAllAddressControl(@PathVariable int id) {
        return addressService.getAddress(id);
    }
}
