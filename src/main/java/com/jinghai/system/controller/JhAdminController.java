package com.jinghai.system.controller;


import com.jinghai.common.context.BaseContext;
import com.jinghai.common.util.Result;
import com.jinghai.framework.shiro.JwtToken;
import com.jinghai.system.domain.dto.LoginDto;

import com.jinghai.system.domain.vo.LoginVo;
import com.jinghai.system.service.JhAdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.security.auth.Subject;

import static com.jinghai.common.properties.SufferProperty.ENCRYSUFFER;

@RestController
@RequestMapping("/jhs")
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
        LoginVo login = adminService.login(loginDto.getUsername(), loginDto.getPassword());
        return Result.ok(login);
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
