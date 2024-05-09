package com.jinghai.system.mapper;

import com.jinghai.system.domain.entity.JhSchoolSession;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author lenovo
* @description 针对表【jh_school_session(学年表)】的数据库操作Mapper
* @createDate 2024-05-05 09:19:56
* @Entity system.domain.entity.JhSchoolSession
*/
@Mapper
public interface JhSchoolSessionMapper extends BaseMapper<JhSchoolSession> {

}




