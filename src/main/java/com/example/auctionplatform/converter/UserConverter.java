package com.example.auctionplatform.converter;

import com.example.auctionplatform.dao.User;
import com.example.auctionplatform.dto.UserDTO;

public class UserConverter {
    public static User convertUserDTO(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setNickname(userDTO.getNickname());
        user.setPhone(userDTO.getPhone());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setMoney(userDTO.getMoney());
        user.setAdmin(userDTO.isAdmin());
        return user;
    }
    public static UserDTO convertUser(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setNickname(user.getNickname());
        userDTO.setPhone(user.getPhone());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setMoney(user.getMoney());
        userDTO.setAdmin(user.isAdmin());
        return userDTO;
    }
}
