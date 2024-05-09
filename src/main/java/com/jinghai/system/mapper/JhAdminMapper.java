package com.jinghai.system.mapper;

import com.jinghai.system.domain.entity.JhAdmin;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author lenovo
* @description 针对表【jh_admin(管理员登录表)】的数据库操作Mapper
* @createDate 2024-05-05 09:30:01
* @Entity system.domain.entity.JhAdmin
*/
@Mapper
public interface JhAdminMapper extends BaseMapper<JhAdmin> {

}




