package com.example.auctionplatform.service;
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
    Response<Void> addNewUser(UserDTO newUser);

    /**
     *删除对应id的用户
     * @param id
     * 要删除的用户的id
     * @return
     * 返回成功或者失败消息
     */
    Response<Void> deleteUserById(int id);

    /**
     * 找到对应id
     * @return
     * 如果存在，返回对应用户
     */
    Response<UserDTO> getUserById(int id);

    /**
     * 返回一个List，包含所有的User，数据量大请慎用
     */
    Response<List<UserDTO>> getAllUser();

    /**
     * 修改对应id的用户的字段，如果对应字段为空或与原来相同，则不修改,并将信息写入日志中
     */
    Response<Void> updateUser(int id,String nickname,String phone,String password,String email);

    /**
     * 提升某个用户的权限到管理员，并将信息写入日志中
     */
    Response<Void> grantAdminById(int id);
    /**
     * 收回某个用户的权限的管理员，并将信息写入日志中
     */
    Response<Void> revokeAdminById(int id);

    /**
     * 给对应id账户提供数值为amount的金钱（单位：RMB）
     */
    Response<Void> raiseMoney(int id, double amount);

    /**
     * 给对应id的账户扣除数值为amount的金钱（单位：RMB）
     */
    Response<Void> decreaseMoney(int id, double amount);
}
