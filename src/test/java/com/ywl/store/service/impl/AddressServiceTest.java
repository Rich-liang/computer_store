package com.ywl.store.service.impl;

import com.ywl.store.entity.Address;
import com.ywl.store.service.IAddressService;
import com.ywl.store.service.ex.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddressServiceTest {
    @Autowired
    IAddressService addressService;

    @Test
    void addNewAddress() {
        try {
            Integer uid = 20;
            String username = "管理员";
            Address address = new Address();
            address.setName("张三");
            address.setPhone("123");
            address.setAddress("hrb");
            addressService.addNewAddress(uid, username, address);
            System.out.println("OK.");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }
}