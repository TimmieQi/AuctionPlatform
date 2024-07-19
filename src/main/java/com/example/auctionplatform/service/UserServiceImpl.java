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
    public User getUserById(int id) {
        Optional<User> tempUser = userRepository.findById(id);
        return tempUser.orElse(null);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public String updateUser(UserDTO userDTO) {
        return "";
    }
}
