package com.jinghai.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 老师表
 *
 * @TableName jh_teacher
 */
@TableName(value = "jh_teacher")
@Data
public class JhTeacher implements Serializable {
    /**
     * 老师id
     */
    @TableId(value = "teacher_id", type = IdType.ASSIGN_ID)
    private Long teacherId;

    /**
     * 老师姓名
     */
    @TableField(value = "teacher_name")
    private String teacherName;

    /**
     * 老师性别
     */
    @TableField(value = "teacher_sex")
    private String teacherSex;

    /**
     * 老师年龄
     */
    @TableField(value = "teacher_age")
    private String teacherAge;

    /**
     * 老师地址
     */
    @TableField(value = "teacher_address")
    private String teacherAddress;

    /**
     * 手机号码
     */
    @TableField(value = "teacher_phone")
    private String teacherPhone;

    /**
     * 身份证号码
     */
    @TableField(value = "teacher_idcar")
    private String teacherIdcar;

    /**
     * 就职学校名称
     */
    @TableField(value = "do_work_school")
    private String doWorkSchool;

    /**
     * 老师教龄
     */
    @TableField(value = "teacher_year")
    private String teacherYear;

    /**
     * 职位
     */
    @TableField(value = "teacher_position")
    private String teacherPosition;

    /**
     * 入职时间
     */
    @TableField(value = "entry_school_time")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date entrySchoolTime;
    /**
     * 离职时间
     */
    @TableField(value = "leave_school_time")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date leaveSchoolTime;

    /**
     * 老师状态
     */
    @TableField(value = "teacher_status")
    private Integer teacherStatus;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}
