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
@CrossOrigin
public class GetUserByTokenController {
    @Autowired
    public GetUserByTokenController(UserService userService) {
        this.userService = userService;
    }
   private final UserService userService;
    @GetMapping("/Get/User")
    public Response<UserDTO> getUserByToken(@RequestHeader(name ="Authorization") String token,
                                                      HttpServletResponse httpServletResponse) {
        try{
            Map<String,Object> map = JWTService.parseToken(token,userService.getSecret());
            Response<UserDTO> response = userService.getUserById((int)map.get("userId"));
            if(!response.isSuccess()){
                return response;
            }
            response.getData().setPassword(null);
            return response;
        }catch (Exception e) {
            httpServletResponse.setStatus(401);
            return Response.newErrorWithEmptyReturn("Not Logged In");
        }
    }
}
