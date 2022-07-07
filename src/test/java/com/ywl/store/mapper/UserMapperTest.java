package com.ywl.store.mapper;

import com.ywl.store.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void insert() {
        User user = new User();
        user.setUsername("qwe");
        user.setPassword("123");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }

    @Test
    void findByUsername() {
        User qwe = userMapper.findByUsername("qwe");
        System.out.println(qwe);
    }

    @Test
    void updatePasswordByUid(){
        userMapper.updatePasswordByUid(8, "123", "管理员", new Date());
    }

    @Test
    void findByUid(){
        User uid = userMapper.findByUid(8);
        System.out.println(uid);
    }

    @Test
    void updateInfoByUid(){
        User user = new User();
        user.setUid(7);
        user.setPhone("123456");
        user.setEmail("123@qq.com");
        user.setGender(1);
        userMapper.updateInfoByUid(user);
    }

    @Test
    void updateAvatarByUid(){
        userMapper.updateAvatarByUid(8, "/upload/avatar.png", "管理员", new Date());
    }
}