package com.ywl.store.service.impl;

import com.ywl.store.entity.User;
import com.ywl.store.mapper.UserMapper;
import com.ywl.store.service.IUserService;
import com.ywl.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) {
        String username = user.getUsername();
        //判断用户是否被注册过
        User result = userMapper.findByUsername(username);
        if (result != null){
            throw new UsernameDuplicatedException("用户名占用");
        }
        //加盐 + md5加密
        String oldPassword = user.getPassword();
        String salt = UUID.randomUUID().toString().toUpperCase();
        String md5Password = getMD5Password(oldPassword, salt);
        user.setPassword(md5Password);
        //补全数据
        user.setSalt(salt);
        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);
        //注册逻辑
        Integer rows = userMapper.insert(user);
        if (rows != 1){
            throw new InsertException("用户注册未知异常");
        }
    }

    @Override
    public User login(String username, String password) {
        //查询用户是否存在
        User result = userMapper.findByUsername(username);
        if (result == null){
            throw new UserNotFoundException(username);
        }

        //查询密码是否正确
        //获取加密后的密码
        String oldPassword = result.getPassword();
        //获取盐值
        String salt = result.getSalt();
        //按规则加密
        String newMd5Password = getMD5Password(password, salt);
        //密码比较
        if (!newMd5Password.equals(oldPassword)){
            throw new PasswordNotMatchException("用户密码错误");
        }

        //判断用户是否注销
        if (result.getIsDelete() == 1){
            throw new UserNotFoundException("用户已注销");
        }

        //返回需要的数据
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());
        return user;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1){
            throw new UserNotFoundException("用户不存在");
        }
        //密码校验
        String oldMd5Password = getMD5Password(oldPassword, result.getSalt());
        if (!result.getPassword().equals(oldMd5Password)){
            throw new PasswordNotMatchException("密码错误");
        }
        //更改密码
        String newMd5Password = getMD5Password(newPassword, result.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid, newMd5Password, username, new Date());
        if (rows != 1){
            throw new UpdateException("更改密码未知异常");
        }
    }

    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1){
            throw new UserNotFoundException("用户数据不存在");
        }
        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());
        return user;
    }

    @Override
    public void changeInfo(Integer uid, String username, User user) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1){
            throw new UserNotFoundException("用户数据不存在");
        }
        user.setUid(uid);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());
        Integer rows = userMapper.updateInfoByUid(user);
        if (rows != 1){
            throw new UpdateException("更新数据未知异常");
        }
    }

    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1){
            throw new UserNotFoundException("用户数据不存在");
        }
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, new Date());
        if (rows != 1){
            throw new UpdateException("更新用户头像产生未知异常");
        }
    }

    //MD5
    private String getMD5Password(String password, String salt){
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }
}