package com.jinghai.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinghai.common.util.Result;
import com.jinghai.system.domain.entity.JhAdmin;

/**
* @author lenovo
* @description 针对表【jh_admin(管理员登录表)】的数据库操作Service
* @createDate 2024-05-05 09:30:01
*/
public interface JhAdminService extends IService<JhAdmin> {

    Result login(String username, String password);

}
