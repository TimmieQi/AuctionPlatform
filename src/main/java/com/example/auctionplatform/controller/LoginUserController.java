package com.example.auctionplatform.controller;

import com.example.auctionplatform.dto.UserDTO;
import com.example.auctionplatform.service.Response;
import com.example.auctionplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Response<UserDTO> loginUser(@RequestBody UserDTO userDTO) {
        String phone = userDTO.getPhone();
        String password = userDTO.getPassword();
        if(phone.length()!=11){
            return Response.newErrorWithEmptyReturn("Wrong phone number format\n");
        }
        return userService.getUserByPhoneAndPassword(phone, password);
    }

}
