package com.example.auctionplatform.controller.AddressController;

import com.example.auctionplatform.dto.AddressDTO;
import com.example.auctionplatform.service.AddressService;
import com.example.auctionplatform.service.JWTService;
import com.example.auctionplatform.service.Response;
import com.example.auctionplatform.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：更新地址信息
 * 作者：万礼阳
 * 日期：2024/7/20 下午4:09
 */
@RestController
@RequestMapping("/api/address")
public class UpdateAddressController {

    @Autowired
    public UpdateAddressController(AddressService addressService, UserService userService) {
        this.addressService = addressService;
        this.userService = userService;
    }
    private final AddressService addressService;
    private final UserService userService;

    @PostMapping("/update/address")
    public Response<Void> updateAddressById(@RequestBody AddressDTO address, @RequestHeader(name ="Authorization") String token,
                                            HttpServletResponse httpServletResponse) {
        try{
            JWTService.parseToken(token,userService.getSecret());
            if(address.getUserId()!=0 && address.getAddressId()!=0){
                return addressService.updateAddressById(address);
            }
            return null;
        }catch (Exception e) {
            httpServletResponse.setStatus(401);
            return Response.newErrorWithEmptyReturn("更新地址失败");
        }

    }
}