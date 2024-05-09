package com.jinghai.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 京海市学生信息表
 * @TableName jh_student_info
 */
@TableName(value ="jh_student_info")
@Data
@Accessors(chain = true)
public class JhStudentInfo implements Serializable {
    /**
     * 学生编号
     */
    @TableId(value = "student_id", type = IdType.ASSIGN_ID)
    private Long studentId;

    /**
     * 学生姓名
     */
    @TableField(value = "student_name")
    private String studentName;

    /**
     * 学生年龄
     */
    @TableField(value = "student_age")
    private String studentAge;

    /**
     * 学生性别
     */
    @TableField(value = "student_sex")
    private String studentSex;

    /**
     * 学生住址
     */
    @TableField(value = "student_address")
    private String studentAddress;

    /**
     * 学生电话号码
     */
    @TableField(value = "student_phone")
    private String studentPhone;

    /**
     * 入学时间
     */
    @TableField(value = "student_begin_school")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date studentBeginSchool;

    /**
     * 就读学校
     */
    @TableField(value = "student_has_school")
    private String studentHasSchool;

    /**
     * 学生身份证信息
     */
    @TableField(value = "student_idcard")
    private String studentIdcard;

    /**
     * 班主任名称
     */
    @TableField(value = "student_leader_techer")
    private String studentLeaderTecher;

    /**
     * 社会面貌
     */
    @TableField(value = "student_social_presence")
    private String studentSocialPresence;

    /**
     * 学生头像
     */
    @TableField(value = "student_avatar")
    private String studentAvatar;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}
