package com.jinghai.system.mapper;

import com.jinghai.system.domain.entity.JhTeacher;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author lenovo
* @description 针对表【jh_teacher(老师表)】的数据库操作Mapper
* @createDate 2024-05-11 21:11:21
* @Entity system.domain.entity.JhTeacher
*/
@Mapper
public interface JhTeacherMapper extends BaseMapper<JhTeacher> {

}




