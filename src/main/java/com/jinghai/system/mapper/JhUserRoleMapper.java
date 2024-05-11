package com.jinghai.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jinghai.system.domain.entity.JhUserRole;
import org.apache.ibatis.annotations.Mapper;

/**
* @author lenovo
* @description 针对表【jh_user_role(用户权限表)】的数据库操作Mapper
* @createDate 2024-05-11 17:07:31
* @Entity system.domain.entity.JhUserRole
*/
@Mapper
public interface JhUserRoleMapper extends BaseMapper<JhUserRole> {

}




