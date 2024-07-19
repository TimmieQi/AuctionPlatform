package com.example.auctionplatform.service;

import com.example.auctionplatform.converter.UserConverter;
import com.example.auctionplatform.dao.User;
import com.example.auctionplatform.dao.UserRepository;
import com.example.auctionplatform.dto.UserDTO;
import com.example.auctionplatform.logger.LogLevel;
import com.example.auctionplatform.logger.LogManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        String message = "Successfully added new user";
        LogManager.LogUser(LogLevel.INFO, message);
        return message;
    }

    @Override
    public String deleteUserById(int id) {
        Optional<User> tempUser = userRepository.findById(id);
        if(tempUser.isEmpty()) {
            return "User with id \""+id+"\" not found";
        }
        userRepository.delete(tempUser.get());
        String message = "Successfully deleted user with id \""+id+"\"";
        LogManager.LogUser(LogLevel.INFO, message);
        return message;
    }

    @Override
    public UserDTO getUserById(int id) {
        Optional<User> tempUser = userRepository.findById(id);
        return tempUser.map(UserConverter::convertUser).orElse(null);
    }

    @Override
    public List<UserDTO> getAllUser() {
        return UserConverter.convertUsers(userRepository.findAll());
    }


    @Override
    public String updateUser(int id,String nickname,String phone,String password,String email) {
        Optional<User> tempUser = userRepository.findById(id);
        if(tempUser.isEmpty()) {
            return "User with id \""+id+"\" not found";
        }
        String message = "";
        if(!(nickname.isEmpty())) {
            if(!tempUser.get().getNickname().equals(nickname)) {
                tempUser.get().setNickname(nickname);
                message += "Nickname set to \""+nickname+"\"\n";
            }
            message +="Nickname cannot be the same\n";
        }
        if(!phone.isEmpty()) {
            if(!tempUser.get().getPhone().equals(phone)) {
                tempUser.get().setPhone(phone);
                message += "Phone set to \""+phone+"\"\n";
            }
            message +="Phone cannot be the same\n";
        }
        if(!password.isEmpty()) {
            if(!tempUser.get().getPassword().equals(password)) {
                tempUser.get().setPassword(password);
                message += "Password set to \""+password+"\"\n";
            }
            message +="Password cannot be the same\n";
        }
        if(!email.isEmpty()) {
            if(!tempUser.get().getEmail().equals(email)) {
                tempUser.get().setEmail(email);
                message += "Email set to \""+email+"\"\n";
            }
            message +="Email cannot be the same\n";
        }
        userRepository.save(tempUser.get());
        LogManager.LogUser(LogLevel.INFO,message);
        return message;
    }

    @Override
    public String grantAdminById(int id) {
        Optional<User> tempUser = userRepository.findById(id);
        if(tempUser.isEmpty()) {
            return "User with id \""+id+"\" not found\n";
        }
        if(tempUser.get().isAdmin()){
            return "User with id \""+id+"\" is already an admin\n";
        }
        tempUser.get().setAdmin(true);
        userRepository.save(tempUser.get());
        String message = "Successfully granted admin" + "to user with id \""+id+"\"\n";
        LogManager.LogUser(LogLevel.WARN,message);
        return message;
    }


    @Override
    public String revokeAdminById(int id) {
        Optional<User> tempUser = userRepository.findById(id);
        if(tempUser.isEmpty()) {
            return "User with id \""+id+"\" not found\n";
        }
        if(!tempUser.get().isAdmin()){
            return "User with id \""+id+"\" is not an admin\n";
        }
        tempUser.get().setAdmin(false);
        userRepository.save(tempUser.get());
        String message = "Successfully revoked admin" + "to user with id \""+id+"\"\n";
        LogManager.LogUser(LogLevel.WARN,message);
        return message;
    }

    @Transactional
    @Override
    public String raiseMoney(int id, double amount) {
        Optional<User> tempUser = userRepository.findById(id);
        if(tempUser.isEmpty()) {
            return "User with id \""+id+"\" not found\n";
        }
        tempUser.get().setMoney(tempUser.get().getMoney()+amount);
        userRepository.save(tempUser.get());
        String message = "Successfully raised "+amount+" RMB to user with id \""+id+"\"\n";
        LogManager.LogUser(LogLevel.INFO,message);
        return message;
    }

    @Transactional
    @Override
    public String decreaseMoney(int id, double amount) {
        Optional<User> tempUser = userRepository.findById(id);
        if(tempUser.isEmpty()) {
            return "User with id \""+id+"\" not found\n";
        }
        if(tempUser.get().getMoney()-amount<0){
            return "User with id \""+id+"\" is not enough money\n";
        }
        tempUser.get().setMoney(tempUser.get().getMoney()-amount);
        userRepository.save(tempUser.get());
        String message = "Successfully decreased "+amount+" RMB to user with id \""+id+"\"\n";
        LogManager.LogUser(LogLevel.INFO,message);
        return message;
    }
}
