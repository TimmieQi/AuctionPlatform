package com.example.auctionplatform.controller.coreController;

import com.example.auctionplatform.dto.UserDTO;
import com.example.auctionplatform.service.JWTService;
import com.example.auctionplatform.service.Response;
import com.example.auctionplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginUserController {


    @Autowired
    public LoginUserController(UserService userService) {
        this.userService = userService;
    }
    private final UserService userService;
    @PostMapping("/Login")
    public Response<String> loginUser(@RequestBody UserDTO userDTO) {
        String phone = userDTO.getPhone();
        String password = userDTO.getPassword();
        if(phone.length()!=11){
            return Response.newErrorWithEmptyReturn("Wrong phone number format\n");
        }
        Response<UserDTO> user = userService.getUserByPhoneAndPassword(phone, password);
        if(user.isSuccess()){
            return Response.newSuccess(JWTService.getToken(user.getData(),userService.getThrowTime(),userService.getSecret()),"Login successful");
        }
        return Response.newErrorWithEmptyReturn("Login failed");
    }
}
