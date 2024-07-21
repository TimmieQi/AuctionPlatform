package com.example.auctionplatform.controller;

import com.example.auctionplatform.dto.UserDTO;
import com.example.auctionplatform.service.FormatCheck;
import com.example.auctionplatform.service.Response;
import com.example.auctionplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api")
public class SignInUserController {
    @Autowired
    public SignInUserController(UserService userService) {
        this.userService = userService;
    }
    private final UserService userService;
    @PostMapping("/signIn")
    public Response<Void> signInUser(@RequestBody UserDTO userDTO){
        //格式检查
        String email = userDTO.getEmail();
        if(!FormatCheck.isValidEmail(email)){
            return Response.newError("Wrong email format\n");
        }
        String telNum = userDTO.getPhone();
        if(telNum.isEmpty()){
            return Response.newError("Please enter the phone number\n");
        }
        if(telNum.length()!=11){
            return Response.newError("Wrong phone number format\n");
        }
        String password = userDTO.getPassword();
        if(password.length() < 8 || password.length() > 20){
            return Response.newError("Wrong password format. Requires an 8 - to 20-digit password.\n");
        }
        String nickname = userDTO.getNickname();
        if(nickname.length() < 2){
            return Response.newError("Wrong nickname format. The name must be at least 2 characters.\n");
        }
        //end格式检查
        UserDTO SignInUserDTO = new UserDTO();
        SignInUserDTO.setNickname(nickname);
        SignInUserDTO.setPhone(telNum);
        SignInUserDTO.setPassword(password);
        SignInUserDTO.setEmail(email);
        return userService.addNewUser(SignInUserDTO);
    }
}
