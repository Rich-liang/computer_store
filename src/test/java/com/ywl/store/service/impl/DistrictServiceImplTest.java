package com.ywl.store.service.impl;

import com.ywl.store.entity.District;
import com.ywl.store.service.IDistrictService;
import com.ywl.store.service.ex.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DistrictServiceImplTest {
    @Autowired
    IDistrictService districtService;

    @Test
    void getByParent() {
        String parent = "86";
        List<District> list = districtService.getByParent(parent);
        System.out.println("count=" + list.size());
        for (District item : list) {
            System.out.println(item);
        }
    }
}