package com.jinghai.system.controller;


import com.jinghai.common.context.BaseContext;
import com.jinghai.common.util.Result;
import com.jinghai.system.domain.dto.LoginDto;

import com.jinghai.system.service.JhAdminService;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
public class JhAdminController {

    @Resource
    private JhAdminService adminService;

    /**
     * 登录
     * @param loginDto
     * @return
     */
    @PostMapping("/login")
    public Result adminLogin(@RequestBody LoginDto loginDto){
        Result loginToken = adminService.login(loginDto.getUsername(), loginDto.getPassword());
        return loginToken;
    }

    /**
     * 退出
     * @return
     */
    @PostMapping("/logout")
    public Result adminlogout(){
        SecurityUtils.getSubject().logout();
        BaseContext.removeCurrentId();
        return Result.ok();
    }
}
