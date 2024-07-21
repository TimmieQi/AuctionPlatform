package com.example.auctionplatform.controller.AddressController;

import com.example.auctionplatform.service.AddressService;
import com.example.auctionplatform.service.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：通过id删除地址，点删除按钮获取id然后
 * 作者：万礼阳
 * 日期：2024/7/20 下午3:42
 */
@RestController
@RequestMapping("/api/address")
public class deleteAddressController {
@Autowired
public deleteAddressController(AddressService addressService) {
    this.addressService = addressService;
}
    private final AddressService addressService;
@DeleteMapping("/delete")
    public Response<Void> delete(@PathVariable("id") int id) {
    return addressService.deleteAddressById(id);
}
}