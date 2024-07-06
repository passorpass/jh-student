package com.jinghai.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户权限表
 * @TableName jh_user_role
 */
@TableName(value ="jh_user_role")
@Data
public class JhUserRole implements Serializable {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 用户权限
     */
    @TableField(value = "autho_permissions")
    private String authoPermissions;

    /**
     * 权限角色
     */
    @TableField(value = "autho_role")
    private String authoRole;

    /**
     * 描述
     */
    @TableField(value = "autho_describe")
    private String authoDescribe;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}
