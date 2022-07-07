package com.ywl.store.mapper;

import com.ywl.store.entity.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddressMapperTest {
    @Autowired
    AddressMapper addressMapper;

    @Test
    void insert() {
        Address address = new Address();
        address.setUid(18);
        address.setName("admin");
        address.setPhone("123");
        address.setAddress("hrb");
        Integer rows = addressMapper.insert(address);
        System.out.println("rows=" + rows);
    }

    @Test
    void countByUid() {
        Integer uid = 18;
        Integer count = addressMapper.countByUid(uid);
        System.out.println("count=" + count);
    }
}