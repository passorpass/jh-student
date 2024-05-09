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
 * 管理员登录表
 * @TableName jh_admin
 */
@TableName(value ="jh_admin")
@Data
@Accessors(chain = true)
public class JhAdmin implements Serializable {
    /**
     * 管理员编号
     */
    @TableId(value = "admin_id",type = IdType.ASSIGN_ID)
    private Long adminId;

    /**
     * 登录账号
     */
    @TableField(value = "admin_username")
    private String adminUsername;

    /**
     * 登录密码
     */
    @TableField(value = "admin_password")
    private String adminPassword;

    /**
     * 管理员头像
     */
    @TableField(value = "admin_avatar")
    private String adminAvatar;

    /**
     * 创建时间
     */
    @TableField(value = "admin_create_time")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date adminCreateTime;

    /**
     * 角色描述
     */
    @TableField(value = "admin_describe")
    private String adminDescribe;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}
