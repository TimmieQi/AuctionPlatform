package com.example.auctionplatform.service;

import com.example.auctionplatform.controller.SignInUserController;
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
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    private final UserRepository userRepository;
    @Override
    public Response<Void> addNewUser(UserDTO newUser) {
        try {
            User tempUser = userRepository.findByEmail(newUser.getEmail());
            if (tempUser != null) {
                return Response.newError("User that uses email \"" + newUser.getEmail() + "\" already exists\n");
            }
            tempUser = userRepository.findByPhone(newUser.getPhone());
            if (tempUser != null) {
                return Response.newError("User that uses phone number \"" + newUser.getPhone() + "\" already exists\n");
            }
            tempUser = UserConverter.convertUserDTO(newUser);
            tempUser.setMoney(0f);//初始0金钱
            userRepository.save(tempUser);//确认是新的用户，存入
            String message = "Successfully added new user";
            LogManager.LogUser(LogLevel.INFO, message);
            return Response.newSuccess(null, message);
        }catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogUser(LogLevel.ERROR, e.getMessage()+"An error occurred while adding new user\n");
            return Response.newError("An error occurred while adding new user\n");
        }
    }

    @Override
    public Response<Void> deleteUserById(int id) {
        try {
            Optional<User> tempUser = userRepository.findById(id);
            if (tempUser.isEmpty()) {
                return Response.newError("User with id \"" + id + "\" not found");
            }
            userRepository.delete(tempUser.get());
            String message = "Successfully deleted user with id \"" + id + "\"";
            LogManager.LogUser(LogLevel.INFO, message);
            return Response.newSuccess(null, message);
        }catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogUser(LogLevel.ERROR, e.getMessage() + "An error occurred while deleting user with id \"" + id + "\"\n");
            return Response.newError("An error occurred while deleting user with id \"" + id + "\"\n");
        }
    }

    @Override
    public Response<UserDTO> getUserById(int id) {
        try {
            Optional<User> tempUser = userRepository.findById(id);
            return tempUser.map(user -> Response.newSuccess(UserConverter.convertUser(user),
                    "User with id \"" + id + "\" found"))
                    .orElseGet(() -> Response.newErrorWithEmptyReturn("User with id \"" + id + "\" not found"));
        }catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogUser(LogLevel.ERROR,e.getMessage()+"An error occurred while getting user with id \"" + id + "\"\n");
            return Response.newErrorWithEmptyReturn("An error occurred while getting user with id \"" + id + "\"\n");
        }
    }

    @Override
    public Response<List<UserDTO>> getAllUser() {
        try {
            List<User> tempUser = userRepository.findAll();
            if (tempUser.isEmpty()) {
                return Response.newErrorWithEmptyReturn("User list is empty");
            }
            return Response.newSuccess(UserConverter.convertUsers(tempUser), "User list found");
        }catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogUser(LogLevel.ERROR,e.getMessage()+"An error occurred while getting user list\n");
            return Response.newErrorWithEmptyReturn(e.getMessage());
        }
    }


    @Override
    public Response<Void> updateUser(int id,String nickname,String phone,String password,String email) {
        try {
            Optional<User> tempUser = userRepository.findById(id);
            if (tempUser.isEmpty()) {
                return Response.newError("User with id \"" + id + "\" not found");
            }
            String message = "";
            if (!(nickname.isEmpty())) {
                if (!nickname.equals(tempUser.get().getNickname())) {
                    tempUser.get().setNickname(nickname);
                    message += "Nickname set to \"" + nickname + "\"\n";
                } else {
                    message += "Nickname cannot be the same\n";
                }
            }
            if (!phone.isEmpty()) {
                if (!tempUser.get().getPhone().equals(phone)) {
                    User otherUser = userRepository.findByPhone(phone);
                    if (otherUser != null) {
                        message += "Phone number already in use\n";
                    }
                    else {
                        tempUser.get().setPhone(phone);
                        message += "Phone set to \"" + phone + "\"\n";
                    }
                } else {
                    message += "Phone cannot be the same\n";
                }
            }
            if (!password.isEmpty()) {
                if (!password.equals(tempUser.get().getPassword())) {
                    tempUser.get().setPassword(password);
                    message += "Password set to \"" + password + "\"\n";
                } else {
                    message += "Password cannot be the same\n";
                }
            }
            if (!email.isEmpty()) {
                if (!email.equals(tempUser.get().getEmail())) {
                    User otherUser = userRepository.findByEmail(email);
                    if (otherUser != null) {
                        message += "Email already in use\n";
                    }else {
                        tempUser.get().setEmail(email);
                        message += "Email set to \"" + email + "\"\n";
                    }
                } else {
                    message += "Email cannot be the same\n";
                }
            }
            userRepository.save(tempUser.get());
            LogManager.LogUser(LogLevel.INFO, message);
            return Response.newSuccess(null, message);
        }catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogUser(LogLevel.ERROR,e.getMessage()+"An error occurred while updating user\n");
            return Response.newError("An error occurred while updating user\n");
        }
    }

    @Override
    public Response<Void> grantAdminById(int id) {
        try {
            Optional<User> tempUser = userRepository.findById(id);
            if (tempUser.isEmpty()) {
                return Response.newError("User with id \"" + id + "\" not found\n");
            }
            if (tempUser.get().isAdmin()) {
                return Response.newError("User with id \"" + id + "\" is already an admin\n");
            }
            tempUser.get().setAdmin(true);
            userRepository.save(tempUser.get());
            String message = "Successfully granted admin" + "to user with id \"" + id + "\"\n";
            LogManager.LogUser(LogLevel.WARN, message);
            return Response.newSuccess(null, message);
        }catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogUser(LogLevel.ERROR,e.getMessage()+"An error occurred while granting admin\n");
            return Response.newError("An error occurred while granting admin\n");
        }
    }


    @Override
    public Response<Void> revokeAdminById(int id) {
        try {
            Optional<User> tempUser = userRepository.findById(id);
            if (tempUser.isEmpty()) {
                return Response.newError("User with id \"" + id + "\" not found\n");
            }
            if (!tempUser.get().isAdmin()) {
                return Response.newError("User with id \"" + id + "\" is not an admin\n");
            }
            tempUser.get().setAdmin(false);
            userRepository.save(tempUser.get());
            String message = "Successfully revoked admin" + "to user with id \"" + id + "\"\n";
            LogManager.LogUser(LogLevel.WARN, message);
            return Response.newSuccess(null, message);
        }catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogUser(LogLevel.ERROR,e.getMessage()+"An error occurred while revoking admin\n");
            return Response.newError("An error occurred while revoking admin\n");
        }
    }

    @Transactional
    @Override
    public Response<Void> raiseMoney(int id, double amount) {
        try {
            Optional<User> tempUser = userRepository.findById(id);
            if (tempUser.isEmpty()) {
                return Response.newError("User with id \"" + id + "\" not found\n");
            }
            tempUser.get().setMoney(tempUser.get().getMoney() + amount);
            userRepository.save(tempUser.get());
            String message = "Successfully raised " + amount + " RMB to user with id \"" + id + "\"\n";
            LogManager.LogUser(LogLevel.INFO, message);
            return Response.newSuccess(null, message);
        }catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogUser(LogLevel.ERROR,e.getMessage()+"An error occurred while raising user\n");
            return Response.newError("An error occurred while raising user\n");
        }
    }

    @Transactional
    @Override
    public Response<Void> decreaseMoney(int id, double amount) {
        try {
            Optional<User> tempUser = userRepository.findById(id);
            if (tempUser.isEmpty()) {
                return Response.newError("User with id \"" + id + "\" not found\n");
            }
            if (tempUser.get().getMoney() - amount < 0) {
                return Response.newError("User with id \"" + id + "\" is not enough money\n");
            }
            tempUser.get().setMoney(tempUser.get().getMoney() - amount);
            userRepository.save(tempUser.get());
            String message = "Successfully decreased " + amount + " RMB to user with id \"" + id + "\"\n";
            LogManager.LogUser(LogLevel.INFO, message);
            return Response.newSuccess(null, message);
        }catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogUser(LogLevel.ERROR,e.getMessage()+"An error occurred while decreasing user\n");
            return Response.newError("An error occurred while decreasing user\n");
        }
    }

    @Override
    public Response<UserDTO> getUserByPhoneAndPassword(String phone, String password) {
        try{
            User optionalUser = userRepository.findByPhone(phone);
            if (optionalUser == null) {
                return Response.newErrorWithEmptyReturn("User with phone \"" + phone + "\" not found\n");
            }
            if (optionalUser.getPassword().equals(password)) { //不需要返回密码给前端
                UserDTO userDTO = new UserDTO();
                userDTO.setPhone(phone);
                userDTO.setId(optionalUser.getId());
                userDTO.setMoney(optionalUser.getMoney());
                userDTO.setAdmin(optionalUser.isAdmin());
                userDTO.setNickname(optionalUser.getNickname());
                userDTO.setEmail(optionalUser.getEmail());
                return Response.newSuccess(userDTO, "Successfully login \"" + optionalUser.getId() + "\"\n");
            }
            return Response.newErrorWithEmptyReturn("Wrong password\n");
        }
        catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogUser(LogLevel.ERROR,e.getMessage()+"An error occurred while getting user\n");
            return Response.newErrorWithEmptyReturn("An error occurred while getting user\n");
        }
    }
}
