package com.ywl.store.mapper;

import com.ywl.store.entity.User;

import java.util.Date;

public interface UserMapper {
    /**
     * 插入用户的数据
     * @param user
     * @return 受影响行数
     * */
    Integer insert(User user);

    /**
     * 根据用户名查询用户数据
     * @param username 用户名
     * @return user
     * */
    User findByUsername(String username);

    /***
     *
     * @param uid
     * @param password
     * @param modifiedUser
     * @param modifiedTime
     * @return
     */
    Integer updatePasswordByUid(Integer uid, String password, String modifiedUser, Date modifiedTime);

    /***
     * 根据id查询user
     * @param uid
     * @return
     */
    User findByUid(Integer uid);

    /***
     * 更新用户数据
     * @param user
     * @return
     */
    Integer updateInfoByUid(User user);

    /***
     * 修改用户头像
     * @param uid
     * @param avatar
     * @param modifiedUser
     * @param modifiedTime
     * @return
     */
    Integer updateAvatarByUid(Integer uid, String avatar, String modifiedUser, Date modifiedTime);
}
