package com.example.auctionplatform.service;
import com.example.auctionplatform.dao.User;
import com.example.auctionplatform.dto.UserDTO;


import java.util.List;

public interface UserService {

    /**
     *新增一个用户，用户的nickname和phone number不能相同
     * @param newUser
     * 这个参数是新的用户
     * @return
     * 返回成功或者失败消息
     */
    String addNewUser(UserDTO newUser);

    /**
     *删除对应id的用户
     * @param id
     * 要删除的用户的id
     * @return
     * 返回成功或者失败消息
     */
    String deleteUserById(int id);

    /**
     * 找到对应id
     * @return
     * 如果存在，返回对应用户
     */
    User getUserById(int id);

    /**
     * 返回一个List，包含所有的User，数据量大请慎用
     */
    List<User> getAllUser();

    String updateUser(UserDTO userDTO);
}
