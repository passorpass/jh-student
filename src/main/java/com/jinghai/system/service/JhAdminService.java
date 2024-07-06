package com.jinghai.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinghai.system.domain.entity.JhUser;
import com.jinghai.system.domain.vo.LoginVo;

/**
* @author lenovo
* @description 针对表【jh_admin(管理员登录表)】的数据库操作Service
* @createDate 2024-05-05 09:30:01
*/
public interface JhAdminService extends IService<JhUser> {

    LoginVo login(String username, String password);

}
