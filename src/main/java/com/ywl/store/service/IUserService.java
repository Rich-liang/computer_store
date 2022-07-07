package com.ywl.store.service;

import com.ywl.store.entity.User;

public interface IUserService {
    /***
     * 用户注册方法
     * @param user
     */
    void reg(User user);

    /***
     *用户登录功能
     * @param username
     * @param password
     * @return 用户数据
     */
    User login(String username, String password);

    /***
     * 修改密码
     * @param uid
     * @param username
     * @param oldPassword
     * @param newPassword
     */
    void changePassword(Integer uid, String username, String oldPassword, String newPassword);

    /***
     * 查询用户数据
     * @param uid
     * @return
     */
    User getByUid(Integer uid);

    /***
     * 更新用户数据
     * @param uid
     * @param username
     * @param user
     */
    void changeInfo(Integer uid, String username, User user);

    /***
     * 修改用户头像
     * @param uid
     * @param avatar
     * @param username
     */
    void changeAvatar(Integer uid, String avatar, String username);
}
