package com.example.auctionplatform.controller.AddressController;

import com.example.auctionplatform.dto.AddressDTO;
import com.example.auctionplatform.logger.LogLevel;
import com.example.auctionplatform.logger.LogManager;
import com.example.auctionplatform.service.AddressService;
import com.example.auctionplatform.service.JWTService;
import com.example.auctionplatform.service.Response;
import com.example.auctionplatform.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：增加地址
 * 作者：万礼阳
 * 日期：2024/7/21 下午3:17
 */

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AddAddressController {

    private final AddressService addressService;
    private final UserService userService;

    @Autowired
    public AddAddressController(AddressService addressService, UserService userService) {
        this.addressService = addressService;
        this.userService = userService;
    }

    @PostMapping("/address/add")
    public Response<Void> addNewAddress(
            @RequestHeader(name = "Authorization") String token,
            @RequestBody AddressDTO addressDTO,
            HttpServletResponse httpServletResponse) {
        LogManager.LogUser(LogLevel.INFO, "Received request to add new address: " + addressDTO.toString());

        try {
            JWTService.parseToken(token, userService.getSecret());
            LogManager.LogUser(LogLevel.INFO, "Token parsed successfully for userId: " + addressDTO.getUserId());
            Response<Void> response = addressService.addNewAddress(addressDTO);
            LogManager.LogUser(LogLevel.INFO, "Address added successfully for userId: " + addressDTO.getUserId());
            return response;
        } catch (Exception e) {
            LogManager.LogOtherError("Failed to add address: " + e.getMessage());
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return Response.newErrorWithEmptyReturn("添加失败");
        }
    }
}
