package com.ywl.store.service.impl;

import com.ywl.store.entity.User;
import com.ywl.store.mapper.UserMapper;
import com.ywl.store.service.IUserService;
import com.ywl.store.service.ex.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private IUserService userService;


    @Test
    void reg() {
        try {
            User user = new User();
            user.setUsername("333");
            user.setPassword("333");
            userService.reg(user);
            System.out.println("ok");
        } catch (ServiceException e) {
            //类对象.名称
            System.out.println(e.getClass().getSimpleName());
            //异常信息
            System.out.println(e.getMessage());
        }
    }

    @Test
    void login(){
        User user = userService.login("444", "444");
        System.out.println(user);
    }

    @Test
    void changePassword(){
        userService.changePassword(7, "444", "123", "444");
    }

    @Test
    void getByUid(){
        System.out.println(userService.getByUid(7));
    }

    @Test
    void changeInfo(){
        User user = new User();
        user.setPhone("12345");
        user.setEmail("123@qq.com");
        user.setGender(0);
        userService.changeInfo(7, "管理员", user);
    }

    @Test
    void changeAvatarByUid(){
        userService.changeAvatar(8, "/upload/test.png", "test");
    }
}