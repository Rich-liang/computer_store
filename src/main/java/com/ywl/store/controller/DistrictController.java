package com.ywl.store.controller;

import com.ywl.store.entity.District;
import com.ywl.store.service.IDistrictService;
import com.ywl.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("districts")
public class DistrictController extends BaseController{
    @Autowired
    IDistrictService districtService;

    @RequestMapping({"/", ""})
    public JsonResult<List<District>> getByParent(String parent) {
        List<District> data = districtService.getByParent(parent);
        return new JsonResult<>(OK, data);
    }
}
