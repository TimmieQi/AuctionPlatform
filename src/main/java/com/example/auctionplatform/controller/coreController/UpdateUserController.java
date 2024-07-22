package com.example.auctionplatform.controller.coreController;

import com.example.auctionplatform.dto.UserDTO;
import com.example.auctionplatform.service.JWTService;
import com.example.auctionplatform.service.Response;
import com.example.auctionplatform.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UpdateUserController {
    @Autowired
    public UpdateUserController(UserService userService) {
        this.userService = userService;
    }
    private final UserService userService;
    @PostMapping("/User/update")
    public Response<Void> updateUser(@RequestHeader(name ="Authorization") String token,
                                     HttpServletResponse httpServletResponse,
                                     @RequestBody UserDTO userDTO) {

        try{
            JWTService.parseToken(token,userService.getSecret());
            return userService.updateUser(userDTO);
        }catch (Exception e) {
            httpServletResponse.setStatus(401);
            return Response.newErrorWithEmptyReturn("Not Logged In");
        }
    }
}
