package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2024-01-11 07:09:07
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult selectUserPage(User user, Integer pageNum, Integer pageSize);

    boolean checkEmailUnique(User user);

    ResponseResult addUser(User user);

    boolean checkPhoneUnique(User user);

    boolean checkUserNameUnique(String userName);

    //修改用户-②更新用户信息
    void updateUser(User user);

}
