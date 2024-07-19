package com.example.auctionplatform.service;

import com.example.auctionplatform.converter.UserConverter;
import com.example.auctionplatform.dao.User;
import com.example.auctionplatform.dao.UserRepository;
import com.example.auctionplatform.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    private final UserRepository userRepository;
    @Override
    public String addNewUser(UserDTO newUser) {
        User tempUser = userRepository.findByEmail(newUser.getEmail());
        if (tempUser != null) {
            return "User that uses email \""+newUser.getEmail()+"\" already exists";
        }
        tempUser = userRepository.findByPhone(newUser.getPhone());
        if (tempUser != null) {
            return "User that uses phone number \""+newUser.getPhone()+"\" already exists";
        }
        tempUser = UserConverter.convertUserDTO(newUser);
        userRepository.save(tempUser);//确认是新的用户，存入
        return "Successfully added new user";
    }

    @Override
    public String deleteUserById(int id) {
        Optional<User> tempUser = userRepository.findById(id);
        if(tempUser.isPresent()) {
            userRepository.delete(tempUser.get());
            return "Successfully deleted user with id \""+id+"\"";
        }
        return "User with id \""+id+"\" not found";
    }

    @Override
    public UserDTO getUserById(int id) {
        Optional<User> tempUser = userRepository.findById(id);
        return tempUser.map(UserConverter::convertUser).orElse(null);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public String updateUser(int id,String nickname,String phone,String password,String email) {
        Optional<User> tempUser = userRepository.findById(id);
        if(tempUser.isEmpty()) {
            return "User with id \""+id+"\" not found";
        }
        String message = "";
        if(!nickname.isEmpty()) {
            tempUser.get().setNickname(nickname);
            message += "Nickname set to \""+nickname+"\"";
        }
        if(!phone.isEmpty()) {
            tempUser.get().setPhone(phone);
            message += "Phone set to \""+phone+"\"";
        }
        if(!password.isEmpty()) {
            tempUser.get().setPassword(password);
        }
        if(!email.isEmpty()) {
            tempUser.get().setEmail(email);
        }
        userRepository.save(tempUser.get());
        return "";
    }

    @Override
    public String grantAdminById(int id) {
        return "";
    }

    @Override
    public String revokeAdminById(int id) {
        return "";
    }
}
