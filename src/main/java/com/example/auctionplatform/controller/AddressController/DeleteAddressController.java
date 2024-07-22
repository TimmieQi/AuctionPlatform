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
 * 功能：通过id删除地址，点删除按钮获取id然后
 * 作者：万礼阳
 * 日期：2024/7/20 下午3:42
 */
@RestController
@RequestMapping("/api/address")
@CrossOrigin
public class DeleteAddressController {
@Autowired

public DeleteAddressController(AddressService addressService, UserService userService) {
    this.addressService = addressService;
    this.userService =userService;
}
private final AddressService addressService;
private final UserService userService;
@DeleteMapping("/delete/id/{id}")
public Response<Void> delete(@PathVariable("id") int id,@RequestHeader(name ="Authorization") String token,
                                            HttpServletResponse httpServletResponse) {
    try{
        JWTService.parseToken(token,userService.getSecret());
        return addressService.deleteAddressById(id);
    }catch (Exception e) {
        httpServletResponse.setStatus(401);
        return Response.newErrorWithEmptyReturn("删除失败");
    }
}
}