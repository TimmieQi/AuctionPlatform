package com.example.auctionplatform.converter;
import com.example.auctionplatform.dao.User;
import com.example.auctionplatform.dto.UserDTO;


import java.util.ArrayList;
import java.util.List;

public class UserConverter {
    public static User convertUserDTO(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setNickname(userDTO.getNickname());
        user.setPhone(userDTO.getPhone());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setMoney(userDTO.getMoney());
        user.setAdmin(userDTO.getAdmin());
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
    public static List<User> convertUserDTOs(List<UserDTO> UserDTOS) {
        List<User> UserList = new ArrayList<>();
        for (UserDTO UserDTO : UserDTOS) {
            UserList.add(convertUserDTO(UserDTO));
        }
        return UserList;
    }
    public static List<UserDTO> convertUsers(List<User> UserS) {
        List<UserDTO> UserDTOList = new ArrayList<>();
        for (User User : UserS) {
            UserDTOList.add(convertUser(User));
        }
        return UserDTOList;
    }
}
