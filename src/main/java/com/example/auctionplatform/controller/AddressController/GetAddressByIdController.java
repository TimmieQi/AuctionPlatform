package com.example.auctionplatform.controller.AddressController;

import com.example.auctionplatform.dao.Address;
import com.example.auctionplatform.dao.User;
import com.example.auctionplatform.dto.AddressDTO;
import com.example.auctionplatform.service.AddressService;
import com.example.auctionplatform.service.JWTService;
import com.example.auctionplatform.service.Response;
import com.example.auctionplatform.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：根据地址编号查询地址内容
 * 作者：万礼阳
 * 日期：2024/7/21 下午3:20
 */
@RestController
@RequestMapping("/api/address")
@CrossOrigin
public class GetAddressByIdController {
    @Autowired
    public GetAddressByIdController(AddressService addressService,UserService userService) {
        this.addressService = addressService;
        this.userService = userService;
    }
    private final UserService userService;
    private final AddressService addressService;
    @GetMapping("/fromId/Id/{id}")
    public Response<AddressDTO> getAllAddressControl(@PathVariable int id, @RequestHeader(name ="Authorization") String token,
                                                     HttpServletResponse httpServletResponse) {
        try{
            JWTService.parseToken(token,userService.getSecret());
            return addressService.getAddress(id);
        }catch (Exception e) {
            httpServletResponse.setStatus(401);
            return Response.newErrorWithEmptyReturn("获取地址信息失败");
        }

    }
}
